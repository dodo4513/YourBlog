package blog.api.post.service;

import blog.api.post.dao.PostRepository;
import blog.api.post.model.entity.Post;
import blog.api.post.model.request.PostRequest;
import blog.api.post.model.request.PostsGetRequest;
import blog.api.post.model.response.PostInfoResponse;
import blog.api.post.model.response.PostResponse;
import blog.api.tag.model.entity.Tag;
import blog.api.tag.model.response.TagResponse;
import blog.api.tag.service.TagService;
import blog.common.util.BeanUtils;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final TagService tagService;

  @Autowired
  public PostService(PostRepository postRepository, TagService tagService) {
    this.postRepository = postRepository;
    this.tagService = tagService;
  }

  public PostResponse getPostInfoResponse(long no) {
    Post post = postRepository.findByNoAndDeleteYn(no, false);
    if (post == null) {

      return null;
    }
    PostResponse postResponse = new PostResponse();
    BeanUtils.copyProperties(post, postResponse);

    if (post.getTags() != null) {
      postResponse.setTags(BeanUtils.copyProperties(post.getTags(), TagResponse.class));
    }

    return postResponse;
  }

  public List<PostResponse> getPostResponses(PostsGetRequest postsGetRequest) {
    List<Post> posts = postRepository.getPosts(postsGetRequest);

    List<PostResponse> postResponses = BeanUtils.copyProperties(posts, PostResponse.class);
    postResponses.forEach(postResponse -> postResponse.setTags(BeanUtils.copyProperties(postResponse.getTags(), TagResponse.class)));

    return postResponses;
  }

  @Transactional
  public Post savePost(PostRequest postRequest) {
    Post post = BeanUtils.copyProperties(postRequest, Post.class);

    List<Tag> tags = tagService.saveTags(postRequest.getTags());
    post.setTags(tags);

    return postRepository.save(post);
  }

  @Transactional
  public PostInfoResponse getPostInfoResponse() {
    PostInfoResponse postInfoResponse = new PostInfoResponse();
    postInfoResponse.setTotalPost(postRepository.countByDeleteYn(false));
    postInfoResponse.setPostFor7Days(postRepository
        .countByRegisterYmdtAfterAndDeleteYn(LocalDateTime.now().minusDays(7), false));

    return postInfoResponse;
  }
}
