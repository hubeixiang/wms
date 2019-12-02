package com.sven.wms.db.autoconfigure;

import com.sven.wms.db.autoconfigure.atomikos.AtomikosJdbcPoolConfig;
import com.sven.wms.db.autoconfigure.c3p0.C3p0JdbcPoolConfig;
import com.sven.wms.db.autoconfigure.druid.DruidJdbcPoolConfig;
import com.sven.wms.db.autoconfigure.entity.*;
import com.sven.wms.db.autoconfigure.enums.JdbcPoolTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
public class DataSourceConnectionDefinition {
    private Logger logger = LoggerFactory.getLogger(DataSourceConnectionDefinition.class);

    @Bean
    public DataSourcesBeanRegistryFactoryPostProcessor dataSourcesBeanRegistryFactoryPostProcessor(ConfigurableEnvironment environment) {
        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(environment);// 设置Binder
        Binder binder = new Binder(sources);
        // 属性绑定
        ApplicationDataSource applicationDataSource = null;
        DatasourceLoaderNames datasourceLoaderNames = binderOfProperties(binder, DatasourceLoaderNames.class);
        if (datasourceLoaderNames != null) {
            if (datasourceLoaderNames.getPoolType() == null) {
                //未填写数据库连接池默认使用druid
                datasourceLoaderNames.setPoolType(JdbcPoolTypeEnum.druid);
            }
            SvenDataSourceProperties svenDataSourceProperties = binderOfProperties(binder, SvenDataSourceProperties.class);
            switch (datasourceLoaderNames.getPoolType()) {
                case atomikos:
                    AtomikosJdbcPoolConfig atomikosNonXaJdbcPoolConfig = binderOfProperties(binder, AtomikosJdbcPoolConfig.class);
                    applicationDataSource = new ApplicationDataSource(datasourceLoaderNames, svenDataSourceProperties, atomikosNonXaJdbcPoolConfig);
                    break;
                case c3p0:
                    C3p0JdbcPoolConfig c3p0JdbcPoolConfig = binderOfProperties(binder, C3p0JdbcPoolConfig.class);
                    applicationDataSource = new ApplicationDataSource(datasourceLoaderNames, svenDataSourceProperties, c3p0JdbcPoolConfig);
                    break;
                case druid:
                    DruidJdbcPoolConfig druidJdbcPoolConfig = binderOfProperties(binder, DruidJdbcPoolConfig.class);
                    applicationDataSource = new ApplicationDataSource(datasourceLoaderNames, svenDataSourceProperties, druidJdbcPoolConfig);
                    break;
                default:
                    logger.error(String.format("custom data source pool type=%s not implements", datasourceLoaderNames.getPoolType()));
                    applicationDataSource = null;
                    break;
            }
        }
        return new DataSourcesBeanRegistryFactoryPostProcessor(environment, applicationDataSource);
    }

    private <T> T binderOfProperties(Binder binder, Class<T> classzz) {
        ConfigurationProperties configurationProperties = AnnotationUtils.findAnnotation(classzz, ConfigurationProperties.class);
        String prefix = "";
        if (configurationProperties != null) {
            prefix = configurationProperties.prefix();
        }
        try {
            T object = binder.bind(prefix, Bindable.of(classzz)).get();
            return object;
        } catch (Exception e) {
            logger.warn(String.format(String.format("properties prefix=%s binder %s Exception:%s", prefix, classzz.getName(), e.getMessage())));
            return null;
        }
    }
}
