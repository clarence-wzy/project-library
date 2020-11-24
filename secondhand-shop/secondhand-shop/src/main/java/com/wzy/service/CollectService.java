package com.wzy.service;

import com.wzy.entity.Collect;
import com.wzy.entity.CollectWithProduct;

import java.util.List;

/**
 * @Package: com.wzy.service
 * @Author: Clarence1
 * @Date: 2019/12/19 17:03
 */
public interface CollectService {

    /**
     * @decription 插入收藏信息
     * @param collect
     * @return
     */
    int insertCollect(Collect collect);

    /**
     * @decription 根据用户id查询收藏信息（包含商品信息）
     * @param userId
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<CollectWithProduct> findAllColllectByUserId(int userId, int startIndex, int pageSize);

    /**
     * @decription 查询用户的收藏总数
     * @param userId
     * @return
     */
    int countAllColllectByUserId(int userId);

    /**
     * @decription 根据用户id和商品id查询收藏信息
     * @param userId
     * @param productId
     * @return
     */
    List<Collect> findCollectByUserIdAndProductId(int userId, int productId);

    /**
     * @decription 更新收藏状态
     * @param collect
     * @return
     */
    int updateCollectStatus(Collect collect);

}
