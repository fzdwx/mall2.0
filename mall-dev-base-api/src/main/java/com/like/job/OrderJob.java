package com.like.job;

import com.like.service.OrderStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-22 16:57
 * 定时扫描订单
 */
@Component
@EnableScheduling
@Slf4j
public class OrderJob {

    @Autowired
    OrderStatusService orderStatusService;

    /**
     * 自动关闭长时间未支付订单
     * 暂定每隔一个小时
     * 问题：
     * 1.存在时间差
     * 2.不支持集群
     * 3.会对数据库进行全表搜索
     * <p>
     * 后续优化：使用延时队列
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder() {
        List<String> ids = orderStatusService.closeOrder();
        if (ids.size() > 0)
            log.warn("订单长时间未支付已经将订单关闭：{}", ids);
    }
}
