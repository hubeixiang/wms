package com.sven.wms.configuration.utils;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
@Configuration
public class UtilsAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(SpringContextUtil.class)
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SpringContextUtil contextUtil() {
		return new SpringContextUtil();
	}
}
