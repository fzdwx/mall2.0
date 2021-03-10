package com.like.pojo.mq.ex;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-10 20:10
 * @deprecated mq消息异常
 */
public class MessageException extends Exception {

    public MessageException() {
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

    public MessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
