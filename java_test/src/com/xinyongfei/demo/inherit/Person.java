package com.xinyongfei.demo.inherit;

public class Person extends AbstractPerson{
    public Person() {

    }

    public Person(String name) {
        this.name = name;
    }

    public void say(String word) {
        System.out.println("In Person word: "+word);
    }
}
