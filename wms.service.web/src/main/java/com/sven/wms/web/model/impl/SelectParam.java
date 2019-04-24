package com.sven.wms.web.model.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.sven.wms.web.model.SqlIdParam;

import java.util.Map;

/**
 * @author sven
 * @date 2019/3/8 16:04
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class SelectParam implements SqlIdParam {
	private Map<String, Object> param;

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
}
