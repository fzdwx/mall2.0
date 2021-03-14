package com.like.mq.producer.broker.impl;

import com.like.mq.base.Message;
import com.like.mq.base.MessageType;
import com.like.mq.producer.broker.RabbitBroker;
import com.like.mq.producer.constant.BrokerMessageConst;
import com.like.mq.producer.constant.BrokerMessageStatus;
import com.like.mq.producer.pojo.BrokerMessage;
import com.like.mq.producer.pool.AsyncBasePool;
import com.like.mq.producer.pool.RabbitTemplateContainer;
import com.like.mq.producer.service.MessageStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

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

    /** 信息存储服务 */
    @Autowired
    private MessageStoreService messageStoreService;

    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        doSendMessage(message);
    }

    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.CONFIRM);
        // 在获取rabbitMq template的时候 会根据 MessageType.CONFIRM 添加确认回调方法
        doSendMessage(message);
    }

    @Override
    public void reliabilitySend(Message message) {
        message.setMessageType(MessageType.RELIABILITY);
        // 0.先查看这个消息是否发过
        BrokerMessage dbBm = messageStoreService.getByMessageId(message.getMessageId());
        if (dbBm == null) {
            // 1.记录发送消息的日志
            Date now = new Date();
            BrokerMessage brokerMessage = new BrokerMessage();
            brokerMessage.setMessageId(message.getMessageId());
            brokerMessage.setStatus(BrokerMessageStatus.SENDING);
            brokerMessage.setMessage(message);
            brokerMessage.setNextRetry(DateUtils.addMinutes(now, BrokerMessageConst.TIME_OUT));
            brokerMessage.setCreateTime(now);
            brokerMessage.setUpdateTime(now);
            // TryCount 在第一次发的时候不用设置
            //brokerMessage.setTryCount(0);
            messageStoreService.insert(brokerMessage);
        }
        // 2.执行发送消息的逻辑
        doSendMessage(message);
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
            CorrelationData correlationData =
                    new CorrelationData(
                            String.format("%s#%s#%s",
                                          message.getMessageId(),
                                          System.currentTimeMillis(),
                                          message.getMessageType()));
            rabbitContainer.get(message).convertAndSend(topic, routingKey, message, correlationData);
            log.info("#RabbitBrokerImpl.doSendMessage# send to mq,messageId:{}", message.getMessageId());
        });

    }
}
