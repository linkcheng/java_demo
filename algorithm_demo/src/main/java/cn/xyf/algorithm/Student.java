package cn.xyf.algorithm;

import java.lang.annotation.*;
import java.lang.reflect.Field;


@DBTable("db_student")
public class Student {
    @DBField(col_name = "stu_id", col_type = "int", col_length = 10)
    private int id;
    @DBField(col_name = "stu_name", col_type = "varchar", col_length = 32)
    private String name;
    @DBField(col_name = "stu_age", col_type = "int", col_length = 10)
    private int age;

    public Student() {}

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

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


    public static void main(String[] args) throws NoSuchFieldException {
        Student student = new Student(1, "张三", 18);

        Class c1 = student.getClass();

        DBTable dbTable = (DBTable)c1.getAnnotation(DBTable.class);
        System.out.println(dbTable.value());

        Field name = c1.getDeclaredField("name");
        DBField dbField = name.getAnnotation(DBField.class);
        System.out.println(dbField.col_name());

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
    int col_length();
}


