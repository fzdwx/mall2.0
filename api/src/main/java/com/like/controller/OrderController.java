package com.like.controller;

import com.like.controller.base.BaseController;
import com.like.enums.OrderStatusEnum;
import com.like.pojo.OrderStatus;
import com.like.pojo.OrderVO;
import com.like.pojo.bo.ShopCartBO;
import com.like.pojo.bo.SubmitOrderBO;
import com.like.service.OrderService;
import com.like.utils.CookieUtils;
import com.like.utils.HttpJSONResult;
import com.like.utils.JsonUtils;
import com.like.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("create")
    @ApiOperation("创建订单")
    public HttpJSONResult create(
            @RequestBody SubmitOrderBO submitOrder,
            HttpServletRequest request, HttpServletResponse response) {
        // 0.获取购物车信息
        String shopCartRedisCache = redisUtil.get(REDIS_KEY_SHOP_CART_PREFIX + submitOrder.getUserId());
        if (StringUtils.isBlank(shopCartRedisCache)) {
            return HttpJSONResult.errorMsg("购物车信息不正确");
        }
        List<ShopCartBO> shopCart = JsonUtils.jsonToList(shopCartRedisCache, ShopCartBO.class);
        // 1.创建订单
        OrderVO order = orderService.createOrder(submitOrder, shopCart);
        String orderId = order.getOrderId();

        // 2.创建订单后，移除购物车中已结算(已提交)的商品
        String newCartJson = refreshShopCart(submitOrder.getUserId(), shopCart, order.getRemoveShopCart());
        CookieUtils.setCookie(request, response, COOKIE_FOODIE_SHOPCART_KEY, newCartJson, true); // 前端操作更新

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
                new HttpEntity<>(order.getMerchant(), headers),
                HttpJSONResult.class);

        HttpJSONResult jsonResult = resp.getBody();
        if (jsonResult.getStatus() != 200)
            return HttpJSONResult.errorMsg("支付执行订单创建失败，请联系管理员");
        return HttpJSONResult.ok(orderId);
    }

    /**
     * 刷新购物车
     * @param userId
     * @param shopCart 原购物车
     * @param removeShopCart 需要删除的信息
     * @return
     */
    private String refreshShopCart(String userId, List<ShopCartBO> shopCart, List<ShopCartBO> removeShopCart) {
        Map<String, ShopCartBO> m = shopCart.stream()
                                            .collect(Collectors.toMap(ShopCartBO::getSpecId, e -> e));
        for (ShopCartBO s : removeShopCart) {
            ShopCartBO shopCartBO = m.get(s.getSpecId());
            shopCartBO.setBuyCounts(shopCartBO.getBuyCounts() - s.getBuyCounts());
            if (shopCartBO.getBuyCounts().equals(0)) {
                m.remove(s.getSpecId());
            } else {
                m.put(s.getSpecId(), shopCartBO);
            }
        }
        Collection<ShopCartBO> values = m.values();
        String s = "";
        if (values.size() == 0) {
            redisUtil.delete(REDIS_KEY_SHOP_CART_PREFIX + userId);
        } else {
            s = JsonUtils.objectToJson(values);
            redisUtil.set(REDIS_KEY_SHOP_CART_PREFIX + userId, s);
        }

        return s;
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
