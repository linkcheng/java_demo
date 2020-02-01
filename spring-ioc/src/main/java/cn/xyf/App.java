package cn.xyf;

public class App {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.setUserDao(new UserMySQLDaoImpl());
        userService.getUser();
    }
}
