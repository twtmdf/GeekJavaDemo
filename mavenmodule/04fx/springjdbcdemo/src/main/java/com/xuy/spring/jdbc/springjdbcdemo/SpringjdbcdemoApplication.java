package com.xuy.spring.jdbc.springjdbcdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@EnableAspectJAutoProxy(exposeProxy = true)
@EnableTransactionManagement
@SpringBootApplication
public class SpringjdbcdemoApplication {

	public static void main(String[] args) throws SQLException {
//        SpringApplication.run(SpringjdbcdemoApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(SpringjdbcdemoApplication.class, args);
        DataSource ds = context.getBean(DataSource.class);
        System.out.println(ds.getClass().getName()); //默认的使用的是tomcat的数据源
        Connection connection = ds.getConnection();
        System.out.println(connection.getCatalog());
        System.out.println(context.getBean(JdbcTemplate.class));
        connection.close();
	}

}
