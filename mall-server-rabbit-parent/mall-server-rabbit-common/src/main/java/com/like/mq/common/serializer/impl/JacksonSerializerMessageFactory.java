package com.like.mq.common.serializer.impl;

import com.like.mq.base.Message;
import com.like.mq.common.serializer.Serializer;
import com.like.mq.common.serializer.SerializerFactory;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 针对mq中message的序列化器
 * @since 2021-03-11 18:19
 */
public class JacksonSerializerMessageFactory implements SerializerFactory {


    /** 饿汉式 实例 */
    public static final JacksonSerializerMessageFactory instance = new JacksonSerializerMessageFactory();

    @Override
    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }
}
