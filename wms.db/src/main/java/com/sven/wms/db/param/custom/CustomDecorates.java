package com.sven.wms.db.param.custom;

import java.io.Serializable;
import java.util.List;

/**
 * @author sven
 * @date 2019/11/19 15:24
 */
public class CustomDecorates implements Serializable {
	private static final long serialVersionUID = 1L;
	//是否需要在一个事务中执行
	private boolean isTransaction;
	//需要执行的内容
	private List<CustomDecorate> context;

	public boolean isTransaction() {
		return isTransaction;
	}

	public void setTransaction(boolean transaction) {
		isTransaction = transaction;
	}

	public List<CustomDecorate> getContext() {
		return context;
	}

	public void setContext(List<CustomDecorate> context) {
		this.context = context;
	}
}
