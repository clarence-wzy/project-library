package com.wzz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Demo class
 * title数据查询
 *
 * @author leewebi-n
 * @date 2019/9/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Questionnaire {


    private Long questionnaireId;
    @NotNull
    private Long userId;
    private String title;
    private String note;
    private String author;
    private int state;
    private int recovered;
    private Date createTime;
    private Date latestTime;
}
