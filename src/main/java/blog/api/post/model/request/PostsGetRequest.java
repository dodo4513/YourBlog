package blog.api.post.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostsGetRequest {

    @ApiModelProperty(value = "페이지 번호")
    int pageNumber = 0;

    @ApiModelProperty(value = "페이지 수")
    int pageSize = 10;
}
