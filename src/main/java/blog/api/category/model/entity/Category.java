package blog.api.category.model.entity;

import blog.common.model.entity.BasicColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 * @author cyclamen on 13/01/2019
 */
@Entity
@Getter
@Setter
public class Category extends BasicColumn {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long categoryNo;

  // 이름
  private String title;

  // 공개 여부
  @Type(type = "yes_no")
  private boolean publicYn;
}
