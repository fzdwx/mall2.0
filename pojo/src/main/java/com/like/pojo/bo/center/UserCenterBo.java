package com.like.pojo.bo.center;

import lombok.Data;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-24 16:38
 */
@Data
public class UserCenterBo {
    private String username;
    private String password;
    private String confirmPassword;
    private String nickname;
    private String realname;
    private String mobile;
    private String email;
    private String sex;
    private String birthday;
}
