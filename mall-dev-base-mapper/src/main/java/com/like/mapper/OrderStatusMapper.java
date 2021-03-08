package com.like.mapper;

import com.like.my.mapper.MyMapper;
import com.like.pojo.OrderStatus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderStatusMapper extends MyMapper<OrderStatus> {
}