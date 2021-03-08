package com.like.mapper;

import com.like.my.mapper.MyMapper;
import com.like.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper extends MyMapper<Users> {
}