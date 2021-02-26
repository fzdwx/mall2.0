package com.like.controller.center;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.like.pojo.vo.MyOrdersVo;
import com.like.service.center.OrderCenterService;
import com.like.utils.HttpJSONResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-26 15:52
 */
@Api(value = "我的订单", tags = "我的订单相关操作接口")
@RestController
@RequestMapping("myOrders")
public class MyOrdersController {
    @Autowired
    OrderCenterService orderCenterService;

    @PostMapping("{userId}/{orderStatus}")
    public HttpJSONResult queryMyOrders(@PathVariable String userId,
                                        @PathVariable String orderStatus,
                                        @RequestParam Integer page,
                                        @RequestParam Integer pageSize) {
        IPage<MyOrdersVo> myOrders = orderCenterService.queryOrdersByUserIdAndOrderStatus(userId, orderStatus, page, pageSize);
        return HttpJSONResult.ok(myOrders);
    }
}