package com.like.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.like.pojo.OrderItems;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-19 15:12
 */
public interface OrderItemsService extends IService<OrderItems> {

    List<OrderItems> queryOrderItems(String orderId);
}
