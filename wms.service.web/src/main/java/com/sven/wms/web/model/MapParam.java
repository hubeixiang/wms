package com.sven.wms.web.model;

import java.util.Map;

/**
 * @author sven
 * @date 2019/3/8 15:19
 */
public class MapParam implements SqlIdParam {
	private Map<String, Object> param;

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
}
