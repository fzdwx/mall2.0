package com.like.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mybatis Generator 2021/02/16
 */
@ApiModel(value = "com.like.pojo.ItemsComments")
@TableName(value = "items_comments")
@Data
public class ItemsComments implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String COL_USER_ID = "user_id";
    public static final String COL_ITEM_ID = "item_id";
    public static final String COL_ITEM_NAME = "item_name";
    public static final String COL_ITEM_SPEC_ID = "item_spec_id";
    public static final String COL_SEPC_NAME = "sepc_name";
    public static final String COL_COMMENT_LEVEL = "comment_level";
    public static final String COL_CONTENT = "content";
    public static final String COL_CREATED_TIME = "created_time";
    public static final String COL_UPDATED_TIME = "updated_time";
    /**
     * id主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id主键")
    private String id;
    /**
     * 用户id 用户名须脱敏
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id 用户名须脱敏")
    private String userId;
    /**
     * 商品id
     */
    @TableField(value = "item_id")
    @ApiModelProperty(value = "商品id")
    private String itemId;
    /**
     * 商品名称
     */
    @TableField(value = "item_name")
    @ApiModelProperty(value = "商品名称")
    private String itemName;
    /**
     * 商品规格id 可为空
     */
    @TableField(value = "item_spec_id")
    @ApiModelProperty(value = "商品规格id 可为空")
    private String itemSpecId;
    /**
     * 规格名称 可为空
     */
    @TableField(value = "sepc_name")
    @ApiModelProperty(value = "规格名称 可为空")
    private String sepcName;
    /**
     * 评价等级 1：好评 2：中评 3：差评
     */
    @TableField(value = "comment_level")
    @ApiModelProperty(value = "评价等级 1：好评 2：中评 3：差评")
    private Integer commentLevel;
    /**
     * 评价内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value = "评价内容")
    private String content;
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


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSpecId() {
        return itemSpecId;
    }

    public void setItemSpecId(String itemSpecId) {
        this.itemSpecId = itemSpecId;
    }

    public String getSepcName() {
        return sepcName;
    }

    public void setSepcName(String sepcName) {
        this.sepcName = sepcName;
    }

    public Integer getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(Integer commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}