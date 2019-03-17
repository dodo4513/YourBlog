package blog.api.category.service;

import blog.api.category.dao.CategoryRepository;
import blog.api.category.model.entity.Category;
import blog.api.category.model.request.GetCategoriesRequest;
import blog.api.category.model.request.SaveCategoryRequest;
import blog.api.category.model.request.SaveCategoryRequests;
import blog.api.category.model.response.CategoriesResponse;
import blog.api.category.model.response.BestCategoriesResponse;
import blog.api.category.model.response.CategoryResponse;
import blog.api.category.model.response.ListingCategoriesResponse;
import blog.api.post.service.PostService;
import blog.common.etc.SystemConstants;
import blog.common.model.enums.CacheName;
import blog.common.model.enums.PublicType;
import blog.common.service.CacheService;
import blog.common.util.BeanUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import blog.common.util.JacksonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cyclamen on 13/01/2019
 */
@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  private final CacheService cacheService;

  @Autowired
  private PostService postService;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository, CacheService cacheService) {
    this.categoryRepository = categoryRepository;
    this.cacheService = cacheService;
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
      categoryList = categoryRepository.findByParentIsNullAndDeleteYn(false);
    } else if (request.getPublicType() == PublicType.ONLY_PUBLIC) {
      categoryList = categoryRepository.findByPublicYnAndParentIsNullAndDeleteYn(true, false);
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

  public List<ListingCategoriesResponse> getListingCategoriesResponse() {
    String cachedCategories = cacheService.get(CacheName.Categories, SystemConstants.CATEGORYS_CACHE_KEY);

    if(cachedCategories == null) {
        List<ListingCategoriesResponse> listingCategoriesResponses =
                categoryRepository.findAll().stream()
                        .map(category -> {
                          ListingCategoriesResponse listingCategoriesResponse = new ListingCategoriesResponse();
                          listingCategoriesResponse.setCategoryNo(category.getCategoryNo());
                          listingCategoriesResponse.setName(category.getTitle());
                          return listingCategoriesResponse;
                        }).collect(Collectors.toList());
        cacheService.put(CacheName.Categories, SystemConstants.CATEGORYS_CACHE_KEY, listingCategoriesResponses);
        return listingCategoriesResponses;
    }

    return JacksonUtils.toForceList(cachedCategories, ListingCategoriesResponse.class);
  }

  public List<BestCategoriesResponse> getBestCategorys(long limit) {
    String cachedBestCategories = cacheService.get(CacheName.BestCategories, SystemConstants.CATEGORYS_CACHE_KEY);

    if(cachedBestCategories == null) {
      return getBestCategorysByCategoryNoCount(limit);
    }

    List<BestCategoriesResponse> bestCategoriesResponses = JacksonUtils.toForceList(cachedBestCategories, BestCategoriesResponse.class);
    assert bestCategoriesResponses != null;
    if(bestCategoriesResponses.size() != limit) {
      return getBestCategorysByCategoryNoCount(limit);
    }

    return bestCategoriesResponses;
  }

  private List<BestCategoriesResponse> getBestCategorysByCategoryNoCount(long limit) {
    List<BestCategoriesResponse> bestCategorysRespons = categoryRepository.getBestCategorysByCategoryNoCount(limit);

    cacheService.put(CacheName.BestCategories, SystemConstants.CATEGORYS_CACHE_KEY, bestCategorysRespons);
    return bestCategorysRespons;
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
