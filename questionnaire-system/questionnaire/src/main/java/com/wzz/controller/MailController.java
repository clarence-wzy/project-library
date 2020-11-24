package com.wzz.controller;

import com.wzz.entity.Invitation;
import com.wzz.log.annotation.ControllerLog;
import com.wzz.mail.common.Constant;
import com.wzz.mail.dto.EmailResult;
import com.wzz.mail.entity.Mail;
import com.wzz.mail.service.MsgService;
import com.wzz.service.InvitationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: MailController</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/9/5 9:11
 */
@Api(tags = "发送邮件接口")
@Controller
@Slf4j
public class MailController {

    @Autowired
    private MsgService msgService;

    @Autowired
    private InvitationService invitationService;

    @ApiOperation(value = "发送邮件")
    @ControllerLog(description = "发送邮件")
    @RequestMapping(value = "/sendEmail/{questionnaire_id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> sendEmail(@PathVariable Integer questionnaire_id) {
        Map<String, Object> map = new HashMap<>(16);
        //查出该问卷下的所有邀请联系人名单
        List<Invitation> invitations = invitationService.findByQuestionnaireId(questionnaire_id);
        List<Mail> mailList = new ArrayList<>();
        try {
            invitations.forEach(x -> mailList.add(new Mail(x.getEmail(), "问卷邀请", x.getUrl())));
            mailList.forEach(m -> msgService.send(m));
            EmailResult result = new EmailResult(Constant.EmailStatus.SUCCESS,Constant.EmailStatus.SUCCESS_INFO);
            map.put("status",result.getCode());
            map.put("statusInfo",result.getCodeInformation());
            return map;
        } catch (Exception e) {
            log.error("发送失败：{}", e);
            EmailResult result = new EmailResult(Constant.EmailStatus.FAIL, Constant.EmailStatus.FAIL_INFO);
            map.put("status",result.getCode());
            map.put("statusInfo",result.getCodeInformation());
            return map;
        }
    }
}
