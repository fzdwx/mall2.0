package com.like.mq.producer.config.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * $BrokerMessageConfiguration
 * 帮我执行SQL脚本
 * 帮我进行数据库表结构的创建
 * @author Alienware
 */
@Configuration
public class BrokerMessageConfiguration {

    @Autowired
    private DataSource rabbitProducerDataSource;

    @Value("classpath:broker_message.sql.sql")
    private Resource schemaScript;

    /**
     * 初始化数据源初始化
     * @return {@link DataSourceInitializer}
     */
    @Bean
    public DataSourceInitializer initDataSourceInitializer() {
        System.err.println("--------------rabbitProducerDataSource-----------:" + rabbitProducerDataSource);
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(rabbitProducerDataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    /**
     * 数据库填充器 如果对应的表不存在就创建
     * @return {@link DatabasePopulator}
     */
    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);
        return populator;
    }
}
