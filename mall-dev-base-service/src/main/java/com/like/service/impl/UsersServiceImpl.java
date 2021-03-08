package com.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.mapper.UsersMapper;
import com.like.pojo.Users;
import com.like.pojo.bo.UserBo;
import com.like.pojo.bo.center.UserCenterBo;
import com.like.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserInfo(String userId) {
        Users dbUser = baseMapper.selectById(userId);
        dbUser.setPassword(null);
        return dbUser;
    }

    @Override
    public Users updateUseUserCenterBO(String userId, UserCenterBo user) {
        Users updateUsers = new Users();
        BeanUtils.copyProperties(user, updateUsers);

        updateUsers.setId(userId);
        updateUsers.setUpdatedTime(new Date());

        updateById(updateUsers);

        return updateUsers;
    }

    @Override
    public Users updateUserFace(String userId, String url) {
        Users users = new Users();
        users.setId(userId);
        users.setFace(url);
        updateById(users);

        return queryUserInfo(userId);
    }

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
