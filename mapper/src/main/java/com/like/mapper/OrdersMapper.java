package com.like.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.like.my.mapper.MyMapper;
import com.like.pojo.Orders;
import com.like.pojo.vo.MyOrdersVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

@Mapper
public interface OrdersMapper extends MyMapper<Orders> {

    /**
     * 查询订单
     *
     * @param p
     * @param param 参数
     * @return {@link MyOrdersVo}
     */
    IPage<MyOrdersVo> queryOrders(Page<MyOrdersVo> pageInfo, @Param("param") HashMap<String, String> param);
}