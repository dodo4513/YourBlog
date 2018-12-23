package blog.api.info.service;

import blog.api.info.dao.VisitRepository;
import blog.api.info.model.entity.Visit;
import blog.api.info.model.request.VisitRequest;
import blog.api.info.model.response.VisitInfoResponse;
import blog.api.info.model.response.VisitResponse;
import blog.common.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author cyclamen on 21/12/2018
 */
@Service
public class VisitService {

    private final VisitRepository visitRepository;

    @Autowired
    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Transactional
    public VisitResponse saveVisit(VisitRequest visitRequest, String clientIp) {
        Visit visit = BeanUtils.copyProperties(visitRequest, Visit.class);
        visit.setClientIp(clientIp);

        return BeanUtils.copyProperties(visitRepository.save(visit), VisitResponse.class);
    }

    VisitInfoResponse makeVisitResponse() {
        VisitInfoResponse visitInfoResponse = new VisitInfoResponse();
        visitInfoResponse.setTotalVisit(visitRepository.count());
        visitInfoResponse.setTodayVisit(visitRepository.countByRegisterYmdtAfter(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT)));
        visitInfoResponse.setYesterdayVisit(visitRepository.countByRegisterYmdtAfter(LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT)));

        return visitInfoResponse;
    }
}
