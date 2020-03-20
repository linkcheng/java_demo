package cn.xyf.pojo;


import cn.xyf.config.Column;
import cn.xyf.config.Table;


@Table(name = "student")
public class Student {
    @Column(name = "id", type = "int", length = 10)
    private int id;
    @Column(name = "name", type = "varchar", length = 32)
    private String name;
    @Column(name = "age", type = "int", length = 10)
    private int age;

    public Student(){}

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
}
