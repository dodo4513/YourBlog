package blog.api.category.service;

import blog.api.category.dao.CategoryRepository;
import blog.api.category.model.entity.Category;
import blog.api.category.model.request.GetCategoriesRequest;
import blog.api.category.model.request.SaveCategoryRequest;
import blog.api.category.model.response.CategoriesResponse;
import blog.api.category.model.response.CategoryResponse;
import blog.api.post.service.PostService;
import blog.common.util.BeanUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cyclamen on 13/01/2019
 */
@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final PostService postService;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository,
      PostService postService) {
    this.categoryRepository = categoryRepository;
    this.postService = postService;
  }

  public CategoryResponse getCategoryResponse(long no) {
    Category category = categoryRepository.findById(no).orElse(null);

    if (category == null) {
      return null;
    }

    CategoryResponse response = BeanUtils.copyProperties(category, CategoryResponse.class);
    response
        .setTotalNumberOfPosts(postService.getCountOfPostsInCategoryNo(category.getCategoryNo()));

    return response;
  }

  public CategoriesResponse getCategoriesResponses(GetCategoriesRequest request) {
    List<Category> categoryList = categoryRepository.findByPublicYn(request.isPublicYn());

    List<CategoryResponse> categoryResponses = BeanUtils
        .copyProperties(categoryList, CategoryResponse.class);
    categoryResponses.forEach(categoryResponse -> categoryResponse
        .setTotalNumberOfPosts(
            postService.getCountOfPostsInCategoryNo(categoryResponse.getCategoryNo())));

    CategoriesResponse response = new CategoriesResponse();
    response.setTotalCount(categoryResponses.size());
    response.setCategoryResponses(categoryResponses);

    return response;
  }

  public Category saveCategory(SaveCategoryRequest request) {
    Category category = copyCategoryRequestToEntity(request);

    return categoryRepository.save(category);
  }

  // Recursive
  private Category copyCategoryRequestToEntity(SaveCategoryRequest categoryRequest) {
    Category parentsCategory = BeanUtils
        .copyProperties(categoryRequest, Category.class,
            BeanUtils.getNullPropertyNames(categoryRequest));

    List<Category> childCategories = new ArrayList<>();
    List<SaveCategoryRequest> childCategoryRequests = categoryRequest.getSubCategories();
    if (childCategoryRequests != null) {
      childCategoryRequests.forEach(subCategory -> {
        Category childCategory = copyCategoryRequestToEntity(subCategory);
        childCategory.setParent(parentsCategory);
        childCategories.add(childCategory);
      });
    }
    parentsCategory.setSubCategories(childCategories);

    return parentsCategory;
  }
}
