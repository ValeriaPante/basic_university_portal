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
-- Table structure for table `iscrizioniappello`
--

DROP TABLE IF EXISTS `iscrizioniappello`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `iscrizioniappello` (
  `id` int NOT NULL,
  `idStud` int NOT NULL,
  `voto` varchar(10) DEFAULT NULL,
  `stato` int NOT NULL,
  `idverbale` int DEFAULT NULL,
  PRIMARY KEY (`id`,`idStud`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iscrizioniappello`
--

LOCK TABLES `iscrizioniappello` WRITE;
/*!40000 ALTER TABLE `iscrizioniappello` DISABLE KEYS */;
INSERT INTO `iscrizioniappello` VALUES (1,1,'25',4,91),(1,11,'assente',4,91),(1,12,'24',4,91),(1,13,'30',4,91),(1,14,'26',4,91),(1,15,'rimandato',4,91),(1,16,'18',4,91),(1,17,'assente',4,34),(2,1,'rimandato',4,92),(2,11,'rimandato',4,92),(2,12,'22',4,92),(2,13,'23',4,92),(2,14,'18',4,92),(2,15,'riprovato',4,92),(2,16,'25',1,NULL),(2,17,NULL,0,NULL),(3,1,'rimandato',3,85),(3,11,'riprovato',4,86),(6,1,'assente',2,NULL),(6,11,'21',1,NULL),(7,1,'18',2,36);
/*!40000 ALTER TABLE `iscrizioniappello` ENABLE KEYS */;
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
