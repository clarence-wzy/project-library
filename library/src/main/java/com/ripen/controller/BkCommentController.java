package com.ripen.controller;


import com.ripen.dao.entity.BkComment;
import com.ripen.service.BkCommentService;
import com.ripen.util.JsonResult;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 书籍评论控制层
 *
 * @author Ripen.Y
 * @version 2021/01/30 20:06
 * @since 2021/01/30
 */
@RestController
@RequestMapping(value = "/book/comment")
@Api(value = "书籍评论控制层")
public class BkCommentController {
    public final Logger LOGGER = LoggerFactory.getLogger (this.getClass ());

    @Autowired
    private BkCommentService bkCommentService;

    /**
     * 新增评价
     *
     * @param account 用户账号
     * @param bkComment 评价信息
     * @return
     */
    @ApiOperation(value = "新增评价")
    @PostMapping(value = "/add/{account}")
    public JsonResult add (@PathVariable("account") String account, @RequestBody BkComment bkComment) {
        if (StringUtil.isNullOrEmpty(account) || bkComment == null) {
            return JsonResult.errorMsg("参数错误");
        }
        bkComment.setUserId(account);
        int rt = bkCommentService.addComment(bkComment);
        if (rt == -2) {
            return JsonResult.errorMsg("参数错误");
        }
        return JsonResult.ok("新增成功");
    }

    /**
     * 获取评价信息
     *
     * @param bkComment 评价信息
     * @return
     */
    @ApiOperation(value = "获取评价信息")
    @PostMapping(value = "/get")
    public JsonResult get (@RequestBody BkComment bkComment) {
        if (bkComment == null) {
            return JsonResult.errorMsg("参数错误");
        }
        List<BkComment> bkCommentList = bkCommentService.getComment(bkComment.getRcdId(), bkComment.getBkId(), bkComment.getUserId(), bkComment.getCommStatus());
        return JsonResult.ok(bkCommentList);
    }


}
