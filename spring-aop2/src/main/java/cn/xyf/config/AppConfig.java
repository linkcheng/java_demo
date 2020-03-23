package cn.xyf.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"cn.xyf.aspect", "cn.xyf.service"})
@EnableAspectJAutoProxy
public class AppConfig {

}
