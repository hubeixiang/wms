package com.sven.wms.web.filter;

import org.apache.catalina.filters.CorsFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sven
 * @date 2019/2/15 14:43
 * @see CorsConfig
 */
//@Component
@Deprecated
public class SimpleCORSFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(SimpleCORSFilter.class);

	@Value("${service.allow.origin}")
	String allowOrigin;

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		final String origin = ((HttpServletRequest) req).getHeader(CorsFilter.REQUEST_HEADER_ORIGIN);
		logger.info(String.format("Filter:service.allow.origin=[%s] HttpServletRequest.Origin=[%s]", allowOrigin, origin));
		//跨域时的允许携带token
		response.addHeader("Access-Control-Allow-Credentials", "true");
		//允许跨域的服务
		if (StringUtils.isNotEmpty(allowOrigin)) {
			response.setHeader("Access-Control-Allow-Origin", allowOrigin);
		} else {
			response.setHeader("Access-Control-Allow-Origin", "*");
		}
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", ">Accept, Origin, XRequestedWith, Content-Type, LastModified");
		chain.doFilter(req, res);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
