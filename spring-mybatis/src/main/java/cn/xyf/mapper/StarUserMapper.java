package cn.xyf.mapper;

import cn.xyf.pojo.StarUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StarUserMapper {
    List<StarUser> selectUserList();
    StarUser getUserById(int id);
    int addUser(StarUser user);
    int updatePwd(Map<String, Object> map);

    // 分页
    List<StarUser> getUserByLimit(Map<String, Integer> map);

    StarUser getUserInfoById(@Param("uid") int id);
}
