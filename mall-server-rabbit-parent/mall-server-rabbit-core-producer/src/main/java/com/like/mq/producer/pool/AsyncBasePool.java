package com.like.mq.producer.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description 异步线程池
 * @since 2021-03-11 16:37
 */
@Slf4j
public class AsyncBasePool {

    public static final int THREAD_SIZE = Runtime.getRuntime().availableProcessors();

    public static final int QUEUE_SIZE = 10000;

    /** 异步 */
    public static ExecutorService senderAsync =
            new ThreadPoolExecutor(THREAD_SIZE, THREAD_SIZE, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(QUEUE_SIZE),
                                   r -> {
                                       Thread t = new Thread(r);
                                       t.setName("mq_client_async_sender");
                                       return t;
                                   },
                                   (runnable, executor) ->
                                           log.error("async sender is error rejected,runnable:{},executor:{}", runnable,
                                                     executor));

    public static void submit(Runnable task) {
        senderAsync.submit(task);
    }
}
