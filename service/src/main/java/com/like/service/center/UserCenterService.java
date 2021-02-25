package com.like.service.center;

import com.baomidou.mybatisplus.extension.service.IService;
import com.like.pojo.Users;
import com.like.pojo.bo.center.UserCenterBo;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-24 15:31
 */
public interface UserCenterService extends IService<Users> {

    /**
     * 查询用户信息
     *
     * @param userId 用户id
     * @return {@link Users}
     */
    Users queryUserInfo(String userId);

    /**
     * 更新用户信息
     *
     * @param userId 用户id
     * @param user   用户
     * @return {@link Users}
     */
    Users updateUseUserCenterBO(String userId, UserCenterBo user);

    /**
     * 更新用户的头像
     *
     * @param userId 用户id
     * @param url    url
     * @return
     */
    Users updateUserFace(String userId, String url);
}
