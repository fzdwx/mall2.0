package com.like.controller;

import com.like.pojo.Users;
import com.like.pojo.bo.UserBo;
import com.like.service.UsersService;
import com.like.utils.DateUtil;
import com.like.utils.HttpJSONResult;
import com.like.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-06 17:39
 */
@RestController
@RequestMapping("passport")
@Api(value = "用户登录注册相关接口")
public class PassportController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/usernameIsExist")
    @ApiOperation(value = "判断用户名是否存在")
    public HttpJSONResult usernameIsExist(@RequestParam String username) {
        if (StringUtils.isBlank(username)) return HttpJSONResult.errorMsg("用户名不能为空");

        return !usersService.queryUserNameIsExist(username) ? HttpJSONResult.ok() : HttpJSONResult.errorMsg("用户名已经存在");
    }

    @PostMapping("/regist")
    @ApiOperation(value = "用户注册")
    public HttpJSONResult regist(@RequestBody UserBo user, HttpServletRequest req,
                                 HttpServletResponse reps) {
        String username = user.getUsername();
        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();

        // 0. 用户名以及密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPassword)) return HttpJSONResult.errorMsg("用户名和密码不能为空");
        // 1. 两次输入密码必须相等
        if (!password.equals(confirmPassword)) return HttpJSONResult.errorMsg("两次输入密码不相同");
        // 2. 用户名是否存在
        if (usersService.queryUserNameIsExist(username)) return HttpJSONResult.errorMsg("用户名已经存在");
        // 3. 密码不少于6位
        if (password.length() < 6) {
            return HttpJSONResult.errorMsg("密码长度不能于6位");
        }

        // 4. 注册
        Users u = usersService.createUser(user);

        if (u == null) return HttpJSONResult.errorMsg("用户数据保存失败，请稍后再试");

        // 1.保护用户隐私信息
        setNullProperty(u);

        CookieUtils.setCookie(req, reps,
                "user",
                JsonUtils.objectToJson(u), true);
        return HttpJSONResult.ok();
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public HttpJSONResult login(@RequestBody UserBo user,
                                HttpServletRequest req,
                                HttpServletResponse reps) throws Exception {
        String username = user.getUsername();
        String password = user.getPassword();

        // 0. 用户名以及密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) return HttpJSONResult.errorMsg("用户名和密码不能为空");

        Users u = usersService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if (u == null) return HttpJSONResult.errorMsg("用户名或密码不正确,请检查后在试");

        // 1.保护用户隐私信息
        setNullProperty(u);

        CookieUtils.setCookie(req, reps,
                "user",
                JsonUtils.objectToJson(u), true);
        return HttpJSONResult.ok(u);
    }

    private void setNullProperty(Users u) {
        u.setPassword("");
        u.setMobile("");
        u.setEmail("");
        u.setBirthday(DateUtil.stringToDate(""));
        u.setCreatedTime(DateUtil.stringToDate(""));
        u.setUpdatedTime(DateUtil.stringToDate(""));
    }

}
