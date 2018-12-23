package blog.common.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author cyclamen on 23/12/2018
 */
public class ControllerUtils {

  public static String getIp() {
    HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes()).getRequest();
    String ip = req.getHeader("X-FORWARDED-FOR");

    return ip != null ? ip : req.getRemoteAddr();
  }
}
