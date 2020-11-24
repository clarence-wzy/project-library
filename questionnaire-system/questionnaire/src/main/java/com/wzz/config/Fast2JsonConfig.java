package com.wzz.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 * @date  2019/8/29
 */
@Configuration
public class Fast2JsonConfig {
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){
        //创建转换对象
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //创建配置文件对象
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.PrettyFormat);
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        converter.setFastJsonConfig(config);
        return new HttpMessageConverters(converter);
    }
}
