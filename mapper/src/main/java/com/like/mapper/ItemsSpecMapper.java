package com.like.mapper;

import com.like.my.mapper.MyMapper;
import com.like.pojo.ItemsSpec;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemsSpecMapper extends MyMapper<ItemsSpec> {
}