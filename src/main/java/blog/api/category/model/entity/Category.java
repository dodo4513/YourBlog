package blog.api.category.model.entity;

import blog.common.model.entity.ForBasicTable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 * @author cyclamen on 13/01/2019
 */
@Entity
@Getter
@Setter
public class Category extends ForBasicTable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long categoryNo;

  // 이름
  private String name;

  // 공개 여부
  @Type(type = "yes_no")
  private boolean publicYn;

  // 전시 순서
  private long displayOrder;

  // 부모 카테고리
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "parent_no")
  private Category parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Category> children;
}
