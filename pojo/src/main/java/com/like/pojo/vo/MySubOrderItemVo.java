package com.like.pojo.vo;

import lombok.Data;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 用户中心，查询我的订单的子订单
 * @since 2021-02-26 13:15
 */
@Data
public class MySubOrderItemVo {

    private String itemId;
    private String itemImg;
    private String itemName;
    private Integer itemSpecId;
    private String itemSpecName;
    private Integer buyCounts;
    private Integer price;
}
