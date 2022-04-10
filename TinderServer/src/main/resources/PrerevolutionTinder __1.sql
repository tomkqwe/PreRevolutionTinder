-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_example
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `i_was_chosen`
--

DROP TABLE IF EXISTS `i_was_chosen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `i_was_chosen` (
  `this_user_id` int NOT NULL,
  `choosed_user_id` int NOT NULL,
  PRIMARY KEY (`this_user_id`,`choosed_user_id`),
  KEY `FKeh7equr5nu6e5ai6raeok1n7` (`choosed_user_id`),
  CONSTRAINT `FKeh7equr5nu6e5ai6raeok1n7` FOREIGN KEY (`choosed_user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKtkqgllahvyt7fo75do093kcry` FOREIGN KEY (`this_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `i_was_chosen`
--

LOCK TABLES `i_was_chosen` WRITE;
/*!40000 ALTER TABLE `i_was_chosen` DISABLE KEYS */;
/*!40000 ALTER TABLE `i_was_chosen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `my_choose`
--

DROP TABLE IF EXISTS `my_choose`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `my_choose` (
  `choosed_user_id` int NOT NULL,
  `this_user_id` int NOT NULL,
  PRIMARY KEY (`choosed_user_id`,`this_user_id`),
  KEY `FKsr181bxed5n5xw3eo8ys6hnhv` (`this_user_id`),
  CONSTRAINT `FKsr181bxed5n5xw3eo8ys6hnhv` FOREIGN KEY (`this_user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKtfyi8ukrbkluc2ebpuxq5gatm` FOREIGN KEY (`choosed_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `my_choose`
--

LOCK TABLES `my_choose` WRITE;
/*!40000 ALTER TABLE `my_choose` DISABLE KEYS */;
/*!40000 ALTER TABLE `my_choose` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `age` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,30,'Ничего не имею. Остались только добрая душа, порядочность и графство. Молодой, 30 л., со средним образованием, трудолюбивый, хочу жениться на состоятельной особе и ценить ее за поддержку. Анонимам не отвечаю','Иван Олегович','Мужской'),(2,36,'Немедленно шатен, 36 лет, состоящий на государственной службе, жалованья 1700 руб. в год, желает жениться на особе, располагающей от 50 до 100 и более тыс. руб. Средства необходимы для раздела выгодного имения, которое приобретается на имя жены.','Дмитрий Дмитриевич','Мужской'),(3,24,'Если бы я был богат, взял бы только бедную девушку в жены; но я бедный, интел. с высш. образов., агроном-техник, поляк, 35 лет, предлагаю себя в мужья только богатой девушке (не менее 100 000 р. капит.).','Кшиштоф','Мужской'),(4,19,'Только что кончившая гимназию девица желает выйти замуж за холостого или бездетного вдовца с состоянием. Возраста не стесняться','Марина','Женский'),(5,20,'20 лет, образованная барышня ищет мужа миллионера, непременно пожилого, во избежание неверности','Агафья','Женский'),(6,24,'Красавица, 24 лет, интеллигентная, брюнетка, очень шикарная, прогремевшая в Москве и Париже по красоте и нарядам, ищет знакомств, цель — замужество, с миллионером пожилых лет','Варвара','Женский'),(7,39,'Так жизнь молодая проходит бесследно. А там и скоро конец. Мои девичьи грезы изменили мне. Стремилась к семейному очагу, но все рассеялось, как дым. И я одна, я всем чужая. Ищу мужа-друга','Тамара Васильевна','Женский'),(8,42,'Вдовец 42 лет желает жениться на девушке „без прошлого“, образованной, знающей музыку и обладающей приятным голосом. Теща нежелательна','Семён Поликарпыч','Мужской'),(9,16,'Южанка брюнетка красивая, интересная барышня 16 л., без прошлого, из хорошей семьи, желает познакомиться с миллионером или с очень богатыми господином. При возможности и желании — брак','Оксана','Женский'),(10,35,'Жену, компаньонку с капиталом в 5 тысяч, ищет солидный господин 35 лет, открывающий столовую. Дело обещает громадный успех','Дядя Федор','Мужской');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'db_example'
--

--
-- Dumping routines for database 'db_example'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-01 15:40:03
