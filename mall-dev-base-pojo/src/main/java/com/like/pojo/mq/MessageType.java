package com.like.pojo.mq;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-10 19:24
 * @deprecated mq里面的消息类型
 */
public final class MessageType {

    /** 快速消息：不需要保证消息的可靠性，也不需要confirm确认回调 */
    public static final String RAPID = "0";
    /** 确认消息：不需要保证消息的可靠性，但是要confirm确认回调 */
    public static final String CONFIRM = "1";
    /** 可靠性消息：保证消息的100%可靠性投递，不允许消息的丢失 保障数据库和所发的消息是原子的(最终一致的) */
    public static final String RELIABILITY = "2";

}
