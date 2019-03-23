package blog.api.tag.controller;

import blog.api.tag.model.TagsResponse;
import blog.api.tag.model.request.FrequentlyUsedTagRequest;
import blog.api.tag.model.response.FrequentlyUsedTagsResponse;
import blog.api.tag.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Tag", description = "태그")
@RestController
@RequestMapping("tags")
public class TagApiController {

  private final TagService tagService;

  @Autowired
  public TagApiController(TagService tagService) {
    this.tagService = tagService;
  }

  @GetMapping
  @ApiOperation(value = "전체 태그 조회", notes = "전체 태그를 조회합니다.")
  public ResponseEntity<TagsResponse> getTag() {

    return ResponseEntity.ok().body(tagService.getTags());
  }

  @GetMapping("frequentlyUsed")
  @ApiOperation(value = "지정된 상위 태그 조회", notes = "지정된 상위 태그 조회 합니다.")
  public ResponseEntity<List<FrequentlyUsedTagsResponse>> getFrequentlyUsedTags(@ModelAttribute FrequentlyUsedTagRequest request) {

    return ResponseEntity.ok().body(tagService.getFrequentlyUsedTags(request));
  }
}
