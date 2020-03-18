package cn.xyf.service;


import cn.xyf.mapper.BlogMapper;
import cn.xyf.mapper.StarUserMapper;
import cn.xyf.pojo.Blog;
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
public class JucBlogServiceImpl implements BlogService {
    private BlogMapper mapper;
    private Executor executor;
    private CompletionService<Blog> service;

    @Autowired
    public void setMapper(BlogMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    @Qualifier("taskConcurrentExecutor")
    public void setExecutor(Executor executor) {
        this.executor = executor;
        this.service = new ExecutorCompletionService<>(this.executor);
    }

    @Override
    public List<Blog> selectAll() {
        List<Blog> users = new ArrayList<>(3);
        List<Integer> list = Arrays.asList(1,2,3);
        for(Integer i : list) {
            service.submit(()-> mapper.select(i));
        }

        try {
            for(int j=0; j<3; j++) {
                Future<Blog> future = service.take();
                users.add(future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
