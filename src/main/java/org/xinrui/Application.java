package org.xinrui;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

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

