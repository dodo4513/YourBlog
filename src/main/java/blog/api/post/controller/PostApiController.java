package blog.api.post.controller;

import blog.api.post.model.entity.Post;
import blog.api.post.model.request.PostWriteRequest;
import blog.api.post.model.request.PostsGetRequest;
import blog.api.post.model.response.PostResponse;
import blog.api.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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
    @ApiOperation(value = "포스트 리스트 조회", notes = "포스트 리스트를 조회합니다.(paging)")
    public ResponseEntity<?> getPosts(@RequestBody PostsGetRequest postsGetRequest) {

        return ResponseEntity.noContent().build();
    }

    @GetMapping("post/{postNo}")
    @ApiOperation(value = "포스트 상세 조회", notes = "포스트 단건을 조회합니다.")
    public ResponseEntity<?> getPost(@PathVariable String postNo) {

        return ResponseEntity.noContent().build();
    }

    @PutMapping("post")
    @ApiOperation(value = "포스트 저장", notes = "포스트를 저장합니다", response = PostResponse.class)
    public ResponseEntity<?> savePost(@RequestBody PostWriteRequest postWriteRequest) {
        Post post = new Post();
        BeanUtils.copyProperties(postWriteRequest, post);
        postService.savePost(post);
        return ResponseEntity.noContent().build();
    }
}
