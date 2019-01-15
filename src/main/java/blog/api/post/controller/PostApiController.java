package blog.api.post.controller;

import blog.api.post.model.request.GetPostsRequest;
import blog.api.post.model.request.SavePostRequest;
import blog.api.post.model.response.PostResponse;
import blog.api.post.model.response.PostsResponse;
import blog.api.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<PostsResponse> getPosts(@ModelAttribute GetPostsRequest getPostsRequest) {

    return ResponseEntity.ok().body(postService.getPostsResponse(getPostsRequest));
  }

  @GetMapping("posts/{no}")
  @ApiOperation(value = "포스트 상세 조회", notes = "포스트를 조회합니다.")
  public ResponseEntity<PostResponse> getPost(@PathVariable long no) {
    PostResponse postResponse = postService.getPostResponse(no);

    if (postResponse == null) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok().body(postResponse);
  }

  @PostMapping("posts")
  @ApiOperation(value = "포스트 저장", notes = "포스트를 저장합니다")
  public ResponseEntity<?> savePost(@RequestBody SavePostRequest savePostRequest) {
    postService.savePost(savePostRequest);

    return ResponseEntity.noContent().build();
  }
}
