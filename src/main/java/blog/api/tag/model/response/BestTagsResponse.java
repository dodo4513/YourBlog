package blog.api.tag.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BestTagsResponse {
    private String name;
    private long count;
}
