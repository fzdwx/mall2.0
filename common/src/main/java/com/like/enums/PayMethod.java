package com.like.enums;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-09 17:00
 */
public enum PayMethod {
    YES(1, "微信"), NO(2, "支付宝");

    public final Integer type;
    public final String value;

    PayMethod(int type, String value) {
        this.type = type;
        this.value = value;
    }
}
