package blog.common.etc;

import blog.common.service.CacheService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author cyclamen on 23/12/2018
 */

@Component
@Slf4j
public class JobScheduler {

  private static final String MIDNIGHT = "0 0 0 * * *";

  private final CacheService cacheService;

  @Autowired
  public JobScheduler(CacheService cacheService) {
    this.cacheService = cacheService;
  }

  @Scheduled(cron = MIDNIGHT)
  public void evictVisitedClientIpCache() {
    cacheService.evict(CacheName.VisitClientIp);
    log.info("Visitor information was deleted.");
  }
}
