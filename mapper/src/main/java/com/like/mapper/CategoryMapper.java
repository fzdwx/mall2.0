package com.like.mapper;

import com.like.my.mapper.MyMapper;
import com.like.pojo.Category;
import com.like.pojo.vo.CategoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper extends MyMapper<Category> {

    public List<CategoryVo> getSubCatList(@Param("rootCatId") Integer rootCatId);
}