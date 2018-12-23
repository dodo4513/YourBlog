package blog.api.post.model.entity;

import blog.api.tag.model.entity.Tag;
import blog.common.etc.MapToJsonConverter;
import blog.common.model.entity.BasicColumn;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Entity
public class Post extends BasicColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long no;

    // 제목
    private String title;

    // 본문
    private String body;

    // 태그
    @ManyToMany
    @JoinTable(name = "post_tag_mapping", joinColumns = @JoinColumn(name = "post_no"), inverseJoinColumns = @JoinColumn(name = "tag_no"))
    private List<Tag> tags;

    // 기타 정보
    @Convert(converter = MapToJsonConverter.class)
    private Map<String, String> extraData;
}
