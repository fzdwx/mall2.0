package com.like.task.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author like
 * @email 980650920@qq.com
 * @date 2021/03/13
 * @Description: zk 配置
 * @since 2021-03-13 17:47
 */
@ConfigurationProperties(prefix = "elastic.job.zk")
@Component
@Data
public class JobZookeeperProperties {

    private int baseSleepTimeMilliseconds = 1000;
    private int connectionTimeoutMilliseconds = 15000;
    private String digest = "";
    private int maxRetries = 3;
    private int maxSleepTimeMilliseconds = 3000;
    private String namespace;
    private String serverLists;
    private int sessionTimeoutMilliseconds = 60000;

}
