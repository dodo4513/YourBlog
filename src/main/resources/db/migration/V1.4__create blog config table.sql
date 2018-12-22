CREATE TABLE blog_config
(
  no            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  config_code   char(50)                          NOT NULL,
  contents      JSON                              NOT NULL,
  delete_yn     char(1) DEFAULT 'N'               NOT NULL,
  register_ymdt TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_ymdt   TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP
);
