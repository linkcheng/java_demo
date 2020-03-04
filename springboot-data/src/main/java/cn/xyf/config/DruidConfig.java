package cn.xyf.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("loginUsername", "admin"); //后台管理界面的登录账号
        initParameters.put("loginPassword", "123456"); //后台管理界面的登录密码

        //后台允许谁可以访问
        //initParameters.put("allow", "localhost")：表示只有本机可以访问
        //initParameters.put("allow", "")：为空或者为null时，表示允许所有访问
        initParameters.put("allow", "");
        //deny：Druid 后台拒绝谁访问
        //initParameters.put("xyz", "192.168.1.20");表示禁止此ip访问

        //设置初始化参数
        bean.setInitParameters(initParameters);
        return bean;
    }
}
