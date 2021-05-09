package io.xuy.dds.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RountingMultiDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceTypeHolder.get();
    }
}
