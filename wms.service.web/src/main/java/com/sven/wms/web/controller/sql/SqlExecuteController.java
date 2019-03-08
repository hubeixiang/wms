package com.sven.wms.web.controller.sql;

import com.sven.wms.business.transaction.TransactionBusiness;
import com.sven.wms.core.entity.vo.LowerCaseResultMap;
import com.sven.wms.db.configure.DBContextHelper;
import com.sven.wms.web.controller.BaseController;
import com.sven.wms.web.model.ServiceResult;
import com.sven.wms.web.model.SelectServiceParamter;
import com.sven.wms.web.util.WebConstans;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
}
