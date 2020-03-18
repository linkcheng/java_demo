package cn.xyf.service;

import cn.xyf.mapper.StarUserMapper;
import cn.xyf.pojo.StarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;


@Service
@ConditionalOnBean(name = "taskConcurrentExecutor")
public class JucStarUserServiceImpl implements StarUserService {
    private StarUserMapper mapper;
    private Executor executor;
    private CompletionService<StarUser> service;

    @Autowired
    public void setMapper(StarUserMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    @Qualifier("taskConcurrentExecutor")
    public void setExecutor(Executor executor) {
        this.executor = executor;
        this.service = new ExecutorCompletionService<>(this.executor);
    }

    @Override
    public List<StarUser> selectAll() {
        List<StarUser> users = new ArrayList<>(5);
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        for(Integer i : list) {
            service.submit(()-> mapper.select(i));
        }

        try {
            for(int j=0; j<5; j++) {
                Future<StarUser> future = service.take();
                users.add(future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
