package blog.api.tag.model.response;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TagsResponse {

  @ApiModelProperty(value = "태그 개수", position = 10)
  private long totalCount;

  @ApiModelProperty(value = "카테고리", position = 20)
  private List<TagResponse> tagResponses;
}
