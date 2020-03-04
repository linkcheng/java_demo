package cn.xyf.controller;

import cn.xyf.mapper.StarUserMapper;
import cn.xyf.pojo.StarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/mybatis")
public class MybatisController {
    private StarUserMapper starUserMapper;

    @Autowired
    public void setStarUserMapper(StarUserMapper starUserMapper) {
        this.starUserMapper = starUserMapper;
    }

    @GetMapping("/users")
    public List<StarUser> getUsers() {
        return starUserMapper.selectAll();
    }

    @GetMapping("/user/{uid}")
    public StarUser getUser(@PathVariable("uid") int id) {
        return starUserMapper.select(id);
    }

    @PostMapping("/user")
    public int addUser() {
        StarUser user = new StarUser(0, "what", "aaaaaa");
        return starUserMapper.insert(user);
    }

    @PutMapping("/user/{uid}")
    public int updateUser(@PathVariable("uid") int id) {
        StarUser user = new StarUser(9, "what", "zzzzzz");
        return starUserMapper.update(user);
    }

    @DeleteMapping("/user/{uid}")
    public int deleteUser(@PathVariable("uid") int id) {
        return starUserMapper.delete(id);
    }
}
