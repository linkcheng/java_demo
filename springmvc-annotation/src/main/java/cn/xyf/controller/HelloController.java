package cn.xyf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/v1")
    public String hello1(Model model) {
        model.addAttribute("msg", "SpringMVC annotation");
        // 会被视图解析器处理，返回 hello.jsp 页面
        return "hello";
    }

    @RequestMapping("/v2")
    public String hello2(Model model) {
        model.addAttribute("msg", "SpringMVC annotation,SpringMVC annotation");
        // 会被视图解析器处理，返回 hello.jsp 页面
        return "hello";
    }
}
