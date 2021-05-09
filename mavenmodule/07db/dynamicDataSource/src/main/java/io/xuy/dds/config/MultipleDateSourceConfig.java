package io.xuy.dds.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class MultipleDateSourceConfig {
    public static final String OFFLINE = "OFFLINE";
    public static final String PUBLISH = "PUBLISH";
    public static final String PUBLISH_SLAVE = "PUBLISH_SLAVE";
    /**
     * 发布库主库(写库)
     * @return
     */
    @Bean("dataSourcePublish")
    @ConfigurationProperties(prefix = "spring.datasource.publish")
    public DataSource createMasterDataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    /**
     * 发布库从库(读库)
     * @return
     */
    @Bean("dataSourcePublishSlave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource createslaveDataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    /**
     * 离线库主库
     * @return
     */
    @Bean("dataSourceOffline")
    @ConfigurationProperties(prefix = "spring.datasource.offline")
    public DataSource createofflineDataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
    /**
     * 设置动态数据源，通过@Primary 来确定主DataSource
     * @return
     */
    @Bean("dataSource")
    @Primary
    public DataSource createDynamicdataSource(@Qualifier("dataSourcePublish") DataSource master, @Qualifier("dataSourcePublishSlave") DataSource slave, @Qualifier("dataSourceOffline") DataSource offline){
        RountingMultiDataSource dynamicDataSource = new RountingMultiDataSource();
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(offline);
        //配置多数据源
        Map<Object, Object> map = new HashMap<>();
        map.put(PUBLISH,master);
        map.put(PUBLISH_SLAVE,slave);
        map.put(OFFLINE,offline);
        dynamicDataSource.setTargetDataSources(map);
        return  dynamicDataSource;
    }
}
