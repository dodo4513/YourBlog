package blog.api.tag.controller;

import blog.api.tag.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Tag", description = "태그")
@RestController
public class TagApiController {

  private final TagService tagService;

  @Autowired
  public TagApiController(TagService tagService) {
    this.tagService = tagService;
  }

  @GetMapping("tags")
  @ApiOperation(value = "전체 태그 조회", notes = "전체 태그를 조회합니다.")
  public ResponseEntity<?> getTag() {

    return ResponseEntity.ok().body(tagService.getTags());
  }

  @GetMapping("tags/counts/{count}")
  @ApiOperation(value = "지정된 상위 태그 조회", notes = "지정된 상위 태그 조회 합니다.")
  public ResponseEntity<?> getBestTag(@PathVariable long count) {

    return ResponseEntity.ok().body(tagService.getBestTags(count));
  }
}
