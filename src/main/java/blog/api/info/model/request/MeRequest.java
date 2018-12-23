package blog.api.info.model.request;

import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cyclamen on 22/12/2018
 */
@Getter
@Setter
public class MeRequest {

  @ApiModelProperty(value = "이름", required = true, position = 10)
  private String name;

  @ApiModelProperty(value = "이메일", required = true, position = 20)
  private String email;

  @ApiModelProperty(value = "기타 정보", position = 30)
  private Map<String, String> extraData;
}
