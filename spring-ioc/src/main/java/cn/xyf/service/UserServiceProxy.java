package cn.xyf.service;


public class UserServiceProxy implements UserService {
    private UserServiceImpl userService;

    /**
     * 装饰器模式应当为所装饰的对象提供增强功能，
     * 代理模式对所代理对象的使用施加控制，并不提供对象本身的增强功能。
     * @param userService
     */
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public void getUser() {
        userService.getUser();
    }

    public void add() {
        System.out.print("[Debug] ");
        userService.add();
    }

    public void delete() {
        userService.delete();
    }

    public void update() {
        userService.update();
    }

    public void query() {
        userService.query();
    }
}
