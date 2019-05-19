package cn.xinyongfei.flowcount.inherit;

public class Person extends AbstractPerson{
    public Person() {

    }

    public Person(String name) {
        this.name = name;
        this.gender = Gender.Female;
    }

    public Person(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void say(String word) {
        System.out.println("In Person word: "+word);
    }
}
