package com.xinyongfei.demo;

import com.xinyongfei.demo.inherit.*;
import com.xinyongfei.demo.vote.Voter;

import java.util.ArrayList;


public class Main {
    static {
        age = 10;
        System.out.println("static block");
    }

    static int age;
    static int a = 1;

    {
        System.out.println("non-static block");
    }

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

    public static void test2() {
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

    public static void swap(Teacher a, Teacher b) {
        Teacher tmp = a;
        a = b;
        b = tmp;
        System.out.println("In swap " + a.name);
        System.out.println("In swap " + b.name);
    }

    public static void test3() {
        Person person1 = new Student();
        Person person2 = new Teacher();

        person1.say("student");
        person2.say("teacher");

        Student stu = (Student) person1;
        stu.study("math");
        System.out.println(person2 instanceof Teacher);

        // swap
        Teacher x = new Teacher("张三");
        Teacher y = new Teacher("李四", Gender.Male);
        System.out.println(x.name);
        System.out.println(x.gender);
        System.out.println(y.name);
        System.out.println(y.gender);
        swap(x, y);
        System.out.println(x.name);
        System.out.println(y.name);
    }

    public static void test4() {
        AbstractPerson ap = new Teacher();

        ap.say("I am a teacher");
    }

    public static void test5() {
        ArrayList<Teacher> staff = new ArrayList<>(10);

        staff.add(new Teacher("T1", 10000));
        staff.add(new Teacher("T2", 12000));
        staff.add(new Teacher("T3", 15000));

        for (Teacher t : staff) {
            t.raiseSalary(5);
        }

        for (Teacher t : staff) {
            System.out.println(t.toString());
        }
    }

    public static void test6() {
//        MyThread mt1 = new MyThread("mt1");
//        MyThread mt2 = new MyThread("mt2");
//        mt1.start();
//        mt2.start();

        MySecondThread mst1 = new MySecondThread("mst1");
        Thread t1 = new Thread(mst1, "窗口1"); // 静态代理
        Thread t2 = new Thread(mst1, "窗口2");
        Thread t3 = new Thread(mst1, "窗口3");
        t1.start();
        t2.start();
        t3.start();

//        for (int i=0; i<10; i++) {
//            System.out.println("main-"+i);
//        }
    }

    public static void main(String[] args) {
        System.out.println("start");
//        new Main();

//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
        test6();
    }
}
