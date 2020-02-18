package cn.xyf.mapper;

import cn.xyf.pojo.StarUser;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class StarUserImpl implements StarUserMapper {
    private SqlSessionTemplate sqlSession;
    private StarUserMapper mapper;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
        this.mapper = this.sqlSession.getMapper(StarUserMapper.class);
    }

    public List<StarUser> query() {
        return mapper.query();
    }

    public int add(StarUser user) {
        return mapper.add(user);
    }

    public int delete(int id) {
        return mapper.delete(id);
    }

    public int update(StarUser user) {
        return mapper.update(user);
    }

    public int insert(StarUser user) {
        return mapper.insert(user);
    }

    public StarUser selectById(int id) {
        return mapper.selectById(id);
    }

    public void replace(StarUser user) {
        mapper.delete(user.getId());
        mapper.insert(user);
    }
}
