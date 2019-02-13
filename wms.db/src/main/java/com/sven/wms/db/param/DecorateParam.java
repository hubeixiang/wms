package com.sven.wms.db.param;

public interface DecorateParam {
    public boolean isUsable();

    public Object getExecuteResult();

    public void setExecuteResult(Object result);
}
