/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sven.wms.db.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;

/**
 * Default implementation of the {@link BeanNameGenerator} interface, delegating to
 * {@link BeanDefinitionReaderUtils#generateBeanName(BeanDefinition, BeanDefinitionRegistry)}.
 *
 * @author Juergen Hoeller
 * @since 2.0.3
 */
public class MybatisBeanNameGenerator extends DefaultBeanNameGenerator {
	private boolean isDefaultDataSource;
	private String dbName;

	public MybatisBeanNameGenerator(boolean isDefaultDataSource, String dbName) {
		this.isDefaultDataSource = isDefaultDataSource;
		this.dbName = dbName;
	}

	@Override
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		String beanClassName = definition.getBeanClassName();
		if (beanClassName == null) {
			throw new BeanDefinitionStoreException("MybatisBeanNameGenerator Unnamed bean definition specifies neither " +
					"'class' nor 'parent' nor 'factory-bean' - can't generate bean name");
		}

		String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
		return String.format("%s%s", dbName, toUpperCaseFirstOne(shortClassName));
		/**
		return Introspector.decapitalize(shortClassName);

		if (isDefaultDataSource || StringUtils.isEmpty(dbName)) {
			return super.generateBeanName(definition, registry);
		} else {
			String name = super.generateBeanName(definition, registry);
			return String.format("%s%s", dbName, toUpperCaseFirstOne(name));
		}
		 **/
	}

	private String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
}
