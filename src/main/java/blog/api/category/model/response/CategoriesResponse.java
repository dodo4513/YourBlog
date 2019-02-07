package blog.api.category.model.response;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cyclamen on 13/01/2019
 */
@Setter
@Getter
public class CategoriesResponse {

  @ApiModelProperty(value = "1depth 카테고리 개수", position = 10)
  private long totalCount;

  @ApiModelProperty(value = "카테고리", position = 20)
  private List<CategoryResponse> categoryResponses;
}
