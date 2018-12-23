package blog.api.info.service;

import blog.api.info.model.enums.ConfigCode;
import blog.api.info.model.request.MeRequest;
import blog.api.info.model.response.MeResponse;
import blog.api.info.model.vo.Me;
import blog.common.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cyclamen on 23/12/2018
 */
@Service
public class MeService {

  private final BlogConfigService blogConfigService;

  @Autowired
  public MeService(BlogConfigService blogConfigService) {
    this.blogConfigService = blogConfigService;
  }

  @Transactional
  public Me saveMe(MeRequest meRequest) {
    Me me = BeanUtils.copyProperties(meRequest, Me.class);
    blogConfigService.saveBlogConfig(me, ConfigCode.ME);

    return me;
  }

  public MeResponse makeMeResponse() {
    return blogConfigService.getBlogConfig(ConfigCode.ME, MeResponse.class);
  }
}
