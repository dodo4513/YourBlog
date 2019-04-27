package blog.api.category.dao;


import blog.api.category.model.entity.Category;
import blog.api.category.model.entity.QCategory;
import blog.api.category.model.request.GetCategoriesRequest;
import com.querydsl.core.BooleanBuilder;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CategoryRepositoryImpl extends QuerydslRepositorySupport implements
    CategoryRepositoryCustom {

  private QCategory category = QCategory.category;

  public CategoryRepositoryImpl() {
    super(Category.class);
  }

  @Override
  public List<Category> getCategories(GetCategoriesRequest getCategoriesRequest) {

    BooleanBuilder booleanBuilder = new BooleanBuilder();

    switch (getCategoriesRequest.getPublicType()) {
      case ONLY_PUBLIC:
        booleanBuilder.and(category.publicYn.eq(true));
        break;
      case ALL:
      default:
    }

    if (getCategoriesRequest.getOnlyRootLevel()) {
      booleanBuilder.and(category.parent.isNull());
    }

    return from(category).where(booleanBuilder).fetch();
  }
}
