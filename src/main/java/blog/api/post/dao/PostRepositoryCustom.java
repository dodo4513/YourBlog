package blog.api.post.dao;

import blog.api.post.model.entity.Post;
import blog.api.post.model.request.GetPostsRequest;
import java.util.List;

public interface PostRepositoryCustom {

  List<Post> getPosts(GetPostsRequest getPostsRequest);

  long getCountPosts(GetPostsRequest getPostsRequest);

}
