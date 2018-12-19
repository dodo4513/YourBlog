package blog.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("test")
public class AdminController {

    // FIXME 필요?
    @GetMapping
    public String main() {
        return "main";
    }

    @GetMapping("post/write")
    public String writePost() {
        return "write-post";
    }
}
