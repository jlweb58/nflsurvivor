/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-11.6.2-MariaDB, for osx10.20 (arm64)
--
-- Host: 127.0.0.1    Database: nfl
-- ------------------------------------------------------
-- Server version	10.3.25-MariaDB-1:10.3.25+maria~focal

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Sequence structure for `app_users_seq`
--

USE nfl;

DROP SEQUENCE IF EXISTS `app_users_seq`;
CREATE SEQUENCE `app_users_seq` start with 1 minvalue 1 maxvalue 9223372036854775806 increment by 50 cache 1000 nocycle ENGINE=InnoDB;
DO SETVAL(`app_users_seq`, 1, 0);

--
-- Sequence structure for `games_seq`
--

DROP SEQUENCE IF EXISTS `games_seq`;
CREATE SEQUENCE `games_seq` start with 1 minvalue 1 maxvalue 9223372036854775806 increment by 50 cache 1000 nocycle ENGINE=InnoDB;
DO SETVAL(`games_seq`, 1, 0);

--
-- Sequence structure for `teams_seq`
--

DROP SEQUENCE IF EXISTS `teams_seq`;
CREATE SEQUENCE `teams_seq` start with 1 minvalue 1 maxvalue 9223372036854775806 increment by 50 cache 1000 nocycle ENGINE=InnoDB;
DO SETVAL(`teams_seq`, 1, 0);

--
-- Sequence structure for `weekly_game_selections_seq`
--

DROP SEQUENCE IF EXISTS `weekly_game_selections_seq`;
CREATE SEQUENCE `weekly_game_selections_seq` start with 1 minvalue 1 maxvalue 9223372036854775806 increment by 50 cache 1000 nocycle ENGINE=InnoDB;
DO SETVAL(`weekly_game_selections_seq`, 1, 0);

--
-- Table structure for table `app_users`
--

DROP TABLE IF EXISTS `app_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_users` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_users`
--

LOCK TABLES `app_users` WRITE;
/*!40000 ALTER TABLE `app_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `app_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES
(1,'1','','SQL','V1__.sql',-1256693278,'jlweb58','2024-12-13 11:42:45',160,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games` (
  `id` bigint(20) NOT NULL,
  `home_team_id` bigint(20) NOT NULL,
  `away_team_id` bigint(20) NOT NULL,
  `week` int(11) NOT NULL,
  `start_time` varchar(255) NOT NULL,
  `home_points` int(11) DEFAULT NULL,
  `away_points` int(11) DEFAULT NULL,
  `point_spread` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_GAMES_ON_AWAY_TEAM` (`away_team_id`),
  KEY `FK_GAMES_ON_HOME_TEAM` (`home_team_id`),
  CONSTRAINT `FK_GAMES_ON_AWAY_TEAM` FOREIGN KEY (`away_team_id`) REFERENCES `teams` (`id`),
  CONSTRAINT `FK_GAMES_ON_HOME_TEAM` FOREIGN KEY (`home_team_id`) REFERENCES `teams` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games`
--

LOCK TABLES `games` WRITE;
/*!40000 ALTER TABLE `games` DISABLE KEYS */;
/*!40000 ALTER TABLE `games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teams`
--

DROP TABLE IF EXISTS `teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teams` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `logo` blob DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teams`
--

LOCK TABLES `teams` WRITE;
/*!40000 ALTER TABLE `teams` DISABLE KEYS */;
/*!40000 ALTER TABLE `teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_roles` (
  `user_id` bigint(20) NOT NULL,
  `user_roles` varchar(255) DEFAULT NULL,
  KEY `fk_users_roles_on_user` (`user_id`),
  CONSTRAINT `fk_users_roles_on_user` FOREIGN KEY (`user_id`) REFERENCES `app_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weekly_game_selections`
--

DROP TABLE IF EXISTS `weekly_game_selections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weekly_game_selections` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `winning_team_id` bigint(20) NOT NULL,
  `game_result` varchar(255) DEFAULT NULL,
  `selected_game_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_WEEKLY_GAME_SELECTIONS_ON_SELECTED_GAME` (`selected_game_id`),
  KEY `FK_WEEKLY_GAME_SELECTIONS_ON_USER` (`user_id`),
  KEY `FK_WEEKLY_GAME_SELECTIONS_ON_WINNING_TEAM` (`winning_team_id`),
  CONSTRAINT `FK_WEEKLY_GAME_SELECTIONS_ON_SELECTED_GAME` FOREIGN KEY (`selected_game_id`) REFERENCES `games` (`id`),
  CONSTRAINT `FK_WEEKLY_GAME_SELECTIONS_ON_USER` FOREIGN KEY (`user_id`) REFERENCES `app_users` (`id`),
  CONSTRAINT `FK_WEEKLY_GAME_SELECTIONS_ON_WINNING_TEAM` FOREIGN KEY (`winning_team_id`) REFERENCES `teams` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weekly_game_selections`
--

LOCK TABLES `weekly_game_selections` WRITE;
/*!40000 ALTER TABLE `weekly_game_selections` DISABLE KEYS */;
/*!40000 ALTER TABLE `weekly_game_selections` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2024-12-13 17:02:10
