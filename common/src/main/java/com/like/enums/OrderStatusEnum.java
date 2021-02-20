package com.like.enums;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-19 17:28
 */
public enum OrderStatusEnum {
    WAIT_PAY(10, "待付款"),
    WAIT_DELIVER(20, "已付款，等待发货"),
    WAIT_RECEIVE(30, "已发货，待收货"),
    SUCCESS(40, "交易成功"),
    CLOSE(50, "交易关闭");

    public final Integer type;
    public final String value;

    OrderStatusEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }
}
