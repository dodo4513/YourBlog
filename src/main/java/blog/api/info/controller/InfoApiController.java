package blog.api.info.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Info", description = "블로그 정보")
@RequestMapping("info")
@RestController
public class InfoApiController {

    @GetMapping("me")
    @ApiOperation(value = "내 프로필 조회", notes = "내 프로필을 조회합니다.")
    public ResponseEntity<?> getMe() {

        return ResponseEntity.noContent().build();
    }

    @GetMapping("count")
    @ApiOperation(value = "접속자 수 조회", notes = "접속자 수 정보를 조회합니다.")
    public ResponseEntity<?> getCount() {

        // TODO 그날 접속한 수, 지금껏 접속한 수 return
        return ResponseEntity.noContent().build();
    }
}
