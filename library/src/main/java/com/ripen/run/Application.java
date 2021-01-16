package com.ripen.run;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动类
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:01
 * @since 2021/01/09
 */
@SpringBootApplication(scanBasePackages = {"com.ripen"})
@MapperScan({"com.ripen.dao.mapper"})
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
