package com.like.mq.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-10 16:55
 */
@SpringBootApplication
public class RabbitMQProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQProducerApplication.class, args);
    }
}
