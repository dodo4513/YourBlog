package blog.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author doyoung hwang on 2019-04-20
 */
@Controller
@RequestMapping("/admin/post")
public class AdminPostController {

  @GetMapping("list")
  public String post() {
    return "list-post";
  }

  @GetMapping("write")
  public String writePost() {
    return "manage-post";
  }
}
