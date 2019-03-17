package blog.api.category.dao;

import blog.api.category.model.entity.Category;
import blog.api.category.model.entity.QCategory;
import blog.api.category.model.response.FrequentlyUsedCategoryResponse;
import blog.api.post.model.entity.QPost;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CategoryRepositoryImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom {

    QCategory category = QCategory.category;
    QPost post = QPost.post;

    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    @Override
    public List<FrequentlyUsedCategoryResponse> getBestCategorysByCategoryNoCount(long limit) {

        return from(post)
                .select(
                        Projections.bean(FrequentlyUsedCategoryResponse.class,
                                category.name.as("name"),
                                post.categoryNo.count().as("count")))
                .leftJoin(post.category, category)
                .groupBy(post.categoryNo)
                .orderBy(post.categoryNo.count().desc())
                .limit(limit)
                .fetch();
    }
}
