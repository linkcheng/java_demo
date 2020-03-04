package cn.xyf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/jdbc")
public class JdbcController {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/users")
    public List<Map<String, Object>> getUsers() {
        String sql = "select * from mybatis.staruser";
        return jdbcTemplate.queryForList(sql);
    }

    @GetMapping("/user/{uid}")
    public Map<String, Object> getUser(@PathVariable("uid") int id) {
        String sql = "select * from mybatis.staruser where id=?";
        return jdbcTemplate.queryForMap(sql, id);
    }

    @PostMapping("/user")
    public String addUser() {
        String sql = "insert into mybatis.staruser (name, pwd) values ('what', 'aaaaaa')";
        jdbcTemplate.update(sql);
        return "add user";
    }

    @PutMapping("/user/{uid}")
    public String updateUser(@PathVariable("uid") int id) {
        String sql = "update mybatis.staruser set name=?, pwd=? where id=?";
        Object[] args = new Object[3];
        args[0] = "what";
        args[1] = "zzzzzz";
        args[2] = 8;

        jdbcTemplate.update(sql, args);
        return "update user";
    }

    @DeleteMapping("/user/{uid}")
    public String deleteUser(@PathVariable("uid") int id) {
        String sql = "delete from mybatis.staruser where id=?";

        jdbcTemplate.update(sql, id);
        return "delete user";
    }
}
