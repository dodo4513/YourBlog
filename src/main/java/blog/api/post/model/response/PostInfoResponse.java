package blog.api.post.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cyclamen on 21/12/2018
 */
@Setter
@Getter
public class PostInfoResponse {

  @ApiModelProperty(value = "총 포스트 수", position = 110)
  long totalPost;

  @ApiModelProperty(value = "7일간 작성한 포스트 수", position = 120)
  long postFor7Days;
}
