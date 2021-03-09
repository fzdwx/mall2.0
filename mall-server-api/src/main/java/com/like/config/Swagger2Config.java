package com.like.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-07 16:35
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(likeInfo())
                .select().apis(RequestHandlerSelectors
                        .basePackage("com.like.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Like的API信息
     *
     * @return {@link ApiInfo}
     */
    private ApiInfo likeInfo() {
        return new ApiInfoBuilder()
                .title("电商项目API接口文档")
                .contact(new Contact(
                        "like",
                        "https://github.com/likedeke",
                        "980650920@qq.com"))
                .description("专为商城项目提供的文档")
                .version("0.0.1")
                .termsOfServiceUrl("47.115.115.226")
                .build();
    }


}
