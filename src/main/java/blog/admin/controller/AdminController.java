package blog.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {

  @GetMapping
  public String main() {
    return "main";
  }

  @GetMapping("empty")
  public String te() {
    return "default-template";
  }

  @GetMapping("set-me")
  @Deprecated
  public String setMe() {
    return "set-me";
  }

}
