package com.like.controller;

import com.like.pojo.Users;
import com.like.pojo.vo.UsersVO;
import com.like.service.UsersService;
import com.like.utils.JsonUtils;
import com.like.utils.MD5Utils;
import com.like.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-06 15:02
 */
@Controller
public class SSOController {

    /** 用户token前缀 */
    public static String REDIS_USER_TOKEN_PREFIX = "userToken:";
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UsersService usersService;

    /**
     * 接收其他服务的登录请求
     */
    @GetMapping("/login")
    public String login(
            String returnUrl, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("returnUrl", returnUrl);

        // TODO: 2021/3/6 后续完善校验是否登录
        return "login";
    }

    @PostMapping("doLogin")
    public String doLogin(
            String username, String password, String returnUrl, Model model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        model.addAttribute("returnUrl", returnUrl);
        // 1.登录检验
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            model.addAttribute("errmsg", "用户名和密码不能为空");
            return "login";
        }
        Users dbUser = usersService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (dbUser == null) {
            model.addAttribute("errmsg", "用户名或密码不正确,请检查后在试");
            return "login";
        }
        // 2.实现用户的redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        UsersVO toWebUser = new UsersVO();
        BeanUtils.copyProperties(dbUser, toWebUser);
        toWebUser.setUserUniqueToken(uniqueToken);
        redisUtil.set(REDIS_USER_TOKEN_PREFIX + toWebUser.getId(), JsonUtils.objectToJson(toWebUser));
        return "login";
    }
}
