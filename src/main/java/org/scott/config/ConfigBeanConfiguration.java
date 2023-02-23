package org.scott.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * project name  simple-admin-backedv1
 * filename  ConfigBeanConfiguration
 * @author liscott
 * @date 2023/2/23 9:58
 * description  配置文件转换Pojo类的 统一配置 类
 */
@Configuration
public class ConfigBeanConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "jwt")
    public SecurityProperties securityProperties() {
        return new SecurityProperties();
    }
}
