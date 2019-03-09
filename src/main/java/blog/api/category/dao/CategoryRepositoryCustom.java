package blog.api.category.dao;

import blog.api.category.model.response.BestCategoriesResponse;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<BestCategoriesResponse> getBestCategorysByCategoryNoCount(long limit);
}
