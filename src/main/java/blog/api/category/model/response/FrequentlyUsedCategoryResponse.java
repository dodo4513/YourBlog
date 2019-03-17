package blog.api.category.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FrequentlyUsedCategoryResponse {
    private String name;
    private long count;
}
