package cn.xyf.algorithm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Check {
    int value() default 99;
    int min() default 0;
    int max() default 100;
}



class Student {
    private int id;
    private String name;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface DBTable{
    String value();
}


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface DBField{
    String col_name();
    String col_type();
    String 
}


