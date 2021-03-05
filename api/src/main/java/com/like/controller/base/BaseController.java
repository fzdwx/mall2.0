package com.like.controller.base;

import com.like.enums.YesOrNo;
import com.like.pojo.Orders;
import com.like.service.OrderService;
import com.like.utils.HttpJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-05 17:04
 */
@RestController
public class BaseController {

    /** 付款网址 */
    public static final String PAYMENT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
    /** 购物车保存cookie的名词 */
    public static final String COOKIE_FOODIE_SHOPCART_KEY = "shopcart";
    /** 用户保存在cookie中的key */
    public static final String COOKIE_FOODIE_USER_INFO_KEY = "user";
    /** 默认分页大小 */
    public static final Integer DEFAULT_PAGESIZE = 10;
    /** 网站首页轮播图保存在redis中的key */
    public static String REDIS_KEY_CAROUSEL = "carousel";
    /** 网站首页一级分类保存在redis中的key */
    public static String REDIS_KEY_CATS = "cats";
    /** 网站首页一级分类的子分类保存在redis中的key REDIS_KEY_SHOP_CART_PREFIX+rootCatId */
    public static String REDIS_KEY_SHOP_CART_PREFIX = "shopCart:";
    /** 用户购物车保存在redis中的key REDIS_KEY_SUB_CAT_PREFIX+rootCatId */
    public static String REDIS_KEY_SUB_CAT_PREFIX = "subCat:";
    /** 服务端口号 */
    @Value("${server.port}")
    private String port;
    /** 支付成功后 -> 支付中心 -> 服務器回调后台(payReturnUrl) */
    public final String PAY_RETURN_URL = "http://8.131.57.243:" + port + "/orders/notifyMerchantOrderPaid/";
    @Autowired
    public OrderService orderService;

    /**
     * 检查用户是否有对应的订单
     * @param userId 用户id
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
