package cn.xyf.controller;

import cn.xyf.pojo.User;
import cn.xyf.utils.JsonUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


//@Controller
@RestController  // 直接返回 json
public class UserController {

    @GetMapping("/user")
    public String getInfo(Model model, @RequestParam("name") String name) {
        model.addAttribute("id", 1);
        model.addAttribute("name", name);
        model.addAttribute("age", 10);
        return "user";
    }

    @GetMapping("/user-info")
    public String showInfo(Model model, User user) {
        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("age", user.getAge());
        return "user";
    }

    @PostMapping("/user")
    public String postName(Model model, @RequestParam("name") String name) {
        model.addAttribute("name", name);
        return "user";
    }

    @RequestMapping("/json")
    // @ResponseBody  // 使用 RestController 不再需要；不走视图解析器，直接返回字符串
    public String userJson() {
        User user = new User(1, "小狂神", 18);
        return JsonUtil.jsonify(user);
    }
}
