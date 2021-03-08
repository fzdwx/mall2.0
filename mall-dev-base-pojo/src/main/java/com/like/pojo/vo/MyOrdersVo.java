package com.like.pojo.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 用户中心，查询我的订单
 * @since 2021-02-26 13:14
 */
@Data
public class MyOrdersVo {

    private String orderId;
    private Date createdTime;
    private Integer payMethod;
    private Integer realPayAmount;
    private Integer postAmount;
    private Integer isComment;
    private Integer orderStatus;

    private List<MySubOrderItemVo> subOrders;
}
