package cn.xyf.flowcount;

public class MySecondThread implements Runnable {
    private String name;
    int count = 50;

    MySecondThread() {
        name="default";
    }

    public MySecondThread(String name) {
        this.name=name;
    }

    @Override
    public void run() {
//        for (int i=0; i<10; i++) {
//            System.out.println(name+"-"+i);
//        }

        run1();
//        run2();
    }

    public void run1() {
        while (count>0){
            synchronized (this) {
                if(count>0) {
                    count--;
                    System.out.println(Thread.currentThread().getName() + "-count:" + count);
                }
            }
        }
    }

    public void run2() {
        while (count>0) {
            sub();
        }
    }

    public synchronized void sub() {
        if(count>0) {
            count--;
            System.out.println(Thread.currentThread().getName() + "-count:" + count);
        }
    }
}
