package com.like;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-05 17:02
 */
@SpringBootApplication
@MapperScan(basePackages = "com.like.mapper")
@ComponentScan(basePackages = {"com.like", "org.n3r.idworker"})
public class MallMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallMainApplication.class, args);

        System.out.println("");
        System.out.println("");
        System.out.println("                                                欢迎使用 Like Mall 商城后台");
        System.out.println("                                       后台文档地址: http://localhost:8888/doc.html");
    }
}
