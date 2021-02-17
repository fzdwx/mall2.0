package com.like.controller;

import com.like.pojo.UserAddress;
import com.like.service.AddressService;
import com.like.utils.HttpJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-14 17:22
 * 用户在确认订单页面可以针对收货地址做如下操作：
 * 1.查询用户所有的收货地址
 * 2.新增收货地址
 * 3.删除收货地址
 * 4.修改收货地址
 * 5.设置默认地址
 */
@Api(value = "地址相关接口")
@RequestMapping("address")
@Slf4j
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/list")
    @ApiOperation(value = "查询用户的收货地址")
    public HttpJSONResult list(@RequestParam String userId) {
        if (StringUtils.isBlank(userId))
            return HttpJSONResult.errorMsg("请求参数不完整");
        log.info("用户id:{}查询收货地址", userId);
        // TODO: 2021/2/16 如果用户已经登录，则需要同步删除后端购物车中的商品
        List<UserAddress> data = addressService.queryAll(userId);

        return HttpJSONResult.ok(data);
    }
}
