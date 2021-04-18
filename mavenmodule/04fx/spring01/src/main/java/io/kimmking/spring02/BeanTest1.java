package io.kimmking.spring02;

import io.kimmking.spring01.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("io.kimmking.spring02")
public class BeanTest1 {
    @Autowired
    private Student student123;

    public String getStudentName(){
        return student123.getName();
    }

    @Bean
    public Student student101() {
        return Student.builder().id(101).name("student bean test 101").build();
    }
    public String getStudent101Name(){
        return student101().getName();
    }
}
