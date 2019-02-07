package blog.api.category.controller;

import blog.api.category.model.request.GetCategoriesRequest;
import blog.api.category.model.request.SaveCategoryRequest;
import blog.api.category.model.response.CategoriesResponse;
import blog.api.category.model.response.CategoryResponse;
import blog.api.category.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cyclamen on 13/01/2019
 */

@Api(tags = "Category", description = "카테고리")
@RestController
@RequestMapping("categories")
public class CategoryApiController {

  private final CategoryService categoryService;

  @Autowired
  public CategoryApiController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  @ApiOperation(value = "카테고리 목록 조회", notes = "카테고리 목록을 조회합니다.")
  public ResponseEntity<CategoriesResponse> getCategories(
      @ModelAttribute GetCategoriesRequest request) {

    CategoriesResponse response = categoryService.getCategoriesResponses(request);

    return ResponseEntity.ok().body(response);
  }

  @GetMapping("{no}")
  @ApiOperation(value = "카테고리 조회", notes = "카테고리를 조회합니다.")
  public ResponseEntity<CategoryResponse> getCategory(@PathVariable long no) {
    CategoryResponse response = categoryService.getCategoryResponse(no);

    if (response == null) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok().body(response);
  }

  @PostMapping
  @ApiOperation(value = "카테고리 저장/수정", notes = "카테고리를 저장/수정합니다")
  public ResponseEntity<?> saveCategory(@RequestBody List<SaveCategoryRequest> requests) {
    categoryService.saveCategory(requests);

    return ResponseEntity.noContent().build();
  }
}
