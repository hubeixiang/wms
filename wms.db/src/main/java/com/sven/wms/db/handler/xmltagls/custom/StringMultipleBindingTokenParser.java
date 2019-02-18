package com.sven.wms.db.handler.xmltagls.custom;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.OgnlCache;

import java.util.List;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public class StringMultipleBindingTokenParser extends StringBindingTokenParser {
    public static GenericTokenParser createParser(TokenHandler handler) {
        return new GenericTokenParser("@s_m{", "}", handler);
    }

    public StringMultipleBindingTokenParser(DynamicContext context) {
        super(context);
    }

    public String handleToken(String content) {
        processBindings(content);
        Object value = OgnlCache.getValue(content, context.getBindings());
        String sqlvalue = null;
        if (value == null) {
            sqlvalue = "''";
        } else if (value instanceof List) {
            List list = (List) value;
            sqlvalue = processStringList(list);
        } else {
            String v = String.valueOf(value);
            if (v.indexOf(",") != -1) {
                //有多个数值,使用逗号分隔
                String[] collections = v.split(",");
                sqlvalue = processStringArray(collections);
            } else {
                sqlvalue = processStringOne(value);
            }
        }
        InjectionSQLExaminationUtil.examination(sqlvalue);
        return sqlvalue;
    }
}
