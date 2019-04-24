package com.sven.wms.web.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.sven.wms.web.model.impl.SelectParam;

import java.io.Serializable;

/**
 * @author sven
 * @date 2019/3/8 14:48
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class SelectServiceParamter implements ServiceParamter<SelectParam>, Serializable {
	private static final long serialVersionUID = 1L;
	private SelectParam data;

	@Override
	public SelectParam getQueryParam() {
		return data;
	}

	@Override
	public void setQueryParam(SelectParam queryParam) {
		this.data = queryParam;
	}
}
