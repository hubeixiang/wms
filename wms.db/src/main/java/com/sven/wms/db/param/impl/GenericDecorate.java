package com.sven.wms.db.param.impl;

import com.sven.wms.db.param.DecorateParam;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sven on 2017/3/27.
 */
public class GenericDecorate implements Serializable {
    private static final long serialVersionUID = 1L;
    private String targetTableName;
    private String sourceTableName;
    private String dataBaseName;
    //需要动态拼接的值
    private List<DecorateParam> paramMapList;
    private OperationMethond operation;

    public GenericDecorate(String targetTableName, String dataBaseName, GenericDecorate.OperationMethond operation) {
        this.targetTableName = targetTableName;
        this.dataBaseName = dataBaseName;
        this.operation = operation;
    }

    //特殊的执行,查询一个表的数据插入另外一个表中insert targetTableName (x,y) select x,y from sourceTableName where ???
    public GenericDecorate(String targetTableName, String sourceTableName, String dataBaseName) {
        this.targetTableName = targetTableName;
        this.sourceTableName = sourceTableName;
        this.dataBaseName = dataBaseName;
        this.operation = GenericDecorate.OperationMethond.executeDynamicSelectInsert;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public String getSourceTableName() {
        return sourceTableName;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public OperationMethond getOperation() {
        return operation;
    }

    public List<DecorateParam> getParamMapList() {
        return paramMapList;
    }

    public void setParamMapList(List<DecorateParam> paramMapList) {
        this.paramMapList = paramMapList;
    }

    public enum OperationMethond {
        //insertDynamicTableBatch
        insert,
        //updateDynamicTableBatch
        update,
        //deleteDynamicTableBatch
        delete,
        //executeDynamicUpdate
        executeDynamicSelectInsert,
        //querySql
        query,
        //executeInsertSql
        executeInsertSql,
        //executeUpdateSql
        executeUpdateSql,
        //executeDeleteSql
        executeDeleteSql,
        //selectCountSql
        selectCountSql,
        //selectOneResult
        selectOneResult
    }
}
