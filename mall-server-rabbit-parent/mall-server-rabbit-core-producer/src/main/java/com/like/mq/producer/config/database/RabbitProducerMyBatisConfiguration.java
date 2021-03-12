package com.like.mq.producer.config.database;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 在RabbitProducerDataSourceConfiguration装载后才装载本类
 * @author like
 * @date 2021/03/12
 */
@Configuration
@AutoConfigureAfter(value = {RabbitProducerDataSourceConfiguration.class})
public class RabbitProducerMyBatisConfiguration {

	@Resource(name = "rabbitProducerDataSource")
	private DataSource rabbitProducerDataSource;

	/**
	 * sql session  生产工厂
	 * @param rabbitProducerDataSource sql session
	 * @return {@link SqlSessionFactory}
	 */
	@Bean(name = "rabbitProducerSqlSessionFactory")
	public SqlSessionFactory rabbitProducerSqlSessionFactory(DataSource rabbitProducerDataSource) {

		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(rabbitProducerDataSource);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			bean.setMapperLocations(resolver.getResources("classpath:com/like/mq/producer/mapper/mapping/*.xml"));
			SqlSessionFactory sqlSessionFactory = bean.getObject();
			sqlSessionFactory.getConfiguration().setCacheEnabled(Boolean.TRUE);
			return sqlSessionFactory;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 创建sql session
	 * @param sqlSessionFactory sql会话工厂
	 * @return {@link SqlSessionTemplate}
	 */
	@Bean(name = "rabbitProducerSqlSessionTemplate")
	public SqlSessionTemplate rabbitProducerSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
