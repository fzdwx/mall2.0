package com.like.controller.center;

import com.like.pojo.Users;
import com.like.pojo.bo.center.UserCenterBo;
import com.like.service.center.UserCenterService;
import com.like.utils.CookieUtils;
import com.like.utils.DateUtil;
import com.like.utils.HttpJSONResult;
import com.like.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 用户中心
 * @since 2021-02-24 15:27
 */
@Api(value = "用户中心", tags = "用户中心相关接口")
@RestController
@RequestMapping("userInfo")
public class UserCenterController {

    @Autowired
    UserCenterService userCenterService;

    @GetMapping("update")
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    public HttpJSONResult queryUserInfo(
            @RequestParam String userId,
            @RequestBody UserCenterBo user,
            HttpServletRequest req, HttpServletResponse reps) {
        Users resUser = userCenterService.updateUseUserCenterBO(userId, user);


        // 保护用户隐私信息
        setNullProperty(resUser);
        CookieUtils.setCookie(req, reps,
                "user",
                JsonUtils.objectToJson(resUser), true);
        // TODO: 2021/2/24 后续会更改，增加token，整合到redis，分布式会话
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
