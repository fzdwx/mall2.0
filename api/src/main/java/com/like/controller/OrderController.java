package com.like.controller;

import com.like.pojo.bo.SubmitOrderBO;
import com.like.service.OrderService;
import com.like.utils.HttpJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-18 19:08
 */
@RequestMapping("orders")
@Api(value = "订单相关接口", tags = {"订单操作相关接口"})
@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("create")
    @ApiOperation("创建订单")
    public HttpJSONResult create(@RequestBody SubmitOrderBO submitOrder) {
        // 1.创建订单
        orderService.createOrder(submitOrder);
        // 2.创建订单后，移除购物车中已结算(已提交)的商品
        // 3.向支付中心发送当前订单，用于保存支付中心的订单数据
        return HttpJSONResult.ok();
    }
}
