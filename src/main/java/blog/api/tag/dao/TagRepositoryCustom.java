package blog.api.tag.dao;

import blog.api.tag.model.response.TagBestResponse;

import java.util.List;

public interface TagRepositoryCustom {
    List<TagBestResponse> getBestTagsByTagNoCount(long count);
}
