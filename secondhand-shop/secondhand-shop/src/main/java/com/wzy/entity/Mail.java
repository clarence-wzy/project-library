package com.wzy.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Package: com.wzy.entity
 * @Author: Clarence1
 * @Date: 2019/9/22 21:10
 */
@Data
public class Mail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 邮件收件人
     */
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式不正确")
    private String recipient;

    /**
     * 邮件主题
     */
    @NotBlank(message = "标题不能为空")
    private String theme;

    /**
     * 邮件内容
     */
    @NotBlank(message = "正文不能为空")
    private String content;

    /**
     * 消息ID
     */
    private String msgId;

    public Mail(@Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式不正确") String recipient, @NotBlank(message = "标题不能为空") String theme, @NotBlank(message = "正文不能为空") String content) {
        this.recipient = recipient;
        this.theme = theme;
        this.content = content;
    }

    /**
     * 一定要加上无参构造方法，否则JVM不会添加默认的无参构造方法，导致反序列化的时候失败
     */
    public Mail(){ }

}
