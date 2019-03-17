CREATE TABLE category
(
  category_no   BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name         VARCHAR(100)                      NOT NULL,
  public_yn     CHAR(1) DEFAULT 'Y'               NOT NULL,
  delete_yn     CHAR(1) DEFAULT 'N'               NOT NULL,
  display_order LONG                              NOT NULL,
  parent_no     LONG                              NULL,
  register_ymdt TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_ymdt   TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP
);
