package blog.api.info.dao;

import blog.api.info.model.entity.Visit;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

  long countByRegisterYmdtAfter(LocalDateTime localDateTime);

  long countByRegisterYmdtBetween(LocalDateTime start, LocalDateTime end);

  long count();
}
