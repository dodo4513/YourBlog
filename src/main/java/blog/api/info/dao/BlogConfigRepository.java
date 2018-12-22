package blog.api.info.dao;

import blog.api.info.model.entity.BlogConfig;
import blog.api.info.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BlogConfigRepository extends JpaRepository<BlogConfig, Long> {

}
