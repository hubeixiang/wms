package com.sven.wms.web.model.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;
import java.util.Map;

/**
 * @author sven
 * @date 2019/5/17 10:21
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ExecuteParam implements Serializable {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> sqlParam;

	public Map<String, Object> getSqlParam() {
		return sqlParam;
	}

	public void setSqlParam(Map<String, Object> sqlParam) {
		this.sqlParam = sqlParam;
	}
}
