package com.sven.wms.db.handler.xmltagls.custom;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.OgnlCache;

import java.util.List;

public class NumberMultipleBindingTokenParser extends NumberBindingTokenParser {
    public static GenericTokenParser createParser(TokenHandler handler) {
        return new GenericTokenParser("@n_m{", "}", handler);
    }

    public NumberMultipleBindingTokenParser(DynamicContext context) {
        super(context);
    }

    public String handleToken(String content) {
        processBindings(content);
        Object value = OgnlCache.getValue(content, context.getBindings());
        String sqlvalue = null;
        if (value == null) {
            sqlvalue = "";
        } else if (value instanceof List) {
            List list = (List) value;
            sqlvalue = processNumberList(list);
        } else {
            String v = String.valueOf(value);
            if (v.indexOf(",") != -1) {
                //有多个数值,使用逗号分隔
                String[] collections = v.split(",");
                sqlvalue = processNumberArray(collections);
            } else {
                sqlvalue = processNumberOne(value);
            }
        }
        InjectionSQLExaminationUtil.examination(sqlvalue);
        return sqlvalue;
    }
}
