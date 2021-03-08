package com.like.enums;

public enum CategoryLevel {
    ROOT(1, "根节点"), SECOND(2, "第二"), THIRD(3, "第三");

    public final Integer code;
    public final String value;

    CategoryLevel(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
