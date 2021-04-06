/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.40 : Database - book_lesson
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`book_lesson` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `book_lesson`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '管理员用户名',
  `PASSWORD` varchar(32) NOT NULL COMMENT '管理员密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`id`,`username`,`PASSWORD`) values (1,'admin','admin');

/*Table structure for table `dict` */

DROP TABLE IF EXISTS `dict`;

CREATE TABLE `dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL COMMENT '父级字典',
  `group_code` varchar(64) NOT NULL COMMENT '字典识别码',
  `code` int(11) DEFAULT NULL COMMENT '代码',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `dict` */

insert  into `dict`(`id`,`pid`,`group_code`,`code`,`name`) values (1,10,'lesson_code',1,'语文'),(2,10,'lesson_code',2,'数学'),(3,10,'lesson_code',3,'英语'),(4,10,'lesson_code',4,'物理'),(5,10,'lesson_code',5,'化学'),(6,10,'lesson_code',6,'生物'),(7,10,'lesson_code',7,'政治'),(8,10,'lesson_code',8,'历史'),(9,10,'lesson_code',9,'Java');

/*Table structure for table `lesson_choose` */

DROP TABLE IF EXISTS `lesson_choose`;

CREATE TABLE `lesson_choose` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lesson_id` int(11) NOT NULL COMMENT '课程id',
  `student_id` int(11) NOT NULL COMMENT '学生id',
  `teacher_id` int(11) NOT NULL COMMENT '讲师id',
  `state` int(11) DEFAULT NULL COMMENT '选课状态：0：取消预约，1：已选，2：过期，3：已选且过期。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `lesson_choose` */

insert  into `lesson_choose`(`id`,`lesson_id`,`student_id`,`teacher_id`,`state`) values (1,1,1,1,3),(2,2,1,2,3),(5,2,1,2,3),(6,2,1,2,3),(8,2,1,2,3),(10,2,1,2,3),(15,3,1,3,3),(16,10,1,3,0),(19,11,1,3,0),(20,11,1,3,0);

/*Table structure for table `lesson_course` */

DROP TABLE IF EXISTS `lesson_course`;

CREATE TABLE `lesson_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(32) NOT NULL COMMENT '课程名称',
  `lesson_start_time` varchar(64) NOT NULL COMMENT '上课开始时间',
  `lesson_end_time` varchar(64) NOT NULL COMMENT '上课结束时间',
  `shool_addr` varchar(16) NOT NULL COMMENT '校区',
  `teacher_name` varchar(16) NOT NULL COMMENT '讲师名字',
  `teacher_id` int(11) NOT NULL COMMENT '讲师id',
  `state` int(11) NOT NULL COMMENT '选课状态：1：可选，2：过期。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `lesson_course` */

insert  into `lesson_course`(`id`,`course_name`,`lesson_start_time`,`lesson_end_time`,`shool_addr`,`teacher_name`,`teacher_id`,`state`) values (1,'高数','2020-03-08T08:00','2020-03-08T10:00','上海','沈剑洪',1,2),(2,'大学物理','2020-03-28T10:00	','2020-03-28T12:00	','上海','罗龙基',2,2),(3,'语文','2020-04-01T10:00	','2020-04-01T12:00	','上海','欧阳',3,2),(8,'数学','2020-03-15T08:00','2020-03-15T10:00','上海','欧阳',3,2),(9,'英语','2020-03-31T08:00','2020-03-31T10:00','上海','欧阳',3,2),(10,'PHH2.0','2020-04-01T08:00','2020-04-01T10:00','上海','欧阳',3,2),(11,'语文','2020-04-30T08:00','2020-04-30T10:00','上海','欧阳',3,2),(12,'数学','2020-04-29T08:00','2020-04-29T10:00','北京','欧阳',3,2),(13,'语文','2020-04-16T08:00','2020-04-16T10:00','上海','欧阳',3,2),(14,'语文','2020-05-30T08:00','2020-05-30T10:00','北京','欧阳',3,1),(15,'英语','2020-05-22T08:00','2020-05-22T10:00','北京','欧阳',3,2),(16,'数学','2020-05-23T08:00','2020-05-23T10:00','北京','欧阳',3,2),(17,'Java','2020-05-31T08:00','2020-05-31T10:00','北京','罗龙基',2,1),(18,'Java','2020-05-30T14:00','2020-04-29T16:00','北京','罗龙基',2,1),(19,'Java','2020-05-29T08:00','2020-05-29T10:00','北京','罗龙基',2,1);

/*Table structure for table `stu_lesson_code` */

DROP TABLE IF EXISTS `stu_lesson_code`;

CREATE TABLE `stu_lesson_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL COMMENT '学生用户名',
  `lesson_code` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `stu_lesson_code` */

insert  into `stu_lesson_code`(`id`,`username`,`lesson_code`) values (1,'lihua',1),(2,'lihua',2),(3,'lihua',3),(4,'lihua',4),(5,'lihua',5),(6,'lihua',9);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `name` varchar(16) DEFAULT NULL COMMENT '学生姓名',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `age` int(10) DEFAULT NULL COMMENT '年龄',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(64) DEFAULT NULL COMMENT '手机号',
  `school` varchar(32) DEFAULT NULL COMMENT '校区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`id`,`username`,`password`,`name`,`gender`,`age`,`email`,`phone`,`school`) values (1,'lihua','lihua','李华','1',18,'liu8654@qq.com','18279719822','上海'),(2,'ruirui','ruirui','ruirui','2',23,'1872565@qq.com','15735646642','上海');

/*Table structure for table `tea_lesson_code` */

DROP TABLE IF EXISTS `tea_lesson_code`;

CREATE TABLE `tea_lesson_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL COMMENT '讲师用户名',
  `lesson_code` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `tea_lesson_code` */

insert  into `tea_lesson_code`(`id`,`username`,`lesson_code`) values (1,'ouyang',1),(2,'ouyang',2),(3,'admin2222',6),(4,'ouyang',3),(5,'luolongji',9),(6,'shenjianhong',6),(7,'ruirui',3);

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(45) DEFAULT NULL COMMENT '密码',
  `NAME` varchar(16) DEFAULT NULL COMMENT '老师姓名',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `age` int(10) DEFAULT NULL COMMENT '年龄',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `school` varchar(32) DEFAULT NULL COMMENT '校区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `teacher` */

insert  into `teacher`(`id`,`username`,`PASSWORD`,`NAME`,`gender`,`age`,`email`,`phone`,`school`) values (1,'shenjianhong','shenjianhong','沈剑洪','男',25,'shen865488@qq.com','18526252412','上海'),(2,'luolongji','luolongji','罗龙基','男',25,'luo865488@qq.com','18654821541','上海'),(3,'ouyang','ouyang','欧阳','男',23,'ouyang12345@qq.com','138383843','北京'),(4,'ruirui','ruirui','蒋汭','女',100,'111','520520','上海'),(5,'admin2222','admin2222','刘振',NULL,NULL,'liu865488158@qq.com','1825684716','on');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
