package com.sven.wms.db.configure;

import com.sven.wms.configuration.configuration.DataSourceProperties;
import com.sven.wms.configuration.utils.SpringContextUtil;
import com.sven.wms.core.entity.vo.LowerCaseResultMap;
import com.sven.wms.db.dao.mapper.GenericMapper;
import com.sven.wms.utils.ClassUtil;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DBContextHolder {
	private static Logger logger = LoggerFactory.getLogger(DBContextHolder.class);
	private static DBContextHolder dbContextHolder;
	private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private final List<String> dbs = new ArrayList<>();
	private final Map<String, SqlTemplateMapperFactoryBean> mapperMethodFactoryBeanMap = new HashMap<>();

	private DBContextHolder() {
	}

	private void init() {
		DataSourceProperties dataSourceProperties = SpringContextUtil.getBean(DataSourceProperties.class);
		for (Map.Entry<String, DataSourceProperties.DataSourceConfig> entry : dataSourceProperties.getDb().entrySet()) {
			dbs.add(entry.getKey());
		}
		ClassLoader classLoader = DataSourceProperties.class.getClassLoader();
		for (String dbName : dbs) {
			String sqlSessionFactoryBeanName = DataConnectionDefinition.getSqlSessionFactoryBeanName(dbName);
			//SqlSessionFactoryBean sqlSessionFactoryBean = (SqlSessionFactoryBean) SpringContextUtil.getBean(sqlSessionFactoryBeanName);
			SqlSessionFactoryBean sqlSessionFactoryBean = (SqlSessionFactoryBean) SpringContextUtil.getBean(SqlSessionFactoryBean.class);
			try {
				SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
				Collection<String> mapperNames = new ArrayList<String>();
				mapperNames.addAll(sqlSessionFactoryBean.getObject().getConfiguration().getMappedStatementNames());
				for (String mapperName : mapperNames) {
					MapperFactoryBean mapperFactoryBean = new MapperFactoryBean();
					int index = mapperName.lastIndexOf(".");
					if (index == -1) {
						continue;
					}
					String mapperInterfaceName = mapperName.substring(0, index);
					try {
						Class<?> mapperInterface = classLoader.loadClass(mapperInterfaceName);
						mapperFactoryBean.setMapperInterface(mapperInterface);
					} catch (Exception e) {
						mapperFactoryBean.setMapperInterface(GenericMapper.class);
					}
					mapperFactoryBean.setSqlSessionTemplate(sqlSessionTemplate);
					mapperFactoryBean.afterPropertiesSet();
					SqlTemplateMapperFactoryBean sqlTemplateMapperFactoryBean = new SqlTemplateMapperFactoryBean(
							DataConnectionDefinition.getTemplateMapperFactoryBeanKey(dbName, mapperName), mapperName, mapperFactoryBean);
					addSqlTemplateMapperFactoryBean(sqlTemplateMapperFactoryBean);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static DBContextHolder getInstance() {

		if (dbContextHolder != null) {
			return dbContextHolder;
		}

		return createInstance();
	}

	private static synchronized DBContextHolder createInstance() {
		if (dbContextHolder != null) {
			return dbContextHolder;
		}

		dbContextHolder = new DBContextHolder();
		dbContextHolder.init();
		return dbContextHolder;
	}

	public void addSqlTemplateMapperFactoryBean(SqlTemplateMapperFactoryBean sqlTemplateMapperFactoryBean) {
		try {
			readWriteLock.writeLock().lock();
			mapperMethodFactoryBeanMap.put(sqlTemplateMapperFactoryBean.getKey(), sqlTemplateMapperFactoryBean);
		} catch (Exception e) {
			logger.error("getMapperByName Exception", e);
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	public SqlTemplateMapperFactoryBean getMapperByName(String dataSourceName, String mapperMethodName) {
		try {
			readWriteLock.readLock().lock();
			String key = DataConnectionDefinition.getTemplateMapperFactoryBeanKey(dataSourceName, mapperMethodName);
			SqlTemplateMapperFactoryBean mapperFactoryBean = mapperMethodFactoryBeanMap.get(key);
			if (mapperFactoryBean == null) {
				return null;
			}
			return mapperFactoryBean;
		} catch (Exception e) {
			logger.error("getMapperByName Exception", e);
		} finally {
			readWriteLock.readLock().unlock();
		}
		return null;
	}

	public List<LowerCaseResultMap> queryList(SqlTemplateMapperFactoryBean sqlTemplateMapperFactoryBean, Object param) {
		return sqlTemplateMapperFactoryBean.getMapperFactoryBean().getSqlSession()
				.selectList(sqlTemplateMapperFactoryBean.getMappedName(), param);
	}
}
