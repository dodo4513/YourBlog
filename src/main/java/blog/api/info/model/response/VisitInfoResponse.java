package blog.api.info.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cyclamen on 21/12/2018
 */
@Getter
@Setter
public class VisitInfoResponse {

    @ApiModelProperty(value = "총 방문자 수", position = 10)
    long totalVisit;

    @ApiModelProperty(value = "오늘 방문자 수", position = 20)
    long todayVisit;
}
