package blog.api.info.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author cyclamen on 22/12/2018
 */
@Getter
@Setter
public class MeResponse {

    @ApiModelProperty(value = "이름", position = 10)
    private String name;

    @ApiModelProperty(value = "대표 이메일", position = 20)
    private String email;

    @ApiModelProperty(value = "등록일", position = 30)
    private LocalDateTime registerYmdt;

    @ApiModelProperty(value = "기타 정보", position = 40)
    private Map<String, String> extraData;
}
