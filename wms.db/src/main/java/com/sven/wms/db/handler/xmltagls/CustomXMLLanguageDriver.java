package com.sven.wms.db.handler.xmltagls;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public class CustomXMLLanguageDriver extends XMLLanguageDriver {
    public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
        CustomXMLScriptBuilder builder = new CustomXMLScriptBuilder(configuration, script, parameterType);
        return builder.parseScriptNode();
    }
}
