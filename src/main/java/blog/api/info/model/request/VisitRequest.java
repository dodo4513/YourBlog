package blog.api.info.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cyclamen on 21/12/2018
 */
@Getter
@Setter
public class VisitRequest {

    @ApiModelProperty(value = "포스트 번호", required = true, position = 10)
    private long postNo;
}
