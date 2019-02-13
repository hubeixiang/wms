package com.sven.wms.db.param.impl;

import java.io.Serializable;

public class ColumnMapping implements Serializable {
    private static final long serialVersionUID = 1L;

    private String targetColumn;
    //原字段名称,或者是某个sql片段.如果是sql片段,配置为
    private String sourceColumn;
    private boolean isSqlValue;

    public ColumnMapping(String targetColumn, String sourceColumn) {
        this(targetColumn, sourceColumn, false);
    }

    public ColumnMapping(String targetColumn, String sourceColumn, boolean isSqlValue) {
        this.targetColumn = targetColumn;
        this.sourceColumn = sourceColumn;
        this.isSqlValue = isSqlValue;
    }

    public String getTargetColumn() {
        return targetColumn;
    }

    public String getSourceColumn() {
        return sourceColumn;
    }

    public boolean isSqlValue() {
        return isSqlValue;
    }

    public String formatSourceColumn() {
        if (isSqlValue()) {
            return String.format("%s%s", DecorateConstans.SQLVALUE_CONSTANS, getSourceColumn());
        } else {
            return sourceColumn;
        }
    }
}
