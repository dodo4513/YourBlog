package blog.api.post.controller;

import blog.api.post.model.request.PostRequest;
import blog.api.post.model.request.PostsGetRequest;
import blog.api.post.model.response.PostResponse;
import blog.api.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Post", description = "포스트")
@RestController
public class PostApiController {

    private final PostService postService;

    @Autowired
    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("posts")
    @ApiOperation(value = "포스트 목록 조회", notes = "포스트 목록을 조회합니다.(paging)")
    public ResponseEntity<?> getPosts(@RequestBody PostsGetRequest postsGetRequest) {

        return ResponseEntity.noContent().build();
    }

    @GetMapping("posts/{no}")
    @ApiOperation(value = "포스트 상세 조회", notes = "포스트 단건을 조회합니다.")
    public ResponseEntity<PostResponse> getPost(@PathVariable long no) {
        PostResponse postResponse = postService.makePostResponse(no);

        if (postResponse == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(postResponse);
    }

    @PostMapping("posts")
    @ApiOperation(value = "포스트 저장", notes = "포스트를 저장합니다", response = PostResponse.class)
    public ResponseEntity<?> savePost(@RequestBody PostRequest postRequest) {
        postService.savePost(postRequest);

        return ResponseEntity.ok().build();
    }
}
