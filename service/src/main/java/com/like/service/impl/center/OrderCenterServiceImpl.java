package com.like.service.impl.center;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.mapper.OrdersMapper;
import com.like.pojo.Orders;
import com.like.pojo.vo.MyOrdersVo;
import com.like.service.center.OrderCenterService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-26 13:10
 */
@Service
public class OrderCenterServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrderCenterService {

    @Override
    public List<MyOrdersVo> queryOrdersByUserIdAndOrderStatus(String userId, String orderStatus) {
        HashMap<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("orderStatus", orderStatus);

        return baseMapper.queryOrders(param);
    }
}
