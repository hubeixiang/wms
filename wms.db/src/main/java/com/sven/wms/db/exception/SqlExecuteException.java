package com.sven.wms.db.exception;

import com.sven.wms.utils.JsonUtils;

/**
 * @author sven
 * @date 2019/11/19 16:41
 */
public class SqlExecuteException extends RuntimeException {
	private Object sqlData;
	private Throwable throwable = null;

	public SqlExecuteException(Object sqlData, Throwable t) {
		super(JsonUtils.toJson(sqlData), t);
		this.sqlData = sqlData;
		this.throwable = t;
	}

	public Object getSqlData() {
		return sqlData;
	}

	public Throwable getThrowable() {
		return throwable;
	}
}
