package com.like.controller;

import com.like.pojo.UserAddress;
import com.like.pojo.bo.AddressBO;
import com.like.service.AddressService;
import com.like.utils.HttpJSONResult;
import com.like.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    @ApiOperation(value = "用户添加地址")
    public HttpJSONResult add(@RequestBody AddressBO address) {
        HttpJSONResult checkRes = checkAddress(address);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }
        log.info("用户新增地址：{}", address);
        boolean b = addressService.addNewUserAddress(address);

        return b ? HttpJSONResult.ok() : HttpJSONResult.errorMsg("保存失败请稍后再试");
    }

    @PostMapping("/delete")
    @ApiOperation(value = "用户删除地址")
    public HttpJSONResult delete(@RequestParam String userId, @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return HttpJSONResult.errorMsg("请求参数缺少");
        }
        log.info("用户删除地址：{}-{}", userId, addressId);
        addressService.deleteUserAddress(userId, addressId);

        return HttpJSONResult.errorMsg("保存失败请稍后再试");
    }

    @PostMapping("/update")
    @ApiOperation(value = "用户修改地址")
    public HttpJSONResult update(@RequestBody AddressBO address) {
        if (StringUtils.isBlank(address.getAddressId())) {
            return HttpJSONResult.errorMsg("修改地址错误:address Id 为空");
        }
        HttpJSONResult checkRes = checkAddress(address);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }
        log.info("用户修改地址：{}", address);
        addressService.updateUserAddress(address);

        return HttpJSONResult.ok();
    }

    @PostMapping("/setDefault")
    @ApiOperation(value = "设置为默认地址")
    public HttpJSONResult setDefaultAddress(@RequestParam String userId, @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return HttpJSONResult.errorMsg("请求参数缺少");
        }
        log.info("用户设置默认地址：{}-{}", userId, addressId);
        addressService.setDefault(userId, addressId);

        return HttpJSONResult.errorMsg("保存失败请稍后再试");
    }

    private HttpJSONResult checkAddress(AddressBO address) {
        if (address == null)
            return HttpJSONResult.errorMsg("地址信息为空");
        String receiver = address.getReceiver();
        if (StringUtils.isBlank(receiver))
            return HttpJSONResult.errorMsg("收货人姓名为空");
        if (receiver.length() > 12)
            return HttpJSONResult.errorMsg("收货人姓名过长");

        String mobile = address.getMobile();
        if (StringUtils.isBlank(mobile))
            return HttpJSONResult.errorMsg("收货人手机号为空");
        if (mobile.length() != 11)
            return HttpJSONResult.errorMsg("收货人手机号长度有误");
        if (!MobileEmailUtils.checkMobileIsOk(mobile))
            return HttpJSONResult.errorMsg("收货人手机号格式有误");

        if (StringUtils.isBlank(address.getCity()) &&
                StringUtils.isBlank(address.getDetail()) &&
                StringUtils.isBlank(address.getDistrict()) &&
                StringUtils.isBlank(address.getProvince())) {
            return HttpJSONResult.errorMsg("收货人信息不正确");
        }
        return HttpJSONResult.ok();
    }
}
