package blog.api.tag.dao;

import blog.api.tag.model.response.FrequentlyUsedTagsResponse;

import java.util.List;

public interface TagRepositoryCustom {
    List<FrequentlyUsedTagsResponse> getFrequentlyUsedTagsByLimit(long limit);
}
