package blog.common.model.entity;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BasicColumn {

  @Type(type = "yes_no")
  private Boolean deleteYn = false;

  @CreatedDate
  private LocalDateTime registerYmdt;

  @LastModifiedDate
  private LocalDateTime updateYmdt;
}
