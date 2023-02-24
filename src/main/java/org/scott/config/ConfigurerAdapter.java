package org.scott.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * project name  simple-admin-backedv1
 * filename  ConfigurerAdapter
 *
 * @author liscott
 * @date 2023/2/22 11:54
 * description  TODO
 */
@Configuration
@EnableWebMvc
public class ConfigurerAdapter implements WebMvcConfigurer {

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        // 当前端返回到一个实体类没有的属性时，也不抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        converter.setObjectMapper(mapper);
        return converter;
    }

    /** 添加转换器 */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        /**
         * 将自定义的时间格式转换器添加到转换器列表中
         * 这样jackson 格式化时候 一旦遇到 Date 类型 就会转换成 我们定义的格式
         */
        converters.add(jackson2HttpMessageConverter());
    }

    /** 过滤静态资源 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
    }

}
