package com.sven.wms.configuration.configuration;

import org.springframework.stereotype.Component;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
@Component
public class MyTestBean {
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
