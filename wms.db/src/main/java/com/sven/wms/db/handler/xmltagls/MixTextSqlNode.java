package com.sven.wms.db.handler.xmltagls;

import com.sven.wms.db.handler.xmltagls.custom.CommonBindingTokenParser;
import com.sven.wms.db.handler.xmltagls.custom.MybatisBindingTokenParser;
import com.sven.wms.db.handler.xmltagls.custom.NumberBindingTokenParser;
import com.sven.wms.db.handler.xmltagls.custom.NumberMultipleBindingTokenParser;
import com.sven.wms.db.handler.xmltagls.custom.StringBindingTokenParser;
import com.sven.wms.db.handler.xmltagls.custom.StringMultipleBindingTokenParser;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;

public class MixTextSqlNode implements SqlNode {
    private String text;
    private boolean isMybatisParser = false;
    private boolean isCommonParser = false;
    private boolean isNumberParser = false;
    private boolean isNumberManyParser = false;
    private boolean isStringParser = false;
    private boolean isStringManyParser = false;

    public MixTextSqlNode(String text) {
        this.text = text;
    }

    public boolean isDynamic() {
        DynamicCheckerTokenParser myBatisChecker = new DynamicCheckerTokenParser();
        GenericTokenParser myBatisParser = MybatisBindingTokenParser.createParser(myBatisChecker);
        myBatisParser.parse(text);
        isMybatisParser = myBatisChecker.isDynamic();
        //custom common
        DynamicCheckerTokenParser commonChecker = new DynamicCheckerTokenParser();
        GenericTokenParser commonParser = CommonBindingTokenParser.createParser(commonChecker);
        commonParser.parse(text);
        isCommonParser = commonChecker.isDynamic();
        //custom number
        DynamicCheckerTokenParser numberChecker = new DynamicCheckerTokenParser();
        GenericTokenParser numberParser = NumberBindingTokenParser.createParser(numberChecker);
        numberParser.parse(text);
        isNumberParser = numberChecker.isDynamic();
        DynamicCheckerTokenParser numberManyChecker = new DynamicCheckerTokenParser();
        GenericTokenParser numberManyParser = NumberMultipleBindingTokenParser.createParser(numberManyChecker);
        numberManyParser.parse(text);
        isNumberManyParser = numberManyChecker.isDynamic();
        //custom string
        DynamicCheckerTokenParser stringChecker = new DynamicCheckerTokenParser();
        GenericTokenParser stringParser = StringBindingTokenParser.createParser(stringChecker);
        stringParser.parse(text);
        isStringParser = stringChecker.isDynamic();
        DynamicCheckerTokenParser stringManyChecker = new DynamicCheckerTokenParser();
        GenericTokenParser stringManyParser = StringMultipleBindingTokenParser.createParser(stringManyChecker);
        stringManyParser.parse(text);
        isStringManyParser = stringManyChecker.isDynamic();

        return isMybatisParser || isCommonParser || isNumberParser || isNumberManyParser || isStringParser || isStringManyParser;
    }

    @Override
    public boolean apply(DynamicContext context) {
        String newText = text;
        if (isMybatisParser) {
            GenericTokenParser myBatisParser = MybatisBindingTokenParser.createParser(new MybatisBindingTokenParser(context));
            newText = myBatisParser.parse(newText);
        }
        if (isCommonParser) {
            GenericTokenParser commonParser = CommonBindingTokenParser.createParser(new CommonBindingTokenParser(context));
            newText = commonParser.parse(newText);
        }
        if (isNumberParser) {
            GenericTokenParser numberParser = NumberBindingTokenParser.createParser(new NumberBindingTokenParser(context));
            newText = numberParser.parse(newText);
        }
        if (isNumberManyParser) {
            GenericTokenParser numberManyParser = NumberMultipleBindingTokenParser.createParser(new NumberMultipleBindingTokenParser(context));
            newText = numberManyParser.parse(newText);
        }
        if (isStringParser) {
            GenericTokenParser stringParser = StringBindingTokenParser.createParser(new StringBindingTokenParser(context));
            newText = stringParser.parse(newText);
        }
        if (isStringManyParser) {
            GenericTokenParser stringParser = StringMultipleBindingTokenParser.createParser(new StringMultipleBindingTokenParser(context));
            newText = stringParser.parse(newText);
        }
        context.appendSql(newText);
        return true;
    }

    private static class DynamicCheckerTokenParser implements TokenHandler {

        private boolean isDynamic;

        public boolean isDynamic() {
            return isDynamic;
        }

        public String handleToken(String content) {
            this.isDynamic = true;
            return null;
        }
    }
}
