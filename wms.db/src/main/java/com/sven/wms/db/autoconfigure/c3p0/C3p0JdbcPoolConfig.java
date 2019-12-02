package com.sven.wms.db.autoconfigure.c3p0;

import com.sven.wms.db.autoconfigure.Constants;
import com.sven.wms.db.autoconfigure.entity.JdbcPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = Constants.JDBC_POOL_C3P0_PREFIX)
public class C3p0JdbcPoolConfig implements JdbcPoolConfig {
}
