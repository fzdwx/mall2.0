package com.like.service.impl.center;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.mapper.UsersMapper;
import com.like.pojo.Users;
import com.like.pojo.bo.center.UserCenterBo;
import com.like.service.center.UserCenterService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

    @Override
    public Users updateUseUserCenterBO(String userId, UserCenterBo user) {
        Users updateUsers = new Users();
        BeanUtils.copyProperties(user, updateUsers);

        updateUsers.setId(userId);
        updateUsers.setUpdatedTime(new Date());

        updateById(updateUsers);

        return updateUsers;
    }
}
