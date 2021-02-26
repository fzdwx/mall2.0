package com.like.controller.center;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.like.pojo.Users;
import com.like.pojo.vo.MyOrdersVo;
import com.like.service.center.OrderCenterService;
import com.like.service.center.UserCenterService;
import com.like.utils.HttpJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    OrderCenterService orderCenterService;

    @GetMapping("userInfo/{userId}")
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    public HttpJSONResult queryUserInfo(@PathVariable String userId) {
        Users user = userCenterService.queryUserInfo(userId);
        return HttpJSONResult.ok(user);
    }

    @PostMapping("orders/{userId}/{orderStatus}")
    public HttpJSONResult queryMyOrders(@PathVariable String userId,
                                        @PathVariable String orderStatus,
                                        @RequestParam Integer page,
                                        @RequestParam Integer pageSize) {
        IPage<MyOrdersVo> myOrders = orderCenterService.queryOrdersByUserIdAndOrderStatus(userId, orderStatus, page, pageSize);
        return HttpJSONResult.ok(myOrders);
    }
}
