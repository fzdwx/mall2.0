package com.like.enums;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-19 17:28
 */
public enum OrderStatusEnum {
    WAIT_PAY(1, "待付款"),
    WAIT_DELIVER(2, "已付款，等待发货"),
    WAIT_RECEIVE(3, "已发货，待收货"),
    SUCCESS(3, "交易成功"),
    CLOSE(3, "交易关闭");

    public final Integer type;
    public final String value;

    OrderStatusEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }
}
