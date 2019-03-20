package blog.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cyclamen on 2019-03-17
 */
@Controller
@RequestMapping("popup")
public class AdminPopupController {

  @GetMapping("default-popup")
  public String getPopup() {
    return "popup/default-popup";
  }
}
