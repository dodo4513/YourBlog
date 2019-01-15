package blog.api.post.model.response;

import blog.api.tag.model.response.TagResponse;
import blog.common.etc.SystemConstants;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostResponse {

  @ApiModelProperty(value = "포스트 번호", position = 10)
  private long postNo;

  @ApiModelProperty(value = "제목", position = 20)
  private String title;

  @ApiModelProperty(value = "본문", position = 30)
  private String body;

  @ApiModelProperty(value = "공개 여부", position = 35)
  private boolean publicYn;

  @ApiModelProperty(value = "태그", position = 40)
  private List<TagResponse> tags;

  @ApiModelProperty(value = "기타 정보", position = 50)
  private Map<String, String> extraData;

  @ApiModelProperty(value = "등록일", position = 60)
  private LocalDateTime registerYmdt;

  @ApiModelProperty(value = "수정일", position = 70)
  private LocalDateTime updateYmdt;
}
