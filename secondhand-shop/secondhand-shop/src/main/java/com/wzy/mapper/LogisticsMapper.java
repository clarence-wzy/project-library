package com.wzy.mapper;

import com.wzy.entity.Logistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Package: com.wzy.mapper
 * @Author: Clarence1
 * @Date: 2020/1/21 14:40
 */
@Mapper
public interface LogisticsMapper {

    /**
     * @decription 根据物流编号查询物流信息
     * @param logisticsId
     * @return
     */
    Logistics findLogistics(String logisticsId);

    /**
     * @decription 插入物流信息
     * @param logistics
     * @return
     */
    int insertLogistics(Logistics logistics);

    /**
     * @decription 根据物流表id更新物流类型和物流号
     * @param logistics
     * @return
     */
    int updateLogistics(Logistics logistics);

    /**
     * @decription 根据物流编号或物流类型查询物流id
     * @param logisticsId
     * @param logisticsType
     * @return
     */
    List<String> findLogisticsIdByIdOrType(String logisticsId, String logisticsType);

}
