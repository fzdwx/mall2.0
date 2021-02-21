package com.like.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.like.pojo.OrderVO;
import com.like.pojo.Orders;
import com.like.pojo.bo.SubmitOrderBO;

public interface OrderService extends IService<Orders> {

    /**
     * 创建订单
     *
     * @param submitOrder 提交订单的bo
     * @return
     */
    OrderVO createOrder(SubmitOrderBO submitOrder);

    /**
     * 更新订单状态
     *
     * @param merchantOrderId 商人订单id
     * @param status          状态
     */
    void updateOrderStatus(String merchantOrderId, Integer status);
}
