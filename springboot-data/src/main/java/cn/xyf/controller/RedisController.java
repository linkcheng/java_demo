package cn.xyf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/redis")
public class RedisController {
    private RedisTemplate<Object, Object> redisTemplate;
    private StringRedisTemplate stringRedisTemplate;
    private RedisTemplate<Object, Object> myRedisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Autowired
    @Qualifier("myRedisTemplate")
    public void setMyRedisTemplate(RedisTemplate<Object, Object> myRedisTemplate) {
        this.myRedisTemplate = myRedisTemplate;
    }

    @GetMapping("/obj/{key}")
    public String getObj(@PathVariable("key") String key) {
        String str = (String)redisTemplate.opsForValue().get(key);
        System.out.println(str);
        return str;
    }

    @PostMapping("/obj/{key}/{val}")
    public String setObj(@PathVariable("key") String key, @PathVariable("val") String val) {
        redisTemplate.opsForValue().set(key, val) ;
        System.out.println("set key="+key+", val="+val);
        return "OK";
    }

    @GetMapping("/str/{key}")
    public String getStr(@PathVariable("key") String key) {
        String str = stringRedisTemplate.opsForValue().get(key);
        System.out.println(str);
        return str;
    }

    @PostMapping("/str/{key}/{val}")
    public String setStr(@PathVariable("key") String key, @PathVariable("val") String val) {
        stringRedisTemplate.opsForValue().set(key, val) ;
        System.out.println("set key="+key+", val="+val);
        return "OK";
    }

    @GetMapping("/my/{key}")
    public String getMyStr(@PathVariable("key") String key) {
        String str = (String)myRedisTemplate.opsForValue().get(key);
        System.out.println(str);
        return str;
    }

    @PostMapping("/my/{key}/{val}")
    public String setMyStr(@PathVariable("key") String key, @PathVariable("val") String val) {
        myRedisTemplate.opsForValue().set(key, val) ;
        System.out.println("set key="+key+", val="+val);
        return "OK";
    }

    @GetMapping("/my/list/{key}")
    public String getMyList(@PathVariable("key") String key) {
        String str = (String) myRedisTemplate.opsForList().leftPop(key);
        System.out.println(str);
        return str;
    }

    @PostMapping("/my/list/{key}/{val}")
    public String setMyList(@PathVariable("key") String key, @PathVariable("val") String val) {
        myRedisTemplate.opsForList().rightPush(key, val);
        System.out.println("set list key="+key+", val="+val);
        return "OK";
    }

    @GetMapping("/my/set/{key}")
    public String getMySet(@PathVariable("key") String key) {
        String str = (String) myRedisTemplate.opsForSet().pop(key);
        System.out.println(str);
        return str;
    }

    @PostMapping("/my/set/{key}/{val}")
    public String setMySet(@PathVariable("key") String key, @PathVariable("val") String val) {
        myRedisTemplate.opsForSet().add(key, val);
        System.out.println("set set key="+key+", val="+val);
        return "OK";
    }

    @GetMapping("/my/hash/{key}/{hkey}")
    public String getMyHash(@PathVariable("key") String key, @PathVariable("hkey") String hkey) {
        String str = (String) myRedisTemplate.opsForHash().get(key, hkey);
        System.out.println(str);
        return str;
    }

    @PostMapping("/my/hash/{key}/{hkey}/{val}")
    public String setMyHash(@PathVariable("key") String key, @PathVariable("hkey") String hkey, @PathVariable("val") String val) {
        myRedisTemplate.opsForHash().put(key, hkey, val);
        System.out.println("set hash key="+key+", hkey="+hkey+", val="+val);
        return "OK";
    }

    @GetMapping("/my/zset/{key}/{val}")
    public double getMySet(@PathVariable("key") String key, @PathVariable("val") String val) {
        double str = 0.0F;
        ZSetOperations<Object, Object> operations = myRedisTemplate.opsForZSet();
        try {
            str = operations.score(key, val);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        System.out.println(str);
        return str;
    }

    @PostMapping("/my/zset/{key}/{val}/{score}")
    public String setMySet(@PathVariable("key") String key, @PathVariable("val") String val, @PathVariable("score") int score) {
        myRedisTemplate.opsForZSet().add(key, val, score);
        System.out.println("set set key="+key+", val="+val+", score="+score);
        return "OK";
    }

}
