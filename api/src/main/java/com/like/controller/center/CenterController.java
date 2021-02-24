package com.like.controller.center;

import com.like.pojo.Users;
import com.like.service.center.UserCenterService;
import com.like.utils.HttpJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-24 16:34
 */
@Api(value = "中心", tags = "中心相关接口")
@RestController
@RequestMapping("center")
public class CenterController {
    @Autowired
    UserCenterService userCenterService;

    @GetMapping("userInfo/{userId}")
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    public HttpJSONResult queryUserInfo(@PathVariable String userId) {
        Users user = userCenterService.queryUserInfo(userId);
        return HttpJSONResult.ok(user);
    }
}
