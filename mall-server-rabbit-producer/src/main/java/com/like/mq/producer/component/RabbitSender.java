package com.like.mq.producer.component;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-10 17:05
 * @deprecated mq的消息发送者
 */
@Component
@Slf4j
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /** 确认消息的回调监听接口，用户确定消息是否被broker所收到 */
    final RabbitTemplate.ConfirmCallback callback = new RabbitTemplate.ConfirmCallback() {
        /**
         * 确认
         * @param correlationData 作为唯一一个表示
         * @param ack 消 broker 是否落盘成功
         * @param cause 失败的异常信息
         */
        @Override
        public void confirm(
                CorrelationData correlationData, boolean ack, String cause) {

        }
    };

    /**
     * 对外发送消息的方法
     * @param message 消息
     * @param headers 额外附加属性
     * @throws Exception 异常
     */
    public void send(Object message, Map<String, Object> headers) throws Exception {

        // 1.构建消息头
        MessageHeaders msgHeaders = new MessageHeaders(headers);
        // 2.创建消息
        Message<?> msgS = MessageBuilder.createMessage(message, msgHeaders);
        // 3.设置回调
        rabbitTemplate.setConfirmCallback(callback);
        // 4.
        rabbitTemplate.convertAndSend("exchange-1", "mall.test", msgS,
                                      defaultMessagePostProcessor(),
                                      defaultCorrelationData());

    }

    /**
     * 默认消息后置处理器
     * @return {@link MessagePostProcessor}
     */
    private MessagePostProcessor defaultMessagePostProcessor() {
        return new MessagePostProcessor() {
            @Override
            public org.springframework.amqp.core.Message postProcessMessage(
                    org.springframework.amqp.core.Message message) throws AmqpException {
                log.info("---> post to do:{}", message);
                return message;
            }
        };
    }

    /**
     * 默认的关联数据 生成唯一消息id
     * @return {@link CorrelationData}
     */
    private CorrelationData defaultCorrelationData() {
        return new CorrelationData(UUID.randomUUID().toString());
    }
}
