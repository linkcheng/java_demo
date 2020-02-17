package cn.xyf.mapper;

import cn.xyf.pojo.StarUser;
import cn.xyf.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StarUserMapperTest {
    private SqlSession sqlSession;
    private StarUserMapper mapper;
    private static Logger logger = Logger.getLogger(StarUserMapperTest.class);

    @Before
    public void before() {
        logger.info("before StarUserMapperTest");
        sqlSession = MybatisUtils.getSqlSession();
        mapper = sqlSession.getMapper(StarUserMapper.class);
    }

    @Test
    public void selectUserList() {
        List<StarUser> users = mapper.selectUserList();
        for (StarUser starUser : users) {
            logger.info(starUser);
        }
    }

    @Test
    public void testGetUserById() {
        StarUser user = mapper.getUserById(1);
        System.out.println(user);
    }

    @Test
    public void testAddUser() {
        StarUser starUser = new StarUser("what", "123123");
        mapper.addUser(starUser);
        // 提交事务
        sqlSession.commit();
    }

    @Test
    public void testUpdatePwd() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", "what");
        map.put("password", "456456");
        mapper.updatePwd(map);
        sqlSession.commit();
    }

    @Test
    public void testGetUserByLimit() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("startIndex", 1);
        map.put("pageSize", 2);

        List<StarUser> users = mapper.getUserByLimit(map);
        for (StarUser user : users) {
            logger.info(user);
        }
    }

    @Test
    public void testGetUserInfoById() {
        StarUser user = mapper.getUserInfoById(1);
        System.out.println(user);
    }

    @After
    public void after() {
        sqlSession.close();
    }
}
