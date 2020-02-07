package cn.xyf;

import cn.xyf.dao.UserDaoMySQLImpl;
import cn.xyf.service.UserServiceImpl;

public class App {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.setUserDao(new UserDaoMySQLImpl());
        userService.getUser();
    }
}
