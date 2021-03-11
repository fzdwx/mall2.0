package com.like.mq;

import com.like.mq.base.Message;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-11 16:03
 * @deprecated 消费者监听消息
 */
public interface MessageListener {
    /**
     * 在消息
     * @param message 消息
     */
    void onMessage(Message message);
}
