package com.wzz.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created on 2019/8/28 by Tinchi
 *
 * @Description:    单个问题的文档实体类
 **/
@ApiModel
@Document(collection = "wen")   //mongodb注解，表示为文档
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {

    private static final long serialVersionUID=2L;

    @JSONField(ordinal=1)
    @Field("q_code")
    @NotNull(message = "q_code不能为空")
    private Integer qCode;

    @JSONField(ordinal=2)
    @Field("q_id")
    @NotNull(message = "q_id不能为空")
    private Integer qId;

    @JSONField(ordinal=3)
    @Field("q_name")
    @NotNull(message = "q_name不能为空")
    private String qName;

    @JSONField(ordinal=4)
    private Boolean q_isRequired;

    @JSONField(ordinal=5)
    private String q_type;

    @JSONField(ordinal=6)
    private List<Option> q_options;

    @JSONField(ordinal=7)
    private Boolean isScore;

    @JSONField(ordinal=8)
    private Double score;

}
