package cn.xyf.log;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log implements MethodBeforeAdvice, AfterReturningAdvice {

    /**
     *
     * @param method method being invoked
     * @param args arguments to the method
     * @param target target of the method invocation. May be {@code null}.
     * @throws Throwable
     */
    public void before(Method method, Object[] args, Object target) throws Throwable {
        printLog("Before "+method.getName());
    }

    /**
     *
     * @param returnValue the value returned by the method, if any
     * @param method method being invoked
     * @param args arguments to the method
     * @param target target of the method invocation. May be {@code null}.
     * @throws Throwable
     */
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        printLog("After "+method.getName());
    }

    private void printLog(String msg) {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cnt = String.format("[%s] | [%s]", format.format(now), msg);
        System.out.println(cnt);
    }
}
