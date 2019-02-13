package com.sven.wms.db.configure;

import org.mybatis.spring.mapper.MapperFactoryBean;

public class SqlTemplateMapperFactoryBean {
	private String key;
	private String mappedName;
	private MapperFactoryBean mapperFactoryBean;

	public SqlTemplateMapperFactoryBean(String key, String mappedName, MapperFactoryBean mapperFactoryBean) {
		this.key = key;
		this.mappedName = mappedName;
		this.mapperFactoryBean = mapperFactoryBean;
	}

	public String getKey() {
		return key;
	}

	public String getMappedName() {
		return mappedName;
	}

	public MapperFactoryBean getMapperFactoryBean() {
		return mapperFactoryBean;
	}
}
