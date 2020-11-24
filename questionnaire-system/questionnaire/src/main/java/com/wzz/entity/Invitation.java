package com.wzz.entity;

import lombok.Data;

/**
 * @author Clarence1
 */
@Data
public class Invitation {

    private int invitationId;
    private String name;
    private String dept;
    private String email;
    private int emailStatus;
    private String emailStatusTime;
    private int recycleStatus;
    private String recycleStatusTime;
    private String url;
    private int questionnaireId;

}
