package com.like.mq.producer.constant;

/**
 * 代理消息状态 对应 broker message 的 status
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-13 15:25
 */
public interface BrokerMessageStatus {

    String SENDING = "0";
    String SEND_OK = "1";
    String SEND_FAIL = "2";
    String SEND_A_MOMENT = "3";


}
