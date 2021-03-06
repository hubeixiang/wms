package com.sven.wms.db.configure;

import com.sven.wms.configuration.configuration.DataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
//@Configuration
public class DataConnectionDefinition {

    @Autowired
    DataSourceProperties dataSourceProperties;

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

    public static String getSpringJdbcTemplate(String dbName) {
        return String.format("%sJdbcTemplate", dbName);
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
