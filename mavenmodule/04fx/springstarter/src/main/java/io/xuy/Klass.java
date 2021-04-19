package io.xuy;

public class Klass {

    private StudentProperties studentProperties;

    public Klass(StudentProperties studentProperties) {
        this.studentProperties = studentProperties;
    }

    public void print() {
        System.out.println("kclass stid ="+this.studentProperties.getId()+"stname ="+this.studentProperties.getName());
    }

}
