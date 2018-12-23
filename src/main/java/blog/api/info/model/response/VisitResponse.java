package blog.api.info.model.response;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cyclamen on 21/12/2018
 */
@Getter
@Setter
public class VisitResponse {

  @ApiModelProperty(value = "방문 정보 번호", position = 10)
  private Long no;

  @ApiModelProperty(value = "포스트 번호", position = 20)
  private Long postNo;

  @ApiModelProperty(value = "등록일", position = 30)
  private LocalDateTime registerYmdt;
}
