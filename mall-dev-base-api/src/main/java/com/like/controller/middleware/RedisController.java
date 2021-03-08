package com.like.controller.middleware;

import com.like.utils.RedisUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 @author like
 @Description redis测试
 @email 980650920@qq.com
 @since 2021-03-03 15:49 */

@RestController("/redis")
@Api(tags = "redis测试接口")
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/set/{key}/{value}")
    public Object set(
            @PathVariable String key,
            @PathVariable String value) {
        redisUtil.set(key, value);
        return "ok";
    }

    @GetMapping("/get/{key}")
    public Object get(@PathVariable String key) {
        return redisUtil.get(key);
    }

    @GetMapping("/del/{key}")
    public Object del(@PathVariable String key) {
        return redisUtil.delete(key);
    }
}
