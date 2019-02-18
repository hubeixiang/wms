package com.sven.wms.db.param.impl;

import com.sven.wms.db.param.DecorateParam;

import java.io.Serializable;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public class DecorateParamSql implements DecorateParam, Serializable {
    private static final long serialVersionUID = 1L;
    //要静态执行的sql
    private String staticSql;
    //执行结果
    private Object result;

    public String getStaticSql() {
        return staticSql;
    }

    public void setStaticSql(String staticSql) {
        this.staticSql = staticSql;
    }

    @Override
    public boolean isUsable() {
        return staticSql != null && !staticSql.equals("");
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
