package com.like.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.enums.YesOrNo;
import com.like.mapper.ItemsCommentsMapper;
import com.like.pojo.ItemsComments;
import com.like.pojo.OrderStatus;
import com.like.pojo.Orders;
import com.like.pojo.bo.center.OrderItemsCommentBO;
import com.like.pojo.vo.MyCommentVO;
import com.like.service.ItemsCommentsService;
import com.like.service.OrderService;
import com.like.service.OrderStatusService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-27 13:47
 */
@Service
public class ItemsCommentsServiceImpl extends ServiceImpl<ItemsCommentsMapper, ItemsComments>
        implements ItemsCommentsService {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String userId, String orderId, List<OrderItemsCommentBO> commentBOs) {
        // 1.保存评价 items_comments
        List<ItemsComments> insertData =
                commentBOs.stream()
                        .map(
                                c -> {
                                    ItemsComments ic = new ItemsComments();
                                    BeanUtils.copyProperties(c, ic);
                                    ic.setId(sid.nextShort());
                                    ic.setUserId(userId);
                                    ic.setCreatedTime(new Date());
                                    ic.setSepcName(c.getItemSpecName());
                                    return ic;
                                })
                        .collect(Collectors.toList());
        saveBatch(insertData);

        // 2.修改订单为已评价
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setIsComment(YesOrNo.YES.code);
        orderService.updateById(orders);

        // 3.修改订单状态的评论时间
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusService.updateById(orderStatus);
    }

    @Override
    public IPage<MyCommentVO> queryCommentList(Integer page, Integer pageSize, String userId) {
        return baseMapper.queryCommentList(new Page<>(page, pageSize), userId);
    }
}
