package com.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.mapper.UserAddressMapper;
import com.like.pojo.UserAddress;
import com.like.pojo.bo.AddressBO;
import com.like.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-17 17:15
 */
@Service
public class AddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements AddressService {


    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserAddress> queryAll(String userId) {

        return list(new QueryWrapper<UserAddress>().eq(UserAddress.COL_USER_ID, userId));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean addNewUserAddress(AddressBO address) {
        // 判断当前用户是否存在地址，如果没有，则设置为 默认地址
        int isDefault = 0;
        List<UserAddress> dbAddress = queryAll(address.getUserId());
        if (dbAddress == null || dbAddress.size() == 0) {
            isDefault = 1;
        }

        String addressId = sid.nextShort();
        UserAddress target = new UserAddress();
        BeanUtils.copyProperties(address, target);
        target.setIsDefault(isDefault);
        target.setCreatedTime(new Date());
        target.setUpdatedTime(new Date());

        return save(target);
    }

    @Override
    public void updateUserAddress(AddressBO address) {

        UserAddress target = new UserAddress();
        BeanUtils.copyProperties(address, target);

        target.setId(address.getAddressId());
        target.setUpdatedTime(new Date());
        updateById(target);
    }
}
