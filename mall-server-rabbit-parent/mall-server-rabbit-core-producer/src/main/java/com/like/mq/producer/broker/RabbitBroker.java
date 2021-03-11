package com.like.mq.producer.broker;

import com.like.mq.base.Message;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-11 16:25
 * @deprecated 具体发送不同类型消息的接口
 */
public interface RabbitBroker {

    /**
     * 快速发送
     * @param message 消息
     */
    void rapidSend(Message message);

    /**
     * 确认发送
     * @param message 消息
     */
    void confirmSend(Message message);

    /**
     * 可靠性发送
     * @param message 消息
     */
    void reliabilitySend(Message message);

    /**
     * 发送消息
     */
    void sendMessages();
}
