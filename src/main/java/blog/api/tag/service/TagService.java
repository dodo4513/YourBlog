package blog.api.tag.service;

import blog.api.tag.dao.TagRepository;
import blog.api.tag.model.entity.Tag;
import blog.api.tag.model.request.TagRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {

  private final TagRepository tagRepository;

  @Autowired
  public TagService(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  @Transactional
  public List<Tag> saveTags(List<TagRequest> tagRequests) {
    List<Tag> tags = tagRequests.stream().map(tagRequest -> {
      Tag tag = new Tag();
      tag.setName(tagRequest.getName());
      return tag;
    }).collect(Collectors.toList());

    return tagRepository.saveAll(tags);
  }
}
