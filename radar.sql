CREATE DATABASE `radar` ;

USE `radar`;


DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` bigint(21) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '类别名称',
  `level` int(2) NOT NULL DEFAULT 0 COMMENT '级别 从1开始',
  `superior_id` bigint(21) NOT NULL DEFAULT -1 COMMENT '上级目录',
  `is_del` int(1) NOT NULL DEFAULT -1 COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '更新时间',
  `create_user_id` bigint(21) NOT NULL DEFAULT -1 COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `goods` */

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `id` bigint(21) NOT NULL AUTO_INCREMENT,
  `name` varchar(300) NOT NULL DEFAULT '',
  `category_id` bigint(21) NOT NULL DEFAULT -1,
  `is_del` int(1) NOT NULL DEFAULT -1,
  `create_user_id` bigint(21) NOT NULL DEFAULT -1,
  `create_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `update_time` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` bigint(21) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '姓名',
  `nickname` varchar(100) NOT NULL DEFAULT '' COMMENT '昵称',
  `gender` int(1) NOT NULL DEFAULT -1 COMMENT '1 男， 2 女',
  `grade` int(2) NOT NULL DEFAULT 0 COMMENT '年级',
  `school_name` varchar(100) NOT NULL DEFAULT '' COMMENT '学校名称',
  `is_del` int(1) NOT NULL DEFAULT -1,
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  `update_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(21) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nickname` varchar(100) NOT NULL DEFAULT '' COMMENT '昵称',
  `phone_number` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `is_del` int(1) NOT NULL DEFAULT -1 COMMENT '删除标志  1 未删除， 2 删除',
  `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间，',
  `update_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `user_student_x` */

DROP TABLE IF EXISTS `user_student_x`;

CREATE TABLE `user_student_x` (
  `id` bigint(21) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(21) NOT NULL,
  `student_id` bigint(21) NOT NULL,
  `is_del` int(1) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  `update_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `wx_user`;

CREATE TABLE `wx_user` (
  `id` bigint(21) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `union_id` varchar(512) NOT NULL DEFAULT '' COMMENT 'unionid',
  `open_id` varchar(512) NOT NULL DEFAULT '',
  `nick_name` varchar(100) NOT NULL DEFAULT '',
  `avatar_url` varchar(512) NOT NULL DEFAULT '',
  `country` varchar(100) NOT NULL DEFAULT '',
  `province` varchar(100) NOT NULL DEFAULT '',
  `city` varchar(100) NOT NULL DEFAULT '',
  `language` varchar(10) NOT NULL DEFAULT '',
  `is_del` int(1) NOT NULL DEFAULT -1,
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  `update_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
