package com.like.mq.producer.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.like.mq.producer.broker.RabbitBroker;
import com.like.mq.producer.constant.BrokerMessageStatus;
import com.like.mq.producer.pojo.BrokerMessage;
import com.like.mq.producer.service.MessageStoreService;
import com.like.task.annotation.ElasticJobConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description 定时在数据库中查找超时的消息
 * @since 2021-03-14 15:41
 */
@Component
@Slf4j
@ElasticJobConfig(name = "retryMessageDataflowJob",
                  cron = "0/59 * * * * ? ",
                  description = "可靠性投递消息补偿任务",
                  overwrite = true,
                  shardingTotalCount = 1, jobTypeName = "dataflowJob")
public class RetryMessageDataflowJob implements DataflowJob<BrokerMessage> {
    public RetryMessageDataflowJob(MessageStoreService messageStoreService) {
        System.out.println("RetryMessageDataflowJob 初始化");
        this.messageStoreService = messageStoreService;
    }

    /** 消息最大重试次数 */
    public static final int MAX_RETRY_COUNT = 3;
    @Autowired
    private MessageStoreService messageStoreService;
    @Autowired
    private RabbitBroker rabbitBroker;

    @Override
    public List<BrokerMessage> fetchData(ShardingContext shardingContext) {
        log.info("获取数据");
        List<BrokerMessage> list = messageStoreService.fetchTimeOutMessage4Retry(BrokerMessageStatus.SENDING);
        if (list != null && list.size() > 0) {
            log.info("检索到消息发送失败，需要重新发送:{}", list);
        }
        return list;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<BrokerMessage> data) {
        log.info("数据处理");
        data.forEach(message -> {
            String messageId = message.getMessageId();
            if (message.getTryCount() >= MAX_RETRY_COUNT) {
                this.messageStoreService.failure(messageId);
                log.warn("--- 消息设置为最终失败，消息id：{} ---", messageId);
            } else {
                //  更新 重试时间
                this.messageStoreService.update4TryCount(messageId);
                // 重新发送
                this.rabbitBroker.reliabilitySend(message.getMessage());
            }
        });
    }
}
