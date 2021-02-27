package com.like.controller.center;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.like.controller.BaseController;
import com.like.enums.YesOrNo;
import com.like.pojo.OrderItems;
import com.like.pojo.Orders;
import com.like.pojo.bo.center.OrderItemsCommentBO;
import com.like.pojo.vo.MyCommentVO;
import com.like.service.ItemsCommentsService;
import com.like.service.OrderItemsService;
import com.like.utils.HttpJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private ItemsCommentsService itemsCommentsService;
    @Autowired
    private OrderItemsService orderItemsService;

    @GetMapping("/list/{userId}")
    @ApiOperation(value = "查询我的所有评价", tags = "查询我的所有评价")
    public HttpJSONResult list(
            @PathVariable String userId,
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return HttpJSONResult.errorMsg("用户id不能为空");
        }
        if (page == null || page < 0) {
            page = 1;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = DEFAULT_PAGESIZE;
        }
        IPage<MyCommentVO> pageData = itemsCommentsService.queryCommentList(page, pageSize, userId);

        return HttpJSONResult.ok(pageData);
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存评论", tags = "保存评论")
    public HttpJSONResult saveComments(
            @RequestBody List<OrderItemsCommentBO> data,
            @RequestParam("userId") String userId,
            @RequestParam("orderId") String orderId) {
        // 1.判断订单和用户是否相关联
        HttpJSONResult res = checkUserMapOrder(userId, orderId);
        if (res.getStatus() != HttpStatus.HTTP_OK) {
            return res;
        }
        // 2.评论类容判断不能为空
        if (data == null || data.isEmpty()) {
            return HttpJSONResult.errorMsg("评论内容不能为空");
        }
        // 3.保存评论
        itemsCommentsService.saveComments(userId, orderId, data);

        return HttpJSONResult.ok();
    }

    @PostMapping("/pending")
    @ApiOperation(value = "查询订单中待评价的商品", tags = "查询订单中待评价的商品")
    public HttpJSONResult pending(
            @RequestParam("userId") String userId, @RequestParam("orderId") String orderId) {
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
