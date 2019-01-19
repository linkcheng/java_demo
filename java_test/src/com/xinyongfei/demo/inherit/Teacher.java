package com.xinyongfei.demo.inherit;

public class Teacher extends Person{
    public void say(String word) {
        System.out.println("In Teacher word: "+word);
    }

    public void work(String work) {
        System.out.println(work + " work");
    }
}
