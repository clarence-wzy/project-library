package com.wzz.mail.consumer;


import com.wzz.mail.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>Title: ResendMail</p>
 * <p>Description:重新发送消息 </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/9/3 14:21
 */
@Component
@Slf4j
public class ResendMail {

    @Autowired
    private MessageService messageService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 最大投递数
     */
    private static final int MAX_TRY_COUNT = 3;

    /**
     * 定时任务
     * 每30s拉取投递失败的消息，重新投递
     */
//    @Scheduled(cron = "0/30 * * * * ?")
//    public void resend(){
//        log.info("开始重新投递消息");
//        //拉取投递失败信息
//        List<Message> messages = messageService.getTimeoutMessage();
//        messages.forEach(message -> {
//            String msgId = message.getMsgId();
//            if(message.getTryCount() >= MAX_TRY_COUNT){
//                messageService.updateMessageStatus(msgId, Constant.MessageStatus.DELIVER_FAIL);
//                log.info("超过最大重试次数，投递失败，msgId：{}",msgId);
//            }else{
//                messageService.updateTryCount(msgId,message.getNextTryTime());
//
//                CorrelationData correlationData = new CorrelationData(msgId);
//                rabbitTemplate.convertAndSend(message.getExchange(), message.getRoutingKey(), MessageHelper.objToMsg(message.getMsg()), correlationData);// 重新投递
//
//                log.info("第 " + (message.getTryCount() + 1) + " 次重新投递消息");
//            }
//        });
//        log.info("定时任务执行结束");
//    }

}
