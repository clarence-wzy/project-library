package com.wzy.service.impl;

import com.wzy.entity.ChildOrder;
import com.wzy.entity.MasterOrder;
import com.wzy.entity.OrderSelect;
import com.wzy.mapper.OrderMapper;
import com.wzy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Package: com.wzy.service.impl
 * @Author: Clarence1
 * @Date: 2020/1/7 16:22
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<MasterOrder> findMasterOrderByUserId(int userId) {
        return orderMapper.findMasterOrderByUserId(userId);
    }

    @Override
    public MasterOrder findMasterOrderByMasterOrderId(String masterOrderId) {
        return orderMapper.findMasterOrderByMasterOrderId(masterOrderId);
    }

    @Override
    public List<ChildOrder> findChildOrderByMasterOrderId(String masterOrderId, String childOrderStatus) {
        return orderMapper.findChildOrderByMasterOrderId(masterOrderId, childOrderStatus);
    }

    @Override
    public int insertMasterOrder(MasterOrder masterOrder) {
        return orderMapper.insertMasterOrder(masterOrder);
    }

    @Override
    public int insertChildOrder(ChildOrder childOrder) {
        return orderMapper.insertChildOrder(childOrder);
    }

    @Override
    public int updateMasterOrderStatus(String masterOrderId, String masterOrderStatus) {
        return orderMapper.updateMasterOrderStatus(masterOrderId,masterOrderStatus);
    }

    @Override
    public int updateChildOrderStatus(String masterOrderId, String childOrderStatus) {
        return orderMapper.updateChildOrderStatus(masterOrderId, childOrderStatus);
    }

    @Override
    public int updateChildOrderStatusByChildOrderId(String childOrderId, String childOrderStatus) {
        return orderMapper.updateChildOrderStatusByChildOrderId(childOrderId, childOrderStatus);
    }

    @Override
    public ChildOrder findChildOrderById(int id) {
        return orderMapper.findChildOrderById(id);
    }

    @Override
    public int updateChildOrderStatusById(int id, String childOrderStatus) {
        return orderMapper.updateChildOrderStatusById(id, childOrderStatus);
    }

    @Override
    public ChildOrder findChildOrderByChildOrderId(String childOrderId) {
        return orderMapper.findChildOrderByChildOrderId(childOrderId);
    }

    @Override
    public List<ChildOrder> findChildOrderByOwn(String ownName) {
        return orderMapper.findChildOrderByOwn(ownName);
    }

    @Override
    public int updateLogisticsIdByChildOrderId(String childOrderId, String logisticsId) {
        return orderMapper.updateLogisticsIdByChildOrderId(childOrderId, logisticsId);
    }

    @Override
    public List<ChildOrder> findChildOrderByOwnWithSelect(OrderSelect orderSelect) {
        return orderMapper.findChildOrderByOwnWithSelect(orderSelect);
    }

    @Override
    public List<String> findMasterOrderIdByConsigneeName(String consigneeName) {
        return orderMapper.findMasterOrderIdByConsigneeName(consigneeName);
    }

    @Override
    public int updateChildOrderStatusSenior(int id, String childOrderStatus, String orderType) {
        return orderMapper.updateChildOrderStatusSenior(id, childOrderStatus, orderType);
    }

}
