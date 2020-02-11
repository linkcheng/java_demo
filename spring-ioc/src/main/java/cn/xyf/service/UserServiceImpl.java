package cn.xyf.service;

import cn.xyf.dao.UserDao;

public class UserServiceImpl implements UserService{
//    UserDao userDao = new UserDaoImpl();
    UserDao userDao;

    public UserServiceImpl() {
        System.out.println("UserServiceImpl init");
    }

    public void setUserDao(UserDao d) {
        userDao = d;
    }

    public void getUser() {
        userDao.getUser();
    }

    public void add() {
        System.out.println("Add user");
    }

    public void delete() {
        System.out.println("Delete user");
    }

    public void update() {
        System.out.println("Update user");
    }

    public void query() {
        System.out.println("Query user");
    }
}
