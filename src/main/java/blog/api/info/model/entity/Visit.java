package blog.api.info.model.entity;

import blog.api.post.model.entity.BasicColumn;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Visit extends BasicColumn {
    // FIXME 방문 데이터는 테이블을 어떻게 구성해야할까?

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long no;

    // 오늘 방문 수
    long todayCount;

    // 어제 방문 수
    long yesterdayCount;

    // 지금까지 방문 수
    long totalCount;
}
