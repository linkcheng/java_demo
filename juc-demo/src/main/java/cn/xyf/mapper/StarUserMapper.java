package cn.xyf.mapper;

import cn.xyf.pojo.StarUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StarUserMapper {
    List<StarUser> selectAll();

    StarUser select(@Param("uid") int i);

    int insert(StarUser user);

    int update(StarUser user);

    int delete(@Param("uid") int id);
}
