package com.sven.wms.web;

import com.sven.wms.configuration.configuration.DataSourceProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@EnableTransactionManagement
@EnableConfigurationProperties({ DataSourceProperties.class })
@SpringBootApplication
@ComponentScan(basePackages = "com.sven.wms")
public class Application {
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
		SpringApplication.run(Application.class, args);
	}
}
