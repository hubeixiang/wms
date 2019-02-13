package com.sven.wms.web;

import com.sven.wms.configuration.configuration.DataSourceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import java.util.Properties;

@EnableConfigurationProperties({ DataSourceProperties.class })
@SpringBootApplication
@ComponentScan(basePackages = "com.sven.wms")
public class Application {
	public static void main(String[] args) {
		Properties systemProperties = System.getProperties();
		if (!systemProperties.containsKey("log.home")) {
			systemProperties.put("log.home", "../logs");
		}
		SpringApplication.run(Application.class, args);
	}
}
