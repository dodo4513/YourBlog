package blog.common.etc.scheduler;

import blog.common.etc.SystemConstants;
import blog.common.model.enums.CacheName;
import blog.common.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author cyclamen on 23/12/2018
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class VisitorInitialize {

  private final CacheService cacheService;

  @Scheduled(cron = SystemConstants.MIDNIGHT_CRON)
  public void evictVisitedClientIpCache() {
    cacheService.evict(CacheName.VisitClientIp);
    log.info("Visitor information was deleted.");
  }
}
