package com.like.controller.base;

import com.like.enums.YesOrNo;
import com.like.pojo.Orders;
import com.like.pojo.Users;
import com.like.pojo.vo.UsersVO;
import com.like.service.OrderService;
import com.like.utils.HttpJSONResult;
import com.like.utils.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-05 17:04
 */
@RestController
public class BaseController {
    /** 付款网址 */
    public static final String PAYMENT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
    /** 默认分页大小 */
    public static final Integer DEFAULT_PAGESIZE = 10;

    /*************************   COOKIE  *************************/
    /** 购物车保存cookie的名词 */
    public static final String COOKIE_FOODIE_SHOPCART_KEY = "shopcart";
    /** 用户保存在cookie中的key */
    public static final String COOKIE_FOODIE_USER_INFO_KEY = "user";

    /*************************   REDIS  *************************/
    /** 网站首页轮播图保存在redis中的key */
    public static String REDIS_KEY_CAROUSEL = "carousel";
    /** 一级分配前缀 */
    public static String REDIS_KEY_CATS = "cats";
    /** 用户商城前缀 REDIS_KEY_SHOP_CART_PREFIX+rootCatId */
    public static String REDIS_KEY_SHOP_CART_PREFIX = "shopCart:";
    /** 子分类前缀   REDIS_KEY_SUB_CAT_PREFIX+rootCatId */
    public static String REDIS_KEY_SUB_CAT_PREFIX = "subCat:";
    /** 用户token前缀 */
    public static String REDIS_USER_TOKEN_PREFIX = "userToken:";

    /*************************   PAY   *************************/
    /** 服务端口号 */
    @Value("${server.port}")
    private String port;
    /** 支付成功后 -> 支付中心 -> 服務器回调后台(payReturnUrl) */
    public final String PAY_RETURN_URL = "http://8.131.57.243:" + port + "/orders/notifyMerchantOrderPaid/";

    /*************************   PAGE  *************************/
    @Autowired
    public OrderService orderService;
    @Autowired
    public RedisUtil redisUtil;

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

    /**
     * 生成返回页面的用户信息,并将当前用户的token保存 到redis中
     * @param createUser 创建用户
     * @return {@link UsersVO}
     */
    public UsersVO conventUsersVO(Users createUser) {
        String uniqueToken = UUID.randomUUID().toString().trim();
        UsersVO toWebUser = new UsersVO();
        BeanUtils.copyProperties(createUser, toWebUser);
        toWebUser.setUserUniqueToken(uniqueToken);
        redisUtil.set(REDIS_USER_TOKEN_PREFIX + toWebUser.getId(), toWebUser.getUserUniqueToken());
        return toWebUser;
    }
}
