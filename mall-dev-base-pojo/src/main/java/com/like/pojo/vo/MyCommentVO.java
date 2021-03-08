package com.like.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 查看我的评价
 * @since 2021-02-27 15:28
 */
@Data
public class MyCommentVO {
    private String commendId;
    private String content;
    private Date createdTime;
    private String itemId;
    private String itemName;
    private String specName;
    private String itemImg;
}
