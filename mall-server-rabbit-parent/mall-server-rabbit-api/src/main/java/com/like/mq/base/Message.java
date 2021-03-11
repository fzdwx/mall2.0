package com.like.mq.base;


import com.like.mq.ex.MessageRunTimeException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description 自定义的mq的消息类
 * @since 2021-03-10 19:17
 */
@Data
public class Message implements Serializable {

    public static final long serialVersionUID = 841277940410721237L;
    /** 消息的附加属性 */
    private Map<String, Object> attributes = new HashMap<>();
    /** 延迟消息的参数配置 */
    private int delayMillis;
    /** 唯一 消息id */
    private String messageId;
    /** 消息类型 */
    private String messageType = "";
    /** 消息路由的规则 */
    private String routingKey;
    /** 消息的主题 */
    private String topic;

    /**
     * 私有化全参构造器
     */
    private Message(
            String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMillis,
            String messageType) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delayMillis = delayMillis;
        this.messageType = messageType;
    }

    /**
     * 构建器
     * @author pdd20
     * @date 2021/03/10
     */
    static class Builder {
        private Map<String, Object> attributes = new HashMap<>();
        private int delayMillis;
        private String messageId;
        private String messageType = "";
        private String routingKey;
        private String topic;

        public static Builder builder() {
            return new Builder();
        }

        public Builder withMessageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder withTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder withRoutingKey(String routingKey) {
            this.routingKey = routingKey;
            return this;
        }

        public Builder withAttributes(Map<String, Object> attributes) {
            this.attributes = attributes;
            return this;
        }

        public Builder withAttribute(String key, Object val) {
            this.attributes.put(key, val);
            return this;
        }

        public Builder withDelayMillis(int delayMillis) {
            this.delayMillis = delayMillis;
            return this;
        }

        public Builder withMessageType(String messageType) {
            this.messageType = messageType;
            return this;
        }

        public Message get() {
            if (StringUtils.isBlank(messageId)) {
                messageId = UUID.randomUUID().toString();
            }
            if (topic == null) {
                throw new MessageRunTimeException("topic must not null");
            }
            return new Message(messageId, topic, routingKey, attributes, delayMillis, messageType);
        }
    }
}
