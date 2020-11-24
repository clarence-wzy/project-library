package com.wzz.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created on 2019/9/17
 *
 * @Author: tinchi
 * @Description:
 **/

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class AnswerData implements Serializable {
    private static final long serialVersionUID = 2L;

    @JSONField(ordinal = 1)
    private Integer qId;

    @JSONField(ordinal = 2)
    private Integer optionId;

    @JSONField(ordinal = 3)
    private String value;

}
