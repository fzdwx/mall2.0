package com.like.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.like.my.mapper.MyMapper;
import com.like.pojo.OrderStatus;
import com.like.pojo.Orders;
import com.like.pojo.vo.MyOrdersVo;
import com.like.pojo.vo.OrderStatusOverviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrdersMapper extends MyMapper<Orders> {

    /**
     * 查询订单
     *
     * @param param    参数
     * @param pageInfo 页面信息
     * @return {@link IPage<MyOrdersVo>}
     */
    IPage<MyOrdersVo> queryOrders(
            Page<MyOrdersVo> pageInfo, @Param("param") HashMap<String, String> param);

    /**
     * 查询订单状态概述
     *
     * @param param 参数
     * @return {@link List<OrderStatus>}
     */
    List<OrderStatusOverviewVO> queryOrdersStatusOverview(@Param("param") Map<String, Object> param);
}
