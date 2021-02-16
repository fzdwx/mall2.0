package com.like.controller;

import com.like.pojo.bo.ShopCartBO;
import com.like.utils.HttpJSONResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-14 17:22
 */
@Api(value = "购物车接口")
@RequestMapping("shopcart")
public class ShopCartController {


    @GetMapping("/add")
    public HttpJSONResult add(@RequestParam String userId, @RequestBody ShopCartBO shopCart) {

        return HttpJSONResult.ok();
    }
}
