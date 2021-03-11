package com.like.mq.producer.broker.impl;

import com.like.mq.base.Message;
import com.like.mq.base.MessageType;
import com.like.mq.producer.broker.RabbitBroker;
import com.like.mq.producer.pool.AsyncBasePool;
import com.like.mq.producer.pool.RabbitTemplateContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description 真正实现发消息功能的类
 * @since 2021-03-11 16:28
 */
@Component
@Slf4j
public class RabbitBrokerImpl implements RabbitBroker {

    /** rabbitTemplate 容器 */
    @Autowired
    private RabbitTemplateContainer rabbitContainer;

    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        doSendMessage(message);
    }

    @Override
    public void confirmSend(Message message) {

    }

    @Override
    public void reliabilitySend(Message message) {

    }

    @Override
    public void sendMessages() {

    }

    /**
     * 发送消息 -> 使用异步线程池发送消息
     * @param message 消息
     */
    private void doSendMessage(Message message) {
        AsyncBasePool.submit(() -> {
            String routingKey = message.getRoutingKey();
            String topic = message.getTopic();
            CorrelationData correlationData = new CorrelationData(
                    String.format("%s#%s", message.getMessageId(), System.currentTimeMillis()));
            rabbitContainer.get(message).convertAndSend(topic, routingKey, message, correlationData);
            log.info("#RabbitBrokerImpl.doSendMessage# send to mq,messageId:{}", message.getMessageId());
        });

    }
}
