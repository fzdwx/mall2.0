package com.like.controller;

import com.like.service.UsersService;
import com.like.utils.HttpJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-06 17:39
 */
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/usernameIsExist")
    public HttpJSONResult usernameIsExist(@RequestParam String username) {
        if (StringUtils.isBlank(username)) return HttpJSONResult.errorMsg("用户名不能为空");

        return usersService.queryUserNameIsExist(username) ? HttpJSONResult.ok() : HttpJSONResult.errorMsg("用户名不存在");
    }

}
