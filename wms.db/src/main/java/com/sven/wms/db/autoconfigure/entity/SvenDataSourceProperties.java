package com.sven.wms.db.autoconfigure.entity;

import com.sven.wms.db.autoconfigure.Constants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = Constants.DATA_SOURCE_PREFIX)
public class SvenDataSourceProperties {
    private Map<String, DataSourceConfig> db = new HashMap<>();

    public Map<String, DataSourceConfig> getDb() {
        return db;
    }

    public void setDb(Map<String, DataSourceConfig> db) {
        this.db = db;
    }
}
