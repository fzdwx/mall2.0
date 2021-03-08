package com.like.enums;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-09 17:00
 */
public enum YesOrNo {
    YES(1, "是"), NO(0, "否");

    public final Integer code;
    public final String value;

    YesOrNo(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
