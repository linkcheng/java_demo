import cn.xyf.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LogTest {
    @Test
    public void testUserService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 方法一：spring api, advisor 环绕
        UserService userService = (UserService) context.getBean("userService");
        userService.query();

        // 方法二：自定义 aspect 切面类
        UserService superUserService = (UserService) context.getBean("superUserService");
        superUserService.query();

        // 方式三：注解
        UserService anonymityUserService = (UserService) context.getBean("anonymityUserService");
        anonymityUserService.add();

    }
}
