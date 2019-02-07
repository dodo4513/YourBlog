package blog.api.info.service;

import blog.api.info.dao.VisitRepository;
import blog.api.info.model.entity.Visit;
import blog.api.info.model.request.VisitRequest;
import blog.api.info.model.response.VisitInfoResponse;
import blog.api.info.model.response.VisitResponse;
import blog.common.model.enums.CacheName;
import blog.common.service.CacheService;
import blog.common.util.BeanUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cyclamen on 21/12/2018
 */
@Service
public class VisitService {

  private final VisitRepository visitRepository;

  private final CacheService cacheService;

  @Autowired
  public VisitService(VisitRepository visitRepository,
      CacheService cacheService) {
    this.visitRepository = visitRepository;
    this.cacheService = cacheService;
  }

  @Transactional
  public VisitResponse saveVisit(VisitRequest visitRequest, String clientIp) {

    if (isAlreadyVisitClient(clientIp)) {
      return null;
    }

    Visit visit = BeanUtils.copyProperties(visitRequest, Visit.class);
    visit.setClientIp(clientIp);

    return BeanUtils.copyProperties(visitRepository.save(visit), VisitResponse.class);
  }

  VisitInfoResponse makeVisitResponse() {
    VisitInfoResponse visitInfoResponse = new VisitInfoResponse();
    LocalDateTime todayMidnight = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);

    visitInfoResponse.setTotalVisit(visitRepository.count());
    visitInfoResponse.setTodayVisit(visitRepository
        .countByRegisterYmdtAfter(todayMidnight));
    visitInfoResponse.setYesterdayVisit(visitRepository.countByRegisterYmdtBetween(
        LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT),
        todayMidnight));

    return visitInfoResponse;
  }

  private boolean isAlreadyVisitClient(String clientIp) {
    if (cacheService.get(CacheName.VisitClientIp, clientIp) != null) {

      return true;
    }
    cacheService.put(CacheName.VisitClientIp, clientIp, "true");

    return false;
  }
}
