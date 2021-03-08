package com.like.interceptor;

import com.like.controller.base.BaseController;
import com.like.utils.HttpJSONResult;
import com.like.utils.JsonUtils;
import com.like.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-06 13:39
 */
@Slf4j
public class UserTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 前处理
     * 拦截请求，访问controller
     */
    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        String userToken = request.getHeader(BaseController.HEADER_USER_TOKEN_KEY);
        String userId = request.getHeader(BaseController.HEADER_USER_ID_KEY);
        boolean flag = false;
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)) {
            String uniqueToken = redisUtil.get(BaseController.REDIS_USER_TOKEN_PREFIX + userId);
            if (StringUtils.isBlank(uniqueToken)) {
                returnErrorResponse(response, HttpJSONResult.errorMsg("请登录..."));
            } else {
                if (!uniqueToken.equals(userToken)) {
                    returnErrorResponse(response, HttpJSONResult.errorMsg("账户在异地登录..."));
                } else {
                    flag = true;
                }
            }
        } else {
            returnErrorResponse(response, HttpJSONResult.errorMsg("请登录..."));
        }
        return flag;
    }

    /**
     * 处理后
     * 请求访问controller之后，渲染视图之前
     */
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 完成后
     * 请求访问controller之后，渲染视图之后
     */
    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler, Exception ex) throws Exception {

    }

    /**
     * 返回错误响应
     * @param response 响应
     * @param result 结果
     * @throws IOException ioexception
     */
    private void returnErrorResponse(HttpServletResponse response, HttpJSONResult result) throws IOException {
        try (ServletOutputStream os = response.getOutputStream()) {
            response.setContentType("text/json; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            os.write(JsonUtils.objectToJson(result).getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
