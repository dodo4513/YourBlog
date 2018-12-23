package blog.api.info.controller;

import blog.api.info.model.request.MeRequest;
import blog.api.info.model.request.VisitRequest;
import blog.api.info.model.response.BlogInfoResponse;
import blog.api.info.model.response.MeResponse;
import blog.api.info.service.InfoService;
import blog.api.info.service.MeService;
import blog.api.info.service.VisitService;
import blog.common.util.ControllerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Info", description = "정보")
@RequestMapping("info")
@RestController
public class InfoApiController {

  private final VisitService visitService;

  private final InfoService infoService;

  private final MeService meService;

  @Autowired
  public InfoApiController(InfoService infoService, VisitService visitService,
      MeService meService) {
    this.infoService = infoService;
    this.visitService = visitService;
    this.meService = meService;
  }

  @GetMapping("me")
  @ApiOperation(value = "내 프로필 조회", notes = "내 프로필을 조회합니다.")
  public ResponseEntity<MeResponse> getMe() {

    return ResponseEntity.ok().body(meService.makeMeResponse());
  }

  @PostMapping("me")
  @ApiOperation(value = "내 프로필 저장", notes = "내 프로필을 저장합니다.")
  public ResponseEntity<?> saveMe(@RequestBody MeRequest meRequest) {
    meService.saveMe(meRequest);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("blog")
  @ApiOperation(value = "블로그 정보 조회", notes = "블로그 여러 정보를 조회합니다. ex) 방문자, 포스팅 정보 등")
  public ResponseEntity<BlogInfoResponse> getCount() {

    return ResponseEntity.ok().body(infoService.makeBlogInfo());
  }

  @PostMapping("visit")
  @ApiOperation(value = "방문 정보 저장", notes = "특정 포스트의 방문 정보를 저장합니다.\nIP로 하루에 1번만 집계되며, 매 자정에 초기화 됩니다.")
  public ResponseEntity<?> saveVisit(VisitRequest visitRequest) {

    // TODO getIP() 가 제대로 동작하는지 서버 올리고 확인해 봐야함.
    visitService.saveVisit(visitRequest, ControllerUtils.getIp());

    return ResponseEntity.noContent().build();
  }
}
