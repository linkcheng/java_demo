import cn.xyf.service.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        System.out.println("read done");
        UserServiceImpl userService = (UserServiceImpl) context.getBean("serviceImpl");
        userService.getUser();
    }
}
