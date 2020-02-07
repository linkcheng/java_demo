package cn.xyf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("cn.xyf")
public class AppConfig {
    /**
     * 有 @Bean，类上不需要 @Component
     * 没有 @Bean，config 类需要 @ComponentScan("cn.xyf")，被扫描类上需要 @Component
     */
    @Bean
    public Hello2 hello2() {
        return new Hello2();
    }
}
