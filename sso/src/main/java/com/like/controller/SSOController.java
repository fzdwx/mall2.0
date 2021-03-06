package com.like.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-06 15:02
 */
@Controller
public class SSOController {

    @GetMapping("/login")
    public String hello(
            String returnUrl, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("returnUrl", returnUrl);

        // TODO: 2021/3/6 后续完善校验是否登录
        return "login";
    }
}
