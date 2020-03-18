package cn.xyf.mapper;

import cn.xyf.pojo.Blog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BlogMapper {
    List<Blog> selectAll();
    Blog select(@Param("bid") int i);
}
