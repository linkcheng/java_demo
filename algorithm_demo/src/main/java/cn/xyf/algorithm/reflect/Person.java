package cn.xyf.algorithm.reflect;

public class Person implements User {
    public String name;

    public String getName() {
        return "Person";
    }

    public void hello() {
        System.out.println("Person hello");
    }
}