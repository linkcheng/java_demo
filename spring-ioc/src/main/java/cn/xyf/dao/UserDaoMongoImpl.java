package cn.xyf.dao;

import org.springframework.stereotype.Repository;

@Repository(value = "mongo")
public class UserDaoMongoImpl implements UserDao{
    public void getUser() {
        System.out.println("Get user from Mongodb");
    }
}
