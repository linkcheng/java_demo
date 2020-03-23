package cn.xyf.singleton;

/**
 * 内部静态创建单例模式
 */
public class StaticInnerSingleton {
    private StaticInnerSingleton() {}

    public StaticInnerSingleton getInstance() {
        return Inner.instance;
    }

    public static class Inner {
        private static final StaticInnerSingleton instance = new StaticInnerSingleton();
    }
}
