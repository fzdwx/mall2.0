package com.like.controller.center;

import cn.hutool.http.HttpStatus;
import com.like.controller.BaseController;
import com.like.enums.YesOrNo;
import com.like.pojo.OrderItems;
import com.like.pojo.Orders;
import com.like.service.OrderItemsService;
import com.like.utils.HttpJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-27 12:36
 */
@Api(value = "我的评价", tags = "我的评价相关操作接口")
@RestController
@RequestMapping("myComment")
public class MyCommentController extends BaseController {
    @Autowired
    private OrderItemsService orderItemsService;


    @PostMapping("/pending")
    @ApiOperation(value = "查询待评价订单", tags = "查询待评价订单")
    public HttpJSONResult pending(@RequestParam("userId") String userId,
                                  @RequestParam("orderId") String orderId,) {
        // 1.判断订单和用户是否相关联
        HttpJSONResult res = checkUserMapOrder(userId, orderId);
        if (res.getStatus() != HttpStatus.HTTP_OK) {
            return res;
        }
        // 2.判断订单是否被评价
        Orders order = (Orders) res.getData();
        if (order.getIsComment() == YesOrNo.YES.code) {
            return HttpJSONResult.errorMsg("该笔订单已经被评价");
        }
        // 3.查询待评价的商品
        List<OrderItems> pendingList = orderItemsService.queryOrderItems(orderId);

        return HttpJSONResult.ok(pendingList);
    }

}
