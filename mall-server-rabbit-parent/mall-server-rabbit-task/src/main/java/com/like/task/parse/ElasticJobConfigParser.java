package com.like.task.parse;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.executor.handler.JobProperties;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.like.task.annotation.ElasticJobConfig;
import com.like.task.autoconfigure.JobZookeeperProperties;
import com.like.task.enums.ElasticJobType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * ElasticJob 配置解析器 在spring boot 初始化完成后进行配置
 * @author like
 * @email 980650920@qq.com
 * @date 2021/03/13
 * @since 2021-03-13 18:29
 */
@Slf4j
public class ElasticJobConfigParser implements ApplicationListener<ApplicationReadyEvent> {

    private JobZookeeperProperties properties;
    private ZookeeperRegistryCenter zkCenter;

    public ElasticJobConfigParser(
            JobZookeeperProperties properties, ZookeeperRegistryCenter zkCenter) {
        this.properties = properties;
        this.zkCenter = zkCenter;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            // 获取ioc容器
            ConfigurableApplicationContext ioc = event.getApplicationContext();
            // 获取和 ElasticJobConfig 相关的配置信息
            Map<String, Object> beans = ioc.getBeansWithAnnotation(ElasticJobConfig.class);
            // 循环
            while (beans.values().iterator().hasNext()) {
                Object next = beans.values().iterator().next();
                Class<?> clazz = next.getClass();
                if (clazz.getName().indexOf("$") > 0) {
                    String className = clazz.getName();

                    clazz = Class.forName(className.substring(0, className.indexOf("$")));
                }
                // 获取配置项
                ElasticJobConfig conf = clazz.getAnnotation(ElasticJobConfig.class);
                // 获取接口类型用于判断是什么类型的任务
                String jobTypeName = conf.jobTypeName();
                String jobClass = clazz.getName();
                String jobName = this.properties.getNamespace() + "." + conf.name();
                String cron = conf.cron();
                String shardingItemParameters = conf.shardingItemParameters();
                String description = conf.description();
                String jobParameter = conf.jobParameter();
                String jobExceptionHandler = conf.jobExceptionHandler();
                String executorServiceHandler = conf.executorServiceHandler();

                String jobShardingStrategyClass = conf.jobShardingStrategyClass();
                String eventTraceRdbDataSource = conf.eventTraceRdbDataSource();
                String scriptCommandLine = conf.scriptCommandLine();

                boolean failover = conf.failover();
                boolean misfire = conf.misfire();
                boolean overwrite = conf.overwrite();
                boolean disabled = conf.disabled();
                boolean monitorExecution = conf.monitorExecution();
                boolean streamingProcess = conf.streamingProcess();

                int shardingTotalCount = conf.shardingTotalCount();
                int monitorPort = conf.monitorPort();
                int maxTimeDiffSeconds = conf.maxTimeDiffSeconds();
                int reconcileIntervalMinutes = conf.reconcileIntervalMinutes();

                // 配置当当网的job配置
                JobCoreConfiguration coreConfig =
                        JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
                                            .shardingItemParameters(shardingItemParameters)
                                            .description(description)
                                            .failover(failover)
                                            .jobParameter(jobParameter)
                                            .misfire(misfire)
                                            .jobProperties(
                                                    JobProperties.JobPropertiesEnum.JOB_EXCEPTION_HANDLER.getKey(),
                                                    jobExceptionHandler)
                                            .jobProperties(
                                                    JobProperties.JobPropertiesEnum.EXECUTOR_SERVICE_HANDLER.getKey(),
                                                    executorServiceHandler)
                                            .build();

                // 创建的job的类型
                JobTypeConfiguration typeConfig = null;
                if (ElasticJobType.SIMPLE.getType().equals(jobTypeName)) {
                    typeConfig = new SimpleJobConfiguration(coreConfig, jobClass);
                }

                if (ElasticJobType.DATAFLOW.getType().equals(jobTypeName)) {
                    typeConfig = new DataflowJobConfiguration(coreConfig, jobClass, streamingProcess);
                }

                if (ElasticJobType.SCRIPT.getType().equals(jobTypeName)) {
                    typeConfig = new ScriptJobConfiguration(coreConfig, scriptCommandLine);
                }
                // LiteJobConfiguration
                LiteJobConfiguration jobConfig = LiteJobConfiguration
                        .newBuilder(typeConfig)
                        .overwrite(overwrite)
                        .disabled(disabled)
                        .monitorPort(monitorPort)
                        .monitorExecution(monitorExecution)
                        .maxTimeDiffSeconds(maxTimeDiffSeconds)
                        .jobShardingStrategyClass(jobShardingStrategyClass)
                        .reconcileIntervalMinutes(reconcileIntervalMinutes)
                        .build();

                // 	创建一个Spring的beanDefinition
                BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(SpringJobScheduler.class);
                factory.setInitMethodName("init");
                factory.setScope("prototype");

                //	1.添加bean构造参数，相当于添加自己的真实的任务实现类
                if (!ElasticJobType.SCRIPT.getType().equals(jobTypeName)) {
                    factory.addConstructorArgValue(next);
                }
                //	2.添加注册中心
                factory.addConstructorArgValue(this.zkCenter);
                //	3.添加LiteJobConfiguration
                factory.addConstructorArgValue(jobConfig);

                //	4.如果有eventTraceRdbDataSource 则也进行添加
                if (StringUtils.hasText(eventTraceRdbDataSource)) {
                    BeanDefinitionBuilder rdbFactory = BeanDefinitionBuilder
                            .rootBeanDefinition(JobEventRdbConfiguration.class);
                    rdbFactory.addConstructorArgReference(eventTraceRdbDataSource);
                    factory.addConstructorArgValue(rdbFactory.getBeanDefinition());
                }

                //  5.添加监听
                List<?> elasticJobListeners = getTargetElasticJobListeners(conf);
                factory.addConstructorArgValue(elasticJobListeners);

                // 	接下来就是把factory 也就是 SpringJobScheduler注入到Spring容器中
                DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) ioc
                        .getAutowireCapableBeanFactory();
                String registerBeanName = conf.name() + "SpringJobScheduler";
                defaultListableBeanFactory.registerBeanDefinition(registerBeanName, factory.getBeanDefinition());
                SpringJobScheduler scheduler = (SpringJobScheduler) ioc.getBean(registerBeanName);
                scheduler.init();
                log.info("启动elastic-job作业: " + jobName);
            }
            log.info("共启动elastic-job数量:{}", beans.size());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.info("elastic-job启动失败");
        }
    }

    private List<BeanDefinition> getTargetElasticJobListeners(ElasticJobConfig conf) {
        List<BeanDefinition> result = new ManagedList<BeanDefinition>(2);
        String listeners = conf.listener();
        if (StringUtils.hasText(listeners)) {
            BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(listeners);
            factory.setScope("prototype");
            result.add(factory.getBeanDefinition());
        }

        String distributedListeners = conf.distributedListener();
        long startedTimeoutMilliseconds = conf.startedTimeoutMilliseconds();
        long completedTimeoutMilliseconds = conf.completedTimeoutMilliseconds();

        if (StringUtils.hasText(distributedListeners)) {
            BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(distributedListeners);
            factory.setScope("prototype");
            factory.addConstructorArgValue(Long.valueOf(startedTimeoutMilliseconds));
            factory.addConstructorArgValue(Long.valueOf(completedTimeoutMilliseconds));
            result.add(factory.getBeanDefinition());
        }
        return result;
    }
}
