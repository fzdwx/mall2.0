package com.like.mq;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description 发送消息的回调函数处理
 * @since 2021-03-11 16:02
 */
public interface SendCallback {

    void onSuccess();

    void onFailure();
}
