package com.like.pojo.mq.ex;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-10 20:11
 */
public class MessageRunTimeException extends RuntimeException {
    public MessageRunTimeException() {
    }

    public MessageRunTimeException(String message) {
        super(message);
    }

    public MessageRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageRunTimeException(Throwable cause) {
        super(cause);
    }

    public MessageRunTimeException(
            String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
