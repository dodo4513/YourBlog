package blog.api.post.model.response;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostsResponse {

  @ApiModelProperty(value = "포스트 총 개수", position = 10)
  private long totalCount;

  @ApiModelProperty(value = "포스트", position = 20)
  List<PostResponse> postResponses;
}
