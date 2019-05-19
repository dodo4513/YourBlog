package blog.api.tag.service;

import blog.api.tag.dao.TagRepository;
import blog.api.tag.model.entity.Tag;
import blog.api.tag.model.request.TagRequest;
import blog.api.tag.model.response.TagResponse;
import blog.api.tag.model.response.TagsResponse;
import blog.common.etc.SystemConstants;
import blog.common.model.enums.CacheName;
import blog.common.service.CacheService;
import blog.common.util.BeanUtils;
import blog.common.util.JacksonUtils;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    cacheService.evict(CacheName.Tags);

    List<Tag> originTags = tagRepository
        .findByNameIn(tagRequests.stream()
            .map(tagRequest -> tagRequest.getName().trim())
            .filter(tagName -> tagName.length() > 0)
            .collect(Collectors.toList()));

    return tagRepository.saveAll(tagRequests.stream()
        .filter(tagRequest -> originTags.stream()
            .noneMatch(originTag -> originTag.getName().trim().equals(tagRequest.getName().trim())))
        .map(tagRequest -> {
          Tag tag = new Tag();
          tag.setName(tagRequest.getName().trim());
          return tag;
        }).collect(Collectors.toList()));
  }

  public List<Tag> getTags(List<String> tagNames) {
    return tagRepository.findByNameIn(tagNames);
  }

  public TagsResponse getTags() {
    String cachedTags = cacheService.get(CacheName.Tags, SystemConstants.TAGS_CACHE_KEY);

    if (cachedTags == null) {
      TagsResponse tagsResponse = new TagsResponse();
      tagsResponse
          .setTagResponses(BeanUtils.copyProperties(tagRepository.findAll(), TagResponse.class));
      tagsResponse.setTotalCount(tagRepository.findAll().size());
      cacheService.put(CacheName.Tags, SystemConstants.TAGS_CACHE_KEY, tagsResponse);

      return tagsResponse;
    }

    return JacksonUtils.toForceModel(cachedTags, TagsResponse.class);
  }
}
