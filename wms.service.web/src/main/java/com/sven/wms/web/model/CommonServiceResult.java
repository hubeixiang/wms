package com.sven.wms.web.model;

/**
 * @author sven
 * @date 2019/5/14 16:41
 */
public class CommonServiceResult<T> {
	//处理结果标志,true:处理正确完成,false:处理错误或者异常
	private boolean dealResult = true;
	//处理错误时服务提示信息,以供从页面查看简单的错误描述
	private String errorMsg;
	//当处理正确完成时,返回的数据
	private T dealDesc;

	public boolean isDealResult() {
		return dealResult;
	}

	public void setDealResult(boolean dealResult) {
		this.dealResult = dealResult;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public T getDealDesc() {
		return dealDesc;
	}

	public void setDealDesc(T dealDesc) {
		this.dealDesc = dealDesc;
	}
}
