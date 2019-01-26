package blog.api.category.model.request;

import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cyclamen on 13/01/2019
 */
@Getter
@Setter
public class SaveCategoryRequest {

  @ApiModelProperty(value = "번호", position = 10)
  private Long categoryNo;

  @ApiModelProperty(value = "이름", required = true, position = 20)
  private String title;

  @ApiModelProperty(value = "공개 여부", position = 30)
  private boolean publicYn = true;

  @ApiModelProperty(value = "하위 카테고리", position = 40)
  private List<SaveCategoryRequest> subCategories;
}
