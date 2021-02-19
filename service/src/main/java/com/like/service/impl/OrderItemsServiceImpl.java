package com.like.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.mapper.OrderItemsMapper;
import com.like.pojo.OrderItems;
import com.like.service.OrderItemsService;
import org.springframework.stereotype.Service;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-19 15:13
 */
@Service
public class OrderItemsServiceImpl extends ServiceImpl<OrderItemsMapper, OrderItems> implements OrderItemsService {

}
