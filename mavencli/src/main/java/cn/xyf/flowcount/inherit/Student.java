package cn.xyf.flowcount.inherit;

public class Student extends Person{
    public Student() {

    }

    public Student(String name) {
        super(name);
    }

    public Student(String name, Gender gender) {
        super(name, gender);
    }

    public void say(String word) {
        System.out.println("In Student word: "+word);
    }

    public void study(String lesson) {
        System.out.println(lesson + " lesson");
    }
}
