package com.wzy.service;

import com.wzy.entity.ShopCart;
import com.wzy.entity.ShopCartWithProduct;

import java.util.List;

/**
 * @Package: com.wzy.service
 * @Author: Clarence1
 * @Date: 2019/12/19 15:01
 */
public interface ShopCartService {

    /**
     * @decription 查询用户id下购物车信息（包含商品信息）
     * @return
     */
    List<ShopCartWithProduct> findAllShopCartByUserId(int userId);

    /**
     * @decription 插入购物车信息
     * @param shopCart
     * @return
     */
    int insertShopCart(ShopCart shopCart);

    /**
     * @decription 更新购物车状态
     * @param shopCart
     * @return
     */
    int updateShopCartStatus(ShopCart shopCart);

    /**
     * @decription 根据购物车id更新状态
     * @param id
     * @return
     */
    int updateShopCartBatch(int id);

    /**
     * @decription 更新购物车中商品数量
     * @param id
     * @param cartNumber
     * @return
     */
    int updateShopCartNumber(int id, int cartNumber);

    /**
     * @decription 根据购物车id更新购物车状态
     * @param cartStatus
     * @param id
     * @return
     */
    int updateShopCartStatusById(String cartStatus, int id);

}
