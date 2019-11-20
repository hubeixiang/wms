package com.sven.wms.db.configure;

import org.mybatis.spring.SqlSessionTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author sven
 * @date 2019/11/19 15:42
 */
public class SqlSessionTemplateInfo {
	private String dataSourceName;
	private SqlSessionTemplate sqlSessionTemplate;
	private List<String> allMappedStatementNames = new ArrayList<>();

	public SqlSessionTemplateInfo(String dataSourceName, SqlSessionTemplate sqlSessionTemplate) {
		this.dataSourceName = dataSourceName;
		this.sqlSessionTemplate = sqlSessionTemplate;
		List<String> sqlStatement = new ArrayList<>();
		if (sqlSessionTemplate != null) {
			Collection<String> all = sqlSessionTemplate.getConfiguration().getMappedStatementNames();
			if (all != null) {
				for (String sqlId : all) {
					if (sqlId.indexOf('.') != -1) {
						sqlStatement.add(sqlId);
					}
				}
			}
		}
		Collections.sort(sqlStatement);
		allMappedStatementNames.addAll(sqlStatement);
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public boolean hasMappedStatementNames(String statementName) {
		return allMappedStatementNames.contains(statementName);
	}

	public String getAllMappedStatementNames() {
		return String.format("allMappedStatementNames=%s", allMappedStatementNames.toString());
	}

	public List<String> getAllMappedStatementNamesList() {
		return allMappedStatementNames;
	}
}
