package cn.xyf.mapper;

import cn.xyf.pojo.Blog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BlogMapper {

    @Select("select * from blog")
    List<Blog> getBlogs();

    @Select("select * from blog where id=#{id}")
    Blog getBlogById(@Param("id") int id);

    List<Blog> getFulBlogsInfo();

    List<Blog> getFulBlogsInfo2();
}
