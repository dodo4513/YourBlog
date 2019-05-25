CREATE TABLE post
(
  post_no       BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  title         VARCHAR(100)                      NOT NULL,
  description   VARCHAR(500)                      NULL,
  splash_image  VARCHAR(100)                      NULL,
  body          TEXT                              NOT NULL,
  public_yn     CHAR(1)                           NOT NULL DEFAULT 'Y',
  extra_data    JSON                              NULL,
  category_no   BIGINT                            NULL,
  view_count    BIGINT                            NULL,
  delete_yn     CHAR(1)                           NOT NULL DEFAULT 'N',
  register_ymdt TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_ymdt   TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP
);
