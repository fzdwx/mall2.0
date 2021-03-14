package com.like.mq.producer.broker;

import com.google.common.collect.Lists;
import com.like.mq.base.Message;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-14 17:30
 */
public class MessageHolder {

    public static final ThreadLocal<MessageHolder> holder = new ThreadLocal<>();
    private List<Message> messages = Lists.newArrayList();

    public static void addMessages(Message message) {

        holder.get().messages.add(message);
    }

    public static List<Message> clear() {
        List<Message> messages = holder.get().messages;

        holder.remove();

        return messages;
    }
}
