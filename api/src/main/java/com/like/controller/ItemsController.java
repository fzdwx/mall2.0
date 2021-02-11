package com.like.controller;

import com.like.pojo.Items;
import com.like.pojo.ItemsImg;
import com.like.pojo.ItemsParam;
import com.like.pojo.ItemsSpec;
import com.like.pojo.vo.ItemInfoVo;
import com.like.service.ItemService;
import com.like.utils.HttpJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-11 10:41
 */
@RestController
@RequestMapping("items")
@Api(value = "商品", tags = {"商品相关操作接口"})
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/info/{itemId}")
    @ApiOperation(value = "查询商品详情")
    public HttpJSONResult itemInfoById(@PathVariable String itemId) {
        if (itemId == null) return HttpJSONResult.errorMsg("itemId不能为空");

        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> imgs = itemService.queryItemImgList(itemId);
        List<ItemsSpec> specs = itemService.queryItemSpecList(itemId);
        ItemsParam param = itemService.queryItemParam(itemId);
        ItemInfoVo info = new ItemInfoVo(item, imgs, specs, param);

        return HttpJSONResult.ok(info);
    }
}

