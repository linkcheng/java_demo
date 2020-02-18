package cn.xyf.mapper;

import cn.xyf.pojo.StarUser;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class StarUserImpl2 extends SqlSessionDaoSupport implements StarUserMapper {
    public List<StarUser> query() {
        return getSqlSession().getMapper(StarUserMapper.class).query();
    }

    public int add(StarUser user) {
        return getSqlSession().getMapper(StarUserMapper.class).add(user);
    }

    public int delete(int id) {
        return getSqlSession().getMapper(StarUserMapper.class).delete(id);
    }

    public int update(StarUser user) {
        return getSqlSession().getMapper(StarUserMapper.class).update(user);
    }

    public int insert(StarUser user) {
        return getSqlSession().getMapper(StarUserMapper.class).insert(user);
    }

    public StarUser selectById(int id) {
        return getSqlSession().getMapper(StarUserMapper.class).selectById(id);
    }

    public void replace(StarUser user) {

    }
}
