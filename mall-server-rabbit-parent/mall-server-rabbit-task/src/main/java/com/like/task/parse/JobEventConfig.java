
package com.like.task.parse;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;


@Configuration
public class JobEventConfig {


    @Bean
    public JobEventConfiguration jobEventConfiguration() {
        return new JobEventRdbConfiguration(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource
                .setUrl("jdbc:mysql://8.131.57.243:3306/mall2?characterEncoding=UTF-8&useSSL=false&useUnicode=true&serverTimezone=UTC");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        try {
            druidDataSource.init();
            druidDataSource.setAsyncInit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return druidDataSource;
    }
}
