package com.wzz.mail.service.impl;

import com.wzz.mail.entity.Message;
import com.wzz.mail.mapper.MessageMapper;
import com.wzz.mail.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: MessageServiceImpl</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/9/3 16:00
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void updateMessageStatus(String msgId, Integer status) {
        messageMapper.updateMessageStatus(msgId, status);
    }

    @Override
    public Message getMessageByMsgId(String msgId) {
        return messageMapper.getMessageByMsgId(msgId);
    }

    @Override
    public List<Message> getTimeoutMessage() {
        return messageMapper.getTimeoutMessage();
    }

    @Override
    public void updateTryCount(String msgId, Date tryTime) {
        messageMapper.updateTryCount(msgId, tryTime);
    }

    @Override
    public void insertMessage(Message message) {
        messageMapper.insertMessage(message);
    }
}
