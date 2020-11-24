package com.wzz.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created on 2019/9/17
 *
 * @Author: tinchi
 * @Description:
 **/

@Document(collection = "analyse")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class Analyse implements Serializable {
    private static final long serialVersionUID=2L;

    @JSONField(ordinal = 1)
    @NotNull
    private String answerId;

    @JSONField(ordinal = 2)
    @NotNull
    private Long qCode;

    @JSONField(ordinal = 3)
    @NotNull
    private String qTitle;

    @JSONField(ordinal = 4)
    @NotNull
    private Integer spendTime;

    @JSONField(ordinal = 5)
    private List<AnswerData> answerData;

}
