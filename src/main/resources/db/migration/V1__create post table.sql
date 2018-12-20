CREATE TABLE post
(
  no            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  title         VARCHAR(200)                      NOT NULL,
  body          TEXT                              NOT NULL,
  delete_yn     CHAR(1) DEFAULT 'Y'               NOT NULL,
  register_ymdt TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_ymdt   TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP
);
