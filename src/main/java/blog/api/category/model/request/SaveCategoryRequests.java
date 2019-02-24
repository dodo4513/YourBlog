package blog.api.category.model.request;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cyclamen on 13/01/2019
 */
@Getter
@Setter
public class SaveCategoryRequests {

  @ApiModelProperty(value = "카테고리", position = 10)
  private List<SaveCategoryRequest> categoryRequests;

  @ApiModelProperty(value = "삭제할 카테고리 번호", position = 20)
  private Long[] removedCategoryNo;
}
