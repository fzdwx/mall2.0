package com.like.mq.producer.service;

import com.like.mq.producer.constant.BrokerMessageStatus;
import com.like.mq.producer.mapper.BrokerMessageMapper;
import com.like.mq.producer.pojo.BrokerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 消息记录存储
 * @since 2021-03-13 15:21
 */
@Service
public class MessageStoreService {

    @Autowired
    private BrokerMessageMapper brokerMessageMapper;


    /**
     * 插入
     * @param brokerMessage 代理消息
     * @return int
     */
    public int insert(BrokerMessage brokerMessage) {
        return brokerMessageMapper.insert(brokerMessage);
    }

    /**
     * ack 成功
     * @param messageId 消息id
     */
    public void success(String messageId) {
        brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_OK, new Date());
    }

    /**
     * ack 失败
     * @param messageId 消息id
     */
    public void failure(String messageId) {
        brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_FAIL, new Date());
    }
}
