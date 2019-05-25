CREATE TABLE post_tag_mapping
(
  post_tag_no BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  post_no     BIGINT                            NOT NULL,
  tag_no      BIGINT                            NOT NULL
);
