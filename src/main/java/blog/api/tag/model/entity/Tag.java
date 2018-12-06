package blog.api.tag.model.entity;

import blog.api.post.model.entity.BasicColumn;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Tag extends BasicColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 이름
    private String name;
}
