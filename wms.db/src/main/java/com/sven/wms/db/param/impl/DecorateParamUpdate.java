package com.sven.wms.db.param.impl;

import com.sven.wms.db.param.DecorateParam;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public class DecorateParamUpdate implements DecorateParam, Serializable {
    private static final long serialVersionUID = 1L;
    //更新的主键变量
    private List<VarableBind> whereKeys;
    //更新的条件常量
    private String whereConstans;
    //需要更新的值
    private List<VarableBind> updateValues;
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
        if (updateValues != null && updateValues.size() > 0) {
            for (VarableBind varableBind : updateValues) {
                updateLine.put(varableBind.getColumn(), varableBind.getValue());
            }
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

    public List<VarableBind> getUpdateValues() {
        return updateValues;
    }

    public void setUpdateValues(List<VarableBind> updateValues) {
        this.updateValues = updateValues;
    }

    @Override
    public boolean isUsable() {
        return updateValues != null && updateValues.size() > 0;
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
