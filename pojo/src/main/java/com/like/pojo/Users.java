package com.like.pojo;

import com.like.enums.Sex;
import com.like.pojo.bo.UserBo;
import com.like.utils.DateUtil;
import com.like.utils.MD5Utils;
import lombok.Data;
import org.n3r.idworker.Sid;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "users")
public class Users {

    private static final String USER_FACE = "https://a.msstatic.com/huya/icenter/main/img/header_hover_6f5fb29.png";

    private static final Sid sid = new Sid();

    public Users() {
    }

    public Users(UserBo user) {
        this(user.getUsername(), user.getPassword());
    }

    public Users(String username, String password) {
        try {
            this.username = username;
            this.password = MD5Utils.getMD5Str(password);
            // 使用idWorker获取主键
            this.id = sid.nextShort();
            // 设置默认信息
            this.nickname = username;
            this.face = USER_FACE;
            this.setBirthday(DateUtil.stringToDate("1970-01-01"));
            this.sex = Sex.secret.code;
            this.setCreatedTime(new Date());
            this.setUpdatedTime(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 主键id 用户id
     */
    @Id
    private String id;

    /**
     * 用户名 用户名
     */
    private String username;

    /**
     * 密码 密码
     */
    private String password;

    /**
     * 昵称 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 头像
     */
    private String face;

    /**
     * 手机号 手机号
     */
    private String mobile;

    /**
     * 邮箱地址 邮箱地址
     */
    private String email;

    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    private Integer sex;

    /**
     * 生日 生日
     */
    private Date birthday;

    /**
     * 创建时间 创建时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 更新时间 更新时间
     */
    @Column(name = "updated_time")
    private Date updatedTime;

}