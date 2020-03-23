package cn.xyf.service;

import cn.xyf.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao d) {
        userDao = d;
    }

    public void getUser() {
        userDao.getUser();
    }
}
