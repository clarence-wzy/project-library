package com.wzz.mail.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * <p>Title: Mail</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/9/2 11:07
 */
@Data
public class Mail {

    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式不正确")
    private String to;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "正文不能为空")
    private String content;

    /**
     * 消息ID
     */
    private String msgId;

    public Mail(@Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式不正确") String to, @NotBlank(message = "标题不能为空") String title, @NotBlank(message = "正文不能为空") String content) {
        this.to = to;
        this.title = title;
        this.content = content;
    }

    /**
     * 一定要加上无参构造方法，否则JVM不会添加默认的无参构造方法，导致反序列化的时候失败
     */
    public Mail(){ }
}
