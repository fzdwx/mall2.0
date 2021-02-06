package com.like.service;

import org.apache.ibatis.annotations.Param;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-06 17:24
 */
public interface UsersService {


    /**
     * 查询用户名存在
     *
     * @param username 用户名
     * @return boolean
     */
    public boolean queryUserNameIsExist(@Param("username") String username);
}
