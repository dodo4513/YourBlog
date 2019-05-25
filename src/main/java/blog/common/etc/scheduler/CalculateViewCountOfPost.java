package blog.common.etc.scheduler;

import blog.api.post.service.PostService;
import blog.common.etc.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author doyoung hwang on 2019-05-25
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class CalculateViewCountOfPost {

  private final PostService postService;

  @Scheduled(cron = SystemConstants.MIDNIGHT_CRON)
  public void calculateViewCountOfPost() {
    postService.updateViewCountOfPost();
    log.info("Visitor information was deleted.");
  }

}
