package com.like.controller;

import com.like.controller.base.BaseController;
import com.like.enums.YesOrNo;
import com.like.pojo.Carousel;
import com.like.pojo.Category;
import com.like.pojo.vo.CategoryVo;
import com.like.pojo.vo.NewItemsVo;
import com.like.service.CarouselService;
import com.like.service.CategoryService;
import com.like.utils.HttpJSONResult;
import com.like.utils.JsonUtils;
import com.like.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
public class IndexController extends BaseController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("carousel")
    @ApiOperation(value = "获取首页轮播图列表")
    public HttpJSONResult carousel() {
        List<Carousel> dbDataList = null;
        // 1.先从redis中获取缓存
        String redisCacheJson = redisUtil.get(REDIS_KEY_CAROUSEL);
        // 2.判断是否有缓存
        if (StringUtils.isBlank(redisCacheJson)) {
            dbDataList = carouselService.queryAllRootLevelCat(YesOrNo.YES.code);
            // 第一次查询，保存一份到redis中作为缓存
            redisUtil.set(REDIS_KEY_CAROUSEL, JsonUtils.objectToJson(dbDataList));
        } else dbDataList = JsonUtils.jsonToList(redisCacheJson, Carousel.class);

        /*
         * 如果轮播图发生修改怎么办？
         * 1.后台运营系统，一旦发生更改，就可以删除缓存，然后重置缓存
         * 2.定时重置，清除缓存的时候尽量分开，避免缓存雪崩
         *
         */
        return HttpJSONResult.ok(dbDataList);
    }

    @GetMapping("cats")
    @ApiOperation(value = "获取首页一级分类信息")
    public HttpJSONResult cats() {
        List<Category> dbDataList = null;

        String redisCacheJson = redisUtil.get(REDIS_KEY_CATS);
        if (StringUtils.isBlank(redisCacheJson)) {
            dbDataList = categoryService.queryAllRootLevelCat();
            redisUtil.set(REDIS_KEY_CATS, JsonUtils.objectToJson(dbDataList));
        } else dbDataList = JsonUtils.jsonToList(redisCacheJson, Category.class);

        return HttpJSONResult.ok(dbDataList);
    }

    @GetMapping("/subCat/{rootCatId}")
    @ApiOperation(value = "获取一级分类下的子分类信息")
    public HttpJSONResult subCat(@PathVariable(required = true) Integer rootCatId) {
        if (rootCatId == null) return HttpJSONResult.errorMsg("分类不存在");
        List<CategoryVo> dbDataList = null;

        String redisCacheJson = redisUtil.get(REDIS_KEY_SUB_CAT_PREFIX + ":" + rootCatId);
        if (StringUtils.isBlank(redisCacheJson)) {
            dbDataList = categoryService.getSubCatList(rootCatId);
            redisUtil.set(REDIS_KEY_SUB_CAT_PREFIX + ":" + rootCatId, JsonUtils.objectToJson(dbDataList));
        } else dbDataList = JsonUtils.jsonToList(redisCacheJson, CategoryVo.class);

        return HttpJSONResult.ok(dbDataList);
    }

    @GetMapping("/sixNewItems/{rootCatId}")
    @ApiOperation(value = "查询一级分类下的最新六条商品数据")
    public HttpJSONResult getSixNewItemsByRootId(@PathVariable(required = true) Integer rootCatId) {
        if (rootCatId == null) return HttpJSONResult.errorMsg("分类不存在");

        List<NewItemsVo> data = categoryService.getSixNewItemsLazy(rootCatId);
        return HttpJSONResult.ok(data);
    }
}
