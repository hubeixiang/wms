package com.sven.wms.db.param.custom;

import java.io.Serializable;
import java.util.Map;

/**
 * @author sven
 * @date 2019/11/19 16:28
 */
public class CustomDecorateResult implements Serializable {
	private static final long serialVersionUID = 1L;
	//sql statement type
	private CustomMethond customMethond;
	// sql id 执行的dbname
	private String db;
	// sql id
	private String sqlId;
	// 参数
	private Map<String, Object> parameter;
	//sql执行是否正常
	private boolean ok;
	// 执行结果数据
	private Object sqlResult;

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

	public Map<String, Object> getParameter() {
		return parameter;
	}

	public void setParameter(Map<String, Object> parameter) {
		this.parameter = parameter;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public Object getSqlResult() {
		return sqlResult;
	}

	public void setSqlResult(Object sqlResult) {
		this.sqlResult = sqlResult;
	}
}
