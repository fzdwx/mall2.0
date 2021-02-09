package com.like.controller;

import com.like.enums.YesOrNo;
import com.like.pojo.Carousel;
import com.like.pojo.Category;
import com.like.service.CarouselService;
import com.like.service.CategoryService;
import com.like.utils.HttpJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
