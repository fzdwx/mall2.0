package com.like.controller;

import com.like.enums.YesOrNo;
import com.like.pojo.Carousel;
import com.like.pojo.Category;
import com.like.pojo.vo.CategoryVo;
import com.like.pojo.vo.NewItemsVo;
import com.like.service.CarouselService;
import com.like.service.CategoryService;
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
 * @since 2021-02-09 16:56
 */
@RestController
@RequestMapping("/index")
@Api(value = "首页", tags = {"首页展示的相关接口"})
public class IndexController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("carousel")
    @ApiOperation(value = "获取首页轮播图列表")
    public HttpJSONResult carousel() {
        List<Carousel> data = carouselService.queryAllRootLevelCat(YesOrNo.YES.code);

        return HttpJSONResult.ok(data);
    }

    @GetMapping("cats")
    @ApiOperation(value = "获取首页轮播图列表")
    public HttpJSONResult cats() {
        List<Category> data = categoryService.queryAllRootLevelCat();
        return HttpJSONResult.ok(data);
    }

    @GetMapping("/subCat/{rootCatId}")
    @ApiOperation(value = "获取一级分类下的子分类信息")
    public HttpJSONResult subCat(@PathVariable(required = true) Integer rootCatId) {
        if (rootCatId == null) return HttpJSONResult.errorMsg("分类不存在");

        List<CategoryVo> data = categoryService.getSubCatList(rootCatId);
        return HttpJSONResult.ok(data);
    }

    @GetMapping("/sixNewItems/{rootCatId}")
    @ApiOperation(value = "查询一级分类下的最新六条商品数据")
    public HttpJSONResult getSixNewItemsByRootId(@PathVariable(required = true) Integer rootCatId) {
        if (rootCatId == null) return HttpJSONResult.errorMsg("分类不存在");

        List<NewItemsVo> data = categoryService.getSixNewItemsLazy(rootCatId);
        return HttpJSONResult.ok(data);
    }
}
