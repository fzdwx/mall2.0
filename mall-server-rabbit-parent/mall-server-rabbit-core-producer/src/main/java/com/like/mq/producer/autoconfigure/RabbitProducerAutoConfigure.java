package com.like.mq.producer.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-11 16:14
 * @deprecated rabbitMQ 自动装配类
 */
@Configuration
@ComponentScan({"com.like.mq.producer"})
public class RabbitProducerAutoConfigure {

}
