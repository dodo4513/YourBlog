package blog.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author doyoung hwang on 2019-04-20
 */
@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {

  @GetMapping("list")
  public String categoryList() {
    return "list-category";
  }

}
