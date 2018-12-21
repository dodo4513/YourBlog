package blog.api.info.model.entity;

import blog.common.model.entity.ImmutableBasicColumn;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Visit extends ImmutableBasicColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long no;

    // 포스트 번호
    private Long postNo;
}
