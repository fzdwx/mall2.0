package com.like.controller;

import com.like.controller.base.BaseController;
import com.like.pojo.Users;
import com.like.pojo.bo.ShopCartBO;
import com.like.pojo.bo.UserBo;
import com.like.pojo.vo.UsersVO;
import com.like.service.UsersService;
import com.like.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-06 17:39
 */
@RestController
@RequestMapping("passport")
@Api(value = "用户登录注册相关接口", tags = {"用户登录注册相关接口"})
public class PassportController extends BaseController {

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
    public HttpJSONResult regist(
            @RequestBody UserBo user, HttpServletRequest req,
            HttpServletResponse reps) throws UnsupportedEncodingException {
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
        Users createUser = usersService.createUser(user);

        if (createUser == null) return HttpJSONResult.errorMsg("用户数据保存失败，请稍后再试");
        // 5.实现用户redis会话 随机生成一个token保存到redis中
        UsersVO conventUsers = conventUsersVO(createUser);

        // 6.保存用户信息到cookie
        CookieUtils.setCookie(req, reps,
                              COOKIE_FOODIE_USER_INFO_KEY,
                              JsonUtils.objectToJson(conventUsers), true);
        syncShopCart(req, reps, createUser.getId());
        return HttpJSONResult.ok(conventUsers);
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public HttpJSONResult login(
            @RequestBody UserBo user,
            HttpServletRequest req,
            HttpServletResponse reps) throws Exception {
        String username = user.getUsername();
        String password = user.getPassword();

        // 0. 用户名以及密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) return HttpJSONResult.errorMsg("用户名和密码不能为空");

        Users createUser = usersService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if (createUser == null) return HttpJSONResult.errorMsg("用户名或密码不正确,请检查后在试");

        // 1.生成用户token 存入redis会话
        UsersVO conventUsers = conventUsersVO(createUser);

        // 2.同步购物车数据 cookie 和 redis 中保存的购物车信息
        syncShopCart(req, reps, createUser.getId());
        // 3.同步到前端
        CookieUtils.setCookie(req, reps,
                              COOKIE_FOODIE_USER_INFO_KEY,
                              JsonUtils.objectToJson(conventUsers), true);
        return HttpJSONResult.ok(conventUsers);
    }

    /**
     * 用户注册成功后，同步cookie中的购物车到redis中
     * @param req http请求
     * @param userId 用户id
     */
    private void syncShopCart(
            HttpServletRequest req, HttpServletResponse resp,
            String userId) throws UnsupportedEncodingException {
        // 0.获取cookie以及redis中的购物车信息
        String cookieShopCart = CookieUtils.getCookieValue(req, COOKIE_FOODIE_SHOPCART_KEY);
        String redisShopCart = redisUtil.get(REDIS_KEY_SHOP_CART_PREFIX + userId);

        // 1.根据各种情况处理
        if (StringUtils.isBlank(redisShopCart)) {
            if (StringUtils.isNotBlank(cookieShopCart)) {           // redis中为空且cookie不为空就让cookie覆盖
                cookieShopCart = URLDecoder.decode(cookieShopCart, "utf-8");  // 要解码，不然全是乱码  写在这为了减少一次判断
                redisUtil.set(REDIS_KEY_SHOP_CART_PREFIX + userId, cookieShopCart);
            } // 都为空就不操作
        } else if (StringUtils.isNotBlank(redisShopCart)) {
            if (StringUtils.isBlank(cookieShopCart)) {  // redis不为空且cookie为空，给cookie中存入redis中保存的购物车
                CookieUtils.setCookie(req, resp, COOKIE_FOODIE_SHOPCART_KEY, redisShopCart, true);
            } else {        // 都不为空，如果cookie和redis中都存在该购物信息，以cookie中的购物数为准
                List<ShopCartBO> shopCart = JsonUtils.jsonToList(cookieShopCart, ShopCartBO.class);
                Map<String, ShopCartBO> redis = JsonUtils.jsonToList(redisShopCart, ShopCartBO.class).stream()
                                                         .collect(Collectors.toMap(ShopCartBO::getSpecId, e -> e));
                for (ShopCartBO s : shopCart) {    // 存在就覆盖购买数量
                    String specId = s.getSpecId();
                    if (redis.get(specId) != null) {
                        redis.remove(specId);
                    }
                }
                shopCart.addAll(redis.values());

                redisUtil.set(REDIS_KEY_SHOP_CART_PREFIX + userId, JsonUtils.objectToJson(shopCart));
            }
        }
    }

    @PostMapping("/logout")
    @ApiOperation(value = "用户退出登录")
    public HttpJSONResult logout(
            @RequestParam String userId,
            HttpServletRequest req,
            HttpServletResponse reps) throws Exception {

        // 清除用户对应的cookie
        CookieUtils.deleteCookie(req, reps, COOKIE_FOODIE_USER_INFO_KEY);
        // 用户退出登录清除user token
        redisUtil.delete(REDIS_USER_TOKEN_PREFIX + userId);
        //  在分布式session中要清除数据
        CookieUtils.deleteCookie(req, reps, COOKIE_FOODIE_SHOPCART_KEY);
        return HttpJSONResult.ok();
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
