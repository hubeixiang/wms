package com.sven.wms.db.autoconfigure.atomikos;

import com.sven.wms.db.autoconfigure.Constants;
import com.sven.wms.db.autoconfigure.entity.JdbcPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = Constants.JDBC_POOL_ATOMIKOS_PREFIX)
public class AtomikosJdbcPoolConfig implements JdbcPoolConfig {
}
