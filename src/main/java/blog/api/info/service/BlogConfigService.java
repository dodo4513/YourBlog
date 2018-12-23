package blog.api.info.service;

import blog.api.info.dao.BlogConfigRepository;
import blog.api.info.model.entity.BlogConfig;
import blog.api.info.model.enums.ConfigCode;
import blog.common.util.JacksonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

    @Transactional
    public <T> BlogConfig saveBlogConfig(T data, ConfigCode configCode) {
        BlogConfig blogConfig = blogConfigRepository.findByConfigCode(configCode);
        if (blogConfig == null) {
            blogConfig = new BlogConfig();
            blogConfig.setConfigCode(configCode);
        } else {
            blogConfig.setRegisterYmdt(LocalDateTime.now());
        }
        blogConfig.setContents(JacksonUtils.toForceJson(data));

        return blogConfigRepository.save(blogConfig);
    }

    <T> T getBlogConfig(ConfigCode configCode, Class<T> clazz) {
        BlogConfig blogConfig = blogConfigRepository.findByConfigCode(configCode);

        return JacksonUtils.toForceModel(blogConfig.getContents(), clazz);
    }
}
