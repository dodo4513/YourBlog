package blog.api.tag.service;

import blog.api.tag.dao.TagRepository;
import blog.api.tag.model.TagsResponse;
import blog.api.tag.model.entity.Tag;
import blog.api.tag.model.request.FrequentlyUsedTagRequest;
import blog.api.tag.model.request.TagRequest;
import blog.api.tag.model.response.FrequentlyUsedTagsResponse;
import blog.api.tag.model.response.TagResponse;
import blog.common.etc.SystemConstants;
import blog.common.model.enums.CacheName;
import blog.common.service.CacheService;
import blog.common.util.BeanUtils;
import blog.common.util.JacksonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

  private final TagRepository tagRepository;
  private final CacheService cacheService;

  @Autowired
  public TagService(TagRepository tagRepository, CacheService cacheService) {
    this.tagRepository = tagRepository;
    this.cacheService = cacheService;
  }

  @Transactional
  public List<Tag> saveTags(List<TagRequest> tagRequests) {
    List<Tag> originTags = tagRepository.findAll();

    cacheService.evict(CacheName.Tags);

    return tagRepository.saveAll(tagRequests
        .stream()
        .filter(tagRequest -> originTags
            .stream()
            .noneMatch(tag -> tag.getName().equals(tagRequest.getName())))
        .filter(tagRequest -> tagRequest.getName().trim().length() != 0)
        .map(tagRequest -> {
          Tag tag = new Tag();
          tag.setName(tagRequest.getName());
          return tag;
        }).collect(Collectors.toList()));
  }

  public TagsResponse getTags() {
    String cachedTags = cacheService.get(CacheName.Tags, SystemConstants.TAGS_CACHE_KEY);

    TagsResponse response = new TagsResponse();

    if (cachedTags == null) {
      List<TagResponse> tagResponses = BeanUtils
          .copyProperties(tagRepository.findAll(), TagResponse.class);

      cacheService.put(CacheName.Tags, SystemConstants.TAGS_CACHE_KEY, tagResponses);

      response.setTotalCount(tagResponses.size());
      response.setTagResponses(tagResponses);

      return response;
    }

    List<TagResponse> tagResponses = JacksonUtils.toForceList(cachedTags, TagResponse.class);

    assert tagResponses != null;
    response.setTotalCount(tagResponses.size());
    response.setTagResponses(tagResponses);

    return response;
  }

  public List<FrequentlyUsedTagsResponse> getFrequentlyUsedTags(FrequentlyUsedTagRequest request) {

    String cachedBestTags = cacheService.get(CacheName.FrequentlyUsedTags, SystemConstants.TAGS_CACHE_KEY);

    long limit = request.getLimit();

    if (cachedBestTags == null) {
      return getFrequentlyUsedTagsByLimit(limit);
    }

    List<FrequentlyUsedTagsResponse> bestTagsRespons = JacksonUtils.toForceList(cachedBestTags, FrequentlyUsedTagsResponse.class);
    assert bestTagsRespons != null;
    if(bestTagsRespons.size() != limit) {
      return getFrequentlyUsedTagsByLimit(limit);
    }

    return bestTagsRespons;
  }

  private List<FrequentlyUsedTagsResponse> getFrequentlyUsedTagsByLimit(long limit) {

    List<FrequentlyUsedTagsResponse> bestTagsRespons = tagRepository.getFrequentlyUsedTagsByLimit(limit);
    cacheService.put(CacheName.FrequentlyUsedTags, SystemConstants.TAGS_CACHE_KEY, bestTagsRespons);

    return bestTagsRespons;
  }
}
