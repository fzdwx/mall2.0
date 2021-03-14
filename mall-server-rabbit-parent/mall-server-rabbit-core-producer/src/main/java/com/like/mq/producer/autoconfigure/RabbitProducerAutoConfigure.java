package com.like.mq.producer.autoconfigure;

import com.like.task.annotation.EnableElasticJob;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description rabbitMQ 自动装配类
 * @since 2021-03-11 16:14
 */
@Configuration
@ComponentScan({"com.like.mq.producer"})
@EnableElasticJob
public class RabbitProducerAutoConfigure {

}
