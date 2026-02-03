package org.xinrui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用程序主类
 *
 * @SpringBootApplication 注解表示这是一个Spring Boot应用程序入口
 * 包含了 @Configuration、@EnableAutoConfiguration 和 @ComponentScan 的组合功能
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

