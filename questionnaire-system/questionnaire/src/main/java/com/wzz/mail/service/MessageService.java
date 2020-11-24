package com.wzz.mail.service;

import com.wzz.mail.entity.Message;

import java.util.Date;
import java.util.List;

/**
 * <p>Title: MessageService</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/9/3 14:56
 */
public interface MessageService {

    /**
     * 更新消息状态
     *
     * @param msgId 消息ID
     * @param status 消息状态
     */
    void updateMessageStatus(String msgId, Integer status);

    /**
     * 获取消息
     *
     * @param msgId 消息ID
     * @return message
     */
    Message getMessageByMsgId(String msgId);

    /**
     * 获取超时消息列表
     *
     * @return
     */
    List<Message> getTimeoutMessage();

    /**
     * 更新重试次数
     *
     * @param msgId 消息ID
     * @param tryTime 尝试时间
     */
    void updateTryCount(String msgId, Date tryTime);

    /**
     * 插入消息
     * @param message 消息实体
     */
    void insertMessage(Message message);
}