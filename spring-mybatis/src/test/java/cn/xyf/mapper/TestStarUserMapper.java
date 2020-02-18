package cn.xyf.mapper;

import cn.xyf.pojo.StarUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestStarUserMapper {
    private SqlSession sqlSession;
    private StarUserMapper mapper;

    @Before
    public void setUp() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream in = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = sqlSessionFactory.openSession(true);
        mapper = sqlSession.getMapper(StarUserMapper.class);
    }

    @Test
    public void testSelectStarUsers() {
        List<StarUser> starUsers = mapper.query();
        for (StarUser starUser : starUsers) {
            System.out.println(starUser);
        }
    }

    @After
    public void tearDown() {
        sqlSession.close();
    }
}
