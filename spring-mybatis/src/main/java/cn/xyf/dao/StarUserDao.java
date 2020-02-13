package cn.xyf.dao;

import cn.xyf.pojo.StarUser;

import java.util.List;

public interface StarUserDao {
    void insert();

    List<StarUser> selectUserList();
}
