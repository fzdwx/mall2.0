package com.like.task.autoconfigure;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * job 解析自动装配
 * @author like
 * @email 980650920@qq.com
 * @date 2021/03/13
 * @since 2021-03-13 17:39
 */
@Slf4j
@Configuration
//@ConditionalOnProperty(prefix = "elastic.job.zk", name = {"namespace", "serverLists"})
@EnableConfigurationProperties(JobZookeeperProperties.class)
@Component
public class JobParseAutoConfiguration {
    public JobParseAutoConfiguration() {
        System.out.println("JobParseAutoConfiguration初始");
    }


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

//    @Value("${elastic.job.zk.namespace}")
//    String namespace;
//    @Value("${elastic.job.zk.serverLists}")
//    String serverLists;

//    @Bean
//    public ElasticJobConfigParser elasticJobConfigParser(
//            JobZookeeperProperties properties, ZookeeperRegistryCenter zkCenter) {
////        properties.setNamespace(namespace);
////        properties.setServerLists(serverLists);
//        ElasticJobConfigParser pa = new ElasticJobConfigParser(properties, zkCenter);
//
//        return pa;
//    }
}
