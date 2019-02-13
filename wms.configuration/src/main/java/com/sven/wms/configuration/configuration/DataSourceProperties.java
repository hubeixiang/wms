package com.sven.wms.configuration.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties
public class DataSourceProperties {

	private Map<String, DataSourceConfig> db = new HashMap<>();

	public Map<String, DataSourceConfig> getDb() {
		return db;
	}

	public static class DataSourceConfig {

		private String host;
		private String url;
		private String username;
		private String password;

		private MybatisConfig mybatis = new MybatisConfig();

		private ConnectionPoolConfig pool = new ConnectionPoolConfig();

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public MybatisConfig getMybatis() {
			return mybatis;
		}

		public ConnectionPoolConfig getPool() {
			return pool;
		}
	}

	public static class MybatisConfig {

		private Factor factor = new Factor();

		private Scanner scanner = new Scanner();

		public Factor getFactor() {
			return factor;
		}

		public Scanner getScanner() {
			return scanner;
		}

		public static class Factor {

			private String configLocation;

			private String mapperLocations;

			public String getConfigLocation() {
				return configLocation;
			}

			public void setConfigLocation(String configLocation) {
				this.configLocation = configLocation;
			}

			public String getMapperLocations() {
				return mapperLocations;
			}

			public void setMapperLocations(String mapperLocations) {
				this.mapperLocations = mapperLocations;
			}

			public boolean isValid() {
				return StringUtils.isNoneBlank(this.configLocation) && StringUtils
						.isNoneBlank(this.mapperLocations);
			}
		}

		public static class Scanner {

			private String basePackage;

			public String getBasePackage() {
				return basePackage;
			}

			public void setBasePackage(String basePackage) {
				this.basePackage = basePackage;
			}

			public boolean isValid() {
				return StringUtils.isNoneBlank(this.basePackage);
			}
		}
	}

	public static class ConnectionPoolConfig {
		private long timeBetweenEvictionRunsMillis = 6000;
		private long minEvictableIdleTimeMillis = 300000;
		private boolean testWhileIdle = true;
		private boolean poolPreoparedStatements = true;
		private int maxOpenPreparedStatements = 20;
		private String filters;

		public long getTimeBetweenEvictionRunsMillis() {
			return timeBetweenEvictionRunsMillis;
		}

		public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
			this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
		}

		public long getMinEvictableIdleTimeMillis() {
			return minEvictableIdleTimeMillis;
		}

		public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
			this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
		}

		public boolean isTestWhileIdle() {
			return testWhileIdle;
		}

		public void setTestWhileIdle(boolean testWhileIdle) {
			this.testWhileIdle = testWhileIdle;
		}

		public boolean isPoolPreoparedStatements() {
			return poolPreoparedStatements;
		}

		public void setPoolPreoparedStatements(boolean poolPreoparedStatements) {
			this.poolPreoparedStatements = poolPreoparedStatements;
		}

		public int getMaxOpenPreparedStatements() {
			return maxOpenPreparedStatements;
		}

		public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
			this.maxOpenPreparedStatements = maxOpenPreparedStatements;
		}

		public String getFilters() {
			return filters;
		}

		public void setFilters(String filters) {
			this.filters = filters;
		}
	}
}
