package com.sven.wms.db.handler.xmltagls.custom;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.OgnlCache;
import org.apache.ibatis.type.SimpleTypeRegistry;

import java.util.List;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public class StringBindingTokenParser implements TokenHandler {
    public static GenericTokenParser createParser(TokenHandler handler) {
        return new GenericTokenParser("@s{", "}", handler);
    }

    protected DynamicContext context;

    public StringBindingTokenParser(DynamicContext context) {
        this.context = context;
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
            sqlvalue = processStringOne(value);
        }
        InjectionSQLExaminationUtil.examination(sqlvalue);
        return sqlvalue;
    }

    protected void processBindings(String content) {
        Object parameter = context.getBindings().get("_parameter");
        if (parameter == null) {
            context.getBindings().put("value", null);
        } else if (SimpleTypeRegistry.isSimpleType(parameter.getClass())) {
            context.getBindings().put("value", parameter);
        }
    }

    protected String processStringList(List collections) {
        if (collections == null || collections.size() == 0) {
            return "''";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < collections.size(); i++) {
                Object o = collections.get(i);
                if (i == 0) {
                    stringBuilder.append("'").append(String.valueOf(o)).append("'");
                } else {
                    stringBuilder.append(",'").append(String.valueOf(o)).append("'");
                }
            }
            return stringBuilder.toString();
        }
    }

    protected String processStringArray(String[] collections) {
        if (collections == null || collections.length == 0) {
            return "''";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < collections.length; i++) {
                Object o = collections[i];
                if (i == 0) {
                    stringBuilder.append("'").append(String.valueOf(o)).append("'");
                } else {
                    stringBuilder.append(",'").append(String.valueOf(o)).append("'");
                }
            }
            return stringBuilder.toString();
        }
    }

    protected String processStringOne(Object value) {
        String sqlvalue = String.valueOf(value);
        return String.format("%s%s%s", "'", sqlvalue, "'");
    }

}
