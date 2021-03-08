package com.like.mapper;

import com.like.my.mapper.MyMapper;
import com.like.pojo.OrderItems;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemsMapper extends MyMapper<OrderItems> {
}