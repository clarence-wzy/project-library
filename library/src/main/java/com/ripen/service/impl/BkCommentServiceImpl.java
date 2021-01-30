package com.ripen.service.impl;

import com.ripen.dao.entity.BkComment;
import com.ripen.dao.entity.BkRecord;
import com.ripen.dao.mapper.BkCommentMapper;
import com.ripen.service.BkCommentService;
import com.ripen.util.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类
 *
 * <p> 功能详细描述
 *
 * @author wuziyi
 * @version 2021/01/17 0:24
 * @see BkCommentService
 * @since 2021/01/17
 */
@Service
public class BkCommentServiceImpl implements BkCommentService {

    @Autowired
    private BkCommentMapper bkCommentMapper;

    @Override
    public int addComment(BkComment bkComment) {
        if (bkComment.getBkId() == null || bkComment.getUserId() == null) {
            return -2;
        }
        bkComment.setRcdId("c_" + SnowFlakeUtil.getId());
        return bkCommentMapper.addComment(bkComment);
    }

    @Override
    public List<BkComment> getComment(String rcdId, String bkId, String userId, Integer status) {
        return bkCommentMapper.getComment(rcdId, bkId, userId, status);
    }
}
