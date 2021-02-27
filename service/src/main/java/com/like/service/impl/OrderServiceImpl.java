package com.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.enums.OrderStatusEnum;
import com.like.enums.YesOrNo;
import com.like.mapper.OrdersMapper;
import com.like.pojo.*;
import com.like.pojo.bo.SubmitOrderBO;
import com.like.pojo.vo.*;
import com.like.service.*;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-19 12:59
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrderService {

    @Autowired
    private com.like.service.OrderItemsService OrderItemsService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemsSpecService itemsSpecService;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public IPage<MyOrdersVo> queryOrdersByUserIdAndOrderStatus(
            String userId, String orderStatus, Integer page, Integer pageSize) {
        HashMap<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("orderStatus", orderStatus);

        Page<MyOrdersVo> pageInfo = new Page<>(page, pageSize);
        return baseMapper.queryOrders(pageInfo, param);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderVO createOrder(SubmitOrderBO submitOrder) {
        String orderId = sid.nextShort();
        String userId = submitOrder.getUserId();
        String addressId = submitOrder.getAddressId();
        String itemSpecIds = submitOrder.getItemSpecIds();
        Integer payMethod = submitOrder.getPayMethod();
        String leftMsg = submitOrder.getLeftMsg();
        Integer postAmount = 0; // 邮费

        // 1.需要保存的订单信息
        UserAddress address = addressService.queryUserAddress(userId, addressId);
        Orders order = new Orders();
        order.setId(orderId);
        order.setUserId(userId);
        order.setReceiverName(address.getReceiver());
        order.setReceiverMobile(address.getMobile());
        order.setReceiverAddress(
                address.getProvince()
                        + " "
                        + address.getCity()
                        + " "
                        + address.getDistrict()
                        + " "
                        + address.getDetail());
        order.setPostAmount(postAmount);
        order.setPayMethod(payMethod);
        order.setLeftMsg(leftMsg);
        order.setExtand("");
        order.setIsComment(YesOrNo.NO.code);
        order.setIsDelete(YesOrNo.NO.code);
        order.setCreatedTime(new Date());
        order.setUpdatedTime(new Date());

        // 购买数量
        int buyCount = 1;
        AtomicReference<Integer> totalAmount = new AtomicReference<>(0);
        AtomicReference<Integer> realPayAmount = new AtomicReference<>(0);
        // 根据specIds获取价格
        String[] s = itemSpecIds.split(",");
        List<ItemsSpec> specs = itemService.queryItemSpecListBySpecIds(Arrays.asList(s));
        specs.forEach(
                a -> {
                    // TODO: 2021/2/19 整合redis后商品购买数量重新从redis中获取
                    totalAmount.updateAndGet(v -> v + a.getPriceNormal() * buyCount);
                    realPayAmount.updateAndGet(v -> v + a.getPriceDiscount() * buyCount);
                });
        order.setTotalAmount(totalAmount.get());
        order.setRealPayAmount(realPayAmount.get());

        save(order);

        // 2.保存子订单信息
        ArrayList<OrderItems> saveOrderItems = new ArrayList<>();

        Map<String, ItemsSpec> itemIdMapSpec =
                specs.stream().collect(Collectors.toMap(ItemsSpec::getItemId, e -> e));
        // a.根据specId获取itemId获取items
        List<String> itemIds =
                specs.stream().map(ItemsSpec::getItemId).collect(Collectors.toList());
        List<Items> items = itemService.queryItemList(itemIds);

        // b.根据itemId获取对应的主图片路径
        Map<String, String> itemIdMapImgUrl = itemService.queryItemMainImgByIds(itemIds);

        for (Items item : items) {
            OrderItems subOrder = new OrderItems();

            String itemId = item.getId();
            String subOrderId = sid.nextShort();

            subOrder.setId(subOrderId);
            subOrder.setOrderId(orderId);
            subOrder.setItemId(itemId);
            subOrder.setItemImg(itemIdMapImgUrl.get(itemId));
            subOrder.setItemName(item.getItemName());
            subOrder.setItemSpecId(itemIdMapSpec.get(itemId).getId());
            subOrder.setItemSpecName(itemIdMapSpec.get(itemId).getName());
            subOrder.setPrice(realPayAmount.get());
            subOrder.setBuyCounts(buyCount);

            saveOrderItems.add(subOrder);
        }
        // c.保存子订单信息
        OrderItemsService.saveBatch(saveOrderItems);

        // 3.保存订单状态 - 等待付款
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());

        orderStatusService.save(waitPayOrderStatus);

        // 4.扣除库存
        //        itemIdMapSpec.forEach((itemId, spec) -> {
        //            Integer stock = spec.getStock();
        //            if (stock > buyCount) {
        //                spec.setStock(stock - buyCount);
        //            }
        //        });
        //        itemsSpecService.updateBatchById(itemIdMapSpec.values());
        itemIdMapSpec.forEach(
                (itemId, spec) -> {
                    itemsSpecService.decreaseItemSpecStock(spec.getId(), buyCount);
                });

        // 5.构建商户订单 用于传给支付中心
        MerchantOrdersVO merchant = new MerchantOrdersVO();
        merchant.setMerchantOrderId(orderId);
        merchant.setMerchantUserId(userId);
        merchant.setAmount(realPayAmount.get() + postAmount);
        merchant.setPayMethod(payMethod);

        // 6.构建自定义订单vo
        OrderVO ordervo = new OrderVO(orderId, merchant);
        return ordervo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrderStatus(String merchantOrderId, Integer status) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(merchantOrderId);
        orderStatus.setOrderStatus(status);

        if (status.equals(OrderStatusEnum.WAIT_DELIVER.type)) {
            orderStatus.setPayTime(new Date());
        }
        if (status.equals(OrderStatusEnum.SUCCESS.type)) {
            orderStatus.setSuccessTime(new Date());
        }
        if (status.equals(OrderStatusEnum.CLOSE.type)) {
            orderStatus.setCloseTime(new Date());
        }
        if (status.equals(OrderStatusEnum.WAIT_RECEIVE.type)) {
            orderStatus.setDeliverTime(new Date());
        }

        orderStatusService.updateById(orderStatus);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public OrderStatus queryPaidOrderInfo(String orderId) {
        return orderStatusService.getById(orderId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteOrder(String userId, String orderId) {
        Orders o = new Orders();
        o.setId(orderId);
        o.setUserId(userId);
        o.setUpdatedTime(new Date());
        o.setIsDelete(YesOrNo.YES.code);

        return updateById(o);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateOrderStatusToReceive(String orderId) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setSuccessTime(new Date());
        orderStatus.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        QueryWrapper<OrderStatus> query =
                new QueryWrapper<OrderStatus>()
                        .eq(OrderStatus.COL_ORDER_STATUS, OrderStatusEnum.WAIT_RECEIVE.type)
                        .eq("order_id", orderId);

        return orderStatusService.update(orderStatus, query);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public OrderStatusCountsVO queryOrdersStatusOverviewCount(String userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        // 1.找到所有当前用户的订单的状态信息
        List<OrderStatusOverviewVO> rawList = baseMapper.queryOrdersStatusOverview(param);
        // 2.根据订单状态分组
        Map<Integer, List<OrderStatusOverviewVO>> grpMap =
                rawList.stream()
                        .collect(Collectors.groupingBy(OrderStatusOverviewVO::getOrderStatus));
        // 3.统计对应的个数
        OrderStatusCountsVO res = new OrderStatusCountsVO();
        grpMap.forEach(
                (status, list) -> {
                    if (Objects.equals(status, OrderStatusEnum.WAIT_PAY.type)) {
                        res.setWaitPayCounts(list.size());
                    } else if (Objects.equals(status, OrderStatusEnum.WAIT_DELIVER.type)) {
                        res.setWaitDeliverCounts(list.size());
                    } else if (Objects.equals(status, OrderStatusEnum.SUCCESS.type)) {
                        res.setWaitCommentCounts(
                                (int)
                                        list.stream()
                                                .filter(
                                                        o ->
                                                                Objects.equals(
                                                                        o.getIsComment(),
                                                                        YesOrNo.NO.code))
                                                .count());
                    } else if (Objects.equals(status, OrderStatusEnum.WAIT_RECEIVE.type)) {
                        res.setWaitReceiveCounts(list.size());
                    }
                });

        return res;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public IPage<OrderTrendVO> queryOrderTrend(Integer page, Integer pageSize, String userId) {
        Page<MyOrdersVo> pageInfo = new Page<>(page, pageSize);
        return baseMapper.queryOrderTrend(pageInfo, userId);
    }
}
