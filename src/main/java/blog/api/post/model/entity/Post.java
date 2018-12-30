package blog.api.post.model.entity;

import blog.api.tag.model.entity.Tag;
import blog.common.etc.MapToJsonConverter;
import blog.common.model.entity.BasicColumn;
import java.util.List;
import java.util.Map;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

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

  // 공개 여부
  @Type(type = "yes_no")
  private boolean publicYn;

  // 기타 정보
  @Convert(converter = MapToJsonConverter.class)
  private Map<String, String> extraData;
}
