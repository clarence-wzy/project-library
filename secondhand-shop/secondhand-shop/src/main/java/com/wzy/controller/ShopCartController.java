package com.wzy.controller;

import com.wzy.entity.ShopCart;
import com.wzy.entity.ShopCartWithProduct;
import com.wzy.service.ShopCartService;
import com.wzy.util.JSONResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Package: com.wzy.controller
 * @Author: Clarence1
 * @Date: 2019/12/19 15:03
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/shopCart")
@Slf4j
public class ShopCartController {

    @Autowired
    private ShopCartService shopCartService;

    @ApiOperation(value = "查询用户的全部购物车信息")
    @GetMapping(value = "/findAllShopCart/{user_id}")
    public JSONResult getAllShopCart(@PathVariable("user_id") int userId) {
        log.info("==============查询【" + userId + "】用户的全部购物车信息==============");
        return JSONResult.ok(shopCartService.findAllShopCartByUserId(userId));
    }

    @ApiOperation(value = "插入购物车信息")
    @PostMapping(value = "/insertShopCart")
    public JSONResult insertShopCart(@RequestBody ShopCart shopCart) {
        shopCart.setCartCreateTime(LocalDateTime.now());
        log.info("==============插入购物车信息【" + shopCart + "】==============");
        return shopCart.equals("") || shopCart == null ? JSONResult.errorMsg("购物车信息不能为空") : JSONResult.ok(shopCartService.insertShopCart(shopCart));
    }

    @ApiOperation(value = "更新购物车状态")
    @PutMapping(value = "/updateShopCartStatus")
    public JSONResult updateShopCartStatus(@RequestBody ShopCart shopCart) {
        if (shopCart.getProductId() <= 0 || shopCart.getUserId() <= 0) {
            return JSONResult.errorMsg("商品id和用户id只能为正整数");
        }
        shopCart.setCartModifyTime(LocalDateTime.now());
        log.info("==============更新【" + shopCart + "】的购物车状态==============");
        return shopCartService.updateShopCartStatus(shopCart) == 1 ? JSONResult.ok("更新购物车状态成功"):JSONResult.errorMsg("更新购物车状态失败");
    }

    @ApiOperation(value = "更新购物车中商品数量")
    @PostMapping(value = "/updateShopCartNumber/{id}/{cartNumber}")
    public JSONResult updateShopCartNumber(@PathVariable("id") int id, @PathVariable("cartNumber") int cartNumber) {
        log.info("==============更新购物车【" + id + "】中商品数量" + cartNumber +"==============");
        return shopCartService.updateShopCartNumber(id, cartNumber) == 1? JSONResult.ok("更新数量成功"):JSONResult.errorMsg("更新数量失败");
    }

    @ApiOperation(value = "根据购物车id更新状态")
    @PutMapping(value = "/updateShopCartBatch/{shopCartIdList}")
    public JSONResult updateShopCartBatch(@PathVariable("shopCartIdList") List<Integer> shopCartIdList) {
        log.info("==============更新购物车id为【" + shopCartIdList + "】的状态==============");
        for (int id : shopCartIdList) {
            int i = shopCartService.updateShopCartBatch(id);
            if (i != 1) {
                return JSONResult.errorMsg("更新购物车" + id + "状态失败");
            }
        }
        return JSONResult.ok("更新购物车状态成功");
    }

}
