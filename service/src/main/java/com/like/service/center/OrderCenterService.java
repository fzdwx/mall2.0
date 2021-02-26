package com.like.service.center;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.like.pojo.Orders;
import com.like.pojo.vo.MyOrdersVo;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-26 13:10
 */
public interface OrderCenterService extends IService<Orders> {

    /**
     * 通过用户id和订单状态查询订单
     *
     * @param userId      用户id
     * @param orderStatus 订单状态
     * @param page        页面
     * @param pageSize    页面大小
     * @return {@link List<MyOrdersVo>}
     */
    IPage<MyOrdersVo> queryOrdersByUserIdAndOrderStatus(String userId, String orderStatus, Integer page, Integer pageSize);
}
