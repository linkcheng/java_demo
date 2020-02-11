package cn.xyf.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UserServiceProxyInvocationHandler implements InvocationHandler {
    private Object object;

    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * 获取代理
     */
    public Object getProxy() {
        return Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log(method.getName());
        return method.invoke(object, args);
    }

    public void log(String msg) {
        System.out.print("[Debug] " + msg + " | ");
    }
}
