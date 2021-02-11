package com.like.enums;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 商品评价等级
 * @since 2021-02-11 11:28
 */
public enum CommentLevel {
    GOOD(1, "好评"), NORMAL(2, "差评"), BAD(3, "中评");

    public final Integer code;
    public final String value;

    CommentLevel(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
