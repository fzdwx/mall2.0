package com.like.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.like.pojo.OrderStatus;
import com.like.pojo.OrderVO;
import com.like.pojo.Orders;
import com.like.pojo.bo.SubmitOrderBO;
import com.like.pojo.vo.MyOrdersVo;
import com.like.pojo.vo.OrderStatusCountsVO;
import com.like.pojo.vo.OrderTrendVO;

import java.util.List;

public interface OrderService extends IService<Orders> {

    /**
     * 通过用户id和订单状态查询订单
     *
     * @param userId      用户id
     * @param orderStatus 订单状态
     * @param page        页面
     * @param pageSize    页面大小
     * @return {@link List <MyOrdersVo>}
     */
    IPage<MyOrdersVo> queryOrdersByUserIdAndOrderStatus(
            String userId, String orderStatus, Integer page, Integer pageSize);

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
     * @param status 状态
     */
    void updateOrderStatus(String merchantOrderId, Integer status);

    /**
     * 查询已经支付的订单的信息
     *
     * @param orderId 订单id
     */
    OrderStatus queryPaidOrderInfo(String orderId);

    /**
     * 订单删除 - 逻辑删除
     *
     * @param userId 用户id
     * @param orderId 订单id
     * @return boolean
     */
    boolean deleteOrder(String userId, String orderId);

    /**
     * 更新订单状态到收货
     *
     * @param orderId 订单id
     * @return boolean
     */
    boolean updateOrderStatusToReceive(String orderId);

    /**
     * 查询订单状态统计概述
     *
     * @param userId 用户id
     * @return
     */
    OrderStatusCountsVO queryOrdersStatusOverviewCount(String userId);

    /**
     * 查询订单动向
     *
     * @param page
     * @param pageSize
     * @param userId   用户id
     * @return {@link OrderTrendVO}
     */
    IPage<OrderTrendVO> queryOrderTrend(Integer page, Integer pageSize, String userId);
}
