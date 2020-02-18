package cn.xyf.mapper;

import cn.xyf.pojo.StarUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StarUserMapper {

    List<StarUser> query();
    int add(StarUser user);
    int delete(@Param("uid") int id);
    int update(StarUser user);
    int insert(StarUser user);
    StarUser selectById(@Param("uid") int id);
    void replace(StarUser user);

}
