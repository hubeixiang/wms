package com.sven.wms.db.handler.xmltagls.custom;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.OgnlCache;
import org.apache.ibatis.type.SimpleTypeRegistry;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public class CommonBindingTokenParser implements TokenHandler {
    public static GenericTokenParser createParser(TokenHandler handler) {
        return new GenericTokenParser("@{", "}", handler);
    }

    private DynamicContext context;

    public CommonBindingTokenParser(DynamicContext context) {
        this.context = context;
    }

    public String handleToken(String content) {
        Object parameter = context.getBindings().get("_parameter");
        if (parameter == null) {
            context.getBindings().put("value", null);
        } else if (SimpleTypeRegistry.isSimpleType(parameter.getClass())) {
            context.getBindings().put("value", parameter);
        }
        Object value = OgnlCache.getValue(content, context.getBindings());
        String sqlvalue = "";
        if (value != null) {
            sqlvalue = String.valueOf(value);
        }
        InjectionSQLExaminationUtil.examination(sqlvalue);
        return sqlvalue;
    }
}
