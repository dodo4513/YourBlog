package blog.api.post.model.response;

import blog.api.category.model.response.CategoryResponse;
import blog.api.tag.model.response.TagResponse;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostResponse {

  @ApiModelProperty(value = "포스트 번호", position = 10)
  private long postNo;

  @ApiModelProperty(value = "제목", position = 20)
  private String title;

  @ApiModelProperty(value = "말머리", position = 25)
  private String description;

  @ApiModelProperty(value = "대표이미지", position = 30)
  private String splashImage;

  @ApiModelProperty(value = "본문", position = 35)
  private String body;

  @ApiModelProperty(value = "공개 여부", position = 40)
  private boolean publicYn;

  @ApiModelProperty(value = "카테고리", position = 45)
  private CategoryResponse category;

  @ApiModelProperty(value = "태그", position = 50)
  private List<TagResponse> tags;

  @ApiModelProperty(value = "기타 정보", position = 55)
  private Map<String, String> extraData;

  @ApiModelProperty(value = "등록일", position = 60)
  private LocalDateTime registerYmdt;

  @ApiModelProperty(value = "수정일", position = 70)
  private LocalDateTime updateYmdt;

  @ApiModelProperty(value = "포스트 뷰 수", position = 80)
  private Long viewCount;

  public long getViewCount() {
    return Optional.ofNullable(viewCount).orElse(0L);
  }
}
