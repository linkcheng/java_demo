package cn.xyf.mapper;

import cn.xyf.pojo.StarUser;
import lombok.Builder;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.security.PublicKey;
import java.util.List;

public class TestStarUserImpl {
    @Test
    public void testQuery() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StarUserMapper starUser = context.getBean("staruser", StarUserMapper.class);

        List<StarUser> starUsers = starUser.query();
        for (StarUser user : starUsers) {
            System.out.println(user);
        }

    }

    @Test
    public void testQuery2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StarUserMapper starUser = context.getBean("staruser2", StarUserMapper.class);

        List<StarUser> starUsers = starUser.query();
        for (StarUser user : starUsers) {
            System.out.println(user);
        }

    }

    @Test
    public void testAdd() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StarUserMapper starUser = context.getBean("staruser2", StarUserMapper.class);
        starUser.add(new StarUser("what", "123123"));
    }


    @Test
    public void testReplace() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StarUserMapper staruser = context.getBean("staruser", StarUserMapper.class);

        StarUser user = StarUser.builder().id(6).name("how").pwd("000000").build();
        staruser.replace(user);

    }


}
