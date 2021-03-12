package com.like.mq.producer.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 服务器接收到的消息记录的实体类
 * @since 2021-03-12 19:37
 */
@Data
public class BrokerMessage {

    private Date createTime;
    private String message;
    private String messageId;
    private Date nextRetry;
    private String status;
    private int tryCount;
    private Date updateTime;
}
