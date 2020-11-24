package com.wzy.controller;

import com.alibaba.fastjson.JSONObject;
import com.wzy.entity.ChildOrder;
import com.wzy.entity.Commentary;
import com.wzy.service.CommentaryService;
import com.wzy.service.OrderService;
import com.wzy.service.ProductService;
import com.wzy.service.UserService;
import com.wzy.util.ApprovalUtil;
import com.wzy.util.JSONResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: com.wzy.controller
 * @Author: Clarence1
 * @Date: 2020/1/18 14:55
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/commentary")
@Slf4j
public class CommentaryController {

    @Autowired
    private CommentaryService commentaryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "根据子订单编号查询评价信息")
    @PostMapping(value = "/insertCommentary")
    public JSONResult insertCommentary(@RequestParam(value = "commentary_json") String commentaryJson, @RequestParam(value = "commentary_images") MultipartFile[] commentaryImages) {
        Commentary commentary = JSONObject.parseObject(commentaryJson, Commentary.class);

        //  检查商品图片、商品详情图片
        int count = 0;
        Map<Object, Object> map = new HashMap<>(10);
        boolean approval = true;
        approval = isApproval(commentaryImages, count, map, approval, log);
        //  上传商品图片
        if (approval) {
            count = 0;
            for (MultipartFile file : commentaryImages) {
                Object result = productService.uploadPicture(file, "commentary/" + commentary.getChildOrderId() + "/");
                map.put(count, result);
                count++;
            }
        }
        commentary.setCommentaryImage(subImageStr(map));

        log.info("==============插入评价信息【" + commentary + "】==============");
        int i = commentaryService.insertCommentary(commentary);
        if (i == 1) {
            return orderService.updateChildOrderStatusByChildOrderId(commentary.getChildOrderId(), "已评价") == 1 ? JSONResult.ok("插入评价信息成功"):JSONResult.errorMsg("更新订单状态失败");
        } else {
            return JSONResult.errorMsg("插入评价信息失败");
        }
    }

    static boolean isApproval(@RequestParam("commentary_images") MultipartFile[] commentaryImages, int count, Map<Object, Object> map, boolean approval, Logger log) {
        for (MultipartFile file : commentaryImages) {
            count++;
            try {
                if(!ApprovalUtil.pictureApprovalUtil(file).equals("合规")) {
                    log.error("第" + count + "张展示照片不合规");
                    map.put(count, "第" + count + "张展示照片不合规");
                    approval = false;
                }
            } catch (Exception e) {
                log.error("检查图片出现异常，异常信息--》" + e.getMessage());
            }
        }
        return approval;
    }

    private String subImageStr(Map<Object, Object> map2) {
        String imageStr = "";
        for (Object image : map2.values()) {
            imageStr = imageStr + image + "、";
        }
        if (!imageStr.equals("") && imageStr.length() > 0) {
            imageStr = imageStr.substring(0, imageStr.length() - 1);
        }
        return imageStr;
    }

    @ApiOperation(value = "根据子订单编号查询评价信息")
    @GetMapping(value = "/findCommentary/{child_order_id}/{user_id}/{username}")
    public JSONResult findCommentary(@PathVariable("child_order_id") String childOrderId, @PathVariable("user_id") int userId,
                                                   @PathVariable("username") String username) {
        log.info("==============查询子订单编号【" + childOrderId + "】和用户【" + userId + "，" + username + "】的评价信息==============");
        List<Commentary> commentarys = commentaryService.findCommentary(childOrderId, userId, username);
        if (commentarys != null) {
            for (Commentary commentary : commentarys) {
                ChildOrder childOrder = orderService.findChildOrderByChildOrderId(commentary.getChildOrderId());
                childOrder.setProduct(productService.findProductByProductId(childOrder.getProductId()));
                commentary.setChildOrder(childOrder);
                commentary.setUser(userService.findUserByUserId(commentary.getUserId()));
            }
            return JSONResult.ok(commentarys);
        } else {
            return JSONResult.errorMsg("未查询出任何评价信息");
        }
    }

}
