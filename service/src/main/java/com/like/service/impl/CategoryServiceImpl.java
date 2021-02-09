package com.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.like.enums.CategoryLevel;
import com.like.mapper.CategoryMapper;
import com.like.pojo.Category;
import com.like.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-09 17:38
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryAllRootLevelCat() {
        QueryWrapper<Category> query = new QueryWrapper<Category>()
                .eq("type", CategoryLevel.ROOT.code);
        return categoryMapper.selectList(query);
    }
}
