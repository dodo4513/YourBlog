package blog.api.category.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cyclamen on 13/01/2019
 */
@Getter
@Setter
public class SaveCategoryRequest {

  @ApiModelProperty(value = "이름", required = true, position = 10)
  private String title;

  @ApiModelProperty(value = "공개 여부", position = 20)
  private boolean publicYn = true;
}
