package com.like.service;

import com.like.pojo.Users;
import com.like.pojo.bo.UserBo;
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

    /**
     * 创建用户
     *
     * @param user 用户
     * @return {@link Users}
     */
    public Users createUser(UserBo user);

    /**
     * 查询用户名和密码是否匹配
     *
     * @param username 用户名
     * @param password 密码
     * @return {@link Users}
     */
    public Users queryUserForLogin(String username, String password);
}
