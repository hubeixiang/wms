package com.sven.wms.web.model;

import java.io.Serializable;

/**
 * @author sven
 * @date 2019/3/8 14:35
 */
public interface ServiceParamter<E> extends Serializable {
	public E getQueryParam();

	public void setQueryParam(E queryParam);
}
