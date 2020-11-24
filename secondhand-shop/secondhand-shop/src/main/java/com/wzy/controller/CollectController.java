package com.wzy.controller;

import com.wzy.entity.Collect;
import com.wzy.service.CollectService;
import com.wzy.util.JSONResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Package: com.wzy.controller
 * @Author: Clarence1
 * @Date: 2019/12/19 15:03
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/collect")
@Slf4j
public class CollectController {

    @Autowired
    private CollectService collectService;

    @ApiOperation(value = "查询用户的全部收藏夹信息")
    @GetMapping(value = "/findAllCollect/{user_id}")
    public JSONResult getAllCollect(@PathVariable("user_id") int userId, int startIndex, int pageSize) {
        log.info("==============查询【" + userId + "】用户第 " + startIndex + " 页 " + pageSize + " 条收藏夹信息==============");
        return JSONResult.ok(collectService.findAllColllectByUserId(userId, startIndex, pageSize));
    }

    @ApiOperation(value = "查询用户的收藏夹总数")
    @GetMapping(value = "/getCollectCount/{user_id}")
    public JSONResult getCollectCount(@PathVariable("user_id") int userId) {
        log.info("==============查询【" + userId + "】用户的收藏夹总数==============");
        return JSONResult.ok(collectService.countAllColllectByUserId(userId));
    }

    @ApiOperation(value = "插入收藏夹信息")
    @PostMapping(value = "/insertCollect")
    public JSONResult insertCollect(@RequestBody Collect collect) {
        collect.setCollectCreateTime(LocalDateTime.now());
        log.info("==============插入收藏夹信息【" + collect + "】==============");
        return collectService.insertCollect(collect) == 1 ? JSONResult.ok("插入收藏夹信息成功") : JSONResult.errorMsg("插入收藏夹信息失败");
    }

    @ApiOperation(value = "根据用户id和商品id查询收藏信息")
    @GetMapping(value = "/findCollectByUserIdAndProductId/{user_id}/{product_id}")
    public JSONResult getCollectByUserIdAndProductId(@PathVariable("user_id") int userId, @PathVariable("product_id") int productId) {
        log.info("==============查询【用户id：" + userId + ", 商品id：" + productId + "】的收藏夹信息==============");
        return JSONResult.ok(collectService.findCollectByUserIdAndProductId(userId, productId));
    }

    @ApiOperation(value = "更新收藏状态")
    @PostMapping(value = "/updateCollectStatus")
    public JSONResult updateCollectStatus(@RequestBody Collect collect) {
        log.info("==============更新【" + collect + "】收藏状态==============");
        List<Collect> collectList = collectService.findCollectByUserIdAndProductId(collect.getUserId(), collect.getProductId());
        if (collectList.size() > 0) {
            collect.setCollectModifyTime(LocalDateTime.now());
            return collectService.updateCollectStatus(collect) == 1 ? JSONResult.ok("更新收藏状态成功") : JSONResult.errorMsg("更新收藏状态失败");
        } else {
            collect.setCollectCreateTime(LocalDateTime.now());
            return collectService.insertCollect(collect) == 1 ? JSONResult.ok("插入收藏夹信息成功") : JSONResult.errorMsg("插入收藏夹信息失败");
        }
    }

}
