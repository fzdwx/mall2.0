package com.like.service;

import com.like.pojo.bo.SubmitOrderBO;

public interface OrderService {

    /**
     * 创建订单
     *
     * @param submitOrder 提交订单的bo
     */
    void createOrder(SubmitOrderBO submitOrder);
}
