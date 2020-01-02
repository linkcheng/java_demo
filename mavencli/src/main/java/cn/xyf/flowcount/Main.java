package cn.xyf.flowcount;

import cn.xyf.flowcount.inherit.*;
import cn.xyf.flowcount.vote.Voter;
import sun.jvm.hotspot.runtime.Bytes;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
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

    public static void swap(cn.xyf.flowcount.inherit.Teacher a, cn.xyf.flowcount.inherit.Teacher b) {
        cn.xyf.flowcount.inherit.Teacher tmp = a;
        a = b;
        b = tmp;
        System.out.println("In swap " + a.name);
        System.out.println("In swap " + b.name);
    }

    public static void test3() {
        cn.xyf.flowcount.inherit.Person person1 = new cn.xyf.flowcount.inherit.Student();
        cn.xyf.flowcount.inherit.Person person2 = new cn.xyf.flowcount.inherit.Teacher();

        person1.say("student");
        person2.say("teacher");

        cn.xyf.flowcount.inherit.Student stu = (cn.xyf.flowcount.inherit.Student) person1;
        stu.study("math");
        System.out.println(person2 instanceof cn.xyf.flowcount.inherit.Teacher);

        // swap
        cn.xyf.flowcount.inherit.Teacher x = new cn.xyf.flowcount.inherit.Teacher("张三");
        cn.xyf.flowcount.inherit.Teacher y = new cn.xyf.flowcount.inherit.Teacher("李四", cn.xyf.flowcount.inherit.Gender.Male);
        System.out.println(x.name);
        System.out.println(x.gender);
        System.out.println(y.name);
        System.out.println(y.gender);
        swap(x, y);
        System.out.println(x.name);
        System.out.println(y.name);
    }

    public static void test4() {
        cn.xyf.flowcount.inherit.AbstractPerson ap = new cn.xyf.flowcount.inherit.Teacher();

        ap.say("I am a teacher");
    }

    public static void test5() {
        ArrayList<cn.xyf.flowcount.inherit.Teacher> staff = new ArrayList<cn.xyf.flowcount.inherit.Teacher>(10);

        staff.add(new cn.xyf.flowcount.inherit.Teacher("T1", 10000));
        staff.add(new cn.xyf.flowcount.inherit.Teacher("T2", 12000));
        staff.add(new cn.xyf.flowcount.inherit.Teacher("T3", 15000));

        for (cn.xyf.flowcount.inherit.Teacher t : staff) {
            t.raiseSalary(5);
        }

        for (cn.xyf.flowcount.inherit.Teacher t : staff) {
            System.out.println(t.toString());
        }
    }

    public static void test6() {
//        MyThread mt1 = new MyThread("mt1");
//        MyThread mt2 = new MyThread("mt2");
//        mt1.start();
//        mt2.start();

        cn.xyf.flowcount.MySecondThread mst1 = new cn.xyf.flowcount.MySecondThread("mst1");
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
//        test6();
        String charset = "utf-8";
        String text = "文件内容";
        try {
            byte[] bt = text.getBytes(charset);
            System.out.println(bt[0]);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
