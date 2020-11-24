package com.wzz.mail.consumer;

import com.wzz.mail.util.JsonUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;

/**
 * <p>Title: MessageHelper</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/9/2 14:34
 */
public class MessageHelper {

    public static Message objToMsg(Object obj) {
        if (null == obj) {
            return null;
        }

        Message message = MessageBuilder.withBody(JsonUtil.objToStr(obj).getBytes()).build();
        // 消息持久化
        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);

        return message;
    }

    public static <T> T msgToObj(Message message, Class<T> clazz) {
        if (null == message || null == clazz) {
            return null;
        }

        String str = new String(message.getBody());
        T obj = JsonUtil.strToObj(str, clazz);

        return obj;
    }
}
