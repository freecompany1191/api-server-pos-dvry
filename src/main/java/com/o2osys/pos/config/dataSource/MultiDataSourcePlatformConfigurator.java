package com.o2osys.pos.config.dataSource;

public interface MultiDataSourcePlatformConfigurator {

    /**
     * Add {@link DataSourceDetails}
     *
     * @param key
     * @param dataSourceDetails
     */
    void addDetails(String key, DataSourceDetails dataSourceDetails);

    /**
     * Retrieve {@link DataSourceDetails}
     *
     * @param key
     * @return
     */
    DataSourceDetails getDetails(String key);
}
