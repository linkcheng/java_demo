package cn.xyf.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect  {
    @Pointcut("@annotation(cn.xyf.aspect.LogReport)")
    public void aspect() {}

    @Before("aspect()")
    public void before() {
        System.out.println("---------方法执行前---------");
    }

    @After("aspect()")
    public void after() {
        System.out.println("---------方法执行后---------");
    }

    @Around("aspect()")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        long begin = System.currentTimeMillis();
        // 执行目标方法proceed
        Object proceed = jp.proceed();
        long end = System.currentTimeMillis();
        System.out.println(jp.getSignature()+"执行时长="+(end-begin));
        return proceed;
    }
}