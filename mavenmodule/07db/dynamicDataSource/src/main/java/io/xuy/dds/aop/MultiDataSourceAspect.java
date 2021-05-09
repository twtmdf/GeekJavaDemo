package io.xuy.dds.aop;

import io.xuy.dds.config.DataSourceTypeHolder;
import io.xuy.dds.config.MultipleDateSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@Order(1)
public class MultiDataSourceAspect {

    @Before("execution(* io.xuy.dds.publish.service.*.*(..))")
    public void publishDataSource(JoinPoint joinPoint) throws Throwable {
        log.debug("use publish master data source");
        DataSourceTypeHolder.set(MultipleDateSourceConfig.PUBLISH);
    }

    @Before("execution(* io.xuy.dds.publish.slave.service.*.*(..))")
    public void publishSlaveDataSource(JoinPoint joinPoint) throws Throwable {
        log.debug("use publish slave data source");
        DataSourceTypeHolder.set(MultipleDateSourceConfig.PUBLISH_SLAVE);
    }
//可以不设置，默认为离线库
//    @Before("execution(* io.xuy.dds.offline.service.*.*(..))")
//    public void offlineDataSource(JoinPoint joinPoint) throws Throwable {
//        DataSourceTypeHolder.set(DataSourceType.OFFLINE);
//    }

    @After("execution(* io.xuy.dds.publish.service.*.*(..)) ||" +
           "execution(* io.xuy.dds.publish.slave.service.*.*(..))")
    public void removeHolder(JoinPoint joinPoint) throws Throwable {
        log.debug("remove data source");
        DataSourceTypeHolder.remove();
    }
}
