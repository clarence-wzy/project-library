package com.wzz.mail.dto;

/**
 * <p>Title: EmailResult</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/9/5 10:07
 */
public class EmailResult {

    private int code;

    private String codeInformation;

    public EmailResult(int code, String codeInformation) {
        this.code = code;
        this.codeInformation = codeInformation;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCodeInformation() {
        return codeInformation;
    }

    public void setCodeInformation(String codeInformation) {
        this.codeInformation = codeInformation;
    }
}
