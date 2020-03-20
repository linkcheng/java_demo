package cn.xyf.service;

import cn.xyf.mapper.StarUserMapper;
import cn.xyf.pojo.StarUser;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


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

    private <T> T parseJsonObject(JSONObject params, String key, Class<T> cls) {
        if (!params.containsKey(key)) {
            System.out.println("key " + key + "not exists");
        }
        return params.getObject(key, cls);
    }

    private JSONObject updateContext(JSONObject context, List<String> eleIds, Map<String, Object> elements) {
        if(null != elements){
            context.putAll(elements);
        } else {
            //如果接口返回空就把每个因子填到context中值为null
            eleIds.forEach(eleId -> {if(!context.containsKey(eleId)) context.put(eleId, null);});
        }
        return context;
    }
}
