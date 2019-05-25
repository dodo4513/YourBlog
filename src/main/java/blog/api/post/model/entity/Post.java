package blog.api.post.model.entity;

import blog.api.category.model.entity.Category;
import blog.api.tag.model.entity.Tag;
import blog.common.etc.MapToJsonConverter;
import blog.common.model.entity.ForBasicTable;
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
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Setter
@Getter
@Entity
public class Post extends ForBasicTable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long postNo;

  // 제목
  private String title;

  // 말머리
  private String description;

  // 대표이미지
  private String splashImage;

  // 본문
  private String body;

  // 태그
  @ManyToMany
  @JoinTable(name = "post_tag_mapping", joinColumns = @JoinColumn(name = "post_no"), inverseJoinColumns = @JoinColumn(name = "tag_no"))
  private List<Tag> tags;

  // 공개 여부
  @Type(type = "yes_no")
  private boolean publicYn;

  // 카테고리
  private long categoryNo;

  // 포스트 뷰 수
  private Long viewCount;

  @ManyToOne
  @JoinColumn(name = "categoryNo", insertable = false, updatable = false)
  private Category category;

  // 기타 정보
  @Convert(converter = MapToJsonConverter.class)
  private Map<String, String> extraData;
}
