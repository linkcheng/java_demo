package com.xinyongfei.demo;

import com.xinyongfei.demo.inherit.Person;
import com.xinyongfei.demo.inherit.Student;
import com.xinyongfei.demo.inherit.Teacher;
import com.xinyongfei.demo.vote.Voter;

import java.security.PublicKey;


public class Main {

    static {
        age = 10;
    }

    static int age;
    static int a = 1;

    public static void method(int num) {
        System.out.println(age);
        age += a;
        System.out.println(age);
    }

    public static void test1() {
        int[] arr = new int[4];
        System.out.println(arr);
        System.out.println(arr[0]);
        System.out.println(arr[1]);
        System.out.println(arr[2]);

        Animal a = new Animal();
        a.name = "Doggie";
        a.color = "red";
        a.run();
    }

    public static void test2(String[] args) {
//        method(1);   7681636
//        test1();

        System.out.println("常量会变哦1："+Const.RAND_CONST);
        System.out.println("常量会变哦2："+Const.RAND_CONST);

        int count = 0;
        int x = count++;
        int y = count+=1;
        System.out.println(x);
        System.out.println(y);

        Voter v1 = new Voter("Hello");
        v1.vote();
        Voter.result();

        for(int i=1; i<101; i++) {
            Voter v = new Voter("Hello" + i);
            v.vote();
        }
        Voter.result();

    }

    public static void main(String[] args) {
        Person person1 = new Student();
        Person person2 = new Teacher();

        person1.say("student");
        person2.say("teacher");

        Student stu = (Student) person1;
        stu.study("math");
        System.out.println(person2 instanceof Teacher);
    }
}
