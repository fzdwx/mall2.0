package com.like.mq.common.serializer;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description mq message 序列化
 * @since 2021-03-11 18:07
 */
public interface Serializer {

    /**
     * 序列化
     * @param source 源
     * @return {@link byte[]}
     */
    byte[] serializeRaw(Object source);

    /**
     * 序列化
     * @param source 源
     * @return {@link String}
     */
    String serialize(Object source);

    /**
     * 反序列化
     * @param content 内容
     * @return {@link T}
     */
    <T> T deSerialize(String content);

    /**
     * 反序列化
     * @param content 内容
     * @return {@link T}
     */
    <T> T deSerialize(byte[] content);
}
