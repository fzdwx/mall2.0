package com.like.service;

import com.like.pojo.Category;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 首页分类展示
 * @since 2021-02-09 17:37
 */
public interface CategoryService {

    public List<Category> queryAllRootLevelCat();

}
