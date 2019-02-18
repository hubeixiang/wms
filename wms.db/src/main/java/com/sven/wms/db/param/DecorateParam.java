package com.sven.wms.db.param;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public interface DecorateParam {
    public boolean isUsable();

    public Object getExecuteResult();

    public void setExecuteResult(Object result);
}
