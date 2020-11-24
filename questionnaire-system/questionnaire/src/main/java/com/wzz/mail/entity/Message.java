package com.wzz.mail.entity;

import lombok.Data;

import java.util.Date;

/**
 * <p>Title: Message</p>
 * <p>Description: 消息实体类，保存消息实体
 * 重新发送消息的时候使用</p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/9/3 14:59
 */
@Data
public class Message {

    private String msgId;

    private String msg;

    private String exchange;

    private String routingKey;

    private Integer status;

    private Integer tryCount;

    private Date nextTryTime;

    private Date updateTime;


    public Message(String msgId, String msg, String exchange, String routingKey) {
        this.msgId = msgId;
        this.msg = msg;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }
}
