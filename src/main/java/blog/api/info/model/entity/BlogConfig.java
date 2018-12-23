package blog.api.info.model.entity;

import blog.api.info.model.enums.ConfigCode;
import blog.common.model.entity.ImmutableBasicColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cyclamen on 22/12/2018
 */
@Getter
@Setter
@Entity
public class BlogConfig extends ImmutableBasicColumn {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long no;

  // key
  @Enumerated(EnumType.STRING)
  private ConfigCode configCode;

  // value (json)
  private String contents;
}
