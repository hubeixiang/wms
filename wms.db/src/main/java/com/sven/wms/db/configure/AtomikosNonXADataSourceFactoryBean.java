package com.sven.wms.db.configure;

import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import com.sven.wms.configuration.configuration.ConnectionPoolConfig;
import com.sven.wms.configuration.configuration.DataSourceProperties;
import com.sven.wms.configuration.configuration.DataSourceType;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.sql.Connection;
import java.sql.SQLException;

import static org.springframework.util.Assert.notNull;

public class AtomikosNonXADataSourceFactoryBean implements FactoryBean<AtomikosNonXADataSourceBean>, InitializingBean {
	private AtomikosNonXADataSourceBean atomikosNonXADataSourceBean;
	private DataSourceProperties.DataSourceConfig dataSourceConfig;

	public DataSourceProperties.DataSourceConfig getDataSourceConfig() {
		return dataSourceConfig;
	}

	public void setDataSourceConfig(DataSourceProperties.DataSourceConfig dataSourceConfig) {
		this.dataSourceConfig = dataSourceConfig;
	}

	@Override
	public AtomikosNonXADataSourceBean getObject() throws Exception {
		return this.atomikosNonXADataSourceBean;
	}

	@Override
	public Class<?> getObjectType() {
		return this.atomikosNonXADataSourceBean == null ? AtomikosNonXADataSourceBean.class : this.atomikosNonXADataSourceBean.getClass();
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		notNull(dataSourceConfig, "Property 'dataSource' is required");
		if (atomikosNonXADataSourceBean == null) {
			atomikosNonXADataSourceBean = new AtomikosNonXADataSourceBean();
			initConnectionInfo(dataSourceConfig, atomikosNonXADataSourceBean);
			initConnectionPoolConfig(dataSourceConfig, atomikosNonXADataSourceBean);
			atomikosNonXADataSourceBean.init();
		}
	}

	private void initConnectionInfo(DataSourceProperties.DataSourceConfig dataBaseConfig,
			AtomikosNonXADataSourceBean atomikosNonXADataSourceBean) {
		// <property name="uniqueResourceName" value="dlDB" />
		atomikosNonXADataSourceBean.setUniqueResourceName(dataBaseConfig.getDbname());
		// <property name="driverClassName">
		// <value>${jdbc.driver}</value>
		// </property>
		atomikosNonXADataSourceBean.setDriverClassName(dataBaseConfig.getDriver());
		// <property name="url">
		// <value>${jdbc.url}</value>
		// </property>
		atomikosNonXADataSourceBean.setUrl(dataBaseConfig.getUrl());
		// <property name="user">
		// <value>${jdbc.username}</value>
		// </property>

		atomikosNonXADataSourceBean.setUser(dataBaseConfig.getUsername());
		// <property name="password">
		// <value>${jdbc.password}</value>
		// </property>
		atomikosNonXADataSourceBean.setPassword(dataBaseConfig.getPassword());
	}

	private void initConnectionPoolConfig(DataSourceProperties.DataSourceConfig dataBaseConfig,
			AtomikosNonXADataSourceBean atomikosNonXADataSourceBean) throws SQLException {
		ConnectionPoolConfig connectionPoolConfig = new ConnectionPoolConfig();
		// <property name="poolSize" value="10" />
		atomikosNonXADataSourceBean.setPoolSize(connectionPoolConfig.getPoolSize());
		// <property name="minPoolSize" value="10" />
		atomikosNonXADataSourceBean.setMinPoolSize(connectionPoolConfig.getMinPoolSize());
		// <property name="maxPoolSize" value="30" />
		atomikosNonXADataSourceBean.setMaxPoolSize(connectionPoolConfig.getMaxPoolSize());
		// <property name="borrowConnectionTimeout" value="60" />
		atomikosNonXADataSourceBean.setBorrowConnectionTimeout(connectionPoolConfig.getBorrowConnectionTimeout());
		// <property name="reapTimeout" value="20" />
		atomikosNonXADataSourceBean.setReapTimeout(connectionPoolConfig.getReapTimeout());
		// <!-- 最大空闲时间 -->
		// <property name="maxIdleTime" value="60" />
		atomikosNonXADataSourceBean.setMaxIdleTime(connectionPoolConfig.getMaxIdleTime());
		// <property name="maintenanceInterval" value="60" />
		atomikosNonXADataSourceBean.setMaintenanceInterval(connectionPoolConfig.getMaintenanceInterval());
		// <property name="loginTimeout" value="60" />
		atomikosNonXADataSourceBean.setLoginTimeout(connectionPoolConfig.getLoginTimeout());
		// <property name="testQuery">
		// <value>select 1 from dual</value>
		// </property>
		atomikosNonXADataSourceBean.setTestQuery(connectionPoolConfig.getTestQuery());
		// setTransactionIsolation
		// maxLifetime,defaults to 0 (no limit)
		atomikosNonXADataSourceBean.setMaxLifetime(connectionPoolConfig.getMaxLifetime());
		atomikosNonXADataSourceBean.setBorrowConnectionTimeout(60);

		DataSourceType dataSourceType = DataSourceType.ORACLE;
		boolean isNeedSetTransaction = isNeedSetTransaction(dataSourceType);
		if (isNeedSetTransaction) {
			atomikosNonXADataSourceBean.setDefaultIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED);
		}
	}

	private boolean isNeedSetTransaction(DataSourceType dataSourceType) {
		boolean needSetTransaction = false;
		switch (dataSourceType) {

		case INFOMIX:
			needSetTransaction = true;
			break;

		case SYSBASE:
			needSetTransaction = true;
			break;
		default:
			needSetTransaction = false;
			break;
		}

		return needSetTransaction;
	}

}
