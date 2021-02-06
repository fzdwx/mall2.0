package com.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.like.mapper.UsersMapper;
import com.like.pojo.Users;
import com.like.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-06 17:25
 */
@Service
@Slf4j
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUserNameIsExist(String username) {
        /*  tk包
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username", username);
        Users u = usersMapper.selectOneByExample(userExample);
        */
        QueryWrapper<Users> query = new QueryWrapper<>();
        Users u = usersMapper.selectOne(query.eq("username", username));
        return u != null;
    }
}
