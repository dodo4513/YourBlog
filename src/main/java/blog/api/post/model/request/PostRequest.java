package blog.api.post.model.request;

import blog.api.tag.model.request.TagRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRequest {

    @ApiModelProperty(value = "제목", required = true, position = 10)
    private String title;

    @ApiModelProperty(value = "본문", required = true, position = 20)
    private String body;

    @ApiModelProperty(value = "태그", position = 20)
    private List<TagRequest> tags;
}
