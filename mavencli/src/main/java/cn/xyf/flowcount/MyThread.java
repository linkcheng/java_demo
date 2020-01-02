package cn.xyf.flowcount;

public class MyThread extends Thread{
    int count = 10;

    MyThread() {
        super("default");
    }

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
//        for (int i=0; i<10; i++) {
//            System.out.println(getName()+"-"+i);
//        }

        while (count>0){
            count--;
            System.out.println(getName()+"-count-"+count);
        }
    }
}
