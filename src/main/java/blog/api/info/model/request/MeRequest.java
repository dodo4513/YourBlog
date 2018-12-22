package blog.api.info.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

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

    @ApiModelProperty(value = "소개", position = 30)
    private String introduction;

    @ApiModelProperty(value = "기타 정보", position = 40)
    private Map<String, String> extraData;
}
