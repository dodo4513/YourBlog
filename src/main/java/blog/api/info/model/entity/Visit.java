package blog.api.info.model.entity;

import blog.common.model.entity.ImmutableBasicColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Visit extends ImmutableBasicColumn {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long visitNo;

  // 포스트 번호
  private Long postNo;

  // 접속자 ip
  private String clientIp;
}
