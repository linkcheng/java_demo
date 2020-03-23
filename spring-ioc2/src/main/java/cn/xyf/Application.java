package cn.xyf;

import cn.xyf.config.ApplicationConfig;
import cn.xyf.service.UserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UserServiceImpl service = context.getBean("userServiceImpl", UserServiceImpl.class);
        service.getUser();
    }
}
