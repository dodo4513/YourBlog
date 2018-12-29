package blog.api.info.service;

import blog.api.info.model.response.BlogInfoResponse;
import blog.api.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cyclamen on 21/12/2018
 */

@Service
public class InfoService {

  private final VisitService visitService;

  private final PostService postService;

  @Autowired
  public InfoService(PostService postService, VisitService visitService) {
    this.postService = postService;
    this.visitService = visitService;
  }

  public BlogInfoResponse makeBlogInfo() {
    BlogInfoResponse blogInfoResponse = new BlogInfoResponse();
    blogInfoResponse.setPostInfoResponse(postService.getPostInfoResponse());
    blogInfoResponse.setVisitInfoResponse(visitService.makeVisitResponse());

    return blogInfoResponse;
  }

}
