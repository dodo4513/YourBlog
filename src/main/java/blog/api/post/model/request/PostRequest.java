package blog.api.post.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    // 제목
    private String title;

    // 본문
    private String body;
}
