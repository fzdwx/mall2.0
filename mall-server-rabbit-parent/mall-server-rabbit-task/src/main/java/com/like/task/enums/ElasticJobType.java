package com.like.task.enums;

/**
 * ElasticJob 类型
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-13 18:33
 */
public enum ElasticJobType {
    SIMPLE("simpleJob", "简单类型"),
    DATAFLOW("dataflowJob", "流式类型"),
    SCRIPT("scriptJob", "脚本类型");

    private String desc;
    private String type;

    private ElasticJobType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
