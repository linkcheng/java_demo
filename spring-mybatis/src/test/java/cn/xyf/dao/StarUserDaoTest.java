package cn.xyf.dao;

import cn.xyf.pojo.StarUser;
import cn.xyf.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class StarUserDaoTest {
    @Test
    public void testInsert() {
        StarUserDaoImpl starUserDao = new StarUserDaoImpl();
        starUserDao.insert();
    }

    @Test
    public void testSelectUserList() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StarUserDao mapper = sqlSession.getMapper(StarUserDao.class);

        List<StarUser> starUsers = mapper.selectUserList();
        for (StarUser starUser : starUsers) {
            System.out.println(starUser);
        }

        sqlSession.close();
    }
}
