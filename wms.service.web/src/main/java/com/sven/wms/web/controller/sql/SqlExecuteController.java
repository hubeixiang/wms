package com.sven.wms.web.controller.sql;

import com.sven.wms.business.transaction.TransactionBusiness;
import com.sven.wms.core.entity.vo.LowerCaseResultMap;
import com.sven.wms.db.configure.DBContextHelper;
import com.sven.wms.db.dao.mapper.GenericMapper;
import com.sven.wms.web.controller.BaseController;
import com.sven.wms.web.model.SelectServiceParamter;
import com.sven.wms.web.model.ServiceResult;
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

import java.util.List;

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

	@ResponseBody
	@ApiOperation(value = "查询指定的SqlId", notes = "执行指定的查询SqlId,并返回结果")
	@RequestMapping(produces = WebConstans.WEB_PRODUCES, method = RequestMethod.POST, value = "/select/{db}/{sqlId}")
	public ServiceResult selectSpecSqlId(
			@ApiParam(required = true, name = "paramter", value = "sqlId执行需要的参数") @RequestBody SelectServiceParamter paramter,
			@ApiParam(required = true, name = "db", value = "sqlId执行的数据库") @PathVariable("db") String db,
			@ApiParam(required = true, name = "sqlId", value = "要执行的sql的,sqlId") @PathVariable("sqlId") String sqlId) {
		String method = "selectSpecSqlId";
		logger.info(String.format("%s startting ...", method));
		Object param = paramter.getQueryParam() == null ? null : paramter.getQueryParam().getParam();
		SqlSessionTemplate sqlSessionTemplate = DBContextHelper.getInstance().getSqlSessionTemplate(db);
		List<LowerCaseResultMap> resultListMap = sqlSessionTemplate.selectList(sqlId, param);
		ServiceResult result = new ServiceResult();
		result.setCode(0);
		result.setData(resultListMap);
		logger.info(String.format("%s end", method));
		return result;
	}

	@ResponseBody
	@ApiOperation(value = "执行自定义SQL", notes = "传入要执行的SQL,依据传入的操作方式,在数据库中执行")
	@RequestMapping(produces = WebConstans.WEB_PRODUCES, method = RequestMethod.POST, value = "/custom/{operation}/{db}")
	public ServiceResult executeCustomSqlId(@ApiParam(required = true, name = "paramter", value = "要执行的SQL") @RequestBody String paramter,
			@ApiParam(required = true, name = "operation", value = "SQL的操作方式,枚举(delete/insert/update/select)") @PathVariable("operation") String operation,
			@ApiParam(required = true, name = "db", value = "sqlId执行的数据库") @PathVariable("db") String db) {
		String method = "selectSpecSqlId";
		logger.info(String.format("%s startting ...", method));
		ServiceResult result = new ServiceResult();
		if (StringUtils.isEmpty(paramter)) {
			result.setCode(-1);
			result.setMsg("要执行的sql不能未空");
			return result;
		} else if (StringUtils.isEmpty(operation)) {
			result.setCode(-1);
			result.setMsg("sql的执行方式不能为空");
			return result;
		} else if (!operation.equals("select") && !operation.equals("delete") && operation.equals("insert") && !operation
				.equals("update")) {
			result.setCode(-1);
			result.setMsg("sql的执行方式只能为枚举类型(select/delete/insert/update)");
			return result;
		}
		GenericMapper genericMapper = DBContextHelper.getInstance().getMapperInterface(db, GenericMapper.class);

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
			result.setCode(-1);
			result.setMsg("sql的执行方式只能为枚举类型(select/delete/insert/update)");
			break;
		}
		logger.info(String.format("%s end", method));
		return result;
	}

	private void executeSelect(ServiceResult result, String sql, GenericMapper genericMapper) {
		if (!sql.startsWith("select ") && !sql.startsWith("SELECT ")) {
			result.setCode(-1);
			result.setMsg("查询SQL,必须以select开始");
		} else {
			List<LowerCaseResultMap> resultListMap = genericMapper.executeSql(sql);
			result.setCode(0);
			result.setData(resultListMap);
		}
	}

	private void executeDelete(ServiceResult result, String sql, GenericMapper genericMapper) {
		if (!sql.startsWith("delete ") && !sql.startsWith("DELETE ")) {
			result.setCode(-1);
			result.setMsg("删除SQL,必须以delete开始");
		} else {
			int delete = genericMapper.executeDeleteSql(sql);
			result.setCode(0);
			result.setData(delete);
		}
	}

	private void executeInsert(ServiceResult result, String sql, GenericMapper genericMapper) {
		if (!sql.startsWith("insert ") && !sql.startsWith("INSERT ")) {
			result.setCode(-1);
			result.setMsg("插入SQL,必须以insert开始");
		} else {
			int insert = genericMapper.executeInsertSql(sql);
			result.setCode(0);
			result.setData(insert);
		}
	}

	private void executeUpdate(ServiceResult result, String sql, GenericMapper genericMapper) {
		if (!sql.startsWith("update ") && !sql.startsWith("UPDATE ")) {
			result.setCode(-1);
			result.setMsg("更新SQL,必须以update开始");
		} else {
			int update = genericMapper.executeUpdateSql(sql);
			result.setCode(0);
			result.setData(update);
		}
	}
}
