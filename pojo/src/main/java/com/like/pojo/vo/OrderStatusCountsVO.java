package com.like.pojo.vo;

import lombok.Data;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-27 18:02
 */
@Data
public class OrderStatusCountsVO {

    private Integer waitCommentCounts;
    private Integer waitDeliverCounts;
    private Integer waitPayCounts;
    private Integer waitReceiveCounts;
}
