package com.like.mq.producer.broker;

import com.google.common.base.Preconditions;
import com.like.mq.MessageProducer;
import com.like.mq.SendCallback;
import com.like.mq.base.Message;
import com.like.mq.base.MessageType;
import com.like.mq.ex.MessageRunTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @@Description 发送消息的实际实现类
 * @since 2021-03-11 16:17
 */
@Component
@Slf4j
public class ProducerClient implements MessageProducer {

    @Autowired
    private RabbitBroker rabbitBroker;

    @Override
    public void send(Message message) throws MessageRunTimeException {
        // 1.检测消息的主题必须为空
        Preconditions.checkNotNull(message.getTopic());
        // 2.根据不同类型选择发送方式
        String messageType = message.getMessageType();
        switch (messageType) {
            case MessageType.RAPID:
                rabbitBroker.rapidSend(message);
                break;
            case MessageType.CONFIRM:
                rabbitBroker.confirmSend(message);
                break;
            case MessageType.RELIABILITY:
                rabbitBroker.reliabilitySend(message);
                break;
            default:
                log.error("sender error not found messageType:{}", messageType);
        }

    }

    @Override
    public void send(List<Message> messages) throws MessageRunTimeException {

    }

    @Override
    public void send(Message message, SendCallback callback) throws MessageRunTimeException {

    }
}
