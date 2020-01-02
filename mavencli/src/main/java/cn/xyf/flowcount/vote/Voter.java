package cn.xyf.flowcount.vote;

public class Voter {
    static final int TOTAL = 100;
    static int count = 0;
    private String name;

    public Voter() {}

    public Voter(String name) {
        this.name = name;
    }

    public void vote() {
        if (count >= TOTAL) {
            System.out.println("Over");
        } else {
            System.out.println(this.name+" votes successfully");
            count ++;
        }
    }

    public static void result() {
        System.out.println("Current vote result is " + count);
    }
}
