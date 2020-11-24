package com.wzz.mail.common;

/**
 * <p>Title: Constant</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/9/3 16:26
 */
public class Constant {
    public interface MessageStatus {
        Integer DELIVERING = 0;// 消息投递中
        Integer DELIVER_SUCCESS = 1;// 投递成功
        Integer DELIVER_FAIL = 2;// 投递失败
        Integer CONSUMED_SUCCESS = 3;// 已消费
    }

    public interface EmailStatus {
        Integer SUCCESS = 1;    //发送成功
        Integer FAIL = 2;   //发送失败

        String SUCCESS_INFO = "邮件发送成功";
        String FAIL_INFO = "邮件发送失败";
    }
}
