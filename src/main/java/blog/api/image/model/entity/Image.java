package blog.api.image.model.entity;

import blog.common.model.entity.ForDeleteableTable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author cyclamen on 2019-03-16
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Image extends ForDeleteableTable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long imageNo;

  // 이름
  private String name;

  // 이미지 타입
  private String type;

  // 데이터
  @Lob
  private byte[] data;

  public Image(String name, String type, byte[] data) {
    this.name = name;
    this.type = type;
    this.data = data;
  }
}
