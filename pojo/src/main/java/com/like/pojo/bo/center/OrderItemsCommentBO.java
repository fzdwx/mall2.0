package com.like.pojo.bo.center;

import lombok.Data;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 前端商品评论保存
 * @since 2021-02-27 13:34
 */
@Data
public class OrderItemsCommentBO {

    private String commentId;
    private String itemId;
    private String itemName;
    private String itemSpecId;
    private String itemSpecName;
    private Integer commentLevel;
    private String content;
}
