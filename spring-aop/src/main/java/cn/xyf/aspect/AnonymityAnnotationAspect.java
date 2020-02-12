package cn.xyf.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AnonymityAnnotationAspect  {

    @Pointcut("execution(* cn.xyf.service.AnonymityUserServiceImpl.*(..))")
    public void aspect() {
    }

    @Before("aspect()")
    public void before1() {
        System.out.println("---------方法执行前11111111---------");
    }

    @Before("execution(* cn.xyf.service.AnonymityUserServiceImpl.*(..))")
    public void before3() {
        System.out.println("---------方法执行前33333333---------");
    }

    @Before("execution(* cn.xyf.service.AnonymityUserServiceImpl.*(..))")
    public void before2() {
        System.out.println("---------方法执行前22222222---------");
    }

    @After("execution(* cn.xyf.service.AnonymityUserServiceImpl.*(..))")
    public void after() {
        System.out.println("---------方法执行后---------");
    }

    @Around("execution(* cn.xyf.service.AnonymityUserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("============环绕前============");
        System.out.println("============签名:"+jp.getSignature()+"============");
        //执行目标方法proceed
        Object proceed = jp.proceed();
        System.out.println("============环绕后============");
        System.out.println(proceed);
    }
}
