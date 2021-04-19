package io.xuy;

public class School {
    
    private Klass klass;

    public School(Klass klass) {
        this.klass = klass;
    }

    public void ding(){
        System.out.println(klass);
    }

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }
}
