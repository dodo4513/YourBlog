package blog.api.post.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostsGetRequest {

  @ApiModelProperty(value = "제목", position = 10)
  String title;

  @ApiModelProperty(value = "태그", position = 20)
  String tags;

  @ApiModelProperty(value = "페이지 번호", position = 30)
  long pageNumber = 0;

  @ApiModelProperty(value = "페이지 수", position = 40)
  long pageSize = 10;
}
