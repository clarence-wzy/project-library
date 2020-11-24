package com.wzz.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created on 2019/8/28 by Tinchi
 *
 * @Description:    选项的实体类(内嵌文档)
 **/

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class Option implements Serializable {

    private static final long serialVersionUID=2L;

    @JSONField(ordinal = 1)
    private Integer index;
    @JSONField(ordinal = 2)
    private String label;
    @JSONField(ordinal = 3)
    private Double value;
    @JSONField(ordinal = 4)
    private String type;
}
