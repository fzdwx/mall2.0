package com.like.mq.common.serializer;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description mq message 序列化工厂
 * @since 2021-03-11 18:05
 */
public interface SerializerFactory {

    /**
     * 创建序列化
     * @return {@link Serializer}
     */
    Serializer create();
}
