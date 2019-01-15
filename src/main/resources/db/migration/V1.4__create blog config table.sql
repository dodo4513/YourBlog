CREATE TABLE blog_config
(
  blog_config_no            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  config_code   char(50)                          NOT NULL,
  contents      JSON                              NOT NULL,
  register_ymdt TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP
);
