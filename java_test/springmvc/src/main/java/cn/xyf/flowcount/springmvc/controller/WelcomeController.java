package cn.xyf.flowcount.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {
    @RequestMapping("/welcome")
    public String welcome() {
        System.out.println("welcome");
        return "welcome";
    }
}
