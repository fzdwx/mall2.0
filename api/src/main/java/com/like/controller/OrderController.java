package com.like.controller;

import com.like.pojo.bo.SubmitOrderBO;
import com.like.service.OrderService;
import com.like.utils.CookieUtils;
import com.like.utils.HttpJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-18 19:08
 */
@RequestMapping("orders")
@Api(value = "订单相关接口", tags = {"订单操作相关接口"})
@RestController
@Slf4j
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @PostMapping("create")
    @ApiOperation("创建订单")
    public HttpJSONResult create(@RequestBody SubmitOrderBO submitOrder,
                                 HttpServletRequest request, HttpServletResponse response) {
        // 1.创建订单
        String orderId = orderService.createOrder(submitOrder);
        // 2.创建订单后，移除购物车中已结算(已提交)的商品
        // TODO: 2021/2/20 整合redis后，完善购物车中的已结算商品清除，并同步到前端的cookie
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART_SESSION, "", true);
        // 3.向支付中心发送当前订单，用于保存支付中心的订单数据
        return HttpJSONResult.ok(orderId);
    }
}
