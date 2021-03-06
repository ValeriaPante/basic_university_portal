-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: progetto
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `verbale`
--

DROP TABLE IF EXISTS `verbale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verbale` (
  `idverbale` int NOT NULL AUTO_INCREMENT,
  `data` date NOT NULL,
  `time` time NOT NULL,
  PRIMARY KEY (`idverbale`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verbale`
--

LOCK TABLES `verbale` WRITE;
/*!40000 ALTER TABLE `verbale` DISABLE KEYS */;
INSERT INTO `verbale` VALUES (9,'2021-04-19','10:58:01'),(10,'2021-05-21','07:19:54'),(11,'2021-05-23','17:00:15'),(12,'2021-05-23','17:10:57'),(13,'2021-05-23','17:10:58'),(14,'2021-05-23','17:10:59'),(15,'2021-05-23','17:11:07'),(16,'2021-05-23','17:11:08'),(17,'2021-05-23','17:16:11'),(18,'2021-05-23','17:16:12'),(19,'2021-05-23','17:16:13'),(20,'2021-05-23','17:16:13'),(21,'2021-05-23','17:16:13'),(22,'2021-05-23','17:16:13'),(23,'2021-05-23','19:18:51'),(24,'2021-05-23','19:18:51'),(25,'2021-05-23','19:21:04'),(26,'2021-05-23','19:22:06'),(27,'2021-05-23','19:24:34'),(28,'2021-05-23','19:26:51'),(29,'2021-05-23','19:27:25'),(30,'2021-05-23','19:30:25'),(31,'2021-05-24','10:31:20'),(32,'2021-05-26','10:03:24'),(33,'2021-06-07','14:51:05'),(34,'2021-06-12','08:51:26'),(35,'2021-06-15','16:32:26'),(36,'2021-06-15','16:57:29'),(37,'2021-06-16','06:58:52'),(38,'2021-06-16','06:59:12'),(39,'2021-06-16','06:59:14'),(40,'2021-06-16','06:59:39'),(41,'2021-06-16','06:59:40'),(42,'2021-06-16','06:59:40'),(43,'2021-06-16','07:05:03'),(44,'2021-06-16','07:05:06'),(45,'2021-06-16','07:05:07'),(46,'2021-06-16','07:05:07'),(47,'2021-06-16','07:05:08'),(48,'2021-06-16','07:05:08'),(49,'2021-06-16','07:05:08'),(50,'2021-06-16','07:05:08'),(51,'2021-06-16','07:05:12'),(52,'2021-06-16','07:05:12'),(53,'2021-06-16','07:05:12'),(54,'2021-06-16','07:05:12'),(55,'2021-06-16','07:05:12'),(56,'2021-06-16','07:05:21'),(57,'2021-06-16','07:06:12'),(58,'2021-06-16','07:09:06'),(59,'2021-06-16','07:09:40'),(60,'2021-06-16','07:11:37'),(61,'2021-06-16','07:11:39'),(62,'2021-06-16','07:11:40'),(63,'2021-06-16','07:11:40'),(64,'2021-06-16','07:11:40'),(65,'2021-06-16','07:11:40'),(66,'2021-06-16','07:11:41'),(67,'2021-06-16','07:11:41'),(68,'2021-06-16','07:11:41'),(69,'2021-06-16','07:11:41'),(70,'2021-06-16','07:11:41'),(71,'2021-06-16','07:11:41'),(72,'2021-06-16','07:11:42'),(73,'2021-06-16','07:11:42'),(74,'2021-06-16','07:11:42'),(75,'2021-06-16','07:11:42'),(76,'2021-06-16','07:11:42'),(77,'2021-06-16','07:11:42'),(78,'2021-06-16','07:11:43'),(79,'2021-06-16','07:11:47'),(80,'2021-06-16','07:12:37'),(81,'2021-06-16','07:13:21'),(82,'2021-06-16','07:14:55'),(83,'2021-06-16','07:15:20'),(84,'2021-06-16','07:20:43'),(85,'2021-06-16','07:21:18'),(86,'2021-06-16','07:23:56'),(87,'2021-06-16','10:25:25'),(88,'2021-06-16','13:29:12'),(89,'2021-06-16','13:32:14'),(90,'2021-06-16','13:34:07'),(91,'2021-06-16','14:02:34'),(92,'2021-06-16','14:12:30');
/*!40000 ALTER TABLE `verbale` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-16 16:38:33
