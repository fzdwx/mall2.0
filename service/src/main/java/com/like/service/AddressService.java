package com.like.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.like.pojo.UserAddress;

import java.util.List;

public interface AddressService extends IService<UserAddress> {

    /**
     * 查询所有该用户下的地址
     *
     * @param userId 用户id
     * @return {@link List<UserAddress>}
     */
    public List<UserAddress> queryAll(String userId);

}
