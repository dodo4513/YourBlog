package blog.api.post.dao;

import blog.api.post.model.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom{

  Post findByNoAndDeleteYn(long no, boolean deleteYn);

  List<Post> findByDeleteYn(boolean deleteYn);

  long countByDeleteYn(boolean deleteYn);

  long countByRegisterYmdtAfterAndDeleteYn(LocalDateTime localDateTime, boolean deleteYn);
}
