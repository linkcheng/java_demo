package cn.xyf;

import cn.xyf.dao.UserDaoMySQLImpl;
import cn.xyf.service.UserService;
import cn.xyf.service.UserServiceImpl;
import cn.xyf.service.UserServiceProxy;
import cn.xyf.service.UserServiceProxyInvocationHandler;

public class App {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.setUserDao(new UserDaoMySQLImpl());
        userService.getUser();

        // 使用静态代理
        UserServiceProxy proxy = new UserServiceProxy();
        proxy.setUserService(userService);
        proxy.add();

        // 使用动态代理
        UserServiceProxyInvocationHandler handler = new UserServiceProxyInvocationHandler();
        handler.setObject(userService);

        UserService service = (UserService) handler.getProxy();
        service.query();
    }
}
