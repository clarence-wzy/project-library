package com.wzy.service.impl;

import com.wzy.entity.Commentary;
import com.wzy.mapper.CommentaryMapper;
import com.wzy.service.CommentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Package: com.wzy.service.impl
 * @Author: Clarence1
 * @Date: 2020/1/18 14:44
 */
@Service
public class CommentaryServiceImpl implements CommentaryService {

    @Autowired
    private CommentaryMapper commentaryMapper;

    @Override
    public int insertCommentary(Commentary commentary) {
        return commentaryMapper.insertCommentary(commentary);
    }

    @Override
    public List<Commentary> findCommentary(String childOrderId, int userId, String username) {
        return commentaryMapper.findCommentary(childOrderId, userId, username);
    }



}
