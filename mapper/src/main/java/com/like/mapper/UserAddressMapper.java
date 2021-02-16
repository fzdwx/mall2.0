package com.like.mapper;

import com.like.my.mapper.MyMapper;
import com.like.pojo.UserAddress;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAddressMapper extends MyMapper<UserAddress> {
}