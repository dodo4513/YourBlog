package blog.api.category.service;

import blog.api.category.dao.CategoryRepository;
import blog.api.category.model.entity.Category;
import blog.api.category.model.request.GetCategoriesRequest;
import blog.api.category.model.request.SaveCategoryRequest;
import blog.api.category.model.request.SaveCategoryRequests;
import blog.api.category.model.response.CategoriesResponse;
import blog.api.category.model.response.CategoryResponse;
import blog.api.post.service.PostService;
import blog.common.util.BeanUtils;
import java.util.ArrayList;
import java.util.Arrays;
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

  @Autowired
  private PostService postService;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository
  ) {
    this.categoryRepository = categoryRepository;
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
    List<Category> categoryList = categoryRepository.getCategories(request);

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
  public List<Category> saveCategories(SaveCategoryRequests requests) {
    if (requests.getMoveCategoryNos().length > 0) {
      Arrays.stream(requests.getMoveCategoryNos()).forEach(nos ->
          postService.moveCategory(nos.getPreCategoryNo(), nos.getPostCategoryNo())
      );
    }

    if (requests.getRemovedCategoryNo().length > 0) {
      categoryRepository.removeCategories(requests.getRemovedCategoryNo());
    }

    return requests.getCategoryRequests().stream()
        .map(request -> categoryRepository.save(copyCategoryRequestToEntity(request, true)))
        .collect(Collectors.toList());
  }

  public CategoryResponse copyCategoryEntityToResponse(Category category) {
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
  private Category copyCategoryRequestToEntity(SaveCategoryRequest request, boolean rootCategory) {

    Category category;

    if (request.getCategoryNo() != 0) {
      category = categoryRepository.findById(request.getCategoryNo()).get();

      BeanUtils.copyProperties(request, category);
    } else {
      category = BeanUtils.copyNullableProperties(request, Category.class);
    }

    if (rootCategory) {
      category.setParent(null);
    }

    List<Category> childCategories = new ArrayList<>();
    List<SaveCategoryRequest> childCategoryRequests = request.getChildren();
    if (childCategoryRequests != null) {
      childCategoryRequests.forEach(childRequest -> {
        Category childCategory = copyCategoryRequestToEntity(childRequest, false);
        childCategory.setParent(category);
        childCategories.add(childCategory);
      });
    }
    category.setChildren(childCategories);

    return category;
  }
}
