package blog.api.category.model.response;

import blog.api.category.model.entity.Category;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cyclamen on 13/01/2019
 */
@Setter
@Getter
public class CategoryResponse {

  @ApiModelProperty(value = "카테고리 번호", position = 10)
  private long categoryNo;

  @ApiModelProperty(value = "제목", position = 20)
  private String name;

  @ApiModelProperty(value = "공개 여부", position = 30)
  private boolean publicYn;

  @ApiModelProperty(value = "전시 순서", position = 35)
  private long displayOrder;

  @ApiModelProperty(value = "게시중인 포스트 수(공개 포스트만 집계)", position = 40)
  private long totalNumberOfPosts;

  @ApiModelProperty(value = "자식 카테고리", position = 45)
  private List<CategoryResponse> children;

  @ApiModelProperty(value = "등록일", position = 50)
  private LocalDateTime registerYmdt;

  @ApiModelProperty(value = "수정일", position = 60)
  private LocalDateTime updateYmdt;
}
