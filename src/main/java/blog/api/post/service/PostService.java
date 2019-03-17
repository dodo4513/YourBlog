package blog.api.post.service;

import blog.api.category.service.CategoryService;
import blog.api.post.dao.PostRepository;
import blog.api.post.model.entity.Post;
import blog.api.post.model.request.GetPostsRequest;
import blog.api.post.model.request.SavePostRequest;
import blog.api.post.model.response.PostInfoResponse;
import blog.api.post.model.response.PostResponse;
import blog.api.post.model.response.PostsResponse;
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
  private final CategoryService categoryService;

  @Autowired
  public PostService(PostRepository postRepository, TagService tagService, CategoryService categoryService) {
    this.postRepository = postRepository;
    this.tagService = tagService;
    this.categoryService = categoryService;
  }

  public PostResponse getPostResponse(long no) {
    Post post = postRepository.findByPostNoAndDeleteYn(no, false);
    
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

  public PostsResponse getPostsResponse(GetPostsRequest getPostsRequest) {

    PostsResponse postsResponse = new PostsResponse();
    List<Post> posts = postRepository.getPosts(getPostsRequest);

    List<PostResponse> postResponses = BeanUtils.copyProperties(posts, PostResponse.class);
    postResponses.forEach(postResponse -> {
      postResponse.setTags(BeanUtils.copyProperties(postResponse.getTags(), TagResponse.class));
      postResponse.setCategory(categoryService.copyCategoryEntityToResponse(
          posts.stream()
              .filter(p -> p.getPostNo() == postResponse.getPostNo())
              .findAny()
              .get()
              .getCategory()));
    });

    postsResponse.setPostResponses(postResponses);
    postsResponse.setTotalCount(postRepository.getCountPosts(getPostsRequest));

    return postsResponse;
  }

  @Transactional
  public Post savePost(SavePostRequest request) {
    Post post = BeanUtils.copyProperties(request, Post.class);

    List<Tag> tags = tagService.saveTags(request.getTags());
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

  public long getCountOfPostsInCategoryNo(long no) {
    return postRepository.countByCategory_CategoryNoAndDeleteYn(no, false);
  }

  @Transactional
  public long moveCategory(long preCategoryNo, long postCategoryNo) {
    return postRepository.updateCategory(preCategoryNo, postCategoryNo);
  }
}
