package blog.api.post.model.request;

import blog.api.tag.model.request.TagRequest;
import blog.common.etc.SystemConstants;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavePostRequest {

  @ApiModelProperty(value = "제목", required = true, position = 10)
  private String title;

  @ApiModelProperty(value = "본문", required = true, position = 20)
  private String body;

  @ApiModelProperty(value = "태그", position = 30)
  private List<TagRequest> tags;

  @ApiModelProperty(value = "공개 여부", position = 40)
  private Boolean publicYn = true;

  @ApiModelProperty(value = "기타 정보", position = 50)
  private Map<String, String> extraData;
}
