package com.like.service.impl.center;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.mapper.UsersMapper;
import com.like.pojo.Users;
import com.like.service.center.UserCenterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-24 15:33
 */
@Service
public class UserCenterServiceImpl extends ServiceImpl<UsersMapper, Users> implements UserCenterService {

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserInfo(String userId) {
        Users dbUser = baseMapper.selectById(userId);
        dbUser.setPassword(null);
        return dbUser;
    }
}
