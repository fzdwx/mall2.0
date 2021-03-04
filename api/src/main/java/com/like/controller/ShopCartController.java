package com.like.controller;

import cn.hutool.core.util.StrUtil;
import com.like.controller.base.BaseController;
import com.like.pojo.bo.ShopCartBO;
import com.like.pojo.vo.ShopCartVO;
import com.like.service.ItemService;
import com.like.utils.HttpJSONResult;
import com.like.utils.JsonUtils;
import com.like.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-14 17:22
 * 购物车相关接口
 */
@Api(value = "购物车接口", tags = {"购物车相关接口"})
@RequestMapping("shopcart")
@Slf4j
@RestController
public class ShopCartController extends BaseController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/add")
    @ApiOperation(value = "添加商品到购物车")
    public HttpJSONResult add(
            @RequestParam String userId,
            @RequestBody ShopCartBO shopCart) {
        if (StrUtil.isBlank(userId)) return HttpJSONResult.errorMsg("user Id 不能为空");
        log.info("购物车数据:{}", shopCart);
        List<ShopCartBO> shopCartList = null;

        // 前端用户在登录的时候，添加商品上购物车，会同时在后端同步购物车到redis中
        String shopCartRedisCacheJson = redisUtil.get(REDIS_KEY_SHOP_CART_PREFIX + userId);
        if (StringUtils.isNotBlank(shopCartRedisCacheJson)) {
            AtomicBoolean isHaving = new AtomicBoolean(false);
            // 1.判断添加到购物车的商品在购物车中是否存在，如果存在就累加数量
            shopCartList = JsonUtils.jsonToList(shopCartRedisCacheJson, ShopCartBO.class).stream().peek((cart) -> {
                if (cart.getSpecId().equals(shopCart.getSpecId())) {
                    cart.setBuyCounts(cart.getBuyCounts() + shopCart.getBuyCounts());
                    isHaving.set(true);
                }
            }).collect(Collectors.toList());
            // 2.不存在就添加到购物车中
            if (!isHaving.get()) {
                shopCartList.add(shopCart);
            }
        } else {
            // 3.用户第一次，新建购物车
            shopCartList = new ArrayList<>();
            shopCartList.add(shopCart);
        }
        // 4.覆盖原有购物车
        redisUtil.set(REDIS_KEY_SHOP_CART_PREFIX + userId, JsonUtils.objectToJson(shopCartList));

        return HttpJSONResult.ok();
    }

    @PostMapping("/del")
    @ApiOperation(value = "从购物车中删除商品")
    public HttpJSONResult del(
            @RequestParam String userId,
            @RequestParam String itemSpecId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId))
            return HttpJSONResult.errorMsg("请求参数不完整");
        log.info("用户id:{}-删除商品规格id:{}", userId, itemSpecId);
        //   如果用户已经登录，则需要同步删除后端购物车中的商品
        List<ShopCartBO> shopCartList = null;
        String shopCartRedisCache = redisUtil.get(REDIS_KEY_SHOP_CART_PREFIX + userId);
        if (StringUtils.isNoneBlank(shopCartRedisCache)) {
            shopCartList = JsonUtils.jsonToList(shopCartRedisCache, ShopCartBO.class).stream().map(shopCart -> {
                if (shopCart.getSpecId().equals(itemSpecId)) {
                    return null;   // 存在就删除
                }
                return shopCart;
            }).collect(Collectors.toList());
            // 覆盖原购物车
            redisUtil.set(REDIS_KEY_SHOP_CART_PREFIX + userId, JsonUtils.objectToJson(shopCartList));
        }
        return HttpJSONResult.ok();
    }

    /**
     * 用于用户长时间未登录网站 刷新购物车中的数据
     */
    @GetMapping("/refresh/{itemSpecIds}")
    @ApiOperation(value = "根据商品规格的id list 查询对应的商品信息")
    public HttpJSONResult refresh(@PathVariable String itemSpecIds) {
        log.info("商品规格id列表:{}", itemSpecIds);
        if (StrUtil.isBlank(itemSpecIds)) return HttpJSONResult.ok();
        List<ShopCartVO> data = itemService.queryItemsBySpecId(itemSpecIds);
        return HttpJSONResult.ok(data);
    }
}
