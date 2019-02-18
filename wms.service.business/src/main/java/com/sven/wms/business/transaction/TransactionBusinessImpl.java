package com.sven.wms.business.transaction;

import com.sven.wms.db.configure.DBContextHelper;
import com.sven.wms.db.dao.mapper.GenericMapper;
import com.sven.wms.db.param.DecorateParam;
import com.sven.wms.db.param.impl.DecorateParamInsert;
import com.sven.wms.db.param.impl.VarableBind;
import com.sven.wms.db.service.GenericService;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
@Service
@Transactional
public class TransactionBusinessImpl implements TransactionBusiness {
	private Logger logger = LoggerFactory.getLogger(TransactionBusinessImpl.class);

	@Autowired
	private GenericService genericService;

	private String tableName = "test_transaction";

	private String exceptionName = "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN";

	@Override
	public int transaction(boolean exception) {
		String db = "coss";
		try {
			List<DecorateParam> inserts = new ArrayList<>();
			DecorateParamInsert insertDecorateParam = new DecorateParamInsert();
			inserts.add(insertDecorateParam);
			List<VarableBind> insertValues = new ArrayList<VarableBind>();
			insertDecorateParam.setInsertValues(insertValues);
			insertValues.add(new VarableBind("id", 1));
			if (exception) {
				insertValues.add(new VarableBind("name", "GenericService_Exception"));
			} else {
				insertValues.add(new VarableBind("name", "GenericService"));
			}

			genericService.insertDynamicTableBatch(db, tableName, inserts);

			GenericMapper genericMapper = DBContextHelper.getInstance().getMapperInterface(db, GenericMapper.class);
			if (exception) {
				genericMapper.executeSql(
						String.format("insert into %s (id,name) values (%s,'%s')", tableName, 2, "GenericMapper" + exceptionName));
			} else {
				genericMapper.executeSql(String.format("insert into %s (id,name) values (%s,'%s')", tableName, 2, "GenericMapper"));
			}

			SqlSessionTemplate sqlSessionTemplate = DBContextHelper.getInstance().getSqlSessionTemplate(db);
			String insertSqlId = "com.sven.wms.db.dao.mapper.TawType2stringMapper.insertTawType";
			if (exception) {
				sqlSessionTemplate.insert(insertSqlId,
						String.format("insert into %s (id,name) values (%s,'%s')", tableName, 3, "SqlSessionTemplate_Exception"));
			} else {
				sqlSessionTemplate.insert(insertSqlId,
						String.format("insert into %s (id,name) values (%s,'%s')", tableName, 3, "SqlSessionTemplate"));
			}
		} catch (RuntimeException e) {
			logger.error("transaction", e);
			throw e;
		}
		return 1;
	}

	@Override
	public int transactionXA(boolean exception) {
		String cossDb = "coss";
		String sdeDb = "sde";
		try {
			List<DecorateParam> inserts = new ArrayList<>();
			DecorateParamInsert insertDecorateParam = new DecorateParamInsert();
			inserts.add(insertDecorateParam);
			List<VarableBind> insertValues = new ArrayList<VarableBind>();
			insertDecorateParam.setInsertValues(insertValues);
			insertValues.add(new VarableBind("id", 10));
			if (exception) {
				insertValues.add(new VarableBind("name", "GenericService_XA_Exception"));
			} else {
				insertValues.add(new VarableBind("name", "GenericService_XA"));
			}

			genericService.insertDynamicTableBatch(cossDb, tableName, inserts);

			GenericMapper genericMapper = DBContextHelper.getInstance().getMapperInterface(sdeDb, GenericMapper.class);
			if (exception) {
				genericMapper.executeSql(
						String.format("insert into %s (id,name) values (%s,'%s')", tableName, 20, "GenericMapper_XA_Exception"));
			} else {
				genericMapper.executeSql(String.format("insert into %s (id,name) values (%s,'%s')", tableName, 20, "GenericMapper_XA"));
			}

			SqlSessionTemplate sqlSessionTemplate = DBContextHelper.getInstance().getSqlSessionTemplate(cossDb);
			String insertSqlId = "com.sven.wms.db.dao.mapper.TawType2stringMapper.insertTawType";
			if (exception) {
				sqlSessionTemplate.insert(insertSqlId,
						String.format("insert into %s (id,name) values (%s,'%s')", tableName, 30, "SqlSessionTemplate_XA_Exception"));
			} else {
				sqlSessionTemplate.insert(insertSqlId,
						String.format("insert into %s (id,name) values (%s,'%s')", tableName, 30, "SqlSessionTemplate_XA"));
			}

			if (exception) {
				genericMapper.executeSql(
						String.format("insert into %s (id,name) values (%s,'%s')", tableName, 40, "GenericMapper_XA" + exceptionName));
			} else {
				genericMapper.executeSql(String.format("insert into %s (id,name) values (%s,'%s')", tableName, 40, "GenericMapper_XA"));
			}
		} catch (RuntimeException e) {
			logger.error("transaction", e);
			throw e;
		}
		return 1;
	}
}
