package com.wzz.mail.service.impl;

import com.wzz.mail.config.RabbitConfig;
import com.wzz.mail.consumer.MessageHelper;
import com.wzz.mail.entity.Mail;
import com.wzz.mail.entity.Message;
import com.wzz.mail.service.MessageService;
import com.wzz.mail.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>Title: MsgServiceImpl</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/9/2 15:30
 */
@Service
@Slf4j
public class MsgServiceImpl implements MsgService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MessageService messageService;

    @Override
    public void send(Mail mail) {
        String msgId = UUID.randomUUID().toString();
        mail.setMsgId(msgId);

        Message message = new Message(msgId,mail.getContent(),RabbitConfig.MAIL_EXCHANGE_NAME,RabbitConfig.MAIL_ROUTING_KEY_NAME);
        //消息入库
        messageService.insertMessage(message);

        CorrelationData correlationData = new CorrelationData(msgId);

        //发送消息
        rabbitTemplate.convertAndSend(RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME,
                MessageHelper.objToMsg(mail), correlationData);
    }
}
