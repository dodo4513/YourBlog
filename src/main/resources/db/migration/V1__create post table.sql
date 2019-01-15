CREATE TABLE post
(
  post_no            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  title         VARCHAR(100)                      NOT NULL,
  body          TEXT                              NOT NULL,
  public_yn     CHAR(1) DEFAULT 'Y'               NOT NULL,
  extra_data    JSON                              NULL,
  category_no   LONG                              NULL,
  delete_yn     CHAR(1) DEFAULT 'N'               NOT NULL,
  register_ymdt TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_ymdt   TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP
);
