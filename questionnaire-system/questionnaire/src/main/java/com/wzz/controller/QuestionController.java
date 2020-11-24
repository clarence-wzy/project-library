package com.wzz.controller;

import com.alibaba.fastjson.JSONObject;
import com.wzz.entity.Question;
import com.wzz.entity.Questionnaire;
import com.wzz.entity.User;
import com.wzz.log.annotation.ControllerLog;
import com.wzz.service.QuestionService;
import com.wzz.service.QuestionnaireService;
import com.wzz.util.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created on 2019/8/28 by Tinchi
 * 如果不设置utf-8返回的数据是乱码
 *
 * @Description: 问卷控制器
 **/
@RestController
@Api(tags = "问卷信息接口")
public class QuestionController {

    Long id = 0L;

    @Resource
    QuestionService questionService;
    @Resource
    private QuestionnaireService questionnaireService;

    @ControllerLog(description = "获取所有问卷信息")
    @ApiOperation(value = "获取所有问卷信息")
    @GetMapping(value = "/questionnaireByUserId")
    public List<Questionnaire> findQuestionnaireById(HttpServletResponse response,HttpSession session){
        response.setCharacterEncoding("utf-8");
        User user = (User) session.getAttribute("user");
        Long userId=user.getUserId();
        return questionnaireService.findQuestionnaireByUserId(userId);
    }


    @ControllerLog(description = "根据问卷id获取所有问卷信息")
    @ApiOperation(value = "根据问卷id获取所有问卷信息")
    @GetMapping(value = "/questionnaireByQCode/{qCode}")
    public JSONObject getAll(@PathVariable Long qCode, HttpServletResponse response){
        response.setCharacterEncoding("utf-8");

        Map<String,Object> map = new HashMap<String,Object>();

        Questionnaire  questionnaire = questionnaireService.findQuestionnaireByQCode(qCode);

        List<Question> question=questionService.findByQCode(qCode);

        map.put("qData",questionnaire);
        map.put("optionsData",question);

        return new JSONObject(map);
    }



    @ControllerLog(description = "获取qCode模板下qId的问题")
    @ApiOperation(value = "获取qCode模板下qId的问题")
    @GetMapping(value = "/question/{qCode}/{qId}")
    public Question getOneQuestion(@PathVariable Long qCode,
                                   @PathVariable int qId,
                                   HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        Question question = questionService.findByQCodeAndQId(qCode, qId);
        return question;
    }


    @ControllerLog(description = "删除qCode该模板下qId的问题")
    @ApiOperation(value = "删除qCode该模板下qId的问题")
    @DeleteMapping(value = "/question/{qCode}/{qId}")
    public JSONResult deleteQuestion(@PathVariable Long qCode,
                                     @PathVariable int qId,
                                     HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");

        int result = questionService.deleteByQCodeAndQId(qCode, qId);
        if (result != 0) {
            return JSONResult.ok("删除成功");
        } else {
            return JSONResult.errorMsg("删除失败");

        }
    }


    @ApiOperation(value = "根据模板id删除模板")
    @DeleteMapping("/deleteQuestionnaire/{qCode}")
    public JSONResult deleteQuestionnaire(@PathVariable("qCode") Long qCode) {
        int i = questionnaireService.deleteQuestionnaire(qCode);
        if (i !=0){
            questionService.deleteByQCode(qCode);
            return JSONResult.ok("删除成功");
        }else {
            return JSONResult.errorMsg("删除失败");
        }
    }


    @ControllerLog(description = "新增qCode该模板下qId的问题")
    @ApiOperation(value = "新增qCode该模板下qId的问题")
    @PostMapping(value = "/question")
    public JSONResult insertQuestion(@RequestBody Question question) {
        int result = questionService.insertQuestion(question);
        if (result != 0) {
            return JSONResult.ok("添加成功");
        } else {
            return JSONResult.errorMsg("添加失败");
        }

    }

    @ControllerLog(description = "新增问卷")
    @ApiOperation(value = "新增问卷")
    @PostMapping(value = "/questionnaire/{q_code}&{type}&{title}")
    public JSONResult insertQuestionnaire(@PathVariable("q_code")Long qCode,
                                          @PathVariable("type")String type,
                                          @PathVariable("title")String title){
        Questionnaire questionnaire=new Questionnaire();

        Map<String,Object> map = new HashMap<String,Object>();

        Long data=0L;

        if (type.equals("blank")){
            //新增一条问卷数据并返回
            questionnaire.setUserId(1L);
            questionnaire.setAuthor("admin");
        }else if (type.equals("exist")){
            //从已有的自定义数据库里找出相应的问卷
            questionnaire= questionnaireService.findQuestionnaireByQCode(qCode);
        }else if (type.equals("template")){
            //从系统模板库找出相应的问卷
            questionnaire=questionnaireService.findQuestionnaireFromTempByQCode(qCode);
            questionnaire.setUserId(1L);
        }

        try {
            questionnaire.setTitle(URLDecoder.decode(title,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        data=insertQuestionnaire(questionnaire);

        if(data > 0) {
            map.put("q_code",data);
            return JSONResult.build(200,"插入成功",new JSONObject(map));
        }else{
            return JSONResult.errorMsg("插入失败");
        }

    }

    /**
     *  将问卷模板插入数据库并返回自增id
     * @param questionnaire
     * @return  自增id
     */
    public Long insertQuestionnaire(Questionnaire questionnaire){

        questionnaireService.insertQuestionnaireTitle(questionnaire);

        id = questionnaire.getQuestionnaireId();

        return id;
    }


    /**
     * leewebi-n
     * 更新单个问题
     * @param qCode
     * @param qId
     * @param question
     * @return
     */
    @ControllerLog(description = "Update question to mongodb.wen by q_code & q_id")
    @ApiOperation(value = "Update question to mongodb.wen by q_code & q_id")
    @PutMapping(value = "/question/updateByQCodeAndQId/{qCode}/{qId}")
    public JSONResult updateByQCodeAndQId(@PathVariable Long qCode, @PathVariable int qId, @RequestBody Question question){

        int result = questionService.updateByQCodeAndQId(qCode,qId,question);

        if (result==1){
            return JSONResult.ok("更新成功");
        }else {
            return JSONResult.errorMsg("更新失败");
        }
    }

}



