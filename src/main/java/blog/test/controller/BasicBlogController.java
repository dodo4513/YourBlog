package blog.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("test")
public class BasicBlogController {

    @GetMapping
    public String main() {
        return "main";
    }

    @GetMapping("post/write")
    public String writePost() {
        return "write-post";
    }
}
