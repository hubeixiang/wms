package com.sven.wms.db.autoconfigure.entity;

import com.sven.wms.db.autoconfigure.Constants;
import com.sven.wms.db.autoconfigure.enums.JdbcPoolTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = Constants.DATA_SOURCE_NAMES_PREFIX)
public class DatasourceLoaderNames {
    private String names;
    //数据源适配类型
    private JdbcPoolTypeEnum poolType;

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public JdbcPoolTypeEnum getPoolType() {
        return poolType;
    }

    public void setPoolType(JdbcPoolTypeEnum poolType) {
        this.poolType = poolType;
    }
}
