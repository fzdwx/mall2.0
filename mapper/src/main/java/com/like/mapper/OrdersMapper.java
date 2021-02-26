package com.like.mapper;

import com.like.my.mapper.MyMapper;
import com.like.pojo.Orders;
import com.like.pojo.vo.MyOrdersVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface OrdersMapper extends MyMapper<Orders> {

    /**
     * 查询订单
     *
     * @param param 参数
     * @return {@link MyOrdersVo}
     */
    List<MyOrdersVo> queryOrders(@Param("param") HashMap<String, String> param);
}