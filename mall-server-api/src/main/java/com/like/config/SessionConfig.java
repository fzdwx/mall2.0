package com.like.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-06 13:17
 */
@Configuration
@EnableRedisHttpSession  // 开启使用redis作为spring session
public class SessionConfig {

}
