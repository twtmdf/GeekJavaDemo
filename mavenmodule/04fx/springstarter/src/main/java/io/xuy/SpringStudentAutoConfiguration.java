package io.xuy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableConfigurationProperties(StudentProperties.class)
@ConditionalOnClass(School.class)
public class SpringStudentAutoConfiguration {
    @Autowired
    private StudentProperties studentProperties;

    @Bean
    @ConditionalOnMissingBean(School.class)
//    @ConditionalOnProperty(prefix = "io.school",value = "enabled", havingValue = "true")
    public School school() {
        return new School(new Klass(studentProperties));
    }

}
