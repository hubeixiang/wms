package com.sven.wms.db.configure;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
public class DataConnectionDefinition {

	@Bean
	public static BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor(ConfigurableEnvironment env) {
		return new DataSourcesBeanFactoryPostProcessor(env);
	}

	public static DataSourcesBeanPostProcessor beanDataSourcesBeanPostProcessor() {
		return new DataSourcesBeanPostProcessor();
	}

	public static String getDataSourceBeanName(String dbName) {
		return String.format("%sDataSource", dbName);
	}

	public static String getSqlSessionFactoryBeanName(String dbName) {
		return String.format("%sSqlSessionFactory", dbName);
	}

	public static String getMapperScannerConfigurerBeanName(String dbName) {
		return String.format("%sMapperScannerConfigurer", dbName);
	}

	public static String getSqlSessionTemplate(String dbName) {
		return String.format("%sSqlSessionTemplate", dbName);
	}

	public static String getTemplateMapperFactoryBeanKey(String dbName, String mapperMethodName) {
		return String.format("%sSqlSessionTemplate%s", dbName, mapperMethodName);
	}
}
