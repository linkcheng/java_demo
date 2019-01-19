package com.xinyongfei.demo.inherit;

import javax.crypto.interfaces.PBEKey;

public class Student extends Person{

    public void say(String word) {
        System.out.println("In Student word: "+word);
    }

    public void study(String lesson) {
        System.out.println(lesson + " lesson");
    }
}
