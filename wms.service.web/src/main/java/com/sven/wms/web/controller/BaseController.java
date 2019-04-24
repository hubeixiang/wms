package com.sven.wms.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author sven
 * @date 2019/3/6 15:51
 */
public class BaseController {
	private static final String ContentTypeKey = "Content-Type";
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}

	public void setRestHeaderContentType(String contentType) {
		response.setHeader(ContentTypeKey, contentType);
	}

	public void setRestHttpStatus(HttpStatus httpStatus) {
		response.setStatus(httpStatus.value());
	}
}
