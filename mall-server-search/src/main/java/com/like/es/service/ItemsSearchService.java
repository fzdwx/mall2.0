package com.like.es.service;

import com.like.utils.PagedGridResult;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-09 15:33
 */
public interface ItemsSearchService {

    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);
}
