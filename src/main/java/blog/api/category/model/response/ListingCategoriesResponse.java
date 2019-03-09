package blog.api.category.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListingCategoriesResponse {
    @ApiModelProperty(value = "카테고리 번호", position = 10)
    private Long categoryNo;

    @ApiModelProperty(value = "이름", position = 20)
    private String title;
}
