package com.wzy.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * @Package: com.wzy.util
 * @Author: Clarence1
 * @Date: 2019/12/27 9:43
 */
public class CustomDoubleSerialize extends JsonSerializer<Double> {

    private DecimalFormat df = new DecimalFormat("##.00");

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        if(value != null) {
            gen.writeString(df.format(value));  
        }
    }
}
