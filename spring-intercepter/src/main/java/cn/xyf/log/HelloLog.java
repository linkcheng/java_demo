package cn.xyf.log;


import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HelloLog {
    @Pointcut("execution(* cn.xyf.controller.*.*(..))")
    public void pointCut() {}

    @Before("pointCut()")
    public void before() {
        System.out.println("============before========");
    }

    @After("pointCut()")
    public void after() {
        System.out.println("============after========");
    }

    @AfterReturning("pointCut()")
    public void afterReturning() {
        System.out.println("============afterReturning========");
    }
}
