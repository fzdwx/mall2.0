package com.like;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-05 17:02
 */
@SpringBootApplication
@MapperScan(basePackages = "com.like.mapper")
public class MallMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallMainApplication.class, args);
    }
}
