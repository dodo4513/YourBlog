package blog.api.info.model.response;

import blog.api.post.model.response.PostInfoResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cyclamen on 21/12/2018
 */

@Getter
@Setter
public class BlogInfoResponse {

  @ApiModelProperty(value = "포스트 관련 정보", position = 10)
  PostInfoResponse postInfoResponse;

  @ApiModelProperty(value = "방문자 정보", position = 20)
  VisitInfoResponse visitInfoResponse;
}
