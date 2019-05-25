package blog.admin.controller;

import blog.api.info.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author doyoung hwang on 2019-05-25
 */
@Controller
@RequestMapping("/admin/setting")
@RequiredArgsConstructor
public class AdminSettingController {

  private final VisitService visitService;

  @GetMapping()
  public String post() {

    return "setting";
  }
}
