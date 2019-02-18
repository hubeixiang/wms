package com.sven.wms.configuration.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public final class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext ctx = null;

	public SpringContextUtil() {
	}

	public static Object getBean(String beanName) throws BeansException {
		return ctx == null ? null : ctx.getBean(beanName);
	}

	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return ctx == null ? null : ctx.getBean(requiredType);
	}

	public static boolean containsBean(String beanName) {
		return ctx != null && ctx.containsBean(beanName);
	}

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		SpringContextUtil.ctx = ctx;
	}
}
