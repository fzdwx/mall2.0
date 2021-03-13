package com.like.task.autoconfigure;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.like.task.parse.ElasticJobConfigParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * job 解析自动装配
 * @author like
 * @email 980650920@qq.com
 * @date 2021/03/13
 * @since 2021-03-13 17:39
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "elastic.job.zk", name = {"namespace", "serverLists"}, matchIfMissing = false)
@EnableConfigurationProperties(JobZookeeperProperties.class)
public class JobParseAutoConfiguration {

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter(JobZookeeperProperties properties) {
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(
                properties.getServerLists(),
                properties.getNamespace());
        zkConfig.setMaxRetries(properties.getMaxRetries());
        zkConfig.setBaseSleepTimeMilliseconds(properties.getBaseSleepTimeMilliseconds());
        zkConfig.setConnectionTimeoutMilliseconds(properties.getConnectionTimeoutMilliseconds());
        zkConfig.setMaxSleepTimeMilliseconds(properties.getMaxSleepTimeMilliseconds());
        zkConfig.setSessionTimeoutMilliseconds(properties.getSessionTimeoutMilliseconds());
        zkConfig.setDigest(properties.getDigest());

        log.info("初始化job注册中心配置成功,zkAddress:{},namespace:{}", properties.getServerLists(), properties.getNamespace());
        return new ZookeeperRegistryCenter(zkConfig);
    }

    @Bean
    public ElasticJobConfigParser elasticJobConfigParser(
            JobZookeeperProperties properties, ZookeeperRegistryCenter zkCenter) {
        ElasticJobConfigParser pa = new ElasticJobConfigParser(properties, zkCenter);

        return pa;
    }
}
