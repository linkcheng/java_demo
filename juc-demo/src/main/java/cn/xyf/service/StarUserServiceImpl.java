package cn.xyf.service;

import cn.xyf.mapper.StarUserMapper;
import cn.xyf.pojo.StarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class StarUserServiceImpl implements StarUserService {
    private StarUserMapper mapper;

    @Autowired
    public void setMapper(StarUserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<StarUser> selectAll() {
        List<StarUser> users= new ArrayList<>(5);
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        for(Integer i : list) {
             users.add(mapper.select(i));
        }
        return users;
    }
}
