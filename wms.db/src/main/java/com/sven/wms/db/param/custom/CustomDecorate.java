package com.sven.wms.db.param.custom;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author sven
 * @date 2019/11/19 15:12
 */
public class CustomDecorate implements Serializable {
	private static final long serialVersionUID = 1L;
	//sql statement type
	private CustomMethond customMethond;
	// sql id 执行的dbname
	private String db;
	// sql id
	private String sqlId;
	// sqlId execute parameters
	private List<Map<String, Object>> paramters;

	public CustomMethond getCustomMethond() {
		return customMethond;
	}

	public void setCustomMethond(CustomMethond customMethond) {
		this.customMethond = customMethond;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	public List<Map<String, Object>> getParamters() {
		return paramters;
	}

	public void setParamters(List<Map<String, Object>> paramters) {
		this.paramters = paramters;
	}
}
