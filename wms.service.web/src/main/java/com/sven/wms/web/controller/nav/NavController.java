package com.sven.wms.web.controller.nav;

import com.sven.wms.configuration.utils.SpringContextUtil;
import com.sven.wms.core.entity.vo.LowerCaseResultMap;
import com.sven.wms.db.configure.DBContextHolder;
import com.sven.wms.db.configure.DataConnectionDefinition;
import com.sven.wms.configuration.configuration.MyTestBean;
import com.sven.wms.db.configure.SqlTemplateMapperFactoryBean;
import com.sven.wms.db.dao.mapper.GenericMapper;
import com.sven.wms.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
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

@RequestMapping("nav")
@Controller
public class NavController {
	private Logger logger = LoggerFactory.getLogger(NavController.class);

	@Autowired
	private MyTestBean myTestBean;

	@Autowired
	private GenericMapper genericMapper;

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
		List<LowerCaseResultMap> result = genericMapper.executeSql(sql);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "sqlQuery")
	public Object sqlQueryCountInfo(@RequestParam(value = "sql") String sql) {
		logger.info("NavController.sqlIdQuery");
		SqlTemplateMapperFactoryBean sqlTemplateMapperFactoryBean = DBContextHolder.getInstance()
				.getMapperByName("coss", "com.sven.wms.db.dao.mapper.GenericMapper.executeSql");
		List<LowerCaseResultMap> result = DBContextHolder.getInstance().queryList(sqlTemplateMapperFactoryBean, sql);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "sqlIdQuery")
	public Object sqlIdQueryCountInfo(@RequestParam(value = "sqlId") String sqlId) {
		logger.info("NavController.sqlIdQuery");
		SqlTemplateMapperFactoryBean sqlTemplateMapperFactoryBean = DBContextHolder.getInstance().getMapperByName("coss", sqlId);
		List<LowerCaseResultMap> result = DBContextHolder.getInstance().queryList(sqlTemplateMapperFactoryBean, null);
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
		SqlTemplateMapperFactoryBean sqlTemplateMapperFactoryBean = DBContextHolder.getInstance().getMapperByName(db, sqlId);
		List<LowerCaseResultMap> result = DBContextHolder.getInstance().queryList(sqlTemplateMapperFactoryBean, param);
		return result;
	}

}
