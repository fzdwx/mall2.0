package com.like.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-21 14:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantOrdersVO {

    private String merchantOrderId;     // 订单id
    private String merchantUserId;      // 用户id
    private Integer amount;             // 支付金额
    private Integer payMethod;          // 支付方式
    private String returnUrl;           // 回调通知url
}
