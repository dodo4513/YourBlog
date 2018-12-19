package blog.api.post.service;

import blog.api.post.dao.PostRepository;
import blog.api.post.model.entity.Post;
import blog.api.post.model.request.PostRequest;
import blog.api.post.model.response.PostResponse;
import blog.api.tag.model.entity.Tag;
import blog.api.tag.model.request.TagRequest;
import blog.api.tag.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final TagService tagRepository;

    @Autowired
    public PostService(PostRepository postRepository, TagService tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    public Optional<Post> getPost(long no) {

        Optional<Post> post = postRepository.findById(no);

        // TODO Response 만드는 메소드를 따로 둬야할까
//        PostResponse postResponse = new PostResponse();
//        BeanUtils.copyProperties(post, postResponse);

        return post;
    }


    @Transactional
    public Post savePost(PostRequest postRequest) {

        Post post = new Post();
        BeanUtils.copyProperties(postRequest, post);

        List<Tag> tags = tagRepository.saveTags(postRequest.getTags());
        post.setTags(tags);

        return postRepository.save(post);
    }
}
