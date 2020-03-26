package cn.xyf.controller;

import cn.xyf.pojo.StarUser2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mongo")
public class MongoController {
    private MongoTemplate mongoTemplate;

    @Autowired

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/{uid}")
    public StarUser2 getSu(@PathVariable("uid") int uid) {
        Query query = new Query(Criteria.where("id").is(uid));
        StarUser2 user2 = mongoTemplate.findOne(query, StarUser2.class);
        System.out.println(user2);
        return user2;
    }

    @GetMapping("/m/{mobile}")
    public StarUser2 getSuByMobile(@PathVariable("mobile") String mobile) {
        Query query = new Query(Criteria.where("mobile").is(mobile));
        StarUser2 user2 = mongoTemplate.findOne(query, StarUser2.class);
        System.out.println(user2);
        return user2;
    }
}
