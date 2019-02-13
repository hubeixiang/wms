package com.sven.wms.db.service;

import com.sven.wms.core.entity.ResultMap;
import com.sven.wms.core.entity.vo.LowerCaseResultMap;
import com.sven.wms.db.dao.mapper.GenericMapper;
import com.sven.wms.db.param.DecorateParam;
import com.sven.wms.db.param.impl.ColumnMapping;
import com.sven.wms.db.param.impl.DecorateConstans;
import com.sven.wms.db.param.impl.DecorateParamDelete;
import com.sven.wms.db.param.impl.DecorateParamInsert;
import com.sven.wms.db.param.impl.DecorateParamSelectInsert;
import com.sven.wms.db.param.impl.DecorateParamSql;
import com.sven.wms.db.param.impl.DecorateParamUpdate;
import com.sven.wms.db.param.impl.VarableBind;
import com.sven.wms.utils.DateUtils2;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sven on 2017/3/17.
 */
@Service("genericService")
@Transactional
public class GenericService {
	private Logger logger = LoggerFactory.getLogger(GenericService.class);

	public List<Map<String, String>> convertResultMap(List<ResultMap> resultMaps) {
		List<Map<String, String>> result = new ArrayList<>();
		if (resultMaps == null || resultMaps.size() == 0) {
			return result;
		}
		for (ResultMap resultMap : resultMaps) {
			Set<Map.Entry> entrySet = resultMap.entrySet();
			Map<String, String> map = new HashMap<>();
			for (Map.Entry entry : entrySet) {
				Object key = entry.getKey();
				Object value = entry.getValue();
				String ckey = String.valueOf(key).toLowerCase();
				map.put(ckey, convert(ckey, value));
			}
			result.add(map);
		}
		return result;
	}

	public GenericMapper getGenericMapper(String dataSourceName) {
		if (StringUtils.isBlank(dataSourceName)) {
			throw new RuntimeException("getGenericMapper dataSourceName Can't null");
		}
//		GenericMapper genericMapper = DBContextHolder.getInstance()
//				.getMapperInterface(dataSourceName, GenericMapper.class);
		GenericMapper genericMapper = new GenericMapper() {
			@Override
			public List<LowerCaseResultMap> executeSql(String sql) {
				return null;
			}

			@Override
			public int executeInsertSql(String sql) {
				return 0;
			}

			@Override
			public int executeUpdateSql(String sql) {
				return 0;
			}

			@Override
			public int executeDeleteSql(String sql) {
				return 0;
			}

			@Override
			public int selectCountSql(String sql) {
				return 0;
			}

			@Override
			public Object selectOneResult(String sql) {
				return null;
			}

			@Override
			public List<LowerCaseResultMap> executeDynamicSql(Map<String, Object> params) {
				return null;
			}

			@Override
			public int insertDynamicTable(Map<String, Object> params) {
				return 0;
			}

			@Override
			public int executeDynamicSelectInsert(Map<String, Object> params) {
				return 0;
			}

			@Override
			public int updateDynamicTable(Map<String, Object> params) {
				return 0;
			}

			@Override
			public int deleteDynamicTable(Map<String, Object> params) {
				return 0;
			}

			@Override
			public void callProcedure(String procedureName) {

			}
		};
		if (genericMapper == null) {
			throw new RuntimeException(
					String.format("getGenericMapper dataSourceName=%s GenericMapper not exists!",
							dataSourceName));
		}
		return genericMapper;
	}

	public List<LowerCaseResultMap> querySql(GenericMapper genericMapper, String sql) {
		if (genericMapper == null) {
			throw new RuntimeException("GenericMapper Can't null");
		} else if (!StringUtils.startsWithIgnoreCase(sql, "SELECT")) {
			throw new RuntimeException("querySql Only select statements can be executed");
		}
		return genericMapper.executeSql(sql);
	}

	public void querySql(String dataSourceName, List<DecorateParam> sql) {
		for (DecorateParam decorateParam : sql) {
			if (decorateParam == null || !decorateParam.isUsable()) {
				continue;
			}
			DecorateParamSql paramSql = (DecorateParamSql) decorateParam;
			Object result = querySql(dataSourceName, paramSql.getStaticSql());
			paramSql.setExecuteResult(result);
		}
	}

	public List<LowerCaseResultMap> querySql(String dataSourceName, String sql) {
		if (StringUtils.isBlank(dataSourceName)) {
			throw new RuntimeException("dataSourceName Can't null");
		} else if (StringUtils.isBlank(sql)) {
			throw new RuntimeException("querySql sql Can't null");
		} else if (!StringUtils.startsWithIgnoreCase(sql, "SELECT")) {
			throw new RuntimeException("querySql Only select statements can be executed");
		} else {
			GenericMapper genericMapper = getGenericMapper(dataSourceName);
			return querySql(genericMapper, sql);
		}
	}

	public int executeInsertSql(GenericMapper genericMapper, String sql) {
		if (genericMapper == null) {
			throw new RuntimeException("GenericMapper Can't null");
		} else if (!StringUtils.startsWithIgnoreCase(sql, "INSERT")) {
			throw new RuntimeException("executeInsertSql Only insert statements can be executed");
		}
		return genericMapper.executeInsertSql(sql);
	}

	public void executeInsertSql(String dataSourceName, List<DecorateParam> sql) {
		for (DecorateParam decorateParam : sql) {
			if (decorateParam == null || !decorateParam.isUsable()) {
				continue;
			}
			DecorateParamSql paramSql = (DecorateParamSql) decorateParam;
			Object result = executeInsertSql(dataSourceName, paramSql.getStaticSql());
			paramSql.setExecuteResult(result);
		}
	}

	public int executeInsertSql(String dataSourceName, String sql) {
		if (StringUtils.isBlank(dataSourceName)) {
			throw new RuntimeException("dataSourceName Can't null");
		} else if (StringUtils.isBlank(sql)) {
			throw new RuntimeException("executeInsertSql sql Can't null");
		} else if (!StringUtils.startsWithIgnoreCase(sql, "INSERT")) {
			throw new RuntimeException("executeInsertSql Only insert statements can be executed");
		} else {
			GenericMapper genericMapper = getGenericMapper(dataSourceName);
			return executeInsertSql(genericMapper, sql);
		}
	}

	public int executeUpdateSql(GenericMapper genericMapper, String sql) {
		if (genericMapper == null) {
			throw new RuntimeException("GenericMapper Can't null");
		} else if (!StringUtils.startsWithIgnoreCase(sql, "UPDATE")) {
			throw new RuntimeException("executeUpdateSql Only update statements can be executed");
		}
		return genericMapper.executeUpdateSql(sql);
	}

	public void executeUpdateSql(String dataSourceName, List<DecorateParam> sql) {
		for (DecorateParam decorateParam : sql) {
			if (decorateParam == null || !decorateParam.isUsable()) {
				continue;
			}
			DecorateParamSql paramSql = (DecorateParamSql) decorateParam;
			Object result = executeUpdateSql(dataSourceName, paramSql.getStaticSql());
			paramSql.setExecuteResult(result);
		}
	}

	public int executeUpdateSql(String dataSourceName, String sql) {
		if (StringUtils.isBlank(dataSourceName)) {
			throw new RuntimeException("dataSourceName Can't null");
		} else if (StringUtils.isBlank(sql)) {
			throw new RuntimeException(" sql Can't null");
		} else if (!StringUtils.startsWithIgnoreCase(sql, "UPDATE")) {
			throw new RuntimeException("executeDeleteSql Only update statements can be executed");
		} else {
			GenericMapper genericMapper = getGenericMapper(dataSourceName);
			return executeUpdateSql(genericMapper, sql);
		}
	}

	public int executeDeleteSql(GenericMapper genericMapper, String sql) {
		if (genericMapper == null) {
			throw new RuntimeException("GenericMapper Can't null");
		} else if (!StringUtils.startsWithIgnoreCase(sql, "DELETE")) {
			throw new RuntimeException("executeDeleteSql Only delete statements can be executed");
		}
		return genericMapper.executeDeleteSql(sql);
	}

	public void executeDeleteSql(String dataSourceName, List<DecorateParam> sql) {
		for (DecorateParam decorateParam : sql) {
			if (decorateParam == null || !decorateParam.isUsable()) {
				continue;
			}
			DecorateParamSql paramSql = (DecorateParamSql) decorateParam;
			Object result = executeDeleteSql(dataSourceName, paramSql.getStaticSql());
			paramSql.setExecuteResult(result);
		}
	}

	public int executeDeleteSql(String dataSourceName, String sql) {
		if (StringUtils.isBlank(dataSourceName)) {
			throw new RuntimeException("dataSourceName Can't null");
		} else if (StringUtils.isBlank(sql)) {
			throw new RuntimeException("executeDeleteSql sql Can't null");
		} else if (!StringUtils.startsWithIgnoreCase(sql, "DELETE")) {
			throw new RuntimeException("executeDeleteSql Only delete statements can be executed");
		} else {
			GenericMapper genericMapper = getGenericMapper(dataSourceName);
			return executeDeleteSql(genericMapper, sql);
		}
	}

	public int selectCountSql(GenericMapper genericMapper, String sql) {
		if (genericMapper == null) {
			throw new RuntimeException("GenericMapper Can't null");
		} else if (!StringUtils.startsWithIgnoreCase(sql, "SELECT")) {
			throw new RuntimeException(
					"selectCountSql only select count statements can be executed");
		}
		return genericMapper.selectCountSql(sql);
	}

	public void selectCountSql(String dataSourceName, List<DecorateParam> sql) {
		for (DecorateParam decorateParam : sql) {
			if (decorateParam == null || !decorateParam.isUsable()) {
				continue;
			}
			DecorateParamSql paramSql = (DecorateParamSql) decorateParam;
			Object result = selectCountSql(dataSourceName, paramSql.getStaticSql());
			paramSql.setExecuteResult(result);
		}
	}

	public int selectCountSql(String dataSourceName, String sql) {
		if (StringUtils.isBlank(dataSourceName)) {
			throw new RuntimeException("dataSourceName Can't null");
		} else if (StringUtils.isBlank(sql)) {
			throw new RuntimeException("selectCountSql sql Can't null");
		} else if (!StringUtils.startsWithIgnoreCase(sql, "SELECT")) {
			throw new RuntimeException(
					"selectCountSql only select count statements can be executed");
		} else {
			GenericMapper genericMapper = getGenericMapper(dataSourceName);
			return selectCountSql(genericMapper, sql);
		}
	}

	public Object selectOneResult(GenericMapper genericMapper, String sql) {
		if (genericMapper == null) {
			throw new RuntimeException("GenericMapper Can't null");
		} else if (!StringUtils.startsWithIgnoreCase(sql, "SELECT")) {
			throw new RuntimeException(
					"selectOneResult only select oneReuslt statements can be executed");
		}
		return genericMapper.selectOneResult(sql);
	}

	public void selectOneResult(String dataSourceName, List<DecorateParam> sql) {
		for (DecorateParam decorateParam : sql) {
			if (decorateParam == null || !decorateParam.isUsable()) {
				continue;
			}
			DecorateParamSql paramSql = (DecorateParamSql) decorateParam;
			Object result = selectOneResult(dataSourceName, paramSql.getStaticSql());
			paramSql.setExecuteResult(result);
		}
	}

	public Object selectOneResult(String dataSourceName, String sql) {
		if (StringUtils.isBlank(dataSourceName)) {
			throw new RuntimeException("dataSourceName Can't null");
		} else if (StringUtils.isBlank(sql)) {
			throw new RuntimeException("selectOneResult sql Can't null");
		} else if (!StringUtils.startsWithIgnoreCase(sql, "SELECT")) {
			throw new RuntimeException(
					"selectOneResult only select oneReuslt statements can be executed");
		} else {
			GenericMapper genericMapper = getGenericMapper(dataSourceName);
			return selectOneResult(genericMapper, sql);
		}
	}

	public void insertDynamicTableBatch(String dataSourceNameA, String dataSourceNameB,
			final String tableName, final List<DecorateParam> paramMapListA,
			final List<DecorateParam> paramMapListB) {
		if (StringUtils.isNotEmpty(dataSourceNameA)) {
			insertDynamicTableBatch(dataSourceNameA, tableName, paramMapListA);
		}
		if (StringUtils.isNotEmpty(dataSourceNameB)) {
			insertDynamicTableBatch(dataSourceNameB, tableName, paramMapListB);
		}
	}

	public int executeDynamicSelectInsertBatch(String dataSourceName, final String targetTableName,
			final String sourceTableName, final List<DecorateParam> paramMapList) {
		if (StringUtils.isBlank(dataSourceName)) {
			throw new RuntimeException("dataSourceName Can't null");
		} else if (StringUtils.isBlank(targetTableName)) {
			throw new RuntimeException(
					"executeDynamicSelectInsertBatch targetTableName Can't null");
		} else if (StringUtils.isBlank(sourceTableName)) {
			throw new RuntimeException(
					"executeDynamicSelectInsertBatch sourceTableName Can't null");
		} else if (paramMapList == null) {
			throw new RuntimeException("executeDynamicSelectInsertBatch paramMapList Can't null");
		}
		GenericMapper genericMapper = getGenericMapper(dataSourceName);

		return executeDynamicSelectInsertBatch(genericMapper, targetTableName, sourceTableName,
				paramMapList);
	}

	public int executeDynamicSelectInsertBatch(GenericMapper genericMapper,
			final String targetTableName, final String sourceTableName,
			final List<DecorateParam> paramMapList) {
		if (genericMapper == null) {
			throw new RuntimeException("GenericMapper Can't null");
		} else if (StringUtils.isBlank(targetTableName)) {
			throw new RuntimeException(
					"executeDynamicSelectInsertBatch  targetTableName Can't null");
		} else if (StringUtils.isBlank(sourceTableName)) {
			throw new RuntimeException(
					"executeDynamicSelectInsertBatch sourceTableName Can't null");
		} else if (paramMapList == null) {
			throw new RuntimeException("executeDynamicSelectInsertBatch paramMapList Can't null");
		}
		return selectInsert(genericMapper, targetTableName, sourceTableName, paramMapList);
	}

	public void insertDynamicTableBatch(String dataSourceName, final String tableName,
			final List<DecorateParam> paramMapList) {
		if (StringUtils.isBlank(dataSourceName)) {
			throw new RuntimeException("dataSourceName Can't null");
		} else if (StringUtils.isBlank(tableName)) {
			throw new RuntimeException("insertDynamicTableBatch tableName Can't null");
		} else if (paramMapList == null) {
			throw new RuntimeException("insertDynamicTableBatch paramMapList Can't null");
		}
		GenericMapper genericMapper = getGenericMapper(dataSourceName);

		insertDynamicTableBatch(genericMapper, tableName, paramMapList);
	}

	public void insertDynamicTableBatch(GenericMapper genericMapper, final String tableName,
			final List<DecorateParam> paramMapList) {
		if (genericMapper == null) {
			throw new RuntimeException("GenericMapper Can't null");
		} else if (StringUtils.isBlank(tableName)) {
			throw new RuntimeException("insertDynamicTableBatch tableName Can't null");
		} else if (paramMapList == null) {
			throw new RuntimeException("insertDynamicTableBatch paramMapList Can't null");
		}
		insert(genericMapper, tableName, paramMapList);
	}

	public void updateDynamicTableBatch(String dataSourceName, final String tableName,
			final List<DecorateParam> paramMapList) {
		if (StringUtils.isBlank(dataSourceName)) {
			throw new RuntimeException("dataSourceName Can't null");
		} else if (StringUtils.isBlank(tableName)) {
			throw new RuntimeException("updateDynamicTableBatch tableName Can't null");
		} else if (paramMapList == null) {
			throw new RuntimeException("updateDynamicTableBatch paramMapList Can't null");
		}
		GenericMapper genericMapper = getGenericMapper(dataSourceName);

		updateDynamicTableBatch(genericMapper, tableName, paramMapList);
	}

	public void updateDynamicTableBatch(GenericMapper genericMapper, final String tableName,
			final List<DecorateParam> paramMapList) {
		if (genericMapper == null) {
			throw new RuntimeException("GenericMapper Can't null");
		} else if (StringUtils.isBlank(tableName)) {
			throw new RuntimeException("updateDynamicTableBatch tableName Can't null");
		} else if (paramMapList == null) {
			throw new RuntimeException("updateDynamicTableBatch paramMapList Can't null");
		}
		update(genericMapper, tableName, paramMapList);
	}

	public void deleteDynamicTableBatch(String dataSourceName, final String tableName,
			final List<DecorateParam> paramMapList) {
		if (StringUtils.isBlank(dataSourceName)) {
			throw new RuntimeException("dataSourceName Can't null");
		} else if (StringUtils.isBlank(tableName)) {
			throw new RuntimeException("deleteDynamicTableBatch tableName Can't null");
		} else if (paramMapList == null) {
			throw new RuntimeException("deleteDynamicTableBatch paramMapList Can't null");
		}
		GenericMapper genericMapper = getGenericMapper(dataSourceName);

		deleteDynamicTableBatch(genericMapper, tableName, paramMapList);
	}

	public void deleteDynamicTableBatch(GenericMapper genericMapper, final String tableName,
			final List<DecorateParam> paramMapList) {
		if (genericMapper == null) {
			throw new RuntimeException("GenericMapper Can't null");
		} else if (StringUtils.isBlank(tableName)) {
			throw new RuntimeException("deleteDynamicTableBatch tableName Can't null");
		} else if (paramMapList == null) {
			throw new RuntimeException("deleteDynamicTableBatch paramMapList Can't null");
		}

		delete(genericMapper, tableName, paramMapList);
	}

	private int selectInsert(GenericMapper genericMapper, final String targetTableName,
			final String sourceTableName, final List<DecorateParam> paramMapList) {

		int total = 0;
		for (DecorateParam param : paramMapList) {
			if (param == null || !param.isUsable()) {
				continue;
			}
			DecorateParamSelectInsert paramSelectInsert = (DecorateParamSelectInsert) param;
			HashMap<String, Object> var10 = new HashMap<>();
			ArrayList<Map> list = new ArrayList<>();
			//分析字段要备份的字段
			for (ColumnMapping columnMapping : paramSelectInsert.getBackupColumns()) {
				list.add(parserValue(columnMapping.getTargetColumn(),
						columnMapping.formatSourceColumn()));
			}

			var10.put("targetTableName", targetTableName);
			var10.put("sourceTableName", sourceTableName);
			var10.put("columnList", list);
			//分析查询的条件
			parserPkInfo(paramSelectInsert.getWhereKeys(), var10);
			if (StringUtils.isNotEmpty(paramSelectInsert.getWhereConstans())) {
				var10.put("whereconstans", paramSelectInsert.getWhereConstans());
			}
			if (list.size() > 0) {
				int count = genericMapper.executeDynamicSelectInsert(var10);
				total = total + count;
				paramSelectInsert.setExecuteResult(count);
			}
		}
		return total;
	}

	private void insert(GenericMapper genericMapper, final String tableName,
			final List<DecorateParam> paramMapList) {
		for (DecorateParam param : paramMapList) {
			if (param == null || !param.isUsable()) {
				continue;
			}
			DecorateParamInsert paramInsert = (DecorateParamInsert) param;
			ArrayList<Map> list = new ArrayList<>();

			for (VarableBind varableBind : paramInsert.getInsertValues()) {
				list.add(parserValue(varableBind.getColumn(), varableBind.getValue()));
			}

			HashMap<String, Object> var10 = new HashMap<>();
			var10.put("tableName", tableName);
			var10.put("columnList", list);
			int sucess = genericMapper.insertDynamicTable(var10);
			paramInsert.setExecuteResult(sucess);
		}
	}

	private void update(GenericMapper genericMapper, final String tableName,
			final List<DecorateParam> paramMapList) {
		for (DecorateParam param : paramMapList) {
			if (param == null || !param.isUsable()) {
				continue;
			}
			DecorateParamUpdate paramUpdate = (DecorateParamUpdate) param;
			ArrayList<Map> list = new ArrayList<>();

			for (VarableBind varableBind : paramUpdate.getUpdateValues()) {
				list.add(parserValue(varableBind.getColumn(), varableBind.getValue()));
			}

			HashMap<String, Object> var12 = new HashMap<>();
			var12.put("tableName", tableName);
			var12.put("columnList", list);
			parserPkInfo(paramUpdate.getWhereKeys(), var12);
			if (StringUtils.isNotEmpty(paramUpdate.getWhereConstans())) {
				var12.put("whereconstans", paramUpdate.getWhereConstans());
			}
			int total = genericMapper.updateDynamicTable(var12);
			paramUpdate.setExecuteResult(total);
		}
	}

	private void delete(GenericMapper genericMapper, String tableName,
			List<DecorateParam> paramMapList) {
		for (DecorateParam param : paramMapList) {
			if (param == null || !param.isUsable()) {
				continue;
			}
			DecorateParamDelete paramDelete = (DecorateParamDelete) param;
			HashMap<String, Object> var12 = new HashMap<>();
			parserPkInfo(paramDelete.getWhereKeys(), var12);
			if (StringUtils.isNotEmpty(paramDelete.getWhereConstans())) {
				var12.put("whereconstans", paramDelete.getWhereConstans());
			}
			if (var12.size() > 0) {
				var12.put("tableName", tableName);
				int total = genericMapper.deleteDynamicTable(var12);
				paramDelete.setExecuteResult(total);
			}
		}
	}

	private void parserPkInfo(List<VarableBind> listPk, Map<String, Object> var12) {
		if (listPk != null && listPk.size() > 0) {
			List<Map<String, Object>> pkList = new ArrayList<>();
			for (VarableBind varableBind : listPk) {
				pkList.add(parserPkValue(varableBind.getColumn(), varableBind.getValue()));
			}
			var12.put("pkList", pkList);
		}
	}

	private Map<String, Object> parserValue(String column, Object value) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("column", column);
		if (value == null) {
			value = "";
		}

		if (value instanceof String && ((String) value)
				.startsWith(DecorateConstans.SQLVALUE_CONSTANS)) {
			map.put("sqlValue", ((String) value).substring(4));
		} else {
			map.put("value", formatValue(value));
		}
		return map;
	}

	private Map<String, Object> parserPkValue(String column, Object value) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("key", column);
		if (value == null) {
			value = "";
		}

		if (value instanceof String && ((String) value)
				.startsWith(DecorateConstans.SQLVALUE_CONSTANS)) {
			map.put("sqlValue",
					((String) value).substring(DecorateConstans.SQLVALUE_CONSTANS_LENGTH));
		} else if (value instanceof List) {
			map.put("listValue", value);
		} else {
			map.put("value", formatValue(value));
		}
		return map;
	}

	/**
	 * 格式化,主要是格式化key=value中字符串中为null或者''的值,因为在GenericMapper中只对为null的情况进行判断,不单独判断 ''的情况
	 *
	 * @param value 需要格式化的值
	 * @return 格式化后的值
	 */
	private Object formatValue(Object value) {
		if (value == null) {
			return null;
		} else {
			if (value instanceof String) {
				String valueString = (String) value;
				if (StringUtils.isEmpty(valueString)) {
					return null;
				}
			}
			return value;
		}
	}

	/**
	 * 调用存储过程
	 *
	 * @param dataSourceName 执行的数据库名
	 * @param procedureName  执行的存储过程名
	 */
	public void callProcedure(String dataSourceName, String procedureName) {
		if (StringUtils.isBlank(dataSourceName)) {
			throw new RuntimeException("dataSourceName Can't null");
		} else if (StringUtils.isBlank(procedureName)) {
			throw new RuntimeException("procedureName Can't null");
		} else {
			GenericMapper genericMapper = getGenericMapper(dataSourceName);
			if (genericMapper == null) {
				throw new RuntimeException("GenericMapper Can't null");
			}
			genericMapper.callProcedure("call " + procedureName + "()");
		}
	}

	private String convert(String column, Object columnValue) {
		String value = "";
		if (columnValue == null) {
			value = "";
		} else if (columnValue instanceof BigDecimal) {
			value = ((BigDecimal) columnValue).toString();
		} else if (columnValue instanceof Short) {
			value = ((Short) columnValue).toString();
		} else if (columnValue instanceof Integer) {
			value = ((Integer) columnValue).toString();
		} else if (columnValue instanceof Long) {
			value = ((Long) columnValue).toString();
		} else if (columnValue instanceof java.sql.Timestamp) {
			long timesc = ((java.sql.Timestamp) columnValue).getTime();
			Date utilDate = new Date(timesc);
			value = DateUtils2.format(utilDate, DateUtils2.DEFAULT_DATETIME_PATTERN);
		} else if (columnValue instanceof Date) {
			long timesc = ((Date) columnValue).getTime();
			Date utilDate = new Date(timesc);
			value = DateUtils2.format(utilDate, DateUtils2.DEFAULT_DATE_PATTERN);
		} else if (columnValue instanceof java.sql.Date) {
			long timesc = ((java.sql.Date) columnValue).getTime();
			Date utilDate = new Date(timesc);
			value = DateUtils2.format(utilDate, DateUtils2.DEFAULT_DATE_PATTERN);
		} else if (columnValue instanceof Clob) {
			Reader inStream = null;
			try {
				inStream = ((Clob) columnValue).getCharacterStream();
				char[] c = new char[(int) ((Clob) columnValue).length()];
				inStream.read(c);
				value = new String(c);
				inStream.close();
			} catch (Exception e) {
				logger.error(
						"getColumnValue, dataType = java.sql.Clob get Exception.fieldName=" + column
								+ ",soruceValue=" + columnValue);
			} finally {
				try {
					if (inStream != null)
						inStream.close();
				} catch (Exception ex) {
				}
			}
		} else {
			value = columnValue.toString();
		}
		return value;
	}
}
