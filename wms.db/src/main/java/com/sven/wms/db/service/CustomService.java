package com.sven.wms.db.service;

import com.sven.wms.db.configure.DBContextHelper;
import com.sven.wms.db.configure.SqlSessionTemplateInfo;
import com.sven.wms.db.exception.SqlExecuteException;
import com.sven.wms.db.param.custom.CustomDecorate;
import com.sven.wms.db.param.custom.CustomDecorateResult;
import com.sven.wms.db.param.custom.CustomDecorateResultUtil;
import com.sven.wms.utils.JsonUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by sven on 2017/3/17.
 */
@Service("customService")
public class CustomService {
	private Logger logger = LoggerFactory.getLogger(CustomService.class);

	private SqlSessionTemplate getSqlSessionTemplate(CustomDecorate customDecorate) {
		SqlSessionTemplateInfo sqlSessionTemplateInfo = DBContextHelper.getInstance().getSqlSessionTemplateInfo(customDecorate.getDb());
		if (sqlSessionTemplateInfo == null) {
			throw new RuntimeException(String.format("db=%s not exists!", customDecorate.getDb()));
		}
		if (!sqlSessionTemplateInfo.hasMappedStatementNames(customDecorate.getSqlId())) {
			throw new RuntimeException(String.format("db=%s,sqlId=%s not exists!", customDecorate.getDb(), customDecorate.getSqlId()));
		}
		SqlSessionTemplate sqlSessionTemplate = sqlSessionTemplateInfo.getSqlSessionTemplate();
		return sqlSessionTemplate;
	}

	public void insert(CustomDecorate customDecorate) {
		SqlSessionTemplate sqlSessionTemplate = getSqlSessionTemplate(customDecorate);
		String sqlId = customDecorate.getSqlId();
		List<Map<String, Object>> paramterList = customDecorate.getParamters();
		if (paramterList != null && paramterList.size() > 0) {
			for (Map<String, Object> parameter : paramterList) {
				try {
					int ret = sqlSessionTemplate.insert(sqlId, parameter);
				} catch (Throwable t) {
					CustomDecorateResult result = CustomDecorateResultUtil.copy(customDecorate);
					result.setParameter(parameter);
					logger.error(String.format("%s Exception:%s", JsonUtils.toJson(result), t.getMessage()), t);
					result.setOk(false);
					result.setSqlResult(t.getMessage());
					throw new SqlExecuteException(result, t);
				}
			}
		}
	}

	public void update(CustomDecorate customDecorate) {
		SqlSessionTemplate sqlSessionTemplate = getSqlSessionTemplate(customDecorate);
		String sqlId = customDecorate.getSqlId();
		List<Map<String, Object>> paramterList = customDecorate.getParamters();
		if (paramterList != null && paramterList.size() > 0) {
			for (Map<String, Object> parameter : paramterList) {
				try {
					int ret = sqlSessionTemplate.update(sqlId, parameter);
				} catch (Throwable t) {
					CustomDecorateResult result = CustomDecorateResultUtil.copy(customDecorate);
					result.setParameter(parameter);
					logger.error(String.format("%s Exception:%s", JsonUtils.toJson(result), t.getMessage()), t);
					result.setOk(false);
					result.setSqlResult(t.getMessage());
					throw new SqlExecuteException(result, t);
				}
			}
		}
	}

	public void delete(CustomDecorate customDecorate) {
		SqlSessionTemplate sqlSessionTemplate = getSqlSessionTemplate(customDecorate);
		String sqlId = customDecorate.getSqlId();
		List<Map<String, Object>> paramterList = customDecorate.getParamters();
		if (paramterList != null && paramterList.size() > 0) {
			for (Map<String, Object> parameter : paramterList) {
				try {
					int ret = sqlSessionTemplate.delete(sqlId, parameter);
				} catch (Throwable t) {
					CustomDecorateResult result = CustomDecorateResultUtil.copy(customDecorate);
					result.setParameter(parameter);
					logger.error(String.format("%s Exception:%s", JsonUtils.toJson(result), t.getMessage()), t);
					result.setOk(false);
					result.setSqlResult(t.getMessage());
					throw new SqlExecuteException(result, t);
				}
			}
		}
	}

	public void select(CustomDecorate customDecorate) {
		SqlSessionTemplate sqlSessionTemplate = getSqlSessionTemplate(customDecorate);
		String sqlId = customDecorate.getSqlId();
		List<Map<String, Object>> paramterList = customDecorate.getParamters();
		if (paramterList != null && paramterList.size() > 0) {
			for (Map<String, Object> parameter : paramterList) {
				try {
					List<Object> resultListMap = sqlSessionTemplate.selectList(sqlId, parameter);
				} catch (Throwable t) {
					CustomDecorateResult result = CustomDecorateResultUtil.copy(customDecorate);
					result.setParameter(parameter);
					logger.error(String.format("%s Exception:%s", JsonUtils.toJson(result), t.getMessage()), t);
					result.setOk(false);
					result.setSqlResult(t.getMessage());
					throw new SqlExecuteException(result, t);
				}
			}
		}
	}

}
