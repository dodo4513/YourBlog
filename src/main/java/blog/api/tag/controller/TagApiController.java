package blog.api.tag.controller;

import blog.api.tag.model.request.TagRequest;
import blog.api.tag.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

  @GetMapping("getTags")
  @ApiOperation(value = "태그 List 페이지 조회", notes = "전체 태그 List를 조회합니다.")
  public ResponseEntity<?> getTagList() {

    return ResponseEntity.ok().body(tagService.getTagList());
  }

  @PostMapping("saveTags")
  @ApiOperation(value = "태그 저장", notes = "태그를 저장 합니다.")
  public ResponseEntity<?> saveTag(@RequestBody TagRequest tagRequest) {

    List<TagRequest> tagRequestList = new ArrayList<>();
    tagRequestList.add(tagRequest);
    return ResponseEntity.ok().body(tagService.saveTags(tagRequestList));
  }


}
