package com.ripen.controller;

import com.ripen.dao.entity.BkRecord;
import com.ripen.service.BkRecordService;
import com.ripen.util.JsonResult;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 书籍借出归还控制层
 *
 * @author Ripen.Y
 * @version 2021/01/30 20:06
 * @since 2021/01/30
 */
@RestController
@RequestMapping(value = "/book/record")
@Api(value = "书籍借出归还控制层")
public class BkRecordController {
    public final Logger LOGGER = LoggerFactory.getLogger (this.getClass ());

    @Autowired
    private BkRecordService bkRecordService;

    /**
     * 书籍借阅
     *
     * @param account 账号
     * @param bkRecordList 书籍记录列表
     * @return
     */
    @ApiOperation(value = "书籍借阅")
    @PostMapping(value = "/borrow/{account}")
    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult borrowBk (@PathVariable("account") String account, @RequestBody List<BkRecord> bkRecordList) {
        if (StringUtil.isNullOrEmpty(account) || bkRecordList == null || bkRecordList.size() == 0) {
            return JsonResult.errorMsg("参数错误");
        }
        LocalDateTime now = LocalDateTime.now();
        List<BkRecord> recordList = new ArrayList<>(bkRecordList.size());
        BkRecord bkRecord;
        for (BkRecord record : bkRecordList) {
            bkRecord = new BkRecord();
            bkRecord.setAccount(account);
            bkRecord.setSerId(record.getSerId());
            bkRecord.setBkId(record.getBkId());
            bkRecord.setLendTime(now);
            bkRecord.setReturnTime(null);
            bkRecord.setExpireTime(record.getExpireTime());
            bkRecord.setUpdateTime(now);
            recordList.add(bkRecord);
        }
        int rt = bkRecordService.addRecord(recordList);
        if (rt == 0) {
            return JsonResult.ok("借阅成功");
        } else if (rt == -2) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonResult.errorMsg("参数错误");
        } else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonResult.errorMsg("系统异常");
        }
    }

    /**
     * 书籍归还
     *
     * @param account 账号
     * @param rcdIdList 书籍记录id列表
     * @return
     */
    @ApiOperation(value = "书籍归还")
    @PostMapping(value = "/return/{account}")
    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult returnBk (@PathVariable("account") String account, @RequestBody List<String> rcdIdList) {
        if (StringUtil.isNullOrEmpty(account) || rcdIdList == null || rcdIdList.size() == 0) {
            return JsonResult.errorMsg("参数错误");
        }
        LocalDateTime now = LocalDateTime.now();

        int rt = 0;
        BkRecord bkRecord;
        for (String rcdId : rcdIdList) {
            bkRecord = new BkRecord();
            bkRecord.setRcdId(rcdId);
            bkRecord.setReturnTime(now);
            rt = bkRecordService.updateRecord(2, bkRecord);
            if (rt != 0) {
                break;
            }
        }

        if (rt == 0) {
            return JsonResult.ok("归还成功");
        } else if (rt == -2) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonResult.errorMsg("参数错误");
        } else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonResult.errorMsg("系统异常");
        }
    }

    /**
     * 获取书籍记录
     *
     * @param type 类型，0：记录，1：记录 + 信息
     * @param bkRecord 书籍记录
     * @return
     */
    @ApiOperation(value = "获取书籍记录")
    @GetMapping(value = "/get/{type}")
    public JsonResult get (@PathVariable("type") int type, BkRecord bkRecord) {
        List<BkRecord> bkRecordList;
        switch (type) {
            case 0 :
            case 1 :
                bkRecordList = bkRecordService.getRecord(type, bkRecord);
                break;
            default :
                return JsonResult.errorMsg("参数错误");
        }
        return JsonResult.ok(bkRecordList);
    }

    @GetMapping(value = "/rank/lend")
    public JsonResult countLend () {
        return JsonResult.ok(bkRecordService.countByBkId());
    }

}
