package io.xuy.dds;

import io.xuy.dds.offline.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@ComponentScan(basePackages = {"io.xuy.dds"})
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableConfigurationProperties
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DynamicDataSourceApplication {

	public static void main(String[] args) throws SQLException {
//		SpringApplication.run(DynamicDataSourceApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(DynamicDataSourceApplication.class, args);
        DataSource ds = context.getBean(DataSource.class);
        log.info("ds name :"+ds.getClass().getName()); //默认的使用的是tomcat的数据源
        Connection connection = ds.getConnection();
        log.info("connection catalog :"+connection.getCatalog());
        log.info("jdbctemplate"+ context.getBean(JdbcTemplate.class));
        connection.close();
        log.info("offline bean {}",context.getBean(OrderInfoService.class));
        log.info("publish bean {}",context.getBean(io.xuy.dds.publish.service.OrderInfoService.class));
        log.info("publish slave bean {}",context.getBean(io.xuy.dds.publish.slave.service.OrderInfoService.class));

	}

}
