package blog.api.post.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostWriteRequest {

    @ApiModelProperty(value = "제목", required = true)
    private String title;

    @ApiModelProperty(value = "본문", required = true)
    private String body;
}
