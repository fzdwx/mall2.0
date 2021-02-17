package com.like.controller;

import cn.hutool.core.util.StrUtil;
import com.like.pojo.bo.ShopCartBO;
import com.like.pojo.vo.ShopCartVO;
import com.like.service.ItemService;
import com.like.utils.HttpJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-14 17:22
 */
@Api(value = "购物车接口")
@RequestMapping("shopcart")
@Slf4j
@RestController
public class ShopCartController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/add")
    @ApiOperation(value = "添加商品到购物车")
    public HttpJSONResult add(@RequestParam String userId, @RequestBody ShopCartBO shopCart,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        if (StrUtil.isBlank(userId)) return HttpJSONResult.errorMsg("user Id 不能为空");
        log.info("购物车数据:{}", shopCart);
        // TODO: 2021/2/16 前端用户在登录的时候，添加商品上购物车，会同时在后端同步购物车到redis中

        HttpSession session = request.getSession();
        return HttpJSONResult.ok();
    }

    @PostMapping("/del")
    @ApiOperation(value = "从购物车中删除商品")
    public HttpJSONResult del(@RequestParam String userId, @RequestParam String itemSpecId,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId))
            return HttpJSONResult.errorMsg("请求参数不完整");
        log.info("用户id:{}-删除商品规格id:{}", userId, itemSpecId);
        // TODO: 2021/2/16 如果用户已经登录，则需要同步删除后端购物车中的商品

        HttpSession session = request.getSession();
        return HttpJSONResult.ok();
    }

    /**
     * 用于用户长时间未登录网站 刷新购物车中的数据
     */
    @GetMapping("/refresh/{itemSpecIds}")
    @ApiOperation(value = "根据商品规格的id list 查询对应的商品信息")
    public HttpJSONResult refresh(@PathVariable String itemSpecIds) {
        log.info("商品规格id列表:{}", itemSpecIds);
        if (StrUtil.isBlank(itemSpecIds)) return HttpJSONResult.ok();
        List<ShopCartVO> data = itemService.queryItemsBySpecId(itemSpecIds);
        return HttpJSONResult.ok(data);
    }
}
