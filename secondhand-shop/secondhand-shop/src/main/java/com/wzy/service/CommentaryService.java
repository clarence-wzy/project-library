package com.wzy.service;

import com.wzy.entity.Commentary;

import java.util.List;

/**
 * @Package: com.wzy.service
 * @Author: Clarence1
 * @Date: 2019/12/19 17:03
 */
public interface CommentaryService {

    /**
     * @decription 插入评价信息
     * @param commentary
     * @return
     */
    int insertCommentary(Commentary commentary);

    /**
     * @decription 根据子订单编号查询评价信息
     * @param childOrderId
     * @param userId
     * @param username
     * @return
     */
    List<Commentary> findCommentary(String childOrderId, int userId, String username);

}
