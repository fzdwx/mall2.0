package com.like.pojo.vo;

import lombok.Data;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-27 19:02
 */
@Data
public class OrderTrendVO {

    private String closeTime;
    private String commentTime;
    private String createdTime;
    private String deliverTime;
    private String orderId;
    private Integer orderStatus;
    private String payTime;
    private String successTime;
}
