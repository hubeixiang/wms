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
public class DecorateParamInsert implements DecorateParam, Serializable {
    private static final long serialVersionUID = 1L;

    private List<VarableBind> insertValues;
    //执行结果
    private Object result;

    public Map<String, Object> convertToGenericDecorateParam() {
        Map<String, Object> insertLine = new HashMap<String, Object>();
        if (insertValues != null && insertValues.size() > 0) {
            for (VarableBind varableBind : insertValues) {
                insertLine.put(varableBind.getColumn(), varableBind.getValue());
            }
        }
        return insertLine;
    }

    public List<VarableBind> getInsertValues() {
        return insertValues;
    }

    public void setInsertValues(List<VarableBind> insertValues) {
        this.insertValues = insertValues;
    }

    @Override
    public boolean isUsable() {
        return insertValues != null && insertValues.size() > 0;
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
