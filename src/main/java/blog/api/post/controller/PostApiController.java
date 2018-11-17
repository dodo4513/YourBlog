package blog.api.post.controller;

import blog.api.post.model.entity.Post;
import blog.api.post.model.request.PostRequest;
import blog.api.post.service.PostService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Post")
@RestController
@RequestMapping("post")
public class PostApiController {

    private final PostService postService;

    @Autowired
    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @PutMapping
    public ResponseEntity<?> savePost(@RequestBody PostRequest postRequest) {
        Post post = new Post();
        BeanUtils.copyProperties(postRequest, post);

        return ResponseEntity.ok(postService.savePost(post));
    }
}
