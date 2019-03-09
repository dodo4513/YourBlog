package blog.api.tag.dao;

import blog.api.tag.model.response.BestTagsResponse;

import java.util.List;

public interface TagRepositoryCustom {
    List<BestTagsResponse> getBestTagsByTagNoCount(long limit);
}
