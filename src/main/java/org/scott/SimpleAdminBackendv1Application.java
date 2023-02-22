package org.scott;

import org.scott.utils.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimpleAdminBackendv1Application {

    public static void main(String[] args) {
        SpringApplication.run(SimpleAdminBackendv1Application.class, args);
    }

    @Bean
    public SpringContextHolder springContextHolder(){
        // 在Spring Boot中注册 SpringContentHolder
        return new SpringContextHolder();
    }

}
