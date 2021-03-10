package com.like.mq.producer.component;


import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-10 17:05
 * @deprecated mq的消息接收者
 */
@Component
@Slf4j
public class RabbitReceive {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-1", durable = "true"),
            exchange = @Exchange(name = "exchange-1",
                                 value = "mall.*",
                                 durable = "true",
                                 type = "topic",
                                 ignoreDeclarationExceptions = "true")
    ))
    public void onMessage(Message message, Channel channel) throws Exception {
        log.info("消费消息:{}", message.getPayload());

        // 确认收到消息，手动ACK
        channel.basicAck(((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG)), true);
    }
}
