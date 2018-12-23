package blog.api.tag.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagRequest {

  @ApiModelProperty(value = "이름", required = true, position = 10)
  private String name;
}
