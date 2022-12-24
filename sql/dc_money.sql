/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.6.10 : Database - dc_money
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dc_money` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `dc_money`;

/*Table structure for table `dc_bill` */

DROP TABLE IF EXISTS `dc_bill`;

CREATE TABLE `dc_bill` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `money` bigint(20) DEFAULT '0' COMMENT '账单金额',
  `user_id` int(11) NOT NULL,
  `details` varchar(255) DEFAULT NULL COMMENT '账单详情',
  `type` int(11) DEFAULT '0' COMMENT '账单类型，1（收入）或0（支出）',
  `tag_id` int(11) DEFAULT '0' COMMENT '标签id',
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `dc_bill` */

insert  into `dc_bill`(`id`,`money`,`user_id`,`details`,`type`,`tag_id`,`gmt_create`,`gmt_modified`) values (1,40,0,'收红包',1,0,'2022-11-24 10:09:56','2022-11-24 10:09:56'),(2,20,0,'买猫',0,0,'2022-11-24 10:12:45','2022-11-24 10:12:45'),(3,10,0,'买肉',0,0,'2022-11-24 10:13:57','2022-11-24 10:13:57'),(4,55,0,'买菜',0,0,'2022-11-24 10:14:47','2022-11-24 10:14:47'),(5,111,1,'买包包',0,0,'2022-11-24 10:17:49','2022-11-24 10:17:49');

/*Table structure for table `dc_tag` */

DROP TABLE IF EXISTS `dc_tag`;

CREATE TABLE `dc_tag` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dc_tag` */

/*Table structure for table `dc_user` */

DROP TABLE IF EXISTS `dc_user`;

CREATE TABLE `dc_user` (
  `id` int(19) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '用户姓名',
  `gender` tinyint(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `email` varchar(255) DEFAULT NULL,
  `balance` int(20) DEFAULT NULL COMMENT '账户余额',
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `open_id` varchar(50) DEFAULT NULL COMMENT '用户的开放id',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '用户昵称',
  `country` varchar(20) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `dc_user` */

insert  into `dc_user`(`id`,`name`,`gender`,`password`,`avatar_url`,`email`,`balance`,`gmt_create`,`gmt_modified`,`open_id`,`nick_name`,`country`,`province`,`city`) values (1,'string',1,'string','string','string',0,'2022-11-24 10:16:27','2022-11-24 10:16:27',NULL,NULL,NULL,NULL,NULL),(3,'L',0,NULL,'https://thirdwx.qlogo.cn/mmopen/vi_32/g8m0vclNX9fNBPYtbRFJyoX30wfOeudCUAcMJXUQPRkV70HzpWQakIian9Q6KNPuzkicJ8wPg3B4ewcCOTGr8K7g/132',NULL,NULL,'2022-11-27 03:36:52','2022-11-27 03:36:52','oG9MC5HgVXaI1PDD_qb72AwtuQvg','L','','','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


