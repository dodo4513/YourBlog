package blog.api.tag.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FrequentlyUsedTagsResponse {
    private String name;
    private long count;
}
