package com.sven.wms.web;

import com.sven.wms.configuration.configuration.DataSourceProperties;
import com.sven.wms.db.autoconfigure.atomikos.AtomikosJdbcPoolConfig;
import com.sven.wms.db.autoconfigure.entity.DatasourceLoaderNames;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Properties;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
@EnableSwagger2
@EnableTransactionManagement
@EnableConfigurationProperties({ DataSourceProperties.class , DatasourceLoaderNames.class })
@SpringBootApplication
@ComponentScan(basePackages = "com.sven.wms")
public class SpringWmsApplication {
	@Bean
	public Object testBean(@Qualifier("jtaTransactionManager") PlatformTransactionManager platformTransactionManager) {
		System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
		return new Object();
	}

	public static void main(String[] args) {
		Properties systemProperties = System.getProperties();
		if (!systemProperties.containsKey("log.home")) {
			systemProperties.put("log.home", "../logs");
		}
		SpringApplication.run(SpringWmsApplication.class, args);
	}
}
