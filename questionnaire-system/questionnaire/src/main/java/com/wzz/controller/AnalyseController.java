package com.wzz.controller;

import com.alibaba.fastjson.JSONObject;
import com.wzz.entity.Analyse;
import com.wzz.entity.AnswerData;
import com.wzz.service.AnalyseService;
import com.wzz.service.InvitationService;
import com.wzz.util.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created on 2019/9/17
 *
 * @Author: tinchi
 * @Description:    问卷统计分析控制器
 **/

@RestController
@RequestMapping("/analyse")
@Api(tags = "统计分析接口")
@Slf4j
public class AnalyseController {

    @Autowired
    private AnalyseService analyseService;

    @Autowired
    private InvitationService invitationService;

    @PostMapping("/receiveData")
    @ApiOperation(value = "接受前端传递的参数")
    public JSONResult receive(@RequestBody Analyse analyse){
        analyseService.insert(analyse);
        return JSONResult.ok();
    }

    @GetMapping("/analyseData/{qCode}")
    @ApiOperation(value = "根据qCode获取相关信息")
    public Object receive1(@PathVariable Long qCode, HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        Map<String, Object> map = new HashMap<>(10);

        int recyleTotal = invitationService.recycleTotal(qCode.intValue());
        int questionnaireTotal = invitationService.emailSuccessTotal(qCode.intValue());
        int spendTimeTotal = 0;
        List<Analyse> analyses = analyseService.getAnalyse(qCode);

        Map<Integer, Map<Integer, Integer>> res = new HashMap<>(16);
        for (Analyse analyse : analyses) {
            Map<Integer, Integer> count = new HashMap<>(16);
            for (AnswerData answerDatum : analyse.getAnswerData()) {
                if (res.get(answerDatum.getQId()) == null && count.get(answerDatum.getOptionId()) == null && !answerDatum.getValue().equals("其他")) {
                    count.put(answerDatum.getOptionId(), Integer.valueOf(answerDatum.getValue()));
                    res.put(answerDatum.getQId(), count);
                } else if (res.get(answerDatum.getQId()) != null && count.get(answerDatum.getOptionId()) != null && !answerDatum.getValue().equals("其他")) {
                    Map<Integer, Integer> mid = res.get(answerDatum.getQId());
                    Integer score = mid.get(answerDatum.getOptionId());
                    mid.put(answerDatum.getOptionId(), (score + Integer.valueOf(answerDatum.getValue())));
                    res.put(answerDatum.getQId(), mid);
                } else {
                    Map<Integer, Integer> mid = res.get(answerDatum.getQId());
                    Integer score = mid.get(answerDatum.getOptionId());
                    mid.put(answerDatum.getOptionId(), (score + 0));
                    res.put(answerDatum.getQId(), mid);
                }
            }
        }

        log.info("res {}", res);


        List<Map> result = new ArrayList<>();

        for (Integer i : res.keySet()) {
            Map<String, Object> objectMap = new HashMap<>(16);
            List<Map> opt = new ArrayList<>();
            //权重总数
            Float valueTotal = 0f;
            //平均值
            Integer valueAvg = 0;
            objectMap.put("qId", i);
            for (Integer j : res.get(i).keySet()) {
                Map<String, Integer> optionMap = new HashMap<>(16);
                optionMap.put("index", j);
                optionMap.put("count", res.get(i).get(j));
                opt.add(optionMap);
                valueTotal += res.get(i).get(j);
            }
            objectMap.put("options", opt);
            objectMap.put("valueTotal", valueTotal);
            objectMap.put("valueAvg", (valueTotal / recyleTotal));
            result.add(objectMap);
        }

        map.put("qCode", qCode);
        map.put("total", questionnaireTotal);
        map.put("recovery", recyleTotal);
        map.put("avgTime", spendTimeTotal / recyleTotal);
        map.put("res", result);

//        return analyses;
        return JSONResult.build(200, "统计成功", new JSONObject(map));
    }






}
