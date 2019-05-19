package cn.xinyongfei.flowcount.inherit;

public class Teacher extends Person{
    private double salary;
//    private LocalDate hireday;

    public Teacher() {

    }

    public Teacher(String name) {
        super(name);
    }

    public Teacher(String name, Gender gender) {
        super(name, gender);
    }

    public Teacher(String name, double salary) {
        super(name);
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void say(String word) {
        System.out.println("In Teacher word: "+word);
    }

    public void work(String work) {
        System.out.println(work + " work");
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    @Override
    public String toString() {
        return "Teacher[name="+name+", salary="+salary+"]";
    }
}
