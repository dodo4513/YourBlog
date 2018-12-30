package blog.api.post.dao;

import blog.api.post.model.entity.Post;
import blog.api.post.model.request.PostsGetRequest;
import java.util.List;

public interface PostRepositoryCustom {

  List<Post> getPosts(PostsGetRequest postsGetRequest);

  long getCountPosts(PostsGetRequest postsGetRequest);

}
