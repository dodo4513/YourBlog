package blog.admin.controller;

import blog.api.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author doyoung hwang on 2019-04-20
 */
@Controller
@RequestMapping("/admin/post")
@RequiredArgsConstructor
public class AdminPostController {

  private final PostService postService;

  @GetMapping("list")
  public String post() {
    return "list-post";
  }

  @GetMapping("write")
  public String writePost() {
    return "manage-post";
  }

  @GetMapping("{postNo}")
  public String viewPost(@PathVariable long postNo, Model model) {

    model.addAttribute("postNo", postNo);
    return "manage-post";
  }
}
