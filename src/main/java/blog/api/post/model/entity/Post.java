package blog.api.post.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Post extends BasicColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long no;

    // 제목
    private String title;

    // 본문
    private String body;
}
