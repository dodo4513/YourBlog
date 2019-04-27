package blog.api.category.dao;

import blog.api.category.model.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

  @Modifying
  @Query("update Category c set c.deleteYn = 'Y' where c.categoryNo in ?1")
  int removeCategories(Long[] categoryNos);
}
