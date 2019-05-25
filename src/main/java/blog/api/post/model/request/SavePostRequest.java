package blog.api.post.model.request;

import blog.api.tag.model.request.TagRequest;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavePostRequest {

  @ApiModelProperty(value = "포스트번호", position = 5)
  private Long postNo;

  @ApiModelProperty(value = "제목", required = true, position = 10)
  private String title;

  @ApiModelProperty(value = "말머리", position = 15)
  private String description;

  @ApiModelProperty(value = "대표 이미지", position = 20)
  private String splashImage;

  @ApiModelProperty(value = "본문", required = true, position = 25)
  private String body;

  @ApiModelProperty(value = "카테고리 번호", position = 30)
  private long categoryNo;

  @ApiModelProperty(value = "태그", position = 40)
  private List<TagRequest> tags;

  @ApiModelProperty(value = "공개 여부", position = 50)
  private Boolean publicYn = true;

  @ApiModelProperty(value = "기타 정보", position = 60)
  private Map<String, String> extraData;
}
