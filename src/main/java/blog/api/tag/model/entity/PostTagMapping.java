package blog.api.tag.model.entity;

import blog.api.post.model.entity.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PostTagMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postTagNo;

    @ManyToOne
    @JoinColumn(name = "post_no", insertable = false, updatable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_no", insertable = false, updatable = false)
    private Tag tag;

}
