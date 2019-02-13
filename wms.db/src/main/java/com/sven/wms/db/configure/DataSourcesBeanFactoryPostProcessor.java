package com.sven.wms.db.configure;

import com.alibaba.druid.pool.DruidDataSource;
import com.sven.wms.configuration.configuration.DataSourceProperties;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.bind.PropertiesConfigurationFactory;
import org.springframework.core.env.ConfigurableEnvironment;

public class DataSourcesBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {

	private static final Logger logger = LoggerFactory.getLogger(DataSourcesBeanFactoryPostProcessor.class);

	private DataSourceProperties dataSourceProperties;

	private BeanDefinitionRegistry registry;

	public DataSourcesBeanFactoryPostProcessor(ConfigurableEnvironment environment) {
		PropertiesConfigurationFactory<DataSourceProperties> factory = new PropertiesConfigurationFactory<>(DataSourceProperties.class);
		try {
			factory.setPropertySources(environment.getPropertySources());
			dataSourceProperties = factory.getObject();
		} catch (Exception e) {
			logger.error("Fail to get data source properties.", e);
		}
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//		System.out.println("调用DataSourcesBeanFactoryPostProcessor的postProcessBeanFactory");
		/**
		 BeanDefinition bd = beanFactory.getBeanDefinition("dataSourceProperties");
		 System.out.println("属性值============" + bd.getPropertyValues().toString());
		 MutablePropertyValues pv = bd.getPropertyValues();
		 if (pv.contains("remark")) {
		 pv.addPropertyValue("remark", "把备注信息修改一下");
		 }
		 bd.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		 **/
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//		System.out.println("调用DataSourcesBeanFactoryPostProcessor的postProcessBeanDefinitionRegistry");
		this.registry = registry;

		if (dataSourceProperties != null) {
			createDataSourceBeans();
		}
	}

	private void createDataSourceBeans() {
		boolean primary = true;
		for (String dbName : dataSourceProperties.getDb().keySet()) {
			final DataSourceProperties.DataSourceConfig config = dataSourceProperties.getDb().get(dbName);
			createDataSourceBean(dbName, config, primary);
			if (primary) {
				primary = false;
			}
			// For mybatis
			if (config.getMybatis().getFactor().isValid()) {
				createSqlSessionFactoryBean(dbName, config.getMybatis());

				if (config.getMybatis().getScanner().isValid()) {
					createMapperScannerConfigurerBean(dbName, config.getMybatis());
				}
			}
		}
	}

	private void createDataSourceBean(String dbName, DataSourceProperties.DataSourceConfig config, boolean primary) {
		final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class);
		builder.addPropertyValue("url", config.getUrl());
		builder.addPropertyValue("username", config.getUsername());
		builder.addPropertyValue("password", config.getPassword());

		final BeanDefinition definition = builder.getBeanDefinition();
		definition.setPrimary(primary);
		registry.registerBeanDefinition(DataConnectionDefinition.getDataSourceBeanName(dbName), definition);
	}

	private void createSqlSessionFactoryBean(String dbName, DataSourceProperties.MybatisConfig config) {
		final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionFactoryBean.class);
		builder.addPropertyReference("dataSource", DataConnectionDefinition.getDataSourceBeanName(dbName));
		builder.addPropertyValue("configLocation", config.getFactor().getConfigLocation());
		builder.addPropertyValue("mapperLocations", config.getFactor().getMapperLocations());

		registry.registerBeanDefinition(DataConnectionDefinition.getSqlSessionFactoryBeanName(dbName), builder.getBeanDefinition());
	}

	private void createMapperScannerConfigurerBean(String dbName, DataSourceProperties.MybatisConfig config) {
		final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
		builder.addPropertyValue("sqlSessionFactoryBeanName", DataConnectionDefinition.getSqlSessionFactoryBeanName(dbName));
		builder.addPropertyValue("basePackage", config.getScanner().getBasePackage());

		registry.registerBeanDefinition(DataConnectionDefinition.getMapperScannerConfigurerBeanName(dbName),
				builder.getBeanDefinition());
	}
}
