CREATE TABLE image
(
  image_no      BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name          VARCHAR(100)                      NOT NULL,
  type          VARCHAR(20)                       NOT NULL,
  data          MEDIUMBLOB                        NOT NULL,
  delete_yn     CHAR(1)                                    DEFAULT 'N' NOT NULL,
  register_ymdt TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP
);
