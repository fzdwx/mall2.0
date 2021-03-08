package com.like.es.config;

import org.apache.http.HttpHost;
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

    /**
     * es高版本客户端
     * @return {@link RestHighLevelClient}
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host, port, "http")
                )
        );
        return client;
    }
}
