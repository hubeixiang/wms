package com.sven.wms.db.service;

import com.sven.wms.db.param.impl.GenericDecorate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sven on 2017/3/27.
 */
@Service("genericDecorateService")
@Transactional
public class GenericDecorateService {

	@Autowired
    GenericService genericService;

    public void batchExecute(List<GenericDecorate> genericDecorates) {
        for (GenericDecorate genericDecorate : genericDecorates) {
            switch (genericDecorate.getOperation()) {
                case insert:
                    genericService.insertDynamicTableBatch(genericDecorate.getDataBaseName(), genericDecorate.getTargetTableName(), genericDecorate.getParamMapList());
                    break;
                case update:
                    genericService.updateDynamicTableBatch(genericDecorate.getDataBaseName(), genericDecorate.getTargetTableName(), genericDecorate.getParamMapList());
                    break;
                case delete:
                    genericService.deleteDynamicTableBatch(genericDecorate.getDataBaseName(), genericDecorate.getTargetTableName(), genericDecorate.getParamMapList());
                    break;
                case executeDynamicSelectInsert:
                    genericService.executeDynamicSelectInsertBatch(genericDecorate.getDataBaseName(), genericDecorate.getTargetTableName(), genericDecorate.getSourceTableName(), genericDecorate.getParamMapList());
                    break;
                case executeInsertSql:
                    genericService.executeInsertSql(genericDecorate.getDataBaseName(), genericDecorate.getParamMapList());
                    break;
                case executeUpdateSql:
                    genericService.executeUpdateSql(genericDecorate.getDataBaseName(), genericDecorate.getParamMapList());
                    break;
                case executeDeleteSql:
                    genericService.executeDeleteSql(genericDecorate.getDataBaseName(), genericDecorate.getParamMapList());
                    break;
                case selectCountSql:
                    genericService.selectCountSql(genericDecorate.getDataBaseName(), genericDecorate.getParamMapList());
                    break;
                case selectOneResult:
                    genericService.selectOneResult(genericDecorate.getDataBaseName(), genericDecorate.getParamMapList());
                    break;
                case query:
                    genericService.querySql(genericDecorate.getDataBaseName(), genericDecorate.getParamMapList());
                    break;
            }
        }
    }
}

