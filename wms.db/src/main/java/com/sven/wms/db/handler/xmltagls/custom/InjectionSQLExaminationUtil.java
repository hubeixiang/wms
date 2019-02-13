package com.sven.wms.db.handler.xmltagls.custom;

import org.apache.commons.lang3.StringUtils;

public class InjectionSQLExaminationUtil {
    public static void examination(String sqlvalue) {
        if (StringUtils.isEmpty(sqlvalue)) {
            return;
        }
        String newsql = sqlvalue.toLowerCase();
        if (newsql.indexOf("select ") != -1) {
            throw new RuntimeException(String.format("please check configure.parameter error:%s", sqlvalue));
        }
    }
}
