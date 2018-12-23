package blog.api.info.model.entity;

import blog.api.info.model.enums.ConfigCode;
import blog.common.model.entity.BasicColumn;
import blog.common.model.entity.ImmutableBasicColumn;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
