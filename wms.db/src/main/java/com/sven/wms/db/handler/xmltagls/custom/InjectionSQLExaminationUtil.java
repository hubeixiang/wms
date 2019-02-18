package com.sven.wms.db.handler.xmltagls.custom;

import org.apache.commons.lang3.StringUtils;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
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
