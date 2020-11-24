package com.wzy.controller;

import com.wzy.entity.Report;
import com.wzy.service.ReportService;
import com.wzy.service.UserService;
import com.wzy.util.JSONResult;
import com.wzy.util.JsonUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @Package: com.wzy.controller
 * @Author: Clarence1
 * @Date: 2020/1/19 17:54
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/report")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "提交举报信息")
    @PostMapping(value = "/insertReport")
    public JSONResult insertReport(@RequestBody Report report) {
        log.info("==============提交举报信息【" + report + "】==============");
        return reportService.insertReport(report) == 1? JSONResult.ok("提交成功") : JSONResult.errorMsg("提交失败");
    }

    @ApiOperation(value = "查询举报信息")
    @GetMapping(value = "/findReport/{id}/{admin_id}/{product_id}/{time_type}")
    public JsonUtil findReport(@PathVariable("id") int id, @PathVariable("admin_id") int adminId,
                               @PathVariable("product_id") int productId, @PathVariable("time_type") String timeType,
                               @RequestParam("page") int page, @RequestParam("limit") int limit) {
        log.info("==============根据【" + id + ","+ adminId + ","+ productId + "," + timeType + "】查询举报信息==============");
        List<Integer> userIds;
        if (adminId != 0) {
            userIds = userService.findUserByAdminId(adminId);
        } else {
            userIds = null;
        }
        List<Report> reportList = reportService.findReport(id, userIds, productId, timeType, limit * (page - 1), limit * page);
        return JsonUtil.ok(reportList.size(), reportList);
    }

    @ApiOperation(value = "审核结果--更新举报信息")
    @PostMapping(value = "/updateReport")
    public JSONResult updateReport(int id, String auditContent, String reportStatus, int deductCredit, int userId) {
        log.info("==============审核结果--更新举报信息【" + id + "-" + auditContent + "-" + reportStatus + "-" + deductCredit + "-" + userId + "】==============");
        if (reportStatus.equals("审核不通过")) {
            return reportService.updateReport(id, auditContent, 0, reportStatus) == 1? JSONResult.ok("更新成功") : JSONResult.errorMsg("更新失败");
        }
        if (reportStatus.equals("审核通过")) {
            int i = reportService.updateReport(id, auditContent, deductCredit, reportStatus);
            if (i != 1) {
                return JSONResult.errorMsg("更新失败");
            }
        }
        return userService.updateCredit(userId, deductCredit) == 1? JSONResult.ok("更新成功") : JSONResult.errorMsg("操作失败");
    }

}
