package com.like.mq.common.serializer.converter;

import com.google.common.base.Preconditions;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 封装一层，对message进行增强
 * @since 2021-03-11 18:41
 */
public class RabbitMessageConverter implements MessageConverter {

    /** 委托 */
    private GenericMessageConverter delegate;

    public final String defaultExpire = String.valueOf(24 * 60 * 60 * 1000);

    public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
        Preconditions.checkNotNull(genericMessageConverter);

        this.delegate = genericMessageConverter;
    }

    @Override
    public Message toMessage(
            Object o, MessageProperties messageProperties) throws MessageConversionException {
        // doSomething ...
        messageProperties.setExpiration(defaultExpire);
        return delegate.toMessage(o, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return delegate.fromMessage(message);
    }
}
