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
import springfox.documentation.spring.web.json.Json;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        // 判断是否可借阅
        Map<String, Object> tmpRtMap = bkRecordService.isBorrowBook(account, now);
        int tmpRt = (int) tmpRtMap.get("rt");
        JsonResult result;
        switch (tmpRt) {
            case -1 :
                return JsonResult.errorMsg("参数错误");
            case 1 :
                result = new JsonResult();
                result.setStatus(501);
                BigDecimal payMoney = (BigDecimal) tmpRtMap.get("money");
                result.setData(payMoney);
                result.setMsg("用户存在逾期未还书籍，请先缴纳费用");
                return result;
            case 2 :
                result = new JsonResult();
                result.setStatus(502);
                result.setMsg("借阅数量超过3本，借阅失败");
                return result;
            default :
                break;
        }

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
     * @param type 类型，0：用户，1：管理员
     * @return
     */
    @ApiOperation(value = "书籍归还")
    @PostMapping(value = "/return/{account}/{type}")
    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult returnBk (@PathVariable("account") String account, @RequestBody List<String> rcdIdList, @PathVariable("type") int type) {
        if (StringUtil.isNullOrEmpty(account) || rcdIdList == null || rcdIdList.size() == 0) {
            return JsonResult.errorMsg("参数错误");
        }
        LocalDateTime now = LocalDateTime.now();

        if (type == 1) {
            // 判断归还书籍中是否存在逾期未还书籍
            List<String> rtList = bkRecordService.getNoReturnBook(account, rcdIdList, now);
            if (rtList != null && rtList.size() > 0) {
                JsonResult result = new JsonResult();
                result.setStatus(501);
                result.setMsg("归还书籍存在逾期未还书籍");
                result.setData(rtList);
                return result;
            }
        }

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

    @ApiOperation(value = "获取用户未归还书籍数量")
    @GetMapping(value = "/count/no_return/{account}")
    public JsonResult countNoReturn (@PathVariable("account") String account) {
        if (StringUtil.isNullOrEmpty(account)) {
            return JsonResult.errorMsg("参数错误");
        }
        Map<String, Object> tmpRtMap = bkRecordService.getNoReturn(account);
        return JsonResult.ok(tmpRtMap);
    }

}
