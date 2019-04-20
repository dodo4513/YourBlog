package blog.api.tag.dao;

import blog.api.tag.model.entity.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

  List<Tag> findByNameIn(List<String> tagNames);
}
