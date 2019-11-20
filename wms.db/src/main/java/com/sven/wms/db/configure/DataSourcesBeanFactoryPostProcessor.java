package com.sven.wms.db.configure;

import com.sven.wms.configuration.configuration.DataSourceProperties;
import com.sven.wms.db.dao.MybatisBeanNameGenerator;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public class DataSourcesBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {

	private static final Logger logger = LoggerFactory.getLogger(DataSourcesBeanFactoryPostProcessor.class);

	private DataSourceProperties dataSourceProperties;

	private BeanDefinitionRegistry registry;

	//spring 1.x.0
	//	public DataSourcesBeanFactoryPostProcessor(ConfigurableEnvironment environment) {
	//		PropertiesConfigurationFactory<DataSourceProperties> factory = new PropertiesConfigurationFactory<>(DataSourceProperties.class);
	//		try {
	//			factory.setPropertySources(environment.getPropertySources());
	//			dataSourceProperties = factory.getObject();
	//		} catch (Exception e) {
	//			logger.error("Fail to get data source properties.", e);
	//		}
	//	}

	//spring 2.x.0
	public DataSourcesBeanFactoryPostProcessor(ConfigurableEnvironment environment) {
		Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(environment);// 设置Binder
		Binder binder = new Binder(sources);
		// 属性绑定
		try {
			DataSourceProperties dataSourceProperties = binder.bind("", Bindable.of(DataSourceProperties.class)).get();
			this.dataSourceProperties = dataSourceProperties;
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
				createSqlSessionTemplate(dbName);
			}
		}
	}

	/**
	 * private void createDataSourceBean(String dbName, DataSourceProperties.DataSourceConfig config, boolean primary) {
	 * final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class);
	 * builder.addPropertyValue("url", config.getUrl());
	 * builder.addPropertyValue("username", config.getUsername());
	 * builder.addPropertyValue("password", config.getPassword());
	 * <p>
	 * final BeanDefinition definition = builder.getBeanDefinition();
	 * definition.setPrimary(primary);
	 * registry.registerBeanDefinition(DataConnectionDefinition.getDataSourceBeanName(dbName), definition);
	 * }
	 **/

	private void createDataSourceBean(String dbName, DataSourceProperties.DataSourceConfig config, boolean primary) {
		final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(AtomikosNonXADataSourceFactoryBean.class);
		builder.addPropertyValue("dataSourceConfig", config);

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
		builder.addPropertyValue("nameGenerator", new MybatisBeanNameGenerator(false, dbName));

		registry.registerBeanDefinition(DataConnectionDefinition.getMapperScannerConfigurerBeanName(dbName), builder.getBeanDefinition());
	}

	private void createSqlSessionTemplate(String dbName) {
		final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionTemplate.class);
		builder.addConstructorArgReference(DataConnectionDefinition.getSqlSessionFactoryBeanName(dbName));

		registry.registerBeanDefinition(DataConnectionDefinition.getSqlSessionTemplate(dbName), builder.getBeanDefinition());
	}
}
