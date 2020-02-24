package cn.xyf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
//@RequestMapping("/hello")   // 尽量不适用，类太大之后容易被忽视
public class NewController {
    @RequestMapping("/hello/v3")
    public String hello3(Model model) {
        model.addAttribute("msg", "springmvc annotation v3333333");
        return "hello";
    }

    // @RequestMapping(value = "/concat/{a}/{b}", method = RequestMethod.GET)
    @GetMapping("/concat/{a}/{b}")
    public String concat(@PathVariable String a, @PathVariable String b, Model model) {
        model.addAttribute("msg", "结果为："+a + b);
        return "hello";
    }


    @GetMapping("/redirect")
    public String redirect(Model model) {
        model.addAttribute("msg", "Redirect");
        return "redirect:/index.jsp";
    }
}
