package com.like.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-08 16:12
 * @deprecated ElasticSearch 配置类
 */
@Configuration
@ConfigurationProperties(prefix = "es")
public class ESConfig {

    @Value("${es.host}")
    private String host;
    @Value("${es.port}")
    private Integer port;

    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
       /* builder.addHeader("Authorization","Bearer"+TOKEN)
                .setHttpAsyncResponseConsumerFactory(new HttpAsyncResponseConsumerFactory
                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));*/
        COMMON_OPTIONS = builder.build();
    }

    /**
     * es高版本客户端
     * @return {@link RestHighLevelClient}
     */
    @Bean
    public RestHighLevelClient client() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host, port, "http")
                )
        );
        return client;
    }
}
