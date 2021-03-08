package com.like.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: AOP 日志记录
 * @since 2021-02-08 17:29
 */
@Component
@Aspect
public class ServiceLogAspect {
    Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * 记录时间日志
     * 1. * 所有返回类型
     * 2. com.like.service.impl 指定包名
     * 3. .. 该包及其子包所有类方法
     * 4. * 所有类
     * 5. .*(..) 该类下的所有方法 匹配任何参数
     *
     * @param point 点
     * @return {@link Object}
     */
    @Around("execution(* com.like.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint point) {

        log.info("======= 开始执行  {}.{} =======",
                point.getTarget().getClass(),
                point.getSignature().getName());
        long start = System.currentTimeMillis();

        // 执行目标service
        Object res = null;
        try {
            res = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        long end = System.currentTimeMillis();

        long time = end - start;

        if (time > 3000) {
            log.error("======= 执行结束，耗时: {} 毫秒 ======= ", time);
        } else if (time > 2000) {
            log.warn("======= 执行结束，耗时: {} 毫秒 ======= ", time);
        } else {
            log.info("======= 执行结束，耗时: {} 毫秒 ======= ", time);
        }

        return res;
    }

}
