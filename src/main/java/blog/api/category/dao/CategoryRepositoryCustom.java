package blog.api.category.dao;

import blog.api.category.model.entity.Category;
import blog.api.category.model.request.GetCategoriesRequest;
import java.util.List;

public interface CategoryRepositoryCustom {

  List<Category> getCategories(GetCategoriesRequest getCategoriesRequest);
}
