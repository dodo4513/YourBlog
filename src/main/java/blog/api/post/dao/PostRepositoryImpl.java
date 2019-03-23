package blog.api.post.dao;


import blog.api.post.model.entity.Post;
import blog.api.post.model.entity.QPost;
import blog.api.post.model.request.GetPostsRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class PostRepositoryImpl extends QuerydslRepositorySupport implements PostRepositoryCustom {

  QPost post = QPost.post;

  public PostRepositoryImpl() {
    super(Post.class);
  }

  @Override
  public List<Post> getPosts(GetPostsRequest request) {
    JPQLQuery<Post> query = getPostJPQLQuery(request);

    if (request.getPageNumber() != null && request.getPageSize() != null) {
      query
          .limit(request.getPageSize())
          .offset((request.getPageNumber() - 1) * request.getPageSize());
    }

    return query
        .where(post.deleteYn.eq(false))
        .orderBy(post.postNo.desc())
        .fetch();
  }


  @Override
  public long getCountPosts(GetPostsRequest request) {

    JPQLQuery<Post> query = getPostJPQLQuery(request);

    return query.fetchCount();
  }

  @Override
  public long updateCategory(long preCategoryNo, long postCategoryNo) {
    return update(post)
        .set(post.categoryNo, postCategoryNo)
        .where(post.categoryNo.eq(preCategoryNo))
        .execute();
  }

  private JPQLQuery<Post> getPostJPQLQuery(GetPostsRequest request) {
    BooleanBuilder condition = new BooleanBuilder();

    if (Strings.isNotEmpty(request.getTitle())) {
      condition.and(post.title.like("%" + request.getTitle() + "%"));
    }

    if (Strings.isNotEmpty(request.getTags())) {
      List<String> tags = Arrays.stream(request.getTags().split(",")).collect(Collectors.toList());
      condition.and(post.tags.any().name.in(tags));
    }

    if (Strings.isNotEmpty(request.getCategories())) {
      List<String> categories = Arrays.stream(request.getCategories().split(",")).collect(Collectors.toList());
      condition.and(post.category.name.in(categories));
    }

    if (request.getPublicYn() != null) {
      condition.and(post.publicYn.eq(request.getPublicYn()));
    }

    return from(post).where(post.deleteYn.eq(false).and(condition));
  }
}
