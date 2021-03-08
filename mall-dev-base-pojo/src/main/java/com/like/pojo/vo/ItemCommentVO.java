package com.like.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 商品评价vo
 * @since 2021-02-11 12:41
 */
@Data
public class ItemCommentVO {
    private Integer commentLevel;
    private String content;
    private String specName;
    private Date createTime;
    private String userFace;
    private String nickName;
}
