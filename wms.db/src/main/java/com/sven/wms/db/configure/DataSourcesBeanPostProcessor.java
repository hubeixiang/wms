package com.sven.wms.db.configure;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public class DataSourcesBeanPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof SqlSessionFactoryBean || beanName.endsWith("SqlSessionFactory")) {
			System.out.println(String.format("postProcessBeforeInitialization,beanName=%s,bean=%s", beanName, bean));
			SqlSessionFactoryBean sqlSessionFactoryBean = (SqlSessionFactoryBean) bean;
			createSqlSessionTemplate(sqlSessionFactoryBean, false);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println(String.format("postProcessAfterInitialization,beanName=%s,bean=%s", beanName, bean));
		if (bean instanceof SqlSessionFactoryBean || beanName.endsWith("SqlSessionFactory")) {
			SqlSessionFactoryBean sqlSessionFactoryBean = (SqlSessionFactoryBean) bean;
			createSqlSessionTemplate(sqlSessionFactoryBean, true);
		}
		return bean;
	}

	private void createSqlSessionTemplate(SqlSessionFactoryBean sqlSessionFactoryBean, boolean isAfter) {
		/**
		try {
			Collection<String> mapperNames = new ArrayList<String>();
			mapperNames.addAll(sqlSessionFactoryBean.getObject().getConfiguration().getMappedStatementNames());
			for (String mapperName : mapperNames) {
				MapperFactoryBean mapperFactoryBean = new MapperFactoryBean();
				mapperFactoryBean.setSqlSessionTemplate(new SqlSessionTemplate(sqlSessionFactoryBean.getObject()));
				mapperFactoryBean.afterPropertiesSet();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 **/
	}
}
