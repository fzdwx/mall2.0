package com.like.mq;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-11 16:02
 * @deprecated 发送消息的回调函数处理
 */
public interface SendCallback {

    void onSuccess();

    void onFailure();
}
