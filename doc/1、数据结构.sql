
-- -----------------------------------------------------------------------------
-- 4.1、推特内容
-- 创建时间：2016年03月14日
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS twitter_message (
  id              BIGINT NOT NULL AUTO_INCREMENT COMMENT '条目id',
  screen_name        VARCHAR(128) NOT NULL COMMENT '用户名',
  content        LONGTEXT NOT NULL COMMENT '内容',
  create_time    BIGINT NOT NULL COMMENT '创建时间',
  PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT '推特内容';


