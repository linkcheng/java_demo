package cn.xyf.log;

import cn.xyf.controller.HelloController;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestLog {
    @Test
    public void testLog() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloController helloController = context.getBean("helloController", HelloController.class);
        helloController.inter1();
        helloController.inter2();
    }
}
