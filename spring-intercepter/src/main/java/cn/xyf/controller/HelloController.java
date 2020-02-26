package cn.xyf.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController{
    @GetMapping("/hello/inter1")
    public String inter1() {
        System.out.println("========inter1=========");
        return "inter1";
    }

    @GetMapping("/inter2")
    public String inter2() {
        System.out.println("========inter2=========");
        return "inter2";
    }
}
