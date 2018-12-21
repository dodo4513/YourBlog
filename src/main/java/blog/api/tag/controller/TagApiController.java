package blog.api.tag.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Tag", description = "태그")
@RestController
public class TagApiController {

    @GetMapping("tag")
    @ApiOperation(value = "[미구현] 전체 태그 조회", notes = "[미구현] 전체 태그를 조회합니다.")
    public ResponseEntity<?> getTag() {

        return ResponseEntity.noContent().build();
    }
}
