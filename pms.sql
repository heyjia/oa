/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.28-0ubuntu0.18.04.4 : Database - pms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pms` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `pms`;

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL DEFAULT '0',
  `name` varchar(32) NOT NULL,
  `address` varchar(32) DEFAULT NULL,
  `describe` varchar(32) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `updt_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `department` */

insert  into `department`(`id`,`pid`,`name`,`address`,`describe`,`crt_time`,`updt_time`) values (1,0,'技术部','321312','3123123','2019-12-28 10:28:15','2019-12-31 09:03:38'),(2,1,'技术一部','','','2019-12-28 10:28:34','2019-12-28 10:28:34'),(3,1,'技术二部','','','2019-12-28 10:28:46','2019-12-28 10:28:46'),(4,0,'后勤部','','','2019-12-28 10:30:29','2019-12-28 10:30:29'),(5,4,'test','','','2019-12-28 13:26:28','2019-12-28 13:26:39'),(11,5,'test1',NULL,'123','2020-01-09 22:42:21','2020-01-09 22:42:21');

/*Table structure for table `position` */

DROP TABLE IF EXISTS `position`;

CREATE TABLE `position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `describe` varchar(32) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `updt_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `position` */

insert  into `position`(`id`,`name`,`describe`,`crt_time`,`updt_time`) values (1,'java低级工程师','','2019-12-28 10:27:17','2019-12-28 10:27:17'),(2,'java高级工程师','','2019-12-28 10:27:26','2019-12-28 10:27:26'),(3,'java中级工程师','','2019-12-28 10:27:32','2019-12-28 10:27:32'),(4,'秘书','','2019-12-28 10:27:36','2019-12-28 10:27:36'),(5,'总监','','2019-12-28 10:27:44','2019-12-28 10:27:44');

/*Table structure for table `post_dept` */

DROP TABLE IF EXISTS `post_dept`;

CREATE TABLE `post_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `post_dept` */

insert  into `post_dept`(`id`,`dept_id`,`post_id`) values (2,2,1),(3,2,2),(4,2,3),(5,3,1),(6,3,2),(7,3,3),(8,3,4),(9,4,4),(10,4,5),(11,5,1),(12,5,2),(13,5,3),(14,1,5),(24,11,4);

/*Table structure for table `privilege` */

DROP TABLE IF EXISTS `privilege`;

CREATE TABLE `privilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `url` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

/*Data for the table `privilege` */

insert  into `privilege`(`id`,`name`,`url`) values (1,'用户管理','/user/**'),(2,'部门管理','/dept/**'),(3,'角色管理','/role/**'),(4,'岗位管理','/post/**'),(5,'权限管理','/prvg/**'),(6,'个人中心','/personal/**'),(7,'首页','/login/toIndex'),(36,'test002','/**'),(37,'test003','/**');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`id`,`name`) values (1,'超级管理员'),(2,'管理员'),(3,'普通用户'),(4,'开发者'),(5,'test');

/*Table structure for table `role_privilege` */

DROP TABLE IF EXISTS `role_privilege`;

CREATE TABLE `role_privilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `privilege_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

/*Data for the table `role_privilege` */

insert  into `role_privilege`(`id`,`role_id`,`privilege_id`) values (9,3,6),(10,3,7),(16,4,3),(17,4,5),(18,4,6),(19,4,7),(20,2,1),(21,2,2),(22,2,3),(23,2,6),(24,2,7),(25,5,1),(26,5,3),(27,5,4),(28,1,1),(29,1,2),(30,1,3),(31,1,4),(32,1,5),(33,1,6),(34,1,7);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `password` varchar(256) NOT NULL,
  `salt` varchar(10) DEFAULT NULL,
  `telephone` varchar(16) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `updt_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`password`,`salt`,`telephone`,`email`,`sex`,`crt_time`,`updt_time`) values (1,'admin','RxYu6nOAWZPe+2dwoo6FK94pt+T+9ZbSG7C29kxJY8ZkgHA0+Jb0g1GjJocQz7lKCqNE+O73WgbivKAJfDqTiXSFIfmY4CGcxpdNQI0sQQSS//P66vZYQnUz1Ip4lA8tQrbdbSVB3qe1NyBuHK9kbj/Safc8g4BBPS8cE3xyl+s=',NULL,'12345678910','123@qq.co',0,'2019-12-28 10:12:42','2020-01-09 22:13:52'),(2,'user','h+vDJjOXgHP99WMUNZRsaRxw6rna6HkMrKRMo7SILoIHoKZfhLHcMXmaebv2QCZclkjiCpE8jHb7/N+Z6z+7955lLlWqtstOWda3uZGo8yAx+K3czaqWWuqotiXI6h4X0hHZwOGTR7aFQ1NAVy34S8ID8ed/YlFPI0sMetkjHQU=',NULL,'','',0,'2019-12-28 10:33:35','2019-12-28 10:33:35'),(3,'dev','GQKCFKa4mlpD/3Fbg5F5ompeE6fNKoJ3KC4j2JF9yuFaiBYqzJv9nC0xuApSzrCXuYjj6J2iU9qeBRQrSogGeSIlKX8VaPAJJuTa28Ixto1xeWJS+xkSEuIW6tt+wPaafYicQJKX+t000k0UC9ZMgKCQwrwhLV1hPOKdaT1eqo0=',NULL,'','',0,'2019-12-28 10:33:57','2019-12-28 10:33:57'),(4,'superuser','LdG/1bHyRNPDVQDQxkizhoXUEnyiMjSTC5LbyGbTPJ51t4S1VQ9jj6MMKh6EkvEKcTfBf6Cu0uv5ZcFJo6INZIUuENWpkiOl+w1Ay9Hwt2JjyqPDoAo8IB8tErlapKtbhgJG0gfhZ0SiJFvoWgwD/LHO7Y62vTpJujt+S2pa49Q=',NULL,'','',0,'2019-12-28 10:34:17','2020-01-09 22:14:27');

/*Table structure for table `user_post` */

DROP TABLE IF EXISTS `user_post`;

CREATE TABLE `user_post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `dept_id` int(11) NOT NULL,
  `salary` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `user_post` */

insert  into `user_post`(`id`,`user_id`,`post_id`,`dept_id`,`salary`) values (1,2,4,4,'5000'),(2,3,2,2,'0'),(3,4,5,1,'123');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `user_role` */

insert  into `user_role`(`id`,`user_id`,`role_id`) values (1,1,1),(2,2,3),(3,3,4),(4,4,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
