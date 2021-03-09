package com.like.sso.controller;

import com.like.pojo.Users;
import com.like.pojo.vo.UsersVO;
import com.like.service.UsersService;
import com.like.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-06 15:02
 * CAS登录接口
 */
@Controller
public class SSOController {

    /** 用户token前缀 userToken:userId */
    public static String REDIS_USER_TOKEN_PREFIX = "userToken:";
    /** 用户全局门票 userTicket:ticket,userId */
    public static String REDIS_USER_TICKET_PREFIX = "userTicket:";
    /** 临时门票 userTempTicket:tempTicket */
    public static String REDIS_USER_TEMP_TICKET_PREFIX = "userTempTicket:";
    /** 用户保存在cookie中的key */
    public static final String COOKIE_FOODIE_USER_INFO_KEY = "user";
    public static String COOKIE_USER_TICKET = "cookieUserTicket";
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

        // 第二次登陆
        String userTicket = CookieUtils.getCookieValue(request, COOKIE_USER_TICKET, true);
        if (verifyUserTicket(userTicket)) {
            String tempTicket = createTempTicket();
            return "redirect:" + returnUrl + "?tempTicket=" + tempTicket;
        }
        // 第一次登陆
        return "login";
    }

    /**
     * 注销
     */
    @PostMapping("/logout")
    @ResponseBody
    public HttpJSONResult logout(
            @RequestParam String userId,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1.获取cookie中用户cas的全局门票
        String userTicket = CookieUtils.getCookieValue(request, COOKIE_USER_TICKET, true);
        // 2.删除cookie中的用户全局门票
        CookieUtils.deleteCookie(request, response, COOKIE_USER_TICKET);
        // 3.删除用户redis中的用户全局门票信息
        redisUtil.delete(REDIS_USER_TICKET_PREFIX + userTicket);
        // 4.清除用户全局会话 token
        redisUtil.delete(REDIS_USER_TOKEN_PREFIX + userId);
        return HttpJSONResult.ok();
    }

    /**
     * CAS的统一接口 第一次登陆
     * 目的：
     * 1.登录后创建用户的全局会话                    -> uniqueToken
     * 2.创建用户全局门票，表示在cas端是否登录         ->  userTicket
     * 3.创建用户的临时票据，用于回跳回传              ->   tempTicket
     */
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
        // 2.实现用户的redis会话 uniqueToken
        String uniqueToken = UUID.randomUUID().toString().trim();   // 创建用户的全局会话
        UsersVO toWebUser = new UsersVO();
        BeanUtils.copyProperties(dbUser, toWebUser);
        toWebUser.setUserUniqueToken(uniqueToken);
        redisUtil.set(REDIS_USER_TOKEN_PREFIX + toWebUser.getId(), JsonUtils.objectToJson(toWebUser));

        // 3.生成ticket门票，全局门票，代表用户在CAS登录过
        String userTicket = UUID.randomUUID().toString().trim();
        CookieUtils.setCookie(request, response, COOKIE_USER_TICKET, userTicket, true);
        // 4.全局门票和用户id关联
        redisUtil.set(REDIS_USER_TICKET_PREFIX + userTicket, toWebUser.getId());
        // 5.生成临时票据，回跳到调用端网站，由cas端临时签发的ticket
        String tempTicket = createTempTicket();

        return "redirect:" + returnUrl + "?tempTicket=" + tempTicket;
    }

    /**
     * 验证临时门票
     * - 用户验证为登录状态后会返回一个临时门票，并在redis中保存这个临时门票
     * - 经过检验后，成功会在cookie保存用户信息
     * - 失败就返回异常
     * @param tempTicket 临时的机票
     * @param request 请求
     * @param response 响应
     * @return {@link HttpJSONResult}
     * @throws Exception 异常
     */
    @PostMapping("/verifyTmpTicket")
    @ResponseBody
    public HttpJSONResult verifyTmpTicket(
            @RequestParam String tempTicket,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1.获取保存在redis中的用户临时门票 验证
        String ticket = redisUtil.get(REDIS_USER_TEMP_TICKET_PREFIX + tempTicket);
        if (StringUtils.isBlank(ticket)) {
            return HttpJSONResult.errorUserTicket("用户临时门票不存在");
        }
        if (!ticket.equals(MD5Utils.getMD5Str(tempTicket))) {
            return HttpJSONResult.errorUserTicket("用户临时门票异常");
        } else {
            redisUtil.delete(REDIS_USER_TEMP_TICKET_PREFIX + tempTicket);
        }
        // 2.获取cookie的用户全局门票 验证并获取用户信息
        String userTicket = CookieUtils.getCookieValue(request, COOKIE_USER_TICKET, true);
        if (StringUtils.isBlank(userTicket)) {
            return HttpJSONResult.errorUserTicket("用户全局门票异常");
        }
        String userId = redisUtil.get(REDIS_USER_TICKET_PREFIX + userTicket);
        String redisUserJson = redisUtil.get(REDIS_USER_TOKEN_PREFIX + userId);

        if (StringUtils.isNotBlank(redisUserJson)) {
            CookieUtils.setCookie(request, response, COOKIE_FOODIE_USER_INFO_KEY, redisUserJson, true);
        }
        return HttpJSONResult.ok(JsonUtils.jsonToPojo(redisUserJson, UsersVO.class));
    }

    /**
     * 验证用户的全局门票
     * @param userTicket 全局门票
     * @return boolean
     */
    private boolean verifyUserTicket(String userTicket) {
        if (StringUtils.isBlank(userTicket)) {
            return false;
        }
        String userId = redisUtil.get(REDIS_USER_TICKET_PREFIX + userTicket);
        if (StringUtils.isBlank(userId)) {
            return false;
        }
        String userInfo = redisUtil.get(REDIS_USER_TOKEN_PREFIX + userId);
        if (StringUtils.isBlank(userInfo)) {
            return false;
        }
        return true;
    }

    /**
     * 创建临时门票 userTempTicket:tempTicket
     * @return {@link String}
     */
    private String createTempTicket() {
        String res = UUID.randomUUID().toString().trim();
        try {
            redisUtil.setEx(REDIS_USER_TEMP_TICKET_PREFIX + res, MD5Utils.getMD5Str(res), 600, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
