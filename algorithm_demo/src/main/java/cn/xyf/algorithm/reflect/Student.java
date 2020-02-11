package cn.xyf.algorithm.reflect;

public class Student extends Person {
    public int score;
    private int grade;

    public Student() {
        this.score = 100;
        this.grade = 1;
    }

    public Student(int score, int grade) {
        this.score = score;
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    private int getScore() {
        return score;
    }

    private void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "score=" + score +
                ", grade=" + grade +
                '}';
    }

    public void hello() {
        System.out.println("Student hello");
    }
}