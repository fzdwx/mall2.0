package com.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.mapper.UsersMapper;
import com.like.pojo.Users;
import com.like.pojo.bo.UserBo;
import com.like.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-06 17:25
 */
@Service
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

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

    @Override
    @Transactional(propagation = Propagation.REQUIRED) // 运行在事务中，如果没有就新创建一个
    public Users createUser(UserBo user) {
        Users u = new Users(user);
        int insert = usersMapper.insert(u);

        return insert == 1 ? u : null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS) // 有事务就加入，没有就算了
    public Users queryUserForLogin(String username, String password) {
        QueryWrapper<Users> query = new QueryWrapper<>();
        query.eq("username", username).eq("password", password);

        return usersMapper.selectOne(query);
    }
}
