//package io.xuy.dds.config;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.aop.Advisor;
//import org.springframework.aop.aspectj.AspectJExpressionPointcut;
//import org.springframework.aop.support.DefaultPointcutAdvisor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionManager;
//import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
//import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
//import org.springframework.transaction.interceptor.TransactionAttribute;
//import org.springframework.transaction.interceptor.TransactionInterceptor;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * AOP开启全局事务控制
// */
//@Aspect
//@Configuration
//public class TransactionAdviceConfig {
//    /**
//     * 定义一个切点，这里指api包下所有的类的方法
//     */
//    private static final String AOP_POINTCUT_EXPRESSION = "execution (* io.xuy.dds.dao..*.*(..))";
//
//    @Autowired
//    private TransactionManager transactionManager;
//
//    /**
//     * 事务拦截器
//     * @return
//     */
//    @Bean
//    public TransactionInterceptor txAdvice() {
//
//        DefaultTransactionAttribute txAttr_REQUIRED = new DefaultTransactionAttribute();
//        txAttr_REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//
//        //只读事务，不做更新操作
//        DefaultTransactionAttribute txAttr_REQUIRED_READONLY = new DefaultTransactionAttribute();
//        txAttr_REQUIRED_READONLY.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        txAttr_REQUIRED_READONLY.setReadOnly(true);
//
//        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
//        Map<String, TransactionAttribute> txMap = new HashMap<>();
//
//        //配置事务方法的前缀,注意如果方法前缀不在这里面是不会执行事务的
//        txMap.put("*", txAttr_REQUIRED);
////        txMap.put("add*", txAttr_REQUIRED);
////        txMap.put("save*", txAttr_REQUIRED);
////        txMap.put("insert*", txAttr_REQUIRED);
////        txMap.put("create*", txAttr_REQUIRED);
////        txMap.put("delete*", txAttr_REQUIRED);
////        txMap.put("update*", txAttr_REQUIRED);
////        txMap.put("modify*", txAttr_REQUIRED);
////        txMap.put("batch*", txAttr_REQUIRED);
////        txMap.put("cancel*", txAttr_REQUIRED);
////        txMap.put("exec*", txAttr_REQUIRED);
////        txMap.put("set*", txAttr_REQUIRED);
//        //配置只读事务方法的前缀
//        txMap.put("get*", txAttr_REQUIRED_READONLY);
//        txMap.put("query*", txAttr_REQUIRED_READONLY);
//        txMap.put("find*", txAttr_REQUIRED_READONLY);
//        txMap.put("select*", txAttr_REQUIRED_READONLY);
//        txMap.put("list*", txAttr_REQUIRED_READONLY);
//        txMap.put("count*", txAttr_REQUIRED_READONLY);
//        txMap.put("is*", txAttr_REQUIRED_READONLY);
//        source.setNameMap(txMap);
//        ((JpaTransactionManager)transactionManager).setGlobalRollbackOnParticipationFailure(false);
//
//        return new TransactionInterceptor(transactionManager, source);
//    }
//
//    /**
//     * 注册事务
//     * @return
//     */
//    @Bean
//    public Advisor txAdviceAdvisor() {
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
//        return new DefaultPointcutAdvisor(pointcut, txAdvice());
//    }
//}
