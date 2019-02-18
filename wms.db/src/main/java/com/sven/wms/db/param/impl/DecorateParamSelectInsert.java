package com.sven.wms.db.param.impl;

import com.sven.wms.db.param.DecorateParam;

import java.io.Serializable;
import java.util.List;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public class DecorateParamSelectInsert implements DecorateParam, Serializable {
    private static final long serialVersionUID = 1L;
    //查询的主键变量
    private List<VarableBind> whereKeys;
    //查询的条件常量
    private String whereConstans;
    //需要备份的字段
    private List<ColumnMapping> backupColumns;
    //执行结果
    private Object result;

    public List<VarableBind> getWhereKeys() {
        return whereKeys;
    }

    public void setWhereKeys(List<VarableBind> whereKeys) {
        this.whereKeys = whereKeys;
    }

    public String getWhereConstans() {
        return whereConstans;
    }

    public void setWhereConstans(String whereConstans) {
        this.whereConstans = whereConstans;
    }

    public List<ColumnMapping> getBackupColumns() {
        return backupColumns;
    }

    public void setBackupColumns(List<ColumnMapping> backupColumns) {
        this.backupColumns = backupColumns;
    }

    @Override
    public boolean isUsable() {
        return backupColumns != null && backupColumns.size() > 0;
    }

    @Override
    public Object getExecuteResult() {
        return result;
    }

    @Override
    public void setExecuteResult(Object result) {
        this.result = result;
    }
}
