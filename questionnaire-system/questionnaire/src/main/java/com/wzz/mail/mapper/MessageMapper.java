package com.wzz.mail.mapper;

import com.wzz.mail.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>Title: MessageMapper</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/9/3 15:12
 */
@Repository
public interface MessageMapper {

    /**
     * 更新消息状态
     * @param msgId 消息ID
     * @param status 状态
     */
    void updateMessageStatus(String msgId, Integer status);

    /**
     * 获取消息
     *
     * @param msgId 消息ID
     * @return List
     */
    Message getMessageByMsgId(String msgId);

    /**
     * 获取超时消息列表
     *
     * @return List
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
     * 消息入库
     * @param message 消息实体
     */
    void insertMessage(Message message);
}
