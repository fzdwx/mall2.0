package com.like.mapper;

import com.like.my.mapper.MyMapper;
import com.like.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends MyMapper<Category> {
}