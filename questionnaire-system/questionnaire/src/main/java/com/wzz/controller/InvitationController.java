package com.wzz.controller;

import com.wzz.config.ThreeDES;
import com.wzz.entity.Invitation;
import com.wzz.entity.Question;
import com.wzz.entity.Questionnaire;
import com.wzz.service.InvitationService;
import com.wzz.service.QuestionService;
import com.wzz.service.QuestionnaireService;
import com.wzz.util.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Clarence1
 */
@Api(tags = "邀请信息接口")
@RestController
public class InvitationController {
    /**
     * 加密的key
     */
    final String key = "cf410f84904a44cc8a7f48fc4134e8f9";

    @Autowired
    private InvitationService invitationService;
    @Resource
    QuestionService questionService;
    @Resource
    private QuestionnaireService questionnaireService;

    @ApiOperation(value = "解析excel并添加进数据库，查询问卷id下的全部信息")
    @PostMapping(value = "/excel/{q_code}")
    public Object excelOperation(@RequestParam("file") MultipartFile file, @PathVariable("q_code") int questionnaireId, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");

        ThreeDES threeDes = new ThreeDES();
        Map<Integer, Map<Integer,Object>> excelMap = invitationService.addInvitationInfo(file);

        Invitation invitation;
        for (Map<Integer,Object> map : excelMap.values()) {
            invitation = new Invitation();
            String url = "http://10.75.19.30/answerPage/";
            if (!map.get(0).equals("")) {
                invitation.setName((String) map.get(0));
                invitation.setEmail((String) map.get(1));
                invitation.setDept((String) map.get(2));
                invitation.setEmailStatus(0);
                invitation.setEmailStatusTime(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));
                invitation.setRecycleStatus(0);
                invitation.setRecycleStatusTime(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));

                //  去掉email中@和.com
                //  例如1022@qq.com，就把 q_code + 1022qq 进行加密
                String[] str1 = ((String) map.get(1)).split("@");
                String[] str2 = str1[1].split("\\.");
                String string = questionnaireId + str1[0] + str2[0];
                string = threeDes.encryptThreeDESECB(URLEncoder.encode(string, "UTF-8"), key);
                url = url+ questionnaireId + "/" + string;

                invitation.setUrl(url);
                invitation.setQuestionnaireId(questionnaireId);

                try {
                    invitationService.insertInvitationInfo(invitation);
                } catch (Exception e) {
                    e.printStackTrace();
                    return JSONResult.errorMsg("插入数据失败");
                }
            }
        }

        return invitationService.findByQuestionnaireId(questionnaireId);
    }

    @ApiOperation(value = "验证url是否存在，并查询问卷回收状态")
    @GetMapping(value = "/link/{url}")
    public JSONResult checkUri(@PathVariable("url") String url, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");

        url = "%/" + url;
        int urlStatus = invitationService.findByUrl(url);
        Invitation recycleStatus = invitationService.findRecycleStatusByUrl(url);
        if (urlStatus == 0) {
            return JSONResult.errorMsg("该Url不存在");
        } else {
            if (recycleStatus.getRecycleStatus() == 1) {
                return JSONResult.errorMsg("该问卷已填写");
            } else {
                long qCode = recycleStatus.getQuestionnaireId();
                Map<String,Object> map = new HashMap<String,Object>(2);
                Questionnaire questionnaire = questionnaireService.findQuestionnaireByQCode(qCode);
                List<Question> question=questionService.findByQCode(qCode);
                map.put("qData",questionnaire);
                map.put("optionsData",question);
                return JSONResult.ok(map);
            }
        }
    }

    @ApiOperation(value = "查询问卷id下的全部信息")
    @GetMapping(value = "/excel/findAll/{q_code}")
    public Object findAllByQuestionnaireId(@PathVariable("q_code") int questionnaireId, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        return invitationService.findByQuestionnaireId(questionnaireId);
    }

}