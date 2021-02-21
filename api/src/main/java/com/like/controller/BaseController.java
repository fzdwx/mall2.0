package com.like.controller;

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

    public static final String paymentUrl = "http://payment.t.com/foodie-payment/payment/createMerchantOrder";
    /**
     * 支付成功后 -> 支付中心 -> 服務器後代后台(payReturnUrl)
     */
    public static final String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid/";
}
