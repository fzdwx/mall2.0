package com.like.controller;

import com.like.service.UsersService;
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
    public int usernameIsExist(@RequestParam String username) {
        if (StringUtils.isBlank(username)) return 500;

        return usersService.queryUserNameIsExist(username) ? 200 : 500;
    }

}
