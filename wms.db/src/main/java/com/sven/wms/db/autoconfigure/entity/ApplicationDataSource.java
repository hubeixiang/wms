package com.sven.wms.db.autoconfigure.entity;

public class ApplicationDataSource {
    private DatasourceLoaderNames datasourceLoaderNames;

    private SvenDataSourceProperties svenDataSourceProperties;

    private JdbcPoolConfig jdbcPoolConfig;

    public ApplicationDataSource(DatasourceLoaderNames datasourceLoaderNames, SvenDataSourceProperties svenDataSourceProperties, JdbcPoolConfig jdbcPoolConfig) {
        this.datasourceLoaderNames = datasourceLoaderNames;
        this.svenDataSourceProperties = svenDataSourceProperties;
        this.jdbcPoolConfig = jdbcPoolConfig;
    }

    public DatasourceLoaderNames getDatasourceLoaderNames() {
        return datasourceLoaderNames;
    }

    public SvenDataSourceProperties getSvenDataSourceProperties() {
        return svenDataSourceProperties;
    }

    public JdbcPoolConfig getJdbcPoolConfig() {
        return jdbcPoolConfig;
    }
}
