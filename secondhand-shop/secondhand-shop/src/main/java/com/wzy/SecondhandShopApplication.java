package com.wzy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;

/**
 * @Package: com.wzy
 * @Author: Clarence1
 * @Date: 2019/11/26 20:24
 */
@SpringBootApplication
@MapperScan({"com.wzy.mapper"})
@EnableSwagger2
public class SecondhandShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondhandShopApplication.class, args);
    }

    /**
     * 文件上传配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("10240KB"); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }



}
