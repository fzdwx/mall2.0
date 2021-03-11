package com.like.mq.common.serializer.converter;


import com.google.common.base.Preconditions;
import com.like.mq.common.serializer.Serializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: message 转换器
 * @since 2021-03-11 18:22
 */
public class GenericMessageConverter implements MessageConverter {

    private Serializer serializer;

    public GenericMessageConverter(Serializer serializer) {
        Preconditions.checkNotNull(serializer);

        this.serializer = serializer;
    }

    /**
     * 把我们定义的message转换成mq能识别的message
     * @param o o
     * @param messageProperties 消息属性
     * @return {@link Message}
     * @throws MessageConversionException 消息转换异常
     */
    @Override
    public Message toMessage(
            Object o, MessageProperties messageProperties) throws MessageConversionException {
        return new Message(serializer.serializeRaw(o), messageProperties);
    }

    /**
     * 转换成我们顶一个message类
     * @param message 消息
     * @return {@link Object}
     * @throws MessageConversionException 消息转换异常
     */
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return serializer.deSerialize(message.getBody());
    }
}
