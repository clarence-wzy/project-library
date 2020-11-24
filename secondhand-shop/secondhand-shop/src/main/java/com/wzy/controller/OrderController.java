package com.wzy.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.wzy.config.AlipayConfig;
import com.wzy.entity.ChildOrder;
import com.wzy.entity.MasterOrder;
import com.wzy.entity.OrderSelect;
import com.wzy.entity.Product;
import com.wzy.service.*;
import com.wzy.util.CodeUtil;
import com.wzy.util.JSONResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Package: com.wzy.controller
 * @Author: Clarence1
 * @Date: 2020/1/7 17:21
 */
@RestController
@RequestMapping(value = "/order")
@CrossOrigin
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShopCartService shopCartService;

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "根据用户id查询订单信息")
    @GetMapping(value = "/findOrderByUserId/{user_id}")
    public JSONResult findOrderByUserId(@PathVariable("user_id") int userId, String childOrderStatus) {
        log.info("==============查询【" + userId + "】的订单信息==============");
        if (childOrderStatus.equals("全部") || childOrderStatus.equals("")) {
            childOrderStatus = null;
        }
        List<MasterOrder> masterOrderList = orderService.findMasterOrderByUserId(userId);
        for (MasterOrder masterOrder : masterOrderList) {
            List<ChildOrder> childOrderList = orderService.findChildOrderByMasterOrderId(masterOrder.getMasterOrderId(), childOrderStatus);
            for (ChildOrder childOrder : childOrderList) {
                childOrder.setProduct(productService.findProductByProductId(childOrder.getProductId()));
            }
            masterOrder.setChildOrderList(childOrderList);
        }
        return JSONResult.ok(masterOrderList);
    }

    @ApiOperation(value = "根据子订单表id查询商品信息")
    @GetMapping(value = "/findProductById/{id}")
    public JSONResult findProductById(@PathVariable("id") int id) {
        log.info("==============查询子订单表【" + id + "】的订单以及商品信息==============");
        ChildOrder childOrder = orderService.findChildOrderById(id);
        childOrder.setProduct(productService.findProductByProductId(childOrder.getProductId()));
        return JSONResult.ok(childOrder);
    }

    @ApiOperation(value = "插入订单信息")
    @PostMapping(value = "/insertOrder/{user_id}/{address_id}/{shop_cart_id}")
    public JSONResult insertOrder(@RequestBody List<ChildOrder> childOrders, @PathVariable("user_id") int userId,
                                  @PathVariable("address_id") int addressId, @PathVariable(value = "shop_cart_id") List<Integer> shopCartIdList) {
        log.info("==============插入【" + userId + "和" + addressId + "】的订单信息" + childOrders + "==============");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String masterOrderId = sdf.format(date) + CodeUtil.generateVerifyCode(4);
        MasterOrder masterOrder = new MasterOrder();
        masterOrder.setMasterOrderId(masterOrderId);
        masterOrder.setUserId(userId);
        masterOrder.setAddressId(addressId);
        masterOrder.setMasterOrderStatus("待支付");

        double totalPrice = 0.00;
        String productName = "";
        for (ChildOrder childOrder : childOrders) {
            totalPrice = totalPrice + childOrder.getPrice() * childOrder.getNumber();
            if (productName == "") {
                productName = productName + childOrder.getProduct().getProductName();
            } else {
                productName = productName + "、" + childOrder.getProduct().getProductName();
            }

            childOrder.setMasterOrderId(masterOrderId);
            childOrder.setChildOrderId(sdf.format(date) + CodeUtil.generateVerifyCode(6));
            childOrder.setChildOrderStatus("待支付");
            if (orderService.insertChildOrder(childOrder) == 1) {
                log.info("插入订单成功");
            } else {
                return JSONResult.errorMsg("插入子订单失败");
            }

            int differNumber = childOrder.getProduct().getNumber() - childOrder.getNumber();
            if (differNumber > 0) {
                productService.updateProductNumber(differNumber, "ENABLE", childOrder.getProductId());
            } else {
                productService.updateProductNumber(differNumber, "SoldOut", childOrder.getProductId());
            }
        }

        masterOrder.setPrice(totalPrice);
        int i = orderService.insertMasterOrder(masterOrder);
        if (i == 1) {
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID, AlipayConfig.RSA_PRIVATE_KEY,
                    AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY);
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            //支付成功后跳转的地址
            request.setReturnUrl(AlipayConfig.return_url + "?masterOrderId=" + masterOrderId + "&&paystatus=true&&shopCartIdList=" + shopCartIdList);
            //异步通知地址paystatus
            request.setNotifyUrl(AlipayConfig.notify_url);

            productName = productName.substring(0, 20) + "...";
            // 商户订单号masterOrderId ，商户网站订单系统中唯一订单号
            request.setBizContent("{\"out_trade_no\":\""+ masterOrderId +"\","
                    + "\"total_amount\":\""+ totalPrice +"\","
                    + "\"subject\":\""+ productName +"\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            String form;
            try {
                form = alipayClient.pageExecute(request).getBody();
                return JSONResult.ok(form);
            } catch (AlipayApiException e) {
                e.printStackTrace();
                return JSONResult.errorMsg(e.getErrMsg());
            }
        } else {
            log.error("插入主订单失败");
            return JSONResult.errorMsg("插入主订单失败");
        }
    }

    @ApiOperation(value = "支付成功更新相关状态")
    @PostMapping(value = "/updateStatus/{master_order_id}/{order_status}/{shop_cart_id}")
    public JSONResult updateStatus(@PathVariable("master_order_id") String masterOrderId, @PathVariable("order_status") String orderStatus,
                                        @PathVariable("shop_cart_id") List<Integer> shopCartIdList) {
        log.info("==============支付成功更新状态【" + masterOrderId + "和" + orderStatus + "和" + shopCartIdList + "】的信息==============");
        if (orderStatus.equals("paid")) {orderStatus = "已支付";}
        int i = orderService.updateMasterOrderStatus(masterOrderId, orderStatus);
        if (i != 1) {
            return JSONResult.errorMsg("更新主订单状态失败");
        }
        orderService.updateChildOrderStatus(masterOrderId, orderStatus);
        for(int k : shopCartIdList) {
            int g = shopCartService.updateShopCartStatusById("Paid", k);
            if (g != 1) {
                return JSONResult.errorMsg("更新购物车状态失败");
            }
        }
        return JSONResult.ok("支付成功更新状态成功");
    }

    @ApiOperation(value = "前往支付")
    @PostMapping(value = "/toPay/{id}")
    public JSONResult toPay(@PathVariable("id") int id) {
        log.info("==============前往支付【" + id + "】的子订单==============");
        ChildOrder childOrder = orderService.findChildOrderById(id);
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID, AlipayConfig.RSA_PRIVATE_KEY,
                AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //支付成功后跳转的地址
        request.setReturnUrl(AlipayConfig.return_url + "?id=" + childOrder.getId() + "&&paystatus=true");
        //异步通知地址paystatus
        request.setNotifyUrl(AlipayConfig.notify_url);

        String productName = productService.findProductByProductId(childOrder.getProductId()).getProductName();
        if (productName.length() > 20) {
            productName = productName.substring(0, 20) + "...";
        }
        // 商户订单号masterOrderId ，商户网站订单系统中唯一订单号
        request.setBizContent("{\"out_trade_no\":\""+ childOrder.getMasterOrderId()+2 +"\","
                + "\"total_amount\":\""+ childOrder.getNumber() * childOrder.getPrice() +"\","
                + "\"subject\":\""+ productName +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form;
        try {
            form = alipayClient.pageExecute(request).getBody();
            return JSONResult.ok(form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return JSONResult.errorMsg(e.getErrMsg());
        }
    }

    @ApiOperation(value = "支付成功根据id更新状态")
    @PostMapping(value = "/updateStatusById/{id}/{order_status}")
    public JSONResult updateStatusById(@PathVariable("id") int id, @PathVariable("order_status") String orderStatus) {
        log.info("==============支付成功更新【" + id + "】状态" + orderStatus + "==============");
        if (orderStatus.equals("paid")) {orderStatus = "已支付";}
        return orderService.updateChildOrderStatusById(id, orderStatus) == 1? JSONResult.ok("支付成功更新状态成功") : JSONResult.errorMsg("更新状态失败");
    }

    @ApiOperation(value = "查询属于自己商品的订单")
    @GetMapping(value = "/findOrderByOwn/{own_name}")
    public JSONResult findOrderByOwn(@PathVariable("own_name") String ownName) {
        log.info("==============查询属于【" + ownName + "】商品的订单==============");
        List<ChildOrder> childOrderList = orderService.findChildOrderByOwn(ownName);
        for (ChildOrder childOrder : childOrderList) {
            childOrder.setProduct(productService.findProductByProductId(childOrder.getProductId()));
            if (childOrder.getLogisticsId() != null) {
                childOrder.setLogistics(logisticsService.findLogistics(childOrder.getLogisticsId()));
            }
            MasterOrder masterOrder = orderService.findMasterOrderByMasterOrderId(childOrder.getMasterOrderId());
            masterOrder.setUser(userService.findUserByUserId(masterOrder.getUserId()));
            masterOrder.setAddress(addressService.findAddressByAddressId(masterOrder.getAddressId()));
            childOrder.setMasterOrder(masterOrder);
        }
        return JSONResult.ok(childOrderList);
    }

    @ApiOperation(value = "根据子订单编号更新子订单状态")
    @PostMapping(value = "/updateChildOrderStatusByChildOrderId/{child_order_id}/{child_order_status}")
    public JSONResult updateChildOrderStatusByChildOrderId(@PathVariable("child_order_id") String childOrderId,
                                                           @PathVariable("child_order_status") String childOrderStatus){
        log.info("==============更新子订单编号为【" + childOrderId + "】的订单的状态" + childOrderStatus + "==============");
        return orderService.updateChildOrderStatusByChildOrderId(childOrderId, childOrderStatus) == 1? JSONResult.ok("更新子订单状态成功"):JSONResult.errorMsg("更新子订单状态失败");
    }

    @ApiOperation(value = "查询属于自己商品的订单（带查询条件）")
    @PostMapping(value = "/findOrderByOwnWithSelect")
    public JSONResult findOrderByOwnWithSelect(@RequestBody OrderSelect orderSelect) {
        log.info("==============查询（带查询条件）属于【" + orderSelect + "】商品的订单==============");
        if (orderSelect.getProductName() != null) {
            List<Product> products = productService.findProductByProductName(orderSelect.getProductName());
            if (products.size() <= 0) {
                return JSONResult.ok();
            } else {
                orderSelect.setProductId(products.get(0).getProductId());
            }
        }

        if (orderSelect.getLogisticsId() != null || orderSelect.getLogisticsType() != null) {
            List<String> logisticsIds = logisticsService.findLogisticsIdByIdOrType(orderSelect.getLogisticsId(), orderSelect.getLogisticsType());
            if (logisticsIds.size() <= 0) {
                return JSONResult.ok();
            } else {
                orderSelect.setLogisticsIds(logisticsIds);
            }
        }

        if (orderSelect.getConsigneeName() != null) {
            List<String> masterOrderIds = orderService.findMasterOrderIdByConsigneeName(orderSelect.getConsigneeName());
            if (masterOrderIds.size() <= 0) {
                return JSONResult.ok();
            } else {
                orderSelect.setMasterOrderIds(masterOrderIds);
            }
        }

        List<ChildOrder> childOrderList = orderService.findChildOrderByOwnWithSelect(orderSelect);
        for (ChildOrder childOrder : childOrderList) {
            childOrder.setProduct(productService.findProductByProductId(childOrder.getProductId()));
            if (childOrder.getLogisticsId() != null) {
                childOrder.setLogistics(logisticsService.findLogistics(childOrder.getLogisticsId()));
            }
            MasterOrder masterOrder = orderService.findMasterOrderByMasterOrderId(childOrder.getMasterOrderId());
            masterOrder.setUser(userService.findUserByUserId(masterOrder.getUserId()));
            masterOrder.setAddress(addressService.findAddressByAddressId(masterOrder.getAddressId()));
            childOrder.setMasterOrder(masterOrder);
        }
        return JSONResult.ok(childOrderList);
    }

    @ApiOperation(value = "更新订单状态（高级版）")
    @PostMapping(value = "/updateStatusSenior/{id}/{order_type}/{child_order_status}")
    public JSONResult updateStatusSenior(@PathVariable("id") int id, @PathVariable("order_type") String orderType,
                                   @PathVariable("child_order_status") String childOrderStatus) {
        log.info("==============支付成功更新状态【" + id + "和" + orderType + "和" + childOrderStatus + "】的信息==============");

        return orderService.updateChildOrderStatusSenior(id, childOrderStatus, orderType) == 1 ? JSONResult.ok("更新订单状态成功"): JSONResult.errorMsg("更新订单状态失败");
    }

}
