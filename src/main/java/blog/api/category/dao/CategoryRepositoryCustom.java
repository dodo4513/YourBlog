package blog.api.category.dao;

import blog.api.category.model.response.FrequentlyUsedCategoryResponse;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<FrequentlyUsedCategoryResponse> getBestCategorysByCategoryNoCount(long limit);
}
