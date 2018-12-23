package blog.api.info.dao;

import blog.api.info.model.entity.BlogConfig;
import blog.api.info.model.enums.ConfigCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogConfigRepository extends JpaRepository<BlogConfig, Long> {

    BlogConfig findByConfigCode(ConfigCode configCode);
}
