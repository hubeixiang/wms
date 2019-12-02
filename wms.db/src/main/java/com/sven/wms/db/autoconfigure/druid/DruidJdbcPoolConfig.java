package com.sven.wms.db.autoconfigure.druid;

import com.sven.wms.db.autoconfigure.Constants;
import com.sven.wms.db.autoconfigure.entity.JdbcPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = Constants.JDBC_POOL_DRUID_PREFIX)
public class DruidJdbcPoolConfig implements JdbcPoolConfig {
}
