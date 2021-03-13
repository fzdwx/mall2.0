package com.like.task.annotation;

import com.like.task.autoconfigure.JobParseAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 开启 ElasticJob
 * @since 2021-03-13 18:04
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(JobParseAutoConfiguration.class)
public @interface EnableElasticJob {

}
