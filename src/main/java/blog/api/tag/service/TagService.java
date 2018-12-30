package blog.api.tag.service;

import blog.api.tag.dao.TagRepository;
import blog.api.tag.model.entity.Tag;
import blog.api.tag.model.request.TagRequest;
import blog.api.tag.model.response.TagResponse;
import blog.common.etc.CacheName;
import blog.common.etc.SystemConstants;
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

  public List<TagResponse> getTags() {
    String cachedTags = cacheService.get(CacheName.Tags, SystemConstants.TAGS_CACHE_KEY);

    if (cachedTags == null) {
      List<TagResponse> tagResponses = BeanUtils
          .copyProperties(tagRepository.findAll(), TagResponse.class);
      cacheService.put(CacheName.Tags, SystemConstants.TAGS_CACHE_KEY, tagResponses);

      return tagResponses;
    }

    return JacksonUtils.toForceList(cachedTags, TagResponse.class);
  }
}
