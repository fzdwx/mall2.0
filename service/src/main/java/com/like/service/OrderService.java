package com.like.service;

import com.like.pojo.bo.SubmitOrderBO;

public interface OrderService {

    /**
     * 创建订单
     *
     * @param submitOrder 提交订单的bo
     * @return
     */
    String createOrder(SubmitOrderBO submitOrder);

    /**
     * 更新订单状态
     *
     * @param merchantOrderId 商人订单id
     * @param status          状态
     */
    void updateOrderStatus(String merchantOrderId, Integer status);
}
