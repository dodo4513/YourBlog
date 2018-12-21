CREATE TABLE visit
(
  no            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  post_no       long                              NOT NULL,
  register_ymdt TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP
);
