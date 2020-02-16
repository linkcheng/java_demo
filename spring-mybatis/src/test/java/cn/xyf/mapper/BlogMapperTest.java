package cn.xyf.mapper;

import cn.xyf.pojo.Blog;
import cn.xyf.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class BlogMapperTest {

    private SqlSession sqlSession;
    private BlogMapper mapper;


    @Before
    public void setUp() {
        sqlSession = MybatisUtils.getSqlSession();
        mapper = sqlSession.getMapper(BlogMapper.class);
    }

    @Test
    public void testGetBlogs() {
        List<Blog> blogs = mapper.getBlogs();
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
    }

    @Test
    public void testGetBlogById() {
        Blog blog = mapper.getBlogById(1);
        System.out.println(blog);
    }

    @Test
    public void testGetFulBlogsInfo() {
        List<Blog> info = mapper.getFulBlogsInfo();
        for (Blog blog : info) {
            System.out.println(blog);
        }

    }

    @Test
    public void testGetFulBlogsInfo2() {
        List<Blog> info = mapper.getFulBlogsInfo2();
        for (Blog blog : info) {
            System.out.println(blog);
        }

    }

    @Test
    public void testQueryBlog() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("title", "hello%");
        map.put("content", "hello%");

        List<Blog> blogs = mapper.queryBlog(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
    }

    @After
    public void teardown() {
        sqlSession.close();
    }
}
