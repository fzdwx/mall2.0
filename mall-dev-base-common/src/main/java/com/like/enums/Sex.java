package com.like.enums;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-07 15:33
 */
public enum Sex {

    woman(0, "女"), mam(1, "男"), secret(2, "保密");

    public final Integer code;
    public final String value;

    Sex(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
