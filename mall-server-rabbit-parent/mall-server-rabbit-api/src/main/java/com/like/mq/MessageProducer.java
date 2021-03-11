package com.like.mq;

import com.like.mq.base.Message;
import com.like.mq.ex.MessageRunTimeException;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-11 16:01
 * @deprecated 消息生产者
 */
public interface MessageProducer {

    /**
     * 发送
     * @param message 消息
     * @throws MessageRunTimeException 消息运行时异常
     */
    void send(Message message) throws MessageRunTimeException;

    /**
     * 发送批量消息
     * @param messages 消息
     * @throws MessageRunTimeException 消息运行时异常
     */
    void send(List<Message> messages) throws MessageRunTimeException;

    /**
     * 发送
     * @param message 消息
     * @param callback 回调
     * @throws MessageRunTimeException 消息运行时异常
     */
    void send(Message message, SendCallback callback) throws MessageRunTimeException;
}
