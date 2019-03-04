package blog.api.tag.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagBestResponse {
    private String name;
    private long count;
}
