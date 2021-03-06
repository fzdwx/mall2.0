package com.like.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-06 15:02
 */
@Controller
public class SSOController {

    @GetMapping("/hello")
    @ResponseBody
    public Object hello() {
        return "hello SSO";
    }
}
