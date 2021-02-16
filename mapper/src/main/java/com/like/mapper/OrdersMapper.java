package com.like.mapper;

import com.like.my.mapper.MyMapper;
import com.like.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends MyMapper<Orders> {
}