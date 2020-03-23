package cn.xyf;

import cn.xyf.config.AppConfig;
import cn.xyf.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService service = context.getBean("userServiceImpl", UserService.class);
        service.query();
    }
}
