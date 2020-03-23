package cn.xyf.singleton;

import java.lang.reflect.Constructor;

/**
 * 懒汉式单例模式
 */
public class LazySingleton {
    private volatile static LazySingleton instance;

    private LazySingleton(){}

    /**
     * 线程不安全
     */
    public static LazySingleton getInstance1() {
        if(instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

    /**
     * 双重检测锁模式 DCL
     * volatile: 保证可见性，不保证原子性，禁止指令重排
     */
    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }

    /**
     * 道高一尺，魔高一丈
     * 通过反射破坏单例
     */
    public static LazySingleton newInstance() throws Exception {
        Constructor<LazySingleton> constructor = LazySingleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }
}
