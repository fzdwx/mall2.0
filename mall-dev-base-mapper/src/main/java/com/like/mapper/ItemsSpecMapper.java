package com.like.mapper;

import com.like.my.mapper.MyMapper;
import com.like.pojo.ItemsSpec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ItemsSpecMapper extends MyMapper<ItemsSpec> {

    int decreaseItemSpecStock(@Param("id") String id, @Param("buyCount") int buyCount);
}