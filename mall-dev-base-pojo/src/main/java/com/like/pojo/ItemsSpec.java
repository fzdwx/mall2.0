package com.like.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Mybatis Generator 2021/02/16
 */
@ApiModel(value = "com.like.pojo.ItemsSpec")
@Data
@TableName(value = "items_spec")
public class ItemsSpec implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String COL_ITEM_ID = "item_id";
    public static final String COL_NAME = "name";
    public static final String COL_STOCK = "stock";
    public static final String COL_DISCOUNTS = "discounts";
    public static final String COL_PRICE_DISCOUNT = "price_discount";
    public static final String COL_PRICE_NORMAL = "price_normal";
    public static final String COL_CREATED_TIME = "created_time";
    public static final String COL_UPDATED_TIME = "updated_time";
    /**
     * 商品规格id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "商品规格id")
    private String id;
    /**
     * 商品外键id
     */
    @TableField(value = "item_id")
    @ApiModelProperty(value = "商品外键id")
    private String itemId;
    /**
     * 规格名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "规格名称")
    private String name;
    /**
     * 库存
     */
    @TableField(value = "stock")
    @ApiModelProperty(value = "库存")
    private Integer stock;
    /**
     * 折扣力度
     */
    @TableField(value = "discounts")
    @ApiModelProperty(value = "折扣力度")
    private BigDecimal discounts;
    /**
     * 优惠价
     */
    @TableField(value = "price_discount")
    @ApiModelProperty(value = "优惠价")
    private Integer priceDiscount;
    /**
     * 原价
     */
    @TableField(value = "price_normal")
    @ApiModelProperty(value = "原价")
    private Integer priceNormal;
    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;
}