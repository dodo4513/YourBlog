package blog.api.tag.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TagResponse {

  @ApiModelProperty(value = "태그 번호", position = 10)
  private Long tagNo;

  @ApiModelProperty(value = "이름", position = 20)
  private String name;
}
