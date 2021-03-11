package com.like.mq.producer.broker;

import com.like.mq.MessageProducer;
import com.like.mq.SendCallback;
import com.like.mq.base.Message;
import com.like.mq.ex.MessageRunTimeException;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-11 16:17
 * @deprecated 消息生成客户端
 */
public class ProducerClient implements MessageProducer {

    @Override
    public void send(Message message) throws MessageRunTimeException {

    }

    @Override
    public void send(Message message, SendCallback callback) throws MessageRunTimeException {

    }

    @Override
    public void send(List<Message> messages) throws MessageRunTimeException {

    }
}
