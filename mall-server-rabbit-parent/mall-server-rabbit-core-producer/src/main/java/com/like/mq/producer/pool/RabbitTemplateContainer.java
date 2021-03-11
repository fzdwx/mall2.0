package com.like.mq.producer.pool;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.like.mq.base.Message;
import com.like.mq.base.MessageType;
import com.like.mq.common.serializer.SerializerFactory;
import com.like.mq.common.serializer.converter.GenericMessageConverter;
import com.like.mq.common.serializer.converter.RabbitMessageConverter;
import com.like.mq.common.serializer.impl.JacksonSerializerMessageFactory;
import com.like.mq.ex.MessageRunTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * RabbitMQ template 池化
 * 每一个topic对应一个RabbitTemplate
 * 1. 提高发送效率
 * 2. 根据不同需求定制RabbitTemplate
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-11 16:59
 */
@Component
@Slf4j
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback {

    /** mq连接工厂 */
    @Autowired
    private ConnectionFactory connectionFactory;

    /** mq容器 */
    private Map<String /* TOPIC */, RabbitTemplate> mqContainer = Maps.newConcurrentMap();

    private Splitter splitter = Splitter.on("#");

    private SerializerFactory serializerFactory = JacksonSerializerMessageFactory.instance;

    public RabbitTemplate get(Message message) throws MessageRunTimeException {
        Preconditions.checkNotNull(message);
        String topic = message.getTopic();
        String routingKey = message.getRoutingKey();
        String messageType = message.getMessageType();

        // 1.存在就返回
        RabbitTemplate rabbitTemplate = mqContainer.get(topic);
        if (rabbitTemplate != null) {
            return rabbitTemplate;
        }
        // 2.不存在就重新创建
        log.info("#RabbitTemplateContainer.get# topic:{} is not exists,create one", topic);

        RabbitTemplate newTemplate = new RabbitTemplate(connectionFactory);
        newTemplate.setExchange(topic);                               // 设置交换机
        newTemplate.setRetryTemplate(new RetryTemplate());           // 设置重试模板
        newTemplate.setRoutingKey(routingKey);                      // 设置路由键
        // 对message的序列化
        newTemplate.setMessageConverter(
                new RabbitMessageConverter(new GenericMessageConverter(serializerFactory.create())));
        if (!MessageType.RAPID.equalsIgnoreCase(messageType)) {
            newTemplate.setConfirmCallback(this);         // 设置确认回调
            //newTemplate.setChannelTransacted(true);      // 事务
        }
        mqContainer.putIfAbsent(topic, newTemplate);

        return mqContainer.get(topic);
    }

    /**
     * 确认
     * TODO: 2021/3/11 具体的消息应答 除迅速消息 MessageType.RAPID
     * @param correlationData 关联数据
     * @param ack 消
     * @param cause 导致
     */
    @Override
    public void confirm(
            CorrelationData correlationData, boolean ack, String cause) {
        List<String> strings = splitter.splitToList(correlationData.getId());
        String messageId = strings.get(0);
        long sendTime = Long.parseLong(strings.get(1));

        if (ack) {
            log.info("send message ack is OK,confirm messageId:{},sendTime:{}", messageId, sendTime);
        } else {
            log.error("send message ack is Fail,confirm messageId:{},sendTime:{}", messageId, sendTime);
        }
    }
}
