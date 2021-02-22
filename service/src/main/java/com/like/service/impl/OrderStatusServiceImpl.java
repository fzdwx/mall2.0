package com.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.enums.OrderStatusEnum;
import com.like.mapper.OrderStatusMapper;
import com.like.pojo.OrderStatus;
import com.like.service.OrderStatusService;
import com.like.utils.DateUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-19 17:34
 */
@Service
public class OrderStatusServiceImpl extends ServiceImpl<OrderStatusMapper, OrderStatus> implements OrderStatusService {

    @Override
    public List<String> closeOrder() {
        // 1.获取所有没有支付的订单
        List<OrderStatus> rawList = list(new QueryWrapper<OrderStatus>()
                .eq(OrderStatus.COL_ORDER_STATUS, OrderStatusEnum.WAIT_PAY.type));
        // 2.获取创建订单超过一天时间的
        List<OrderStatus> waitCloseOrders = rawList.stream()
                .filter(raw -> DateUtil.daysBetween(raw.getCreatedTime(), new Date()) >= 1)
                .collect(Collectors.toList());
        // 3.关闭订单
        return doCloseOrder(waitCloseOrders);
    }

    private List<String> doCloseOrder(List<OrderStatus> waitCloseOrders) {
        List<OrderStatus> orders = waitCloseOrders.stream().peek(order -> {
            order.setCloseTime(new Date());
            order.setOrderStatus(OrderStatusEnum.CLOSE.type);
        }).collect(Collectors.toList());

        updateBatchById(orders);

        return orders.stream().map(OrderStatus::getOrderId).collect(Collectors.toList());
    }
}
