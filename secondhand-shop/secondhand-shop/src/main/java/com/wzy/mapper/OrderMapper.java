package com.wzy.mapper;

import com.wzy.entity.ChildOrder;
import com.wzy.entity.MasterOrder;
import com.wzy.entity.OrderSelect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Package: com.wzy.mapper
 * @Author: Clarence1
 * @Date: 2019/11/26 20:28
 */
@Mapper
public interface OrderMapper {

    /**
     * @decription 根据用户id查询主订单信息
     * @param userId
     * @return
     */
    List<MasterOrder> findMasterOrderByUserId(int userId);

    /**
     * @decription 根据主订单编号查询主订单
     * @param masterOrderId
     * @return
     */
    MasterOrder findMasterOrderByMasterOrderId(String masterOrderId);

    /**
     * @decription 根据主订单id查询子订单信息
     * @param masterOrderId
     * @param childOrderStatus
     * @return
     */
    List<ChildOrder> findChildOrderByMasterOrderId(String masterOrderId, String childOrderStatus);

    /**
     * @decription 插入主订单信息
     * @param masterOrder
     * @return
     */
    int insertMasterOrder(MasterOrder masterOrder);

    /**
     * @decription 插入子订单信息
     * @param childOrder
     * @return
     */
    int insertChildOrder(ChildOrder childOrder);

    /**
     * @decription 根据主订单编号更新主订单状态
     * @param masterOrderId
     * @param masterOrderStatus
     * @return
     */
    int updateMasterOrderStatus(String masterOrderId, String masterOrderStatus);

    /**
     * @decription 根据主订单编号更新子订单状态和支付时间
     * @param masterOrderId
     * @param childOrderStatus
     * @return
     */
    int updateChildOrderStatus(String masterOrderId, String childOrderStatus);

    /**
     * @decription 根据子订单编号更新子订单状态
     * @param childOrderId
     * @param childOrderStatus
     * @return
     */
    int updateChildOrderStatusByChildOrderId(String childOrderId, String childOrderStatus);

    /**
     * @decription 根据子订单表id查看子订单信息
     * @param id
     * @return
     */
    ChildOrder findChildOrderById(int id);

    /**
     * @decription 根据子订单表id更新子订单状态和支付时间
     * @param id
     * @param childOrderStatus
     * @return
     */
    int updateChildOrderStatusById(int id, String childOrderStatus);

    /**
     * @decription 根据子订单编号查看子订单信息
     * @param childOrderId
     * @return
     */
    ChildOrder findChildOrderByChildOrderId(String childOrderId);

    /**
     * @decription 根据所属人姓名查询子订单信息
     * @param ownName
     * @return
     */
    List<ChildOrder> findChildOrderByOwn(String ownName);

    /**
     * @decription 根据子订单编号更新订单的物流编号
     * @param childOrderId
     * @param logisticsId
     * @return
     */
    int updateLogisticsIdByChildOrderId(String childOrderId, String logisticsId);

    /**
     * @decription 根据所属人姓名查询子订单信息（带查询条件）
     * @param orderSelect
     * @return
     */
    List<ChildOrder> findChildOrderByOwnWithSelect(OrderSelect orderSelect);

    /**
     * @decription 根据收货人姓名查询主订单编号
     * @param consigneeName
     * @return
     */
    List<String> findMasterOrderIdByConsigneeName(String consigneeName);

    /**
     * @decription 更新订单状态（高级版）
      * @param id
     * @param childOrderStatus
     * @param orderType
     * @return
     */
    int updateChildOrderStatusSenior(int id, String childOrderStatus, String orderType);

}
