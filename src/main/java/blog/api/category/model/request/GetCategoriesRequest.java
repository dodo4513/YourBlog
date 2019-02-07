package blog.api.category.model.request;

import blog.common.model.enums.PublicType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cyclamen on 13/01/2019
 */
@Getter
@Setter
public class GetCategoriesRequest {

  @ApiModelProperty(value = "공개", position = 10)
  private PublicType publicType = PublicType.ONLY_PUBLIC;
}
