package com.like.controller;

import com.like.controller.base.BaseController;
import com.like.enums.OrderStatusEnum;
import com.like.pojo.OrderStatus;
import com.like.pojo.OrderVO;
import com.like.pojo.bo.SubmitOrderBO;
import com.like.pojo.vo.MerchantOrdersVO;
import com.like.service.OrderService;
import com.like.utils.CookieUtils;
import com.like.utils.HttpJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("create")
    @ApiOperation("创建订单")
    public HttpJSONResult create(@RequestBody SubmitOrderBO submitOrder,
                                 HttpServletRequest request, HttpServletResponse response) {
        // 1.创建订单
        OrderVO order = orderService.createOrder(submitOrder);
        String orderId = order.getOrderId();
        // 2.创建订单后，移除购物车中已结算(已提交)的商品
        // TODO: 2021/2/20 整合redis后，完善购物车中的已结算商品清除，并同步到前端的cookie
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART_SESSION, "", true);
        // 3.向支付中心发送当前订单，用于保存支付中心的订单数据
        // 设置回调路径
        order.getMerchant().setReturnUrl(PAY_RETURN_URL + orderId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");

        order.getMerchant().setAmount(1);// TODO: 2021/2/21  测试
        // 发送请求到支付中心，创建订单
        ResponseEntity<HttpJSONResult> resp = restTemplate.postForEntity(
                PAYMENT_URL,
                new HttpEntity<MerchantOrdersVO>(order.getMerchant(), headers),
                HttpJSONResult.class);

        HttpJSONResult jsonResult = resp.getBody();
        if (jsonResult.getStatus() != 200)
            return HttpJSONResult.errorMsg("支付执行订单创建失败，请联系管理员");
        return HttpJSONResult.ok(orderId);
    }

    @PostMapping("/notifyMerchantOrderPaid/{merchantOrderId}")
    public Integer notifyMerchantOrderPaid(@PathVariable String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);

        return HttpStatus.OK.value();
    }


    @PostMapping("/getPaidOrderInfo/{orderId}")
    public HttpJSONResult getPaidOrderInfo(@PathVariable String orderId) {
        OrderStatus status = orderService.queryPaidOrderInfo(orderId);

        return HttpJSONResult.ok(status);
    }
}
