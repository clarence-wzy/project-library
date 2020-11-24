package com.wzy.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.wzy.config.AlipayConfig;
import com.wzy.util.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * @Package: com.wzy.controller
 * @Author: Clarence1
 * @Date: 2020/1/14 18:15
 */
@RestController
@RequestMapping(value = "/pay")
@CrossOrigin
@Slf4j
public class PayController {

    @PostMapping(value = "/aliPay/{master_order_id}/{total_price}")
    public JSONResult alipay(@PathVariable("master_order_id") String masterOrderId, @PathVariable("total_price") double totalPrice,
                             @RequestParam("payee") String payee, @RequestParam("productName") String productName) {
        Random r = new Random();
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID, AlipayConfig.RSA_PRIVATE_KEY,
                AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //支付成功后跳转的地址
        request.setReturnUrl(AlipayConfig.return_url);
        //异步通知地址
        request.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //付款金额，必填
        String total_amount =Integer.toString(r.nextInt(9999));
        //订单名称，必填
        String subject ="奥迪A8 2016款 A8L 60 TFSl quattro豪华型";
        //商品描述，可空
//        String body = "尊敬的会员欢迎购买奥迪A8 2016款 A8L 60 TFSl quattro豪华型";
        request.setBizContent("{\"out_trade_no\":\""+ masterOrderId +"\","
                + "\"total_amount\":\""+ totalPrice +"\","
                + "\"subject\":\""+ productName +"\","
//                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        log.info(request.getBizContent());
        String form;
        try {
            form = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return JSONResult.errorMsg(e.getErrMsg());
        }
        return JSONResult.ok(form);
    }

}
