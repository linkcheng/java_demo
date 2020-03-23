package cn.xyf.service;

import cn.xyf.aspect.LogReport;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{
    @LogReport
    public void query() {
        System.out.println("Query user");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}