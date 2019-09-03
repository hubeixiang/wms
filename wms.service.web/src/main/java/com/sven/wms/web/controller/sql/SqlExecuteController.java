package com.sven.wms.web.controller.sql;

import com.sven.wms.business.transaction.TransactionBusiness;
import com.sven.wms.configuration.configuration.DataSourceProperties;
import com.sven.wms.core.entity.vo.LowerCaseResultMap;
import com.sven.wms.db.configure.DBContextHelper;
import com.sven.wms.db.dao.mapper.GenericMapper;
import com.sven.wms.utils.JsonUtils;
import com.sven.wms.web.controller.BaseController;
import com.sven.wms.web.model.CommonServiceResult;
import com.sven.wms.web.model.impl.EffectResult;
import com.sven.wms.web.model.impl.ExecuteParam;
import com.sven.wms.web.util.WebConstans;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sven
 * @date 2019/3/8 14:28
 */
@RequestMapping(WebConstans.WEB_VERSION_A + "/sql")
@Controller
@Api(value = "sql执行相关操作", tags = "sql执行相关操作")
public class SqlExecuteController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(SqlExecuteController.class);

	@Autowired
	private TransactionBusiness transactionBusiness;

	@Autowired
	private DataSourceProperties dataSourceProperties;

	@ResponseBody
	@ApiOperation(value = "查询加载数据库以及数据对应的sqlid", notes = "查询加载数据库以及数据对应的sqlid")
	@RequestMapping(produces = WebConstans.WEB_PRODUCES, method = RequestMethod.POST, value = "/select/sqlinfo")
	public CommonServiceResult selectSqlInfo() {
		String method = "selectSqlInfo";
		logger.info(String.format("%s startting ...", method));
		Map<String, List<String>> resultListMap = new HashMap<>();
		for (Map.Entry<String, DataSourceProperties.DataSourceConfig> entry : dataSourceProperties.getDb().entrySet()) {
			String db = entry.getKey();
			SqlSessionTemplate sqlSessionTemplate = DBContextHelper.getInstance().getSqlSessionTemplate(db);
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
			resultListMap.put(db, sqlStatement);
		}
		CommonServiceResult result = new CommonServiceResult();
		result.setDealResult(true);
		result.setDealDesc(resultListMap);
		logger.info(String.format("%s end", method));
		return result;
	}

	@ResponseBody
	@ApiOperation(value = "查询指定的SqlId", notes = "执行指定的查询SqlId,并返回结果")
	@RequestMapping(produces = WebConstans.WEB_PRODUCES, method = RequestMethod.POST, value = "/select/{db}/{sqlId}")
	public CommonServiceResult selectSpecSqlId(
			@ApiParam(required = true, name = "paramter", value = "sqlId执行需要的参数") @RequestBody ExecuteParam paramter,
			@ApiParam(required = true, name = "db", value = "sqlId执行的数据库") @PathVariable("db") String db,
			@ApiParam(required = true, name = "sqlId", value = "要执行的sql的,sqlId") @PathVariable("sqlId") String sqlId) {
		String method = "selectSpecSqlId";
		Object param = paramter.getSqlParam();
		logger.info(String.format("%s startting ...,param=%s", method, param == null ? null : JsonUtils.toJson(param)));
		CommonServiceResult result = new CommonServiceResult();
		SqlSessionTemplate sqlSessionTemplate = DBContextHelper.getInstance().getSqlSessionTemplate(db);
		if (sqlSessionTemplate == null) {
			result.setDealResult(false);
			result.setErrorMsg(String.format("db=%s 未能初始化", db));
			return result;
		}
		try {
			List<LowerCaseResultMap> resultListMap = sqlSessionTemplate.selectList(sqlId, param);
			result.setDealResult(true);
			result.setDealDesc(resultListMap);
		} catch (Throwable t) {
			logger.error(String.format("%s exception:", method), t);
			result.setDealResult(false);
			result.setErrorMsg(String.format("selectList(%s,%s) exception:%s", sqlId, param, t.getMessage()));
		}
		logger.info(String.format("%s end", method));
		return result;
	}

	@ResponseBody
	@ApiOperation(value = "执行指定的SqlId", notes = "执行指定的非查询SqlId,并返回结果")
	@RequestMapping(produces = WebConstans.WEB_PRODUCES, method = RequestMethod.POST, value = "/update/{db}/{sqlId}")
	public CommonServiceResult updateSpecSqlId(
			@ApiParam(required = true, name = "paramter", value = "sqlId执行需要的参数") @RequestBody ExecuteParam paramter,
			@ApiParam(required = true, name = "db", value = "sqlId执行的数据库") @PathVariable("db") String db,
			@ApiParam(required = true, name = "sqlId", value = "要执行的sql的,sqlId") @PathVariable("sqlId") String sqlId) {
		String method = "updateSpecSqlId";
		Object param = paramter.getSqlParam();
		logger.info(String.format("%s startting ...,param=%s", method, param == null ? null : JsonUtils.toJson(param)));
		SqlSessionTemplate sqlSessionTemplate = DBContextHelper.getInstance().getSqlSessionTemplate(db);
		CommonServiceResult result = new CommonServiceResult();
		if (sqlSessionTemplate == null) {
			result.setDealResult(false);
			result.setErrorMsg(String.format("db=%s 未能初始化", db));
			return result;
		}
		try {
			int resultInt = sqlSessionTemplate.update(sqlId, param);
			result.setDealResult(true);
			EffectResult effectResult = new EffectResult();
			effectResult.setEffectNumber(resultInt);
			result.setDealDesc(effectResult);
		} catch (Throwable t) {
			logger.error(String.format("%s exception:", method), t);
			result.setDealResult(false);
			result.setErrorMsg(String.format("update(%s,%s) exception:%s", sqlId, param, t.getMessage()));
		}
		logger.info(String.format("%s end", method));
		return result;
	}

	@ResponseBody
	@ApiOperation(value = "执行自定义SQL", notes = "传入要执行的SQL,依据传入的操作方式,在数据库中执行")
	@RequestMapping(produces = WebConstans.WEB_PRODUCES, method = RequestMethod.POST, value = "/custom/{operation}/{db}")
	public CommonServiceResult executeCustomSqlId(
			@ApiParam(required = true, name = "paramter", value = "要执行的SQL") @RequestBody String paramter,
			@ApiParam(required = true, name = "operation", value = "SQL的操作方式,枚举(delete/insert/update/select)") @PathVariable("operation") String operation,
			@ApiParam(required = true, name = "db", value = "sqlId执行的数据库") @PathVariable("db") String db) {
		String method = "selectSpecSqlId";
		logger.info(String.format("%s startting ...paramter=%s", method, paramter));
		CommonServiceResult result = new CommonServiceResult();
		if (StringUtils.isEmpty(paramter)) {
			result.setDealResult(false);
			result.setErrorMsg("要执行的sql不能未空");
			return result;
		} else if (StringUtils.isEmpty(operation)) {
			result.setDealResult(false);
			result.setErrorMsg("sql的执行方式不能为空");
			return result;
		} else if (!operation.equals("select") && !operation.equals("delete") && operation.equals("insert") && !operation
				.equals("update")) {
			result.setDealResult(false);
			result.setErrorMsg("sql的执行方式只能为枚举类型(select/delete/insert/update)");
			return result;
		}
		GenericMapper genericMapper = DBContextHelper.getInstance().getMapperInterface(db, GenericMapper.class);
		if (genericMapper == null) {
			result.setDealResult(false);
			result.setErrorMsg(String.format("db=%s 未能初始化GenericMapper对象", db));
			return result;
		}

		switch (operation) {
		case "select":
			executeSelect(result, paramter, genericMapper);
			break;
		case "delete":
			executeDelete(result, paramter, genericMapper);
			break;
		case "insert":
			executeInsert(result, paramter, genericMapper);
			break;
		case "update":
			executeUpdate(result, paramter, genericMapper);
			break;
		default:
			result.setDealResult(false);
			result.setErrorMsg("sql的执行方式只能为枚举类型(select/delete/insert/update)");
			break;
		}
		logger.info(String.format("%s end", method));
		return result;
	}

	private void executeSelect(CommonServiceResult result, String sql, GenericMapper genericMapper) {
		if (!sql.startsWith("select ") && !sql.startsWith("SELECT ")) {
			result.setDealResult(false);
			result.setErrorMsg("查询SQL,必须以select开始");
		} else {
			List<LowerCaseResultMap> resultListMap = genericMapper.executeSql(sql);
			result.setDealResult(true);
			result.setDealDesc(resultListMap);
		}
	}

	private void executeDelete(CommonServiceResult result, String sql, GenericMapper genericMapper) {
		if (!sql.startsWith("delete ") && !sql.startsWith("DELETE ")) {
			result.setDealResult(false);
			result.setErrorMsg("删除SQL,必须以delete开始");
		} else {
			int delete = genericMapper.executeDeleteSql(sql);
			result.setDealResult(true);
			result.setDealDesc(delete);
		}
	}

	private void executeInsert(CommonServiceResult result, String sql, GenericMapper genericMapper) {
		if (!sql.startsWith("insert ") && !sql.startsWith("INSERT ")) {
			result.setDealResult(false);
			result.setErrorMsg("插入SQL,必须以insert开始");
		} else {
			int insert = genericMapper.executeInsertSql(sql);
			result.setDealResult(true);
			result.setDealDesc(insert);
		}
	}

	private void executeUpdate(CommonServiceResult result, String sql, GenericMapper genericMapper) {
		if (!sql.startsWith("update ") && !sql.startsWith("UPDATE ")) {
			result.setDealResult(false);
			result.setErrorMsg("更新SQL,必须以update开始");
		} else {
			int update = genericMapper.executeUpdateSql(sql);
			result.setDealResult(true);
			result.setDealDesc(update);
		}
	}
}
