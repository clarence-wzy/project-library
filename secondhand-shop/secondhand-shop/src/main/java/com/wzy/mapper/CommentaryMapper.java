package com.wzy.mapper;

import com.wzy.entity.Commentary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Package: com.wzy.mapper
 * @Author: Clarence1
 * @Date: 2020/1/18 14:43
 */
@Mapper
public interface CommentaryMapper {

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
