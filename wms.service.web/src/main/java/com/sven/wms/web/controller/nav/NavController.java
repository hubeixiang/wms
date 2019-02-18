package com.sven.wms.web.controller.nav;

import com.sven.wms.business.transaction.TransactionBusiness;
import com.sven.wms.configuration.utils.SpringContextUtil;
import com.sven.wms.core.entity.vo.LowerCaseResultMap;
import com.sven.wms.db.configure.DBContextHelper;
import com.sven.wms.db.configure.DataConnectionDefinition;
import com.sven.wms.configuration.configuration.MyTestBean;
import com.sven.wms.db.dao.mapper.GenericMapper;
import com.sven.wms.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 现阶段侧测试controller
 *
 * @author sven
 * @date 2019/2/15 14:43
 */
@RequestMapping("nav")
@Controller
public class NavController {
	private Logger logger = LoggerFactory.getLogger(NavController.class);

	@Autowired
	private MyTestBean myTestBean;

	@Autowired
	private TransactionBusiness transactionBusiness;

	@ResponseBody
	@RequestMapping(value = "init")
	public Object queryNavInfo() {
		logger.info("NavController.queryNavInfo");
		DataConnectionDefinition dataConfig = SpringContextUtil.getBean(DataConnectionDefinition.class);
		if (myTestBean == null) {
			return "{}";
		} else {
			return JsonUtils.toJson(myTestBean);
		}
	}

	@ResponseBody
	@RequestMapping(value = "query")
	public Object queryCountInfo() {
		logger.info("NavController.query");
		String sql = "select province_id,count(*) count from objects group by province_id";
		//		List<LowerCaseResultMap> result = genericMapper.executeSql(sql);
		List<LowerCaseResultMap> result = DBContextHelper.getInstance().getMapperInterface("coss", GenericMapper.class).executeSql(sql);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "custQuery")
	public Object customQueryCountInfo(@RequestParam(value = "sqlId") String sqlId, @RequestParam(value = "db") String db,
			@RequestParam(value = "param") String jsonMapParam) {
		logger.info("NavController.custQuery");
		Map<String, String> param = new HashMap<>();
		if (StringUtils.isNotEmpty(jsonMapParam)) {
			param = JsonUtils.fromJson(jsonMapParam, Map.class);
		}
		SqlSessionTemplate sqlSessionTemplate = DBContextHelper.getInstance().getSqlSessionTemplate(db);
		List<LowerCaseResultMap> result = sqlSessionTemplate.selectList(sqlId, param);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "transaction")
	public Object transactionBusiness() {
		int result = transactionBusiness.transaction(false);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "transactionXA")
	public Object transactionBusinessXA() {
		int result = transactionBusiness.transactionXA(false);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "transaction_e")
	public Object transactionBusiness_e() {
		int result = transactionBusiness.transaction(true);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "transactionXA_e")
	public Object transactionBusinessXA_e() {
		int result = transactionBusiness.transactionXA(true);
		return result;
	}
}
