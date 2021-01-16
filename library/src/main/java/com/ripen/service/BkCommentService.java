package com.ripen.service;

import com.ripen.dao.entity.BkComment;
import com.ripen.dao.entity.BkRecord;
import com.ripen.service.impl.BkCommentServiceImpl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 书籍评论服务层
 *
 * @author Ripen.Y
 * @version 2021/01/16 23:58
 * @see BkCommentServiceImpl
 * @since 2021/01/16
 */
public interface BkCommentService {

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
    List<BkRecord> getComment (String rcdId, String bkId, String userId, Integer status);


}
