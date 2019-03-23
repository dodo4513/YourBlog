package blog.api.tag.model;

import blog.api.tag.model.response.TagResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TagsResponse {
    @ApiModelProperty(value = "1depth 태그 개수", position = 10)
    private long totalCount;

    @ApiModelProperty(value = "태그", position = 20)
    private List<TagResponse> tagResponses;
}
