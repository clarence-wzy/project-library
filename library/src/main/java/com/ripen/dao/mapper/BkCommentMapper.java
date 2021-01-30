package com.ripen.dao.mapper;

import com.ripen.dao.entity.BkComment;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 书籍评论映射
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:10
 * @since 2021/01/09
 */
@Mapper
public interface BkCommentMapper {

    /**
     * 新增书籍评论
     *
     * @param bkComment
     * @return
     */
    int addComment (BkComment bkComment);

    /**
     * 获取书籍评论
     *
     * @param rcdId
     * @param bkId
     * @param userId
     * @param status
     * @return
     */
    List<BkComment> getComment (@Param("rcdId") String rcdId, @Param("bkId") String bkId, @Param("userId") String userId, @Param("status") Integer status);

}
