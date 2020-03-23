package cn.xyf.config;

import cn.xyf.dao.UserDao;
import cn.xyf.dao.UserDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"cn.xyf.service"})
public class ApplicationConfig {

    @Bean
    public UserDao userDaoImpl() {
        return new UserDaoImpl();
    }
}
