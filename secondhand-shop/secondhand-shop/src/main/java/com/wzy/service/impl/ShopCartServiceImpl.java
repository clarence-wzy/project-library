package com.wzy.service.impl;

import com.wzy.entity.ShopCart;
import com.wzy.entity.ShopCartWithProduct;
import com.wzy.mapper.ShopCartMapper;
import com.wzy.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Package: com.wzy.service.impl
 * @Author: Clarence1
 * @Date: 2019/12/19 15:01
 */
@Service
public class ShopCartServiceImpl implements ShopCartService {

    @Autowired
    private ShopCartMapper shopCartMapper;

    @Override
    public List<ShopCartWithProduct> findAllShopCartByUserId(int userId) {
        return shopCartMapper.findAllShopCartByUserId(userId);
    }

    @Override
    public int insertShopCart(ShopCart shopCart) {
        return shopCartMapper.insertShopCart(shopCart);
    }

    @Override
    public int updateShopCartStatus(ShopCart shopCart) {
        return shopCartMapper.updateShopCartStatus(shopCart);
    }

    @Override
    public int updateShopCartBatch(int id) {
        return shopCartMapper.updateShopCartBatch(id);
    }

    @Override
    public int updateShopCartNumber(int id, int cartNumber) {
        return shopCartMapper.updateShopCartNumber(id, cartNumber);
    }

    @Override
    public int updateShopCartStatusById(String cartStatus, int id) {
        return shopCartMapper.updateShopCartStatusById(cartStatus, id);
    }
}
