CREATE TABLE visit
(
  visit_no            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  post_no       long                              NOT NULL,
  client_ip     char(45)                          NOT NULL,
  register_ymdt TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP
);
