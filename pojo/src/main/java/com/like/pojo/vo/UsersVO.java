package com.like.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-05 19:21
 */
@Data
public class UsersVO {
    /**
     * 生日 生日
     */
    private Date birthday;
    /**
     * 头像 头像
     */

    private String face;
    /**
     * 主键id 用户id
     */
    private String id;
    /**
     * 昵称 昵称
     */

    private String nickname;
    /**
     * 性别 性别 1:男  0:女  2:保密
     */

    private Integer sex;
    /** 用户独特的令牌 */
    private String userUniqueToken;
    /**
     * 用户名 用户名
     */
    private String username;
}
