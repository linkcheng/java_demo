import cn.xyf.*;
import cn.xyf.dao.UserDaoMongoImpl;
import cn.xyf.service.SuperuserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {

    @Test
    public void testHello() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Hello hello = (Hello) context.getBean("hello");
        System.out.println(hello.toString());
    }

    @Test
    public void testNode() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Node node = context.getBean("node", Node.class);
        System.out.println(node.toString());
    }

    @Test
    public void testDao() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserDaoMongoImpl dao = context.getBean("mongo", UserDaoMongoImpl.class);
        dao.getUser();
    }

    @Test
    public void testService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        SuperuserServiceImpl service = context.getBean("superuserServiceImpl", SuperuserServiceImpl.class);
        service.getUser();
    }


    @Test
    public void testHello2() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Hello2 hello2 = context.getBean("hello2", Hello2.class);
        System.out.println(hello2.toString());
    }

    @Test
    public void testNode2() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Node2 node2 = context.getBean("node2", Node2.class);
        System.out.println(node2.toString());
    }
}
