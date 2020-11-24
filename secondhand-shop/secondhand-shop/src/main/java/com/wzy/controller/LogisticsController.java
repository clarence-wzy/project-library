package com.wzy.controller;

import com.wzy.entity.Logistics;
import com.wzy.service.LogisticsService;
import com.wzy.service.OrderService;
import com.wzy.util.CodeUtil;
import com.wzy.util.JSONResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Package: com.wzy.controller
 * @Author: Clarence1
 * @Date: 2020/1/21 22:51
 */
@RestController
@RequestMapping(value = "/logistics")
@CrossOrigin
@Slf4j
public class LogisticsController {

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "插入物流信息")
    @PostMapping(value = "/insertLogistics")
    public JSONResult insertLogistics(@RequestBody Logistics logistics) {
        log.info("==============插入物流信息【" + logistics + "】==============");
        if (logistics.getLogisticsType().contains("校")) {
            //  校园类型的物流号为“611”+四个随机数，共7位数
            String logisticsId = "611" + CodeUtil.generateVerifyCode(4);
            logistics.setLogisticsId(logisticsId);
        }
        int i = logisticsService.insertLogistics(logistics);
        if (i != 1) {
            return JSONResult.errorMsg("插入物流信息失败");
        }
        orderService.updateLogisticsIdByChildOrderId(logistics.getChildOrderId(), logistics.getLogisticsId());
        return JSONResult.ok("插入物流信息成功");
    }

    @ApiOperation(value = "更新物流信息")
    @PostMapping(value = "/updateLogistics")
    public JSONResult updateLogistics(@RequestBody Logistics logistics) {
        log.info("==============更新物流信息【" + logistics + "】==============");
        logisticsService.updateLogistics(logistics);
        orderService.updateLogisticsIdByChildOrderId(logistics.getChildOrderId(), logistics.getLogisticsId());
        return JSONResult.ok("更新物流信息成功");
    }

}
