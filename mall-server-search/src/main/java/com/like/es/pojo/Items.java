package com.like.es.pojo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-08 16:46
 * @deprecated es 中 保存的商品属性
 */
@Document(indexName = "mall-items")
@Data
public class Items {
    public static final String itemIdCol = "itemId";
    public static final String itemNameCol = "itemName";
    public static final String imgUrlCol = "imgUrl";
    public static final String priceCol = "price";
    public static final String sellCountsCol = "sellCounts";
    @Field(store = true, type = FieldType.Text, index = false)
    private String imgUrl;
    @Field(store = true, type = FieldType.Text, index = false)
    private String itemId;
    @Field(store = true, type = FieldType.Text, index = true)
    private String itemName;
    @Field(store = true, type = FieldType.Integer, index = true)
    private Integer price;
    @Field(store = true, type = FieldType.Integer, index = false)
    private Integer sellCounts;
}
