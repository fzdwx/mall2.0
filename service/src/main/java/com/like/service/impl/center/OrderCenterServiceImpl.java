package com.like.service.impl.center;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.mapper.OrdersMapper;
import com.like.pojo.Orders;
import com.like.pojo.vo.MyOrdersVo;
import com.like.service.center.OrderCenterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-26 13:10
 */
@Service
public class OrderCenterServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrderCenterService {

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public IPage<MyOrdersVo> queryOrdersByUserIdAndOrderStatus(String userId, String orderStatus, Integer page, Integer pageSize) {
        HashMap<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("orderStatus", orderStatus);

        Page<MyOrdersVo> pageInfo = new Page<>(page, pageSize);
        return baseMapper.queryOrders(pageInfo, param);

    }
}
