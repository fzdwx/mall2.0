package com.like.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.like.pojo.OrderStatus;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-19 17:33
 */
public interface OrderStatusService extends IService<OrderStatus> {

    /**
     * 关闭订单
     *
     * @return {@link List<String>} 被关闭的订单的orderIdList
     */
    List<String> closeOrder();
}
