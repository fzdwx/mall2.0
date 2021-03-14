package com.like.task.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * es job 配置
 * @author like
 * @email 980650920@qq.com
 * @date 2021/03/13
 * @since 2021-03-13 18:21
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticJobConfig {

    String name();

    String cron() default "";

    String jobTypeName() default "";

    int shardingTotalCount() default 0;

    int monitorPort() default 0;

    int maxTimeDiffSeconds() default 0;

    int reconcileIntervalMinutes() default 0;

    String shardingItemParameters() default "";

    String jobParameters() default "";

    boolean failover() default false;

    boolean misfire() default true;

    String description() default "";

    boolean overwrite() default false;

    boolean streamingProcess() default false;

    String scriptCommandLine() default "";

    boolean monitorExecution() default false;

    String jobParameter() default "";

    String jobExceptionHandler() default "";

    String executorServiceHandler() default "";

    String jobShardingStrategyClass() default "";

    String eventTraceRdbDataSource() default "";

    boolean disabled() default false;

    String listener() default "";

    String distributedListener() default "";

    long startedTimeoutMilliseconds() default 0;

    long completedTimeoutMilliseconds() default 0;
}
