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

  @ApiModelProperty(value = "공개 여부", position = 10)
  private PublicType publicType = PublicType.ONLY_PUBLIC;

  @ApiModelProperty(value = "최상위 카테고리만 조회", position = 10)
  private Boolean onlyRootLevel = false;

}
