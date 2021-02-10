package com.like.mapper;

import com.like.my.mapper.MyMapper;
import com.like.pojo.Category;
import com.like.pojo.vo.CategoryVo;
import com.like.pojo.vo.NewItemsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper extends MyMapper<Category> {

    public List<CategoryVo> getSubCatList(@Param("rootCatId") Integer rootCatId);

    public List<NewItemsVo> getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> paramsMap);
}