package cn.xyf.service;

import cn.xyf.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service(value = "superuserServiceImpl")
public class SuperuserServiceImpl implements UserService {

    @Value("admin")
    private String name;

    @Autowired
    @Qualifier(value = "mongo")
    private UserDao userDao;


    public void setName(String name) {
        this.name = name;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void getUser() {
        System.out.print("# ");
        userDao.getUser();
    }

    public void add() {

    }

    public void delete() {

    }

    public void update() {

    }

    public void query() {

    }
}
