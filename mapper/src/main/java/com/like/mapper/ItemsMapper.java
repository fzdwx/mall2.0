package com.like.mapper;

import com.like.my.mapper.MyMapper;
import com.like.pojo.Items;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemsMapper extends MyMapper<Items> {
}