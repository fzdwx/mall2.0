package com.like.service.impl;

import com.like.enums.OrderStatusEnum;
import com.like.enums.YesOrNo;
import com.like.pojo.*;
import com.like.pojo.bo.SubmitOrderBO;
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
public class OrderServiceImpl implements OrderService {

    @Autowired
    private com.like.service.OrderItemsService OrderItemsService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private Sid sid;
    @Autowired
    private ItemsSpecService itemsSpecService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createOrder(SubmitOrderBO submitOrder) {
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
        order.setReceiverAddress(address.getProvince() + " " +
                address.getCity() + " " +
                address.getDistrict() + " " +
                address.getDetail());
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
        specs.forEach(a -> {
            // TODO: 2021/2/19 整合redis后商品购买数量重新从redis中获取
            totalAmount.updateAndGet(v -> v + a.getPriceNormal() * buyCount);
            realPayAmount.updateAndGet(v -> v + a.getPriceDiscount() * buyCount);
        });
        order.setTotalAmount(totalAmount.get());
        order.setRealPayAmount(realPayAmount.get());

        // 2.保存子订单信息
        ArrayList<OrderItems> saveOrderItems = new ArrayList<>();

        Map<String, ItemsSpec> itemIdMapSpec = specs.stream()
                .collect(Collectors.toMap(ItemsSpec::getItemId, e -> e));
        // a.根据specId获取itemId获取items
        List<String> itemIds = specs.stream()
                .map(ItemsSpec::getItemId)
                .collect(Collectors.toList());
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
        itemIdMapSpec.forEach((itemId, spec) -> {
            itemsSpecService.decreaseItemSpecStock(spec.getId(), buyCount);
        });
    }
}
