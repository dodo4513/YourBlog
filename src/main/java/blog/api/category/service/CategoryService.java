package blog.api.category.service;

import blog.api.category.dao.CategoryRepository;
import blog.api.category.model.entity.Category;
import blog.api.category.model.request.GetCategoriesRequest;
import blog.api.category.model.request.SaveCategoryRequest;
import blog.api.category.model.response.CategoriesResponse;
import blog.api.category.model.response.CategoryResponse;
import blog.api.post.service.PostService;
import blog.common.model.enums.PublicType;
import blog.common.util.BeanUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    List<Category> categoryList = null;
    if (request.getPublicType() == PublicType.ALL) {
      categoryList = categoryRepository.findByParentIsNull();
    } else if (request.getPublicType() == PublicType.ONLY_PUBLIC) {
      categoryList = categoryRepository.findByPublicYnAndParentIsNull(true);
    }

    assert categoryList != null;

    List<CategoryResponse> categoryResponses = categoryList.stream()
        .map(this::copyCategoryEntityToResponse)
        .collect(Collectors.toList());

    CategoriesResponse response = new CategoriesResponse();
    response.setTotalCount(categoryResponses.size());
    response.setCategoryResponses(categoryResponses);

    return response;
  }

  @Transactional
  public List<Category> saveCategory(List<SaveCategoryRequest> requests) {

    // FIXME 기존 no를 보존해야해 잉 근대 저장도잘안대는군?
    categoryRepository.deleteAll();

    return requests.stream()
        .map(request -> categoryRepository.save(copyCategoryRequestToEntity(request)))
        .collect(Collectors.toList());
  }

  private CategoryResponse copyCategoryEntityToResponse(Category category) {
    CategoryResponse parentsResponse = BeanUtils
        .copyNullableProperties(category, CategoryResponse.class);

    List<CategoryResponse> childCategoryResponses = new ArrayList<>();
    List<Category> childCategories = category.getChildren();
    if (childCategories != null) {
      childCategories.forEach(subCategory -> {
        CategoryResponse categoryResponse = copyCategoryEntityToResponse(subCategory);
        childCategoryResponses.add(categoryResponse);
      });
    }
    parentsResponse.setChildren(childCategoryResponses);
    parentsResponse.setTotalNumberOfPosts(
        postService.getCountOfPostsInCategoryNo(parentsResponse.getCategoryNo()));

    return parentsResponse;
  }

  // Recursive
  private Category copyCategoryRequestToEntity(SaveCategoryRequest categoryRequest) {
    Category parentsCategory = BeanUtils.copyNullableProperties(categoryRequest, Category.class);

    List<Category> childCategories = new ArrayList<>();
    List<SaveCategoryRequest> childCategoryRequests = categoryRequest.getChildren();
    if (childCategoryRequests != null) {
      childCategoryRequests.forEach(subCategory -> {
        Category childCategory = copyCategoryRequestToEntity(subCategory);
        childCategory.setParent(parentsCategory);
        childCategories.add(childCategory);
      });
    }
    parentsCategory.setChildren(childCategories);

    return parentsCategory;
  }
}
