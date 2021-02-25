package com.like.config;

import com.like.resource.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-21 15:09
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    FileUpload fileUpload;

    /**
     * 实现静态资源的映射
     *
     * @param registry 注册表
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                // 怎么访问：比如/image/userFace/1123.jpg
                .addResourceLocations("file:" + "D:\\Java\\project\\stduyproject\\mall2.0\\api\\src\\main\\resources\\static\\image") // 映射本地文件
                .addResourceLocations("classpath:/META-INF/resources/"); // 映射swagger

    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
