package blog.api.info.model.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author cyclamen on 23/12/2018
 */
@Getter
@Setter
public class Me {
    // 이름
    private String name;

    // 메일
    private String email;

    // 기타 정보
    private Map<String, String> extraData;

    // 등록일
    private LocalDateTime registerYmdt;
}
