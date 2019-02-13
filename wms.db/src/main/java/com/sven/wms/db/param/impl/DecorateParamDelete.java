package com.sven.wms.db.param.impl;

import com.sven.wms.db.param.DecorateParam;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecorateParamDelete implements DecorateParam, Serializable {
    private static final long serialVersionUID = 1L;
    //删除的主键变量
    private List<VarableBind> whereKeys;
    //更新的条件常量
    private String whereConstans;
    //执行结果
    private Object result;

    public Map<String, Object> convertToGenericDecorateParam() {
        Map<String, Object> updateLine = new HashMap<String, Object>();
        if (whereKeys != null && whereKeys.size() > 0) {
            updateLine.put(DecorateConstans.UPDATE_PK, whereKeys);
        }
        if (whereConstans != null && !whereConstans.equals("")) {
            updateLine.put(DecorateConstans.WHERE_CONSTANS, whereConstans);
        }
        return updateLine;
    }

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

    @Override
    public boolean isUsable() {
        return true;
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
