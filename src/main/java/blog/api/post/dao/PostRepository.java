package blog.api.post.dao;

import blog.api.post.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByNoAndDeleteYn(long no, boolean deleteYn);

    List<Post> findByDeleteYn(boolean deleteYn);

    long countByDeleteYn(boolean deleteYn);

    long countByRegisterYmdtAfterAndDeleteYn(LocalDateTime localDateTime, boolean deleteYn);
}
