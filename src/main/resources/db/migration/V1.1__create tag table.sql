CREATE TABLE tag
(
  no            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name          VARCHAR(200)                      NOT NULL,
  delete_yn     char(1) DEFAULT 'N'               NOT NULL,
  register_ymdt TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_ymdt   TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP
);
