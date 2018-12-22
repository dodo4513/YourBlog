package blog.api.info.controller;

import blog.api.info.model.enums.ConfigCode;
import blog.api.info.model.request.MeRequest;
import blog.api.info.model.request.VisitRequest;
import blog.api.info.model.response.BlogInfoResponse;
import blog.api.info.model.response.VisitResponse;
import blog.api.info.service.BlogConfigService;
import blog.api.info.service.InfoService;
import blog.api.info.service.VisitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Info", description = "정보")
@RequestMapping("info")
@RestController
public class InfoApiController {

    private final VisitService visitService;

    private final InfoService infoService;

    private final BlogConfigService blogConfigService;

    @Autowired
    public InfoApiController(InfoService infoService, VisitService visitService, BlogConfigService blogConfigService) {
        this.infoService = infoService;
        this.visitService = visitService;
        this.blogConfigService = blogConfigService;
    }

    @GetMapping("me")
    @ApiOperation(value = "[미구현] 내 프로필 조회", notes = "[미구현] 내 프로필을 조회합니다.")
    public ResponseEntity<?> getMe() {

        return ResponseEntity.noContent().build();
    }

    @PostMapping("me")
    @ApiOperation(value = "내 프로필 저장", notes = "내 프로필을 저장합니다.")
    public ResponseEntity<?> saveMe(@RequestBody MeRequest meRequest) {

        return ResponseEntity.ok().body(blogConfigService.saveBlogConfig(meRequest, ConfigCode.ME));
    }

    @GetMapping("blog")
    @ApiOperation(value = "블로그 정보 조회", notes = "블로그 여러 정보를 조회합니다. ex) 방문자, 포스팅 정보 등")
    public ResponseEntity<BlogInfoResponse> getCount() {

        return ResponseEntity.ok().body(infoService.makeBlogInfo());
    }

    @PostMapping("visit")
    @ApiOperation(value = "방문 정보 저장", notes = "특정 포스트의 방문 정보를 저장합니다.")
    public ResponseEntity<VisitResponse> saveVisit(VisitRequest visitRequest) {

        return ResponseEntity.ok().body(visitService.saveVisit(visitRequest));
    }
}
