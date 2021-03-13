package com.like.mq.producer.pojo;

import com.like.mq.base.Message;
import lombok.Data;

import java.util.Date;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 服务器接收到的消息记录的日志 实体类
 * @since 2021-03-12 19:37
 */
@Data
public class BrokerMessage {

    private String messageId;
    private String status;
    private Date createTime;
    private Message message;
    private Date updateTime;
    private Date nextRetry;
    private int tryCount;
}
