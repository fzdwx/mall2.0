package com.like.controller.center;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.like.controller.BaseController;
import com.like.enums.OrderStatusEnum;
import com.like.pojo.vo.MyOrdersVo;
import com.like.service.OrderService;
import com.like.service.center.OrderCenterService;
import com.like.utils.HttpJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
public class MyOrdersController extends BaseController {
    @Autowired
    private OrderCenterService orderCenterService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}")
    @ApiOperation(value = "查询我的订单", tags = "查询我的订单")
    public HttpJSONResult queryMyOrders(@PathVariable String userId,
                                        @RequestParam("orderStatus") String orderStatus,
                                        @RequestParam("page") Integer page,
                                        @RequestParam("pageSize") Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return HttpJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = DEFAULT_PAGESIZE;
        }
        if (StringUtils.isBlank(orderStatus)) {
            orderStatus = null;
        }
        IPage<MyOrdersVo> myOrders = orderCenterService.queryOrdersByUserIdAndOrderStatus(userId, orderStatus, page, pageSize);
        return HttpJSONResult.ok(myOrders);
    }

    /**
     * 商家发货
     *
     * @param orderId 订单id
     * @return {@link HttpJSONResult}
     */
    @GetMapping("/deliver/{orderId}")
    @ApiOperation(value = "模拟商家发货", tags = "模拟商家发货")
    public HttpJSONResult delivery(@PathVariable String orderId) {
        orderService.updateOrderStatus(orderId, OrderStatusEnum.WAIT_RECEIVE.type);
        return HttpJSONResult.ok();
    }
}
