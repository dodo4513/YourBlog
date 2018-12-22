package blog.api.info.service;

import blog.api.info.dao.BlogConfigRepository;
import blog.api.info.model.entity.BlogConfig;
import blog.api.info.model.enums.ConfigCode;
import blog.common.util.JacksonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cyclamen on 22/12/2018
 */
@Service
public class BlogConfigService {

    private final BlogConfigRepository blogConfigRepository;

    @Autowired
    public BlogConfigService(BlogConfigRepository blogConfigRepository) {
        this.blogConfigRepository = blogConfigRepository;
    }

    public <T> BlogConfig saveBlogConfig(T data, ConfigCode configCode) {
        BlogConfig blogConfig = new BlogConfig();
        blogConfig.setConfigCode(configCode);
        blogConfig.setContents(JacksonUtils.toForceJson(data));

        return blogConfigRepository.save(blogConfig);
    }

}
