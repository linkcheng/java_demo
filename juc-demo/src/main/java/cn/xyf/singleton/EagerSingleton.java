package cn.xyf.singleton;

/**
 * 饿汉式单例模式，缺点浪费内存
 */
public class EagerSingleton {
    private final static EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() {}

    public static EagerSingleton getInstance() {
        return instance;
    }
}
