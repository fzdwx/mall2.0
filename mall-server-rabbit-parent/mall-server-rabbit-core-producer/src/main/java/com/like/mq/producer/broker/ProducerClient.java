package com.like.mq.producer.broker;

import com.google.common.base.Preconditions;
import com.like.mq.MessageProducer;
import com.like.mq.SendCallback;
import com.like.mq.base.Message;
import com.like.mq.ex.MessageRunTimeException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-11 16:17
 * @deprecated 发送消息的实际实现类
 */
@Component
public class ProducerClient implements MessageProducer {

    @Override
    public void send(Message message) throws MessageRunTimeException {
        // 1.检测消息的主题必须为空
        Preconditions.checkNotNull(message.getTopic());
        // 2.根据不同类型选择发送方式
        String messageType = message.getMessageType();


    }

    @Override
    public void send(List<Message> messages) throws MessageRunTimeException {

    }

    @Override
    public void send(Message message, SendCallback callback) throws MessageRunTimeException {

    }
}
