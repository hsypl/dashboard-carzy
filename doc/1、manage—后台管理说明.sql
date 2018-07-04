set character_set_client=utf8,character_set_connection=utf8,character_set_results=utf8;
CREATE DATABASE IF NOT EXISTS ilearning_op DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
USE ilearning_op;

-- -----------------------------------------------------------------------------
-- 4.1、平台系统菜单信息表
-- 管理平台系统启动时，将加载的系统菜单注解信息持久化到菜单信息表，包括名称、
-- 包名、排序等信息，并为每个条目生成唯一id（数据库自增），用于导航树生成、用户或角色授权时使用。
-- 如果有新增菜单，当系统重新加载后，菜单信息都会更新（id保持不变），以包名作为唯一标识。
-- 当数据库中的菜单已不在加载的菜单中时，则系统自动将数据库中的记录标记为“舍弃”，管理员即可
-- 在管理系统中将其删除
-- 创建时间：2016年03月14日
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS menu_info (
  id              BIGINT NOT NULL AUTO_INCREMENT COMMENT '条目id',
  parent_id       BIGINT NOT NULL COMMENT '上级id',
  code            VARCHAR(128) NOT NULL COMMENT '条目编码，可用于本地化时使用',
  value           VARCHAR(128) NOT NULL COMMENT '条目名称，如果系统支持本地化，则以code字段为准',
  order_number    INT NOT NULL COMMENT '同级显示顺序',
  pkg_name        VARCHAR(255) NOT NULL COMMENT '包名',
  path            VARCHAR(255) NOT NULL COMMENT '访问路径',
  enable          TINYINT NOT NULL COMMENT '是否启用，true－启用，false－停用，默认为启用，如果停用，则下级递归停用',
  discard         TINYINT NOT NULL COMMENT '是否舍弃，true－舍弃，false－未舍弃，如果舍弃，可在管理后台删除',
  PRIMARY KEY(id),
  UNIQUE(pkg_name),
  INDEX(parent_id),
  INDEX(enable)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT '菜单信息表';


-- -----------------------------------------------------------------------------
-- 4.2、平台系统模块信息表
-- 管理平台系统启动时，将加载的系统模块注解信息持久化到模块信息表，包括模块名称、包名、
-- 路径、排序等信息，并为每个条目生成唯一id（数据库自增），用于导航树生成、用户或角色授权时使用。
-- 如果有新增模块，当系统重新加载后，模块信息都会更新（id保持不变），以包名作为唯一标识。
-- 当数据库中的模块已不在加载的模块中时，则系统自动将数据库中的记录标记为“舍弃”，管理员即可
-- 在管理系统中将其删除
-- 创建时间：2016年03月14日
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS module_info (
  id              BIGINT NOT NULL AUTO_INCREMENT COMMENT '条目id',
  parent_id       BIGINT NOT NULL COMMENT '上级id',
  code            VARCHAR(128) NOT NULL COMMENT '条目编码，可用于本地化时使用',
  value           VARCHAR(128) NOT NULL COMMENT '条目名称，如果系统支持本地化，则以code字段为准',
  order_number    INT NOT NULL COMMENT '同级显示顺序',
  pkg_name        VARCHAR(255) NOT NULL COMMENT '包名和类名',
  path            VARCHAR(255) NOT NULL COMMENT '访问路径',
  enable          TINYINT NOT NULL COMMENT '是否启用，true－启用，false－停用，默认为启用，如果停用，则下级递归停用',
  inlet_uri       VARCHAR(255) NOT NULL COMMENT '模块入口地址',
  discard         TINYINT NOT NULL COMMENT '是否舍弃，true－舍弃，false－未舍弃，如果舍弃，可在管理后台删除',
  PRIMARY KEY(id),
  UNIQUE(pkg_name),
  INDEX(parent_id),
  INDEX(enable)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT '模块信息表';

-- -----------------------------------------------------------------------------
-- 4.3、平台系统命令信息表
-- 管理平台系统启动时，将加载的系统模块命令注解信息持久化到命令信息表，包括命令名、
-- 命令访问路径等信息，并为每个条目生成唯一id（数据库自增），用于导航树生成、用户或角色授权时使用。
-- 如果有新增模块命令，当系统重新加载后，命令信息都会更新（id保持不变），以访问路径作为唯一标识。
-- 当数据库中的命令已不在加载的命令中时，则系统自动将数据库中的记录标记为“舍弃”，管理员即可
-- 在管理系统中将其删除
-- 创建时间：2016年03月14日
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS command_info (
  id              BIGINT NOT NULL AUTO_INCREMENT COMMENT '条目id',
  parent_id       BIGINT NOT NULL COMMENT '上级id',
  code            VARCHAR(128) NOT NULL COMMENT '条目编码，可用于本地化时使用',
  value           VARCHAR(128) NOT NULL COMMENT '条目名称，如果系统支持本地化，则以code字段为准',
  order_number    INT NOT NULL COMMENT '同级显示顺序',
  pkg_name        VARCHAR(255) NOT NULL COMMENT '包名和方法名',
  path            VARCHAR(255) NOT NULL COMMENT '访问路径',
  enable          TINYINT NOT NULL COMMENT '是否启用，true－启用，false－停用，默认为启用',
  inlet           TINYINT NOT NULL COMMENT '是否是模块入口',
  discard         TINYINT NOT NULL COMMENT '是否舍弃，true－舍弃，false－未舍弃，如果舍弃，可在管理后台删除',
  PRIMARY KEY(id),
  UNIQUE(path),
  INDEX(parent_id),
  INDEX(enable)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT '命令信息表';

-- -----------------------------------------------------------------------------
-- 4.4.3、平台用户权限信息表 user_privilege
-- 平台用户权限信息表，保存平台用户菜单、模块、命令权限关联关系，包括用户id、类型、权限条目id等。
-- 创建时间：2016年03月14日
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS user_privilege (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录id',
  user_id VARCHAR(64) NOT NULL COMMENT '系统用户id',
  item_id BIGINT NOT NULL COMMENT '条目id',
  item_type INT NOT NULL DEFAULT 1 COMMENT '条目类型，0-菜单，1-模块，2-命令',
  PRIMARY KEY (id),
  INDEX(item_id),
  INDEX(user_id),
  INDEX(item_type),
  UNIQUE(user_id, item_id, item_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT '平台用户权限信息表';
