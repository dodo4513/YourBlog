package blog.api.post.model.response;

import blog.api.tag.model.entity.Tag;
import blog.api.tag.model.response.TagResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostResponse {

    @ApiModelProperty(value = "포스트 번호", position = 10)
    private Long no;

    @ApiModelProperty(value = "제목", position = 20)
    private String title;

    @ApiModelProperty(value = "본문", position = 30)
    private String body;

    @ApiModelProperty(value = "태그", position = 40)
    private List<TagResponse> tags;
}
