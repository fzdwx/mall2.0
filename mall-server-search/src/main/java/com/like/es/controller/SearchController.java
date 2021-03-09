package com.like.es.controller;

import com.like.es.constant.ES;
import com.like.es.service.ItemsSearchService;
import com.like.utils.HttpJSONResult;
import com.like.utils.PagedGridResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-09 15:07
 * @deprecated es 服务提供给外部搜索商品信息的接口
 */
@RestController
public class SearchController {

    @Autowired
    private ItemsSearchService ItemsSearchService;

    @GetMapping("/hello")
    public String hello() {
        return "You Know, for Search!";
    }

    @GetMapping("/es/search/items")
    public HttpJSONResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return HttpJSONResult.errorMsg("关键字为空");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = ES.PAGE_SIZE;
        }

        PagedGridResult pageGrid = ItemsSearchService.searchItems(keywords, sort, page, pageSize);

        return HttpJSONResult.ok(pageGrid);
    }
}
