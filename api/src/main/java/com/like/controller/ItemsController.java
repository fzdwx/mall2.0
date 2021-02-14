package com.like.controller;

import com.like.pojo.Items;
import com.like.pojo.ItemsImg;
import com.like.pojo.ItemsParam;
import com.like.pojo.ItemsSpec;
import com.like.pojo.vo.CommentLevelCountsVO;
import com.like.pojo.vo.ItemInfoVo;
import com.like.service.ItemService;
import com.like.utils.HttpJSONResult;
import com.like.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-11 10:41
 */
@RestController
@RequestMapping("items")
@Api(value = "商品", tags = {"商品相关操作接口"})
public class ItemsController extends BaseController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/info/{itemId}")
    @ApiOperation(value = "查询商品详情")
    public HttpJSONResult itemInfoById(@PathVariable(required = true) String itemId) {
        if (itemId == null) return HttpJSONResult.errorMsg("itemId不能为空");

        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> imgs = itemService.queryItemImgList(itemId);
        List<ItemsSpec> specs = itemService.queryItemSpecList(itemId);
        ItemsParam param = itemService.queryItemParam(itemId);
        ItemInfoVo info = new ItemInfoVo(item, imgs, specs, param);

        return HttpJSONResult.ok(info);
    }


    @GetMapping("/commentLevel")
    @ApiOperation(value = "查询商品各个评价数量")
    public HttpJSONResult queryCommentsLevelCountsGroupByLevel(@RequestParam String itemId) {
        if (itemId == null) return HttpJSONResult.errorMsg("itemId不能为空");
        CommentLevelCountsVO data = itemService.queryCommentCounts(itemId);

        return HttpJSONResult.ok(data);
    }


    @GetMapping("/comments")
    @ApiOperation(value = "查询商品的评价")
    public HttpJSONResult comments(@RequestParam String itemId, @RequestParam Integer level,
                                   @RequestParam Integer page, @RequestParam Integer pageSize) {
        if (itemId == null) return HttpJSONResult.errorMsg("itemId不能为空");
        if (page == null) page = 1;
        if (pageSize == null) pageSize = PAGESIZE;

        PagedGridResult res = itemService.queryPagedComments(itemId, level, page, pageSize);

        return HttpJSONResult.ok(res);
    }


    @GetMapping("/search")
    @ApiOperation(value = "搜索商品信息列表")
    public HttpJSONResult searchItems(@RequestParam String keywords, @RequestParam String sort,
                                      @RequestParam Integer page, @RequestParam Integer pageSize) {
        if (keywords == null) return HttpJSONResult.errorMsg("关键字不能为空");
        if (page == null) page = 1;
        if (pageSize == null) pageSize = PAGESIZE;

        PagedGridResult res = itemService.searchItems(keywords, sort, page, pageSize);

        return HttpJSONResult.ok(res);
    }


    @GetMapping("/catItems")
    @ApiOperation(value = "根据三级分类返回商品信息列表")
    public HttpJSONResult searchItemsByThirdCategory(@RequestParam Integer catId, @RequestParam String sort,
                                                     @RequestParam Integer page, @RequestParam Integer pageSize) {
        if (catId == null) return HttpJSONResult.errorMsg("三级分类id不能为空");
        if (page == null) page = 1;
        if (pageSize == null) pageSize = PAGESIZE;

        PagedGridResult res = itemService.searchItemsByThirdCategory(catId, sort, page, pageSize);

        return HttpJSONResult.ok(res);
    }
}

