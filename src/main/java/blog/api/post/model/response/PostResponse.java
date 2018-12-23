package blog.api.post.model.response;

import blog.api.tag.model.response.TagResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PostResponse {

    @ApiModelProperty(value = "포스트 번호", position = 10)
    private long no;

    @ApiModelProperty(value = "제목", position = 20)
    private String title;

    @ApiModelProperty(value = "본문", position = 30)
    private String body;

    @ApiModelProperty(value = "태그", position = 40)
    private List<TagResponse> tags;

    @ApiModelProperty(value = "기타 정보", position = 50)
    private Map<String, String> extraData;

    @ApiModelProperty(value = "등록일", position = 60)
    private LocalDateTime registerYmdt;

    @ApiModelProperty(value = "수정일", position = 70)
    private LocalDateTime updateYmdt;
}
