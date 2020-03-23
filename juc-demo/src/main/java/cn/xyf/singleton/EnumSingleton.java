package cn.xyf.singleton;

import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 枚举实现单例模式
 */
public enum  EnumSingleton {
    INSTANCE;

    public static EnumSingleton getInstance() {
//        AtomicInteger
//        ReentrantLock
        AtomicStampedReference<int> a = new AtomicStampedReference<int>();
        return INSTANCE;
    }
}
