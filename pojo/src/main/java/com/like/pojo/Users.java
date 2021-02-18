package com.like.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.like.enums.Sex;
import com.like.pojo.bo.UserBo;
import com.like.utils.DateUtil;
import com.like.utils.MD5Utils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.n3r.idworker.Sid;

import java.io.Serializable;
import java.util.Date;

import static com.like.utils.DateUtil.ISO_EXPANDED_DATE_FORMAT;

/**
 * Created by Mybatis Generator 2021/02/16
 */
@ApiModel(value = "com.like.pojo.Users")
@Data
@TableName(value = "users")
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {

    @TableField(exist = false)
    private static Sid sid = new Sid();

    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_FACE_URL = "https://i1.hdslb.com/bfs/face/c6679005eb3ccd47bf1d3a1a4a45622541ff2a59.jpg@140w_140h_1c.webp";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_NICKNAME = "nickname";
    public static final String COL_REALNAME = "realname";
    public static final String COL_FACE = "face";
    public static final String COL_MOBILE = "mobile";
    public static final String COL_EMAIL = "email";
    public static final String COL_SEX = "sex";
    public static final String COL_BIRTHDAY = "birthday";
    public static final String COL_CREATED_TIME = "created_time";
    public static final String COL_UPDATED_TIME = "updated_time";
    /**
     * 主键id 用户id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键id 用户id")
    private String id;
    /**
     * 用户名 用户名
     */
    @TableField(value = "username")
    @ApiModelProperty(value = "用户名 用户名")
    private String username;
    /**
     * 密码 密码
     */
    @TableField(value = "password")
    @ApiModelProperty(value = "密码 密码")
    private String password;
    /**
     * 昵称 昵称
     */
    @TableField(value = "nickname")
    @ApiModelProperty(value = "昵称 昵称")
    private String nickname;
    /**
     * 真实姓名 真实姓名
     */
    @TableField(value = "realname")
    @ApiModelProperty(value = "真实姓名 真实姓名")
    private String realname;
    /**
     * 头像 头像
     */
    @TableField(value = "face")
    @ApiModelProperty(value = "头像 头像")
    private String face;
    /**
     * 手机号 手机号
     */
    @TableField(value = "mobile")
    @ApiModelProperty(value = "手机号 手机号")
    private String mobile;
    /**
     * 邮箱地址 邮箱地址
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "邮箱地址 邮箱地址")
    private String email;
    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    @TableField(value = "sex")
    @ApiModelProperty(value = "性别 性别 1:男  0:女  2:保密")
    private Integer sex;
    /**
     * 生日 生日
     */
    @TableField(value = "birthday")
    @ApiModelProperty(value = "生日 生日")
    private Date birthday;
    /**
     * 创建时间 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间 创建时间")
    private Date createdTime;
    /**
     * 更新时间 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "更新时间 更新时间")
    private Date updatedTime;

    public Users(UserBo user) {
        this(user.getUsername(), user.getPassword());
    }

    public Users(String username, String password) {
        this.id = sid.nextShort();
        this.username = username;
        try {
            this.password = MD5Utils.getMD5Str(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.nickname = username;
        this.realname = username;
        this.sex = Sex.secret.code;
        this.face = DEFAULT_FACE_URL;
        this.createdTime = new Date();
        this.updatedTime = new Date();
        this.birthday = DateUtil.stringToDate("1970-01-01", ISO_EXPANDED_DATE_FORMAT);
    }
}