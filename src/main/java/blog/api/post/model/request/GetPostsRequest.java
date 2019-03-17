package blog.api.post.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPostsRequest {

  @ApiModelProperty(value = "제목", position = 10)
  private String title;

  @ApiModelProperty(value = "태그", position = 20)
  private String tags;

  @ApiModelProperty(value = "카테고리", position = 20)
  private String categories;

  @ApiModelProperty(value = "공개", position = 25)
  private Boolean publicYn;

  @ApiModelProperty(value = "페이지 번호", position = 30)
  private Long pageNumber;

  @ApiModelProperty(value = "페이지 수", position = 40)
  private Long pageSize;
}
