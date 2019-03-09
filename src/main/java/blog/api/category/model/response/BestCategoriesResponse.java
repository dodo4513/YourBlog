package blog.api.category.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BestCategoriesResponse {
    private String title;
    private long count;
}
