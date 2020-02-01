package cn.xyf;

public class UserServiceImpl implements UserService{
//    UserDao userDao = new UserDaoImpl();
    UserDao userDao;

    public void setUserDao(UserDao d) {
        userDao = d;
    }

    public void getUser() {
        userDao.getUser();
    }
}
