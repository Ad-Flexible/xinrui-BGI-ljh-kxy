package org.xinrui.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate配置类
 * 用于配置和提供RestTemplate Bean实例
 * RestTemplate是Spring框架提供的用于执行HTTP请求的同步客户端工具
 */
@Configuration
public class RestTemplateConfig {

    // 从配置读取超时时间
    @Value("${rest.template.connect-timeout}")
    private int CONNECT_TIMEOUT ;

    @Value("${rest.template.read-timeout}")
    private int READ_TIMEOUT ;


    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(CONNECT_TIMEOUT);
        factory.setReadTimeout(READ_TIMEOUT);
        return new RestTemplate(factory);
    }
}
