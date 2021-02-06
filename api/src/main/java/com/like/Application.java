package com.like;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-05 17:02
 */
@SpringBootApplication
@MapperScan(basePackages = "com.like.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
