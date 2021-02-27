package com.like.controller;

import com.like.enums.YesOrNo;
import com.like.pojo.Orders;
import com.like.service.OrderService;
import com.like.utils.HttpJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-05 17:04
 */
@RestController
public class BaseController {

    public static final String FOODIE_SHOPCART_SESSION = "shopcart";
    /**
     * 默认分页大小
     */
    public static final Integer DEFAULT_PAGESIZE = 10;

    /**
     * 付款网址
     */
    public static final String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
    /**
     * 支付成功后 -> 支付中心 -> 服務器後代后台(payReturnUrl)
     */
    public static final String payReturnUrl = "http://qm8czj.natappfree.cc/orders/notifyMerchantOrderPaid/";


    @Autowired
    public OrderService orderService;

    /**
     * 检查用户是否有对应的订单
     *
     * @param userId  用户id
     * @param orderId 订单id
     * @return {@link HttpJSONResult}
     */
    public HttpJSONResult checkUserMapOrder(String userId, String orderId) {
        Orders queryEnt = new Orders();
        queryEnt.setId(orderId);
        queryEnt.setUserId(userId);
        queryEnt.setIsDelete(YesOrNo.NO.code);

        Orders order = orderService.getById(queryEnt);
        if (order == null) {
            return HttpJSONResult.errorMsg("该订单不存在");
        }
        return HttpJSONResult.ok(order);
    }

}
