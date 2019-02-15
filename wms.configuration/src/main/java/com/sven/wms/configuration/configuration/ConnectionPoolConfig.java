package com.sven.wms.configuration.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ConnectionPoolConfig {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionPoolConfig.class);

	// <property name="poolSize" value="10" />
	private int poolSize = 10;
	// <property name="minPoolSize" value="10" />
	private int minPoolSize = 10;
	// <property name="maxPoolSize" value="30" />
	private int maxPoolSize = 30;
	// <property name="borrowConnectionTimeout" value="60" />
	private int borrowConnectionTimeout = 60;
	// <property name="reapTimeout" value="20" />
	// 最大获取数据时间，如果不设置这个值，Atomikos使用默认的5分钟，那么在处理大批量数据读取的时候，一旦超过5分钟，就会抛出类似
	// Resultset is close 的错误.
	private int reapTimeout = 0;
	// <property name="maxIdleTime" value="60" />
	private int maxIdleTime = 120;
	// <property name="maintenanceInterval" value="60" />
	// <maintenance-interval>60</maintenance-interval> <!--连接回收时间-->
	private int maintenanceInterval = 120;
	// <property name="loginTimeout" value="60" />
	// <login-timeout>0</login-timeout>
	// <!--java数据库连接池，最大可等待获取datasouce的时间-->
	private int loginTimeout = 60;
	// <property name="testQuery">
	private String testQuery = "select 1 as con from dual";
	// Sets the maximum amount of seconds that a connection is kept in the pool
	// before it is destroyed automatically.
	// Optional, defaults to 0 (no limit).
	private int maxLifetime = 0;

	public void init(Properties properties) {
		// <property name="minPoolSize" value="10" />
		this.poolSize = getIntegerProperty("poolSize", properties,this.poolSize);
		// <property name="maxPoolSize" value="30" />
		this.maxPoolSize = getIntegerProperty("maxPoolSize", properties,this.maxPoolSize);
		// <property name="borrowConnectionTimeout" value="60" />
		this.borrowConnectionTimeout = getIntegerProperty("borrowConnectionTimeout", properties,this.borrowConnectionTimeout);
		// <property name="reapTimeout" value="20" />
		// 最大获取数据时间，如果不设置这个值，Atomikos使用默认的5分钟，那么在处理大批量数据读取的时候，一旦超过5分钟，就会抛出类似
		// Resultset is close 的错误.
		this.reapTimeout = getIntegerProperty("reapTimeout", properties,this.reapTimeout);
		// <property name="maxIdleTime" value="60" />
		this.maxIdleTime = getIntegerProperty("maxIdleTime", properties,this.maxIdleTime);
		// <property name="maintenanceInterval" value="60" />
		// <maintenance-interval>60</maintenance-interval> <!--连接回收时间-->
		this.maintenanceInterval = getIntegerProperty("maintenanceInterval", properties,this.maintenanceInterval);

		// <property name="loginTimeout" value="60" />
		// <login-timeout>0</login-timeout>
		// <!--java数据库连接池，最大可等待获取datasouce的时间-->
		this.loginTimeout = getIntegerProperty("loginTimeout", properties,this.loginTimeout);
		// <property name="testQuery">
		this.testQuery = getStringProperty("testQuery", properties,this.testQuery);
		// Sets the maximum amount of seconds that a connection is kept in the
		// pool before it is destroyed automatically.
		// Optional, defaults to 0 (no limit).
		this.maxLifetime = getIntegerProperty("maxLifetime", properties,this.maxLifetime);

	}

	public Properties toProperties(Properties properties) {
		if (properties == null) {
			properties = new Properties();
		}
		// <property name="minPoolSize" value="10" />
		properties.setProperty("poolSize", poolSize + "");
		// <property name="maxPoolSize" value="30" />
		properties.setProperty("maxPoolSize", maxPoolSize + "");
		// <property name="borrowConnectionTimeout" value="60" />
		properties.setProperty("borrowConnectionTimeout", borrowConnectionTimeout + "");
		// <property name="reapTimeout" value="20" />
		// 最大获取数据时间，如果不设置这个值，Atomikos使用默认的5分钟，那么在处理大批量数据读取的时候，一旦超过5分钟，就会抛出类似
		// Resultset is close 的错误.
		properties.setProperty("reapTimeout", reapTimeout + "");
		// <property name="maxIdleTime" value="60" />
		properties.setProperty("maxIdleTime", maxIdleTime + "");
		// <property name="maintenanceInterval" value="60" />
		// <maintenance-interval>60</maintenance-interval> <!--连接回收时间-->
		properties.setProperty("maintenanceInterval", maintenanceInterval + "");
		// <property name="loginTimeout" value="60" />
		// <login-timeout>0</login-timeout>
		// <!--java数据库连接池，最大可等待获取datasouce的时间-->
		properties.setProperty("loginTimeout", loginTimeout + "");
		// <property name="testQuery">
		properties.setProperty("testQuery", testQuery);
		// Sets the maximum amount of seconds that a connection is kept in the
		// pool before it is destroyed automatically.
		// Optional, defaults to 0 (no limit).
		properties.setProperty("maxLifetime", maxLifetime + "");

		return properties;
	}

	private String getStringProperty(String key, Properties properties,String defaultValue) {
		String valueStr = null;
		try {
			valueStr = properties.getProperty(key);
			if (valueStr == null) {
				return defaultValue;
			}
			valueStr = valueStr.trim();
			return valueStr;
		} catch (Exception e) {
			logger.error("getIntegerProperty,key:" + key + ",valueStr:" + valueStr);
		}
		return defaultValue;
	}

	private Integer getIntegerProperty(String key, Properties properties,int defaultValue) {
		String valueStr = null;
		try {
			valueStr = properties.getProperty(key);
			if (valueStr == null) {
				return defaultValue;
			}
			Integer value = Integer.valueOf(valueStr);
			return value;
		} catch (Exception e) {
			logger.error("getIntegerProperty,key:" + key + ",valueStr:" + valueStr);
		}
		return defaultValue;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getBorrowConnectionTimeout() {
		return borrowConnectionTimeout;
	}

	public void setBorrowConnectionTimeout(int borrowConnectionTimeout) {
		this.borrowConnectionTimeout = borrowConnectionTimeout;
	}

	public int getReapTimeout() {
		return reapTimeout;
	}

	public void setReapTimeout(int reapTimeout) {
		this.reapTimeout = reapTimeout;
	}

	public int getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(int maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public int getMaintenanceInterval() {
		return maintenanceInterval;
	}

	public void setMaintenanceInterval(int maintenanceInterval) {
		this.maintenanceInterval = maintenanceInterval;
	}

	public int getLoginTimeout() {
		return loginTimeout;
	}

	public void setLoginTimeout(int loginTimeout) {
		this.loginTimeout = loginTimeout;
	}

	public String getTestQuery() {
		return testQuery;
	}

	public void setTestQuery(String testQuery) {
		this.testQuery = testQuery;
	}

	public int getMaxLifetime() {
		return maxLifetime;
	}

	public void setMaxLifetime(int maxLifetime) {
		this.maxLifetime = maxLifetime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConnectionPoolConfig [poolSize=");
		builder.append(poolSize);
		builder.append(", minPoolSize=");
		builder.append(minPoolSize);
		builder.append(", maxPoolSize=");
		builder.append(maxPoolSize);
		builder.append(", borrowConnectionTimeout=");
		builder.append(borrowConnectionTimeout);
		builder.append(", reapTimeout=");
		builder.append(reapTimeout);
		builder.append(", maxIdleTime=");
		builder.append(maxIdleTime);
		builder.append(", maintenanceInterval=");
		builder.append(maintenanceInterval);
		builder.append(", loginTimeout=");
		builder.append(loginTimeout);
		builder.append(", testQuery=");
		builder.append(testQuery);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + borrowConnectionTimeout;
		result = prime * result + loginTimeout;
		result = prime * result + maintenanceInterval;
		result = prime * result + maxIdleTime;
		result = prime * result + maxPoolSize;
		result = prime * result + minPoolSize;
		result = prime * result + poolSize;
		result = prime * result + reapTimeout;
		result = prime * result + ((testQuery == null) ? 0 : testQuery.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnectionPoolConfig other = (ConnectionPoolConfig) obj;
		if (borrowConnectionTimeout != other.borrowConnectionTimeout)
			return false;
		if (loginTimeout != other.loginTimeout)
			return false;
		if (maintenanceInterval != other.maintenanceInterval)
			return false;
		if (maxIdleTime != other.maxIdleTime)
			return false;
		if (maxPoolSize != other.maxPoolSize)
			return false;
		if (minPoolSize != other.minPoolSize)
			return false;
		if (poolSize != other.poolSize)
			return false;
		if (reapTimeout != other.reapTimeout)
			return false;
		if (testQuery == null) {
			if (other.testQuery != null)
				return false;
		} else if (!testQuery.equals(other.testQuery))
			return false;
		return true;
	}

}
