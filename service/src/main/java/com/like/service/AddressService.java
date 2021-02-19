package com.like.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.like.pojo.UserAddress;
import com.like.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService extends IService<UserAddress> {

    /**
     * 查询所有该用户下的地址
     *
     * @param userId 用户id
     * @return {@link List<UserAddress>}
     */
    public List<UserAddress> queryAll(String userId);

    /**
     * 添加新用户地址
     *
     * @param address 地址
     * @return boolean
     */
    boolean addNewUserAddress(AddressBO address);

    /**
     * 更新用户地址
     *
     * @param address 地址
     * @return boolean
     */
    void updateUserAddress(AddressBO address);

    /**
     * 删除用户地址
     *
     * @param userId    用户id
     * @param addressId 地址标识
     */
    void deleteUserAddress(String userId, String addressId);

    /**
     * 设置默认
     *
     * @param userId    用户id
     * @param addressId 地址标识
     */
    void setDefault(String userId, String addressId);

    /**
     * 查询用户地址
     *
     * @param userId    用户id
     * @param addressId 地址标识
     * @return {@link UserAddress}
     */
    UserAddress queryUserAddress(String userId, String addressId);
}
