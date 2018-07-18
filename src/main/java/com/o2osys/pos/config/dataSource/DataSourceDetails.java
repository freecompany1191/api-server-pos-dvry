package com.o2osys.pos.config.dataSource;

import javax.sql.DataSource;

import org.springframework.transaction.PlatformTransactionManager;

public class DataSourceDetails {

    private final DataSource dataSource;
    private final PlatformTransactionManager platformTransactionManager;

    public DataSourceDetails(final DataSource dataSource, final PlatformTransactionManager platformTransactionManager) {
        this.dataSource = dataSource;
        this.platformTransactionManager = platformTransactionManager;
    }

    public static DataSourceDetails of(final DataSource dataSource, final PlatformTransactionManager platformTransactionManager) {
        return new DataSourceDetails(dataSource, platformTransactionManager);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public PlatformTransactionManager getPlatformTransactionManager() {
        return platformTransactionManager;
    }
}
