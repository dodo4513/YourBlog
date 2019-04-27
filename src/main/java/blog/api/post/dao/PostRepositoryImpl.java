package blog.api.post.dao;


import blog.api.post.model.entity.Post;
import blog.api.post.model.entity.QPost;
import blog.api.post.model.request.GetPostsRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
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

    if (request.getTags() != null) {
      condition.and(post.tags.any().name.in(request.getTags()));
    }

    if (request.getCategories() != null) {
      condition.and(post.category.name.in(request.getCategories()));
    }

    if (request.getPublicYn() != null) {
      condition.and(post.publicYn.eq(request.getPublicYn()));
    }

    return from(post).where(post.deleteYn.eq(false).and(condition));
  }
}
