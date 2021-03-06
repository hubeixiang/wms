package com.sven.wms.db.configure;

import com.sven.wms.configuration.utils.SpringContextUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public class DBContextHelper {
	private static Logger logger = LoggerFactory.getLogger(DBContextHelper.class);
	private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private static DBContextHelper dbContextHelper;
	private ConcurrentMap<String, SqlSessionTemplateInfo> dbSqlSessionTemplateInfo = new ConcurrentHashMap<>();

	private DBContextHelper() {
	}

	private void init() {
	}

	public static DBContextHelper getInstance() {

		if (dbContextHelper != null) {
			return dbContextHelper;
		}

		return createInstance();
	}

	private static synchronized DBContextHelper createInstance() {
		if (dbContextHelper != null) {
			return dbContextHelper;
		}

		dbContextHelper = new DBContextHelper();
		dbContextHelper.init();
		return dbContextHelper;
	}

	public <T> T getMapperInterface(String dataSourceName, Class<T> daoClass) {
		String key = null;
		String tempClassName = null;
		try {
			tempClassName = daoClass.getName();
			String shortClassName = ClassUtils.getShortName(tempClassName);
			key = String.format("%s%s", dataSourceName, toUpperCaseFirstOne(shortClassName));
			return (T) SpringContextUtil.getBean(key);
		} catch (Exception e) {
			logger.error(String.format("DBContextHelper.getMapperInterface(%s,%s) Exception", dataSourceName, tempClassName), e);
		}
		return null;
	}

	public SqlSessionTemplate getSqlSessionTemplate(String dataSourceName) {
		String key = null;
		try {
			key = DataConnectionDefinition.getSqlSessionTemplate(dataSourceName);
			return (SqlSessionTemplate) SpringContextUtil.getBean(key);
		} catch (Exception e) {
			logger.error(String.format("DBContextHelper.getSqlSessionTemplate(%s) Exception", key), e);
		}
		return null;
	}

	public SqlSessionTemplateInfo getSqlSessionTemplateInfo(String dataSourceName) {
		try {
			readWriteLock.readLock().lock();
			SqlSessionTemplateInfo sqlSessionTemplateInfo = dbSqlSessionTemplateInfo.get(dataSourceName);
			if (sqlSessionTemplateInfo != null) {
				return sqlSessionTemplateInfo;
			}
		} finally {
			readWriteLock.readLock().unlock();
		}
		return initSqlSessionTemplateInfo(dataSourceName);
	}

	private SqlSessionTemplateInfo initSqlSessionTemplateInfo(String dataSourceName) {
		try {
			readWriteLock.writeLock().lock();
			SqlSessionTemplate sqlSessionTemplate = getSqlSessionTemplate(dataSourceName);
			if (sqlSessionTemplate == null) {
				throw new RuntimeException(String.format("db=%s is not exists!", dataSourceName));
			}
			SqlSessionTemplateInfo sqlSessionTemplateInfo = new SqlSessionTemplateInfo(dataSourceName, sqlSessionTemplate);
			dbSqlSessionTemplateInfo.put(sqlSessionTemplateInfo.getDataSourceName(), sqlSessionTemplateInfo);
			return sqlSessionTemplateInfo;
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	private String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
}
