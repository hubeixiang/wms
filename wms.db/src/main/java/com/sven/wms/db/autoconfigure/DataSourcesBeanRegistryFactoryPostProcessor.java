package com.sven.wms.db.autoconfigure;

import com.sven.wms.db.autoconfigure.atomikos.AtomikosJdbcPoolConfig;
import com.sven.wms.db.autoconfigure.atomikos.AtomikosXADataSourceFactoryBean;
import com.sven.wms.db.autoconfigure.c3p0.C3p0JdbcPoolConfig;
import com.sven.wms.db.autoconfigure.druid.DruidJdbcPoolConfig;
import com.sven.wms.db.autoconfigure.entity.*;
import com.sven.wms.db.autoconfigure.enums.JdbcPoolTypeEnum;
import com.sven.wms.db.configure.DataConnectionDefinition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class DataSourcesBeanRegistryFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    private Logger logger = LoggerFactory.getLogger(DataSourcesBeanRegistryFactoryPostProcessor.class);

    private ConfigurableEnvironment environment;
    private ApplicationDataSource applicationDataSource;
    private BeanDefinitionRegistry registry;

    public DataSourcesBeanRegistryFactoryPostProcessor(ConfigurableEnvironment environment, ApplicationDataSource applicationDataSource) {
        this.environment = environment;
        this.applicationDataSource = applicationDataSource;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;

        if (applicationDataSource != null && applicationDataSource.getDatasourceLoaderNames() != null && StringUtils.isNotEmpty(applicationDataSource.getDatasourceLoaderNames().getNames()) && applicationDataSource.getDatasourceLoaderNames().getPoolType() != null) {
            createDataSourceBeans();
        } else {
            logger.debug("No custom related data source configured");
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    private void createDataSourceBeans() {
        DatasourceLoaderNames datasourceLoaderNames = applicationDataSource.getDatasourceLoaderNames();
        JdbcPoolTypeEnum poolType = datasourceLoaderNames.getPoolType();
        String[] names = datasourceLoaderNames.getNames().split(",");
        List<String> dbNames = new ArrayList<>();
        for (String name : names) {
            if (!dbNames.contains(name)) {
                dbNames.add(name);
            }
        }

        SvenDataSourceProperties svenDataSourceProperties = applicationDataSource.getSvenDataSourceProperties();
        if (svenDataSourceProperties == null) {
            logger.error(String.format("data source properties %s prefix application not exist!", Constants.DATA_SOURCE_PREFIX));
            return;
        }
        JdbcPoolConfig jdbcPoolConfig = applicationDataSource.getJdbcPoolConfig();
        String currentDbName = null;
        try {
            boolean primary = true;
            for (String dbName : dbNames) {
                currentDbName = dbName;
                if (svenDataSourceProperties.getDb().containsKey(dbName)) {
                    boolean ok = false;
                    DataSourceConfig dataSourceConfig = svenDataSourceProperties.getDb().get(dbName);
                    dataSourceConfig.setName(dbName);
                    switch (poolType) {
                        case atomikos:
                            createAtomikosDataSourceBeans(primary, dbName, dataSourceConfig, jdbcPoolConfig == null ? null : ((AtomikosJdbcPoolConfig) jdbcPoolConfig));
                            primary = false;
                            ok = true;
                            break;
                        case c3p0:
                            createC3p0DataSourceBeans(primary, dbName, dataSourceConfig, jdbcPoolConfig == null ? null : ((C3p0JdbcPoolConfig) jdbcPoolConfig));
                            primary = false;
                            ok = true;
                            break;
                        case druid:
                            createDruidDataSourceBeans(primary, dbName, dataSourceConfig, jdbcPoolConfig == null ? null : ((DruidJdbcPoolConfig) jdbcPoolConfig));
                            primary = false;
                            ok = true;
                        default:
                            logger.error(String.format("custom data source pool type=%s not implements", poolType));
                            break;
                    }
                    if (ok) {
                        //创建spring 的 JdbcTemplate
                        createSpringJdbcTemplate(dbName);
                    }
                } else {
                    logger.error(String.format("%s.%s data source application not exists!", Constants.DATA_SOURCE_PREFIX, dbName));
                }
            }
        } catch (Exception e) {
            logger.error(String.format("%s.createDataSourceBeans(%s) Exception :%s", this.getClass().getName(), currentDbName, e.getMessage()), e);
        }
    }

    private void createAtomikosDataSourceBeans(boolean primary, String dbName, DataSourceConfig dataSourceConfig, AtomikosJdbcPoolConfig jdbcPoolConfig) {
        final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(AtomikosXADataSourceFactoryBean.class);
        builder.addPropertyValue("dataSourceConfig", dataSourceConfig);
        builder.addPropertyValue("atomikosJdbcPoolConfig", jdbcPoolConfig);

        final BeanDefinition definition = builder.getBeanDefinition();
        definition.setPrimary(primary);
        registry.registerBeanDefinition(DataConnectionDefinition.getDataSourceBeanName(dbName), definition);
    }

    private void createC3p0DataSourceBeans(boolean primary, String dbName, DataSourceConfig dataSourceConfig, C3p0JdbcPoolConfig jdbcPoolConfig) {

    }

    private void createDruidDataSourceBeans(boolean primary, String dbName, DataSourceConfig dataSourceConfig, DruidJdbcPoolConfig jdbcPoolConfig) {

    }

    private void createSpringJdbcTemplate(String dbName) {
        final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(JdbcTemplate.class);
        builder.addConstructorArgReference(DataConnectionDefinition.getDataSourceBeanName(dbName));

        registry.registerBeanDefinition(DataConnectionDefinition.getSpringJdbcTemplate(dbName), builder.getBeanDefinition());
    }
}
