-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: se02_project
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `userId` bigint DEFAULT NULL,
                        `createAt` timestamp NULL DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        KEY `Cart_userId_fk` (`userId`),
                        CONSTRAINT `Cart_userId_fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,2,'2024-04-05 04:31:08'),(2,1,'2024-04-05 07:29:13'),(3,3,'2024-05-13 02:02:24');
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartitem`
--

DROP TABLE IF EXISTS `cartitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartitem` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `productID` bigint DEFAULT NULL,
                            `cartID` bigint DEFAULT NULL,
                            `quantity` int DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `CartItem_productID_fk` (`productID`),
                            KEY `CartItem_cartID_fk` (`cartID`),
                            CONSTRAINT `CartItem_cartID_fk` FOREIGN KEY (`cartID`) REFERENCES `cart` (`id`),
                            CONSTRAINT `CartItem_productID_fk` FOREIGN KEY (`productID`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartitem`
--

LOCK TABLES `cartitem` WRITE;
/*!40000 ALTER TABLE `cartitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `cartitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `categoryName` varchar(25) DEFAULT NULL,
                            `description` text,
                            `isActive` tinyint DEFAULT NULL,
                            `createAt` timestamp NULL DEFAULT NULL,
                            `createBy` varchar(50) DEFAULT NULL,
                            `modifyAt` timestamp NULL DEFAULT NULL,
                            `modifyBy` varchar(50) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'ceiling','Discover chic ceiling lights to elevate your space. Explore modern designs and timeless classics for a touch of elegance in any room.',1,'2024-04-02 03:04:56',NULL,NULL,NULL),(2,'wall','Add warmth and style to your walls with our stunning wall lights. From sleek sconces to elegant fixtures, our collection offers the perfect blend of form and function. Illuminate your space with ease and sophistication. Explore our selection now and brighten up your home with effortless elegance.',1,'2024-04-02 03:04:56',NULL,NULL,NULL),(3,'lamp','Illuminate your space with our exquisite lamps. From elegant table lamps to sleek floor lamps, our collection offers timeless designs and superior craftsmanship. Enhance any room with warm, inviting light and stylish accents. Explore our selection now and bring effortless elegance to your home.',1,'2024-04-02 03:04:56',NULL,NULL,NULL),(4,'outdoor','Light up your outdoor spaces with style. Explore our collection of durable and elegant outdoor lights today!',1,'2024-04-02 03:04:56',NULL,NULL,NULL),(5,'fan','Stay cool and stylish with our range of ceiling fans. From modern designs to classic styles, our collection offers the perfect blend of comfort and elegance. Enhance any room\'s ambiance while keeping the air flowing smoothly. Discover the ideal fan for your space today.',1,'2024-04-02 03:04:56',NULL,NULL,NULL),(6,'accents','Elevate your space with our curated selection of home accents. From decorative vases to stylish throw pillows, our collection offers the perfect finishing touches to any room. Add personality and charm to your home decor effortlessly. Explore our range of accents and transform your space today.',1,'2024-04-02 03:04:56',NULL,NULL,NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment` text,
  `rate` smallint DEFAULT NULL,
  `createAt` timestamp NULL DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,'I bought the \'Modern LED Pendant Light\' for my kitchen remodel, and it exceeded my expectations. Not only does it provide excellent lighting, but its sleek design also complements the contemporary style of my kitchen perfectly. Very satisfied!',3,'2024-04-02 16:40:06','michael@gmail.com','Michael'),(2,'I\'ve been searching for the perfect bedside lamps for ages, and I finally found them at Chic and Lighting! The \'Vintage Glass Table Lamps\' I purchased are stunning and add a cozy ambiance to my bedroom. Great selection and fast shipping!',3,'2024-04-02 16:40:06','emily@gmail.com','Emily'),(3,'I recently purchased the \'Elegant Crystal Chandelier\' from Chic and Lighting, and I\'m absolutely thrilled with it! The quality is top-notch, and it adds a touch of glamour to my dining room. Highly recommend!',3,'2024-04-02 16:40:06','sarah@gmail.com','Sarah'),(4,'Very good',3,'2024-04-02 16:40:06','j97@gmail.com','Jack');
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint DEFAULT NULL,
  `orderStatusId` bigint DEFAULT NULL,
  `orderDate` timestamp NOT NULL,
  `notes` text,
  `firstname` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Order_userId_fk` (`userId`),
  KEY `Order_orderStatusId_fk` (`orderStatusId`),
  CONSTRAINT `Order_orderStatusId_fk` FOREIGN KEY (`orderStatusId`) REFERENCES `orderstatus` (`id`),
  CONSTRAINT `Order_userId_fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (13,2,5,'2024-04-05 10:01:52','none','Nguyễn','Sơn','Bac Ninh','0926539328'),(14,2,4,'2024-04-05 15:15:01','none','Nguyễn','Sơn','Bac Ninh','0926539328'),(15,2,4,'2024-04-06 09:39:20','none','Nguyễn','Sơn','Bac Ninh','0926539328'),(16,2,3,'2024-04-08 03:15:23','none','Nguyễn','Sơn','Bac Ninh','0926539328'),(17,2,5,'2024-04-08 03:17:06','none','Nguyễn','Sơn','Bac Ninh','0926539328'),(18,2,1,'2024-04-08 03:18:23','none','Nguyễn','Sơn','Bac Ninh','0926539328'),(19,2,5,'2024-04-12 06:04:34','Giao gio hanh chinh','Nguyễn','Sơn','Bac Ninh','0926539328'),(20,2,1,'2024-04-12 06:07:56','Giao gio hanh chinh','Nguyễn','Sơn','Bac Ninh','0926539328'),(21,2,5,'2024-04-13 09:37:24','Giao gio hanh chinh','Nguyễn','Sơn','Bac Ninh','0926539328'),(22,3,4,'2024-05-13 02:43:56','None','Nguyen Duc','Son','Yen Phong','0926539328'),(23,3,5,'2024-05-13 02:47:35','None','Nguyen Duc','Son','Yen Phong','0926539328'),(24,2,4,'2024-05-14 02:29:38','None','Nguyen Duc','Son','Yen Phong','0926539328');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderdetail`
--

DROP TABLE IF EXISTS `orderdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderdetail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `productID` bigint DEFAULT NULL,
  `orderID` bigint DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `OrderDetail_productID_fk` (`productID`),
  KEY `OrderDetail_orderID_fk` (`orderID`),
  CONSTRAINT `OrderDetail_orderID_fk` FOREIGN KEY (`orderID`) REFERENCES `order` (`id`),
  CONSTRAINT `OrderDetail_productID_fk` FOREIGN KEY (`productID`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderdetail`
--

LOCK TABLES `orderdetail` WRITE;
/*!40000 ALTER TABLE `orderdetail` DISABLE KEYS */;
INSERT INTO `orderdetail` VALUES (15,3,13,2,598.00),(16,25,13,3,2397.00),(17,41,14,3,119.85),(18,16,15,2,538.00),(19,19,15,3,1197.00),(20,15,15,2,198.00),(21,15,16,1,99.00),(22,8,17,3,1347.00),(23,3,18,1,299.00),(24,13,18,2,458.00),(25,17,19,5,995.00),(26,37,19,4,800.00),(27,37,20,4,800.00),(28,24,21,3,1107.00),(29,1,22,1,179.00),(30,4,22,3,1347.00),(31,2,23,3,2397.00),(32,15,23,1,369.00),(33,8,24,2,598.00),(34,11,24,1,299.00);
/*!40000 ALTER TABLE `orderdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderstatus`
--

DROP TABLE IF EXISTS `orderstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderstatus` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `bootstapicon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderstatus`
--

LOCK TABLES `orderstatus` WRITE;
/*!40000 ALTER TABLE `orderstatus` DISABLE KEYS */;
INSERT INTO `orderstatus` VALUES (1,'Wait for delivery','bi bi-hourglass'),(2,'Shipping','bi bi-truck'),(3,'Accomplished','bi bi-calendar2-check'),(4,'Canceled','bi bi-box-seam'),(5,'Wait for confirmation','bi bi-clock');
/*!40000 ALTER TABLE `orderstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,'COD'),(2,'Pay at shop');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `productName` varchar(255) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `categoryId` bigint DEFAULT NULL,
  `description` text,
  `quantity` int DEFAULT NULL,
  `isActive` tinyint DEFAULT NULL,
  `createAt` timestamp NULL DEFAULT NULL,
  `createBy` varchar(50) DEFAULT NULL,
  `modifyAt` timestamp NULL DEFAULT NULL,
  `modifyBy` varchar(50) DEFAULT NULL,
  `image` text,
  `productStatusId` bigint DEFAULT NULL,
  `saleprice` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Product_categoryId_fk` (`categoryId`),
  KEY `Product_productStatusId_fk` (`productStatusId`),
  CONSTRAINT `Product_categoryId_fk` FOREIGN KEY (`categoryId`) REFERENCES `category` (`id`),
  CONSTRAINT `Product_productStatusId_fk` FOREIGN KEY (`productStatusId`) REFERENCES `productstatus` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'CYPRESS CHAMPAGNE DOUBLE WALL SCONCE',NULL,379.00,2,'',15,1,'2024-04-02 03:19:54',NULL,'2024-05-14 10:06:06','admin','/img/lightType/w8.jpg',2,179.00),(2,'ELEONORA MARBLE TABLE LAMP',NULL,999.00,3,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',7,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/l7.jpg',2,799.00),(3,'SABINE POLISHED BRASS ARTICULATING WALL SCONCE',NULL,199.00,2,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/w13.jpg',3,NULL),(4,'LAMINA POLISHED BRASS PENDANT LIGHT',NULL,449.00,1,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',13,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/cl9.jpg',1,NULL),(5,'SABINE BLACKENED BRASS ARTICULATING WALL SCONCE',NULL,199.00,2,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/w14.jpg',3,NULL),(6,'BRIO BLACKENED BRASS PENDANT LIGHT',NULL,399.00,1,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/cl2.jpg',1,NULL),(7,'COPA TIERED NATURAL RATTAN PENDANT LIGHT',NULL,499.00,1,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/cl4.jpg',1,NULL),(8,'EXPOSIOR INDOOR/OUTDOOR BLACK PENDANT LIGHT MODEL 017',NULL,299.00,1,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/cl5.jpg',1,NULL),(9,'HAKKA CONICAL RATTAN PENDANT LIGHT',NULL,400.00,1,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/cl7.jpg',1,NULL),(10,'HAMMERED BRASS SHALLOW DOME PENDANT LIGHT',NULL,399.00,1,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/cl8.jpg',1,NULL),(11,'BRIO POLISHED BRASS PENDANT LIGHT',NULL,399.00,1,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',9,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/cl3.jpg',2,299.00),(12,'LANI WHITE PENDANT LIGHT',NULL,449.00,1,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/cl10.jpg',1,NULL),(13,'BRIO POLISHED BRASS ARTICULATING WALL SCONCE',NULL,269.00,2,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/w7.jpg',1,NULL),(14,'GRAZIANO INDOOR/OUTDOOR TRAVERTINE WALL SCONCE',NULL,299.00,2,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/w10.jpg',1,NULL),(15,'CRINKLE POLISHED BRASS TABLE LAMP',NULL,369.00,3,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',9,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/l6.jpg',1,NULL),(16,'BRIO BLACKENED BRASS ARTICULATING WALL SCONCE',NULL,269.00,2,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/w6.jpg',1,NULL),(17,'SOPORTE BLACKENED BRASS TABLE LAMP',NULL,399.00,3,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/l1.jpg',1,NULL),(18,'SOPORTE POLISHED BRASS TABLE LAMP',NULL,399.00,3,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/l2.jpg',1,NULL),(19,'BEAU BLACK TABLE LAMP',NULL,249.00,3,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/l3.jpg',1,NULL),(20,'BEAU POLISHED BRASS TABLE LAMP',NULL,249.00,3,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/l4.jpg',1,NULL),(21,'BIANCA MARBLE TABLE LAMP',NULL,269.00,3,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/l5.jpg',1,NULL),(22,'EXPOSIOR POLISHED BRASS AND WALNUT TABLE LAMP',NULL,369.00,3,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/l8.jpg',1,NULL),(23,'GRAZIANO INDOOR/OUTDOOR TRAVERTINE WALL SCONCE',NULL,299.00,4,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/od2.jpg',1,NULL),(24,'RONDA INDOOR/OUTDOOR BLACK FLUSH MOUNT LIGHT',NULL,199.00,4,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/od3.jpg',1,NULL),(25,'RONDA INDOOR/OUTDOOR MATTE BLACK WALL SCONCE',NULL,149.00,4,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/od4.jpg',1,NULL),(26,'RONDA INDOOR/OUTDOOR POLISHED BRASS FLUSH MOUNT LIGHT',NULL,199.00,4,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/od5.jpg',1,NULL),(27,'RONDA INDOOR/OUTDOOR BLACK FLUSH MOUNT LIGHT',NULL,199.00,4,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/od6.jpg',1,NULL),(28,'5 BROWN WINGS FAN',NULL,191.00,5,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/f1.jpg',1,NULL),(29,'3 BROWN WINGS FAN',NULL,80.00,5,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/f2.jpg',1,NULL),(30,'BALL CANDLES SET OF 2',NULL,12.95,6,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/ha1.jpg',1,NULL),(31,'BRIGHTON TAPERED POLISHED STAINLESS STEEL PENDANT LIGHT 2',NULL,499.00,1,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/cl19.jpg',2,199.00),(32,'ALDUS IVORY AND POLISHED BRASS ARTICULATING WALL SCONCE',NULL,229.00,2,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/w4.jpg',3,NULL),(33,'EXPOSIOR INDOOR/OUTDOOR BLACK PENDANT LIGHT MODEL 017',NULL,299.00,4,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/od1.jpg',1,NULL),(34,'5 BLACK WINGS FAN',NULL,191.00,5,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/f3.jpg',1,NULL),(35,'3 BROWN WINGS FAN PREMIUM',NULL,91.00,5,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/f4.jpg',1,NULL),(36,'5 WHITE WINGS FA',NULL,191.00,5,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/f9.jpg',1,NULL),(37,'BLACK TWISTED TAPER CANDLES SET OF 2',NULL,4.95,6,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/ha2.jpg',1,NULL),(38,'BRIX GOLDEN BLACK MARBLE INCENSE BURNER',NULL,39.95,6,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/ha3.jpg',1,NULL),(39,'CINQ LARGE MULTI WHITE TAPER CANDLE HOLDER',NULL,199.00,6,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/ha4.jpg',1,NULL),(40,'DREAMER IN LONDON CEDARWOOD AND VANILLA CANDLE',NULL,65.00,6,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/ha5.jpg',1,NULL),(41,'FALCON METAL INCENSE BURNER',NULL,69.95,6,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/ha6.jpg',1,NULL),(42,'ALDUS BLACK AND POLISHED BRASS ARTICULATING WALL SCONCE',NULL,229.00,2,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',8,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/w3.jpg',1,NULL),(43,'5 BROWN LEAF WINGS FAN',NULL,200.00,5,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/f5.jpg',1,NULL),(44,'WHITE TWISTED TAPER CANDLES SET OF 2',NULL,4.95,6,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/ha7.jpg',1,NULL),(45,'CINQ MEDIUM MULTI WHITE TAPER CANDLE HOLDER',NULL,99.00,6,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/ha8.jpg',1,NULL),(46,'DREAMER IN LONDON SCENTED CANDLE',NULL,59.00,6,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/ha9.jpg',1,NULL),(47,'MYSTIC CITY LONDON SCENTED CANDLE',NULL,65.00,6,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/ha10.jpg',1,NULL),(48,'BRIGHTON TAPERED BRASS PENDANT LIGHT',NULL,499.00,1,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/cl18.jpg',1,NULL),(49,'CINQ SMALL MULTI WHITE TAPER CANDLE HOLDER',NULL,59.00,6,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/ha11.jpg',1,NULL),(50,'DREAMER IN LONDON SCENTED CANDLE',NULL,59.00,6,'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...',10,1,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/ha12.jpg',1,NULL),(51,'NEW CEILING LIGHT',NULL,500.00,1,'Bed ceiling light',14,0,'2024-04-02 03:19:54',NULL,NULL,NULL,'/img/lightType/20240514_162547_images.jpg',1,NULL),(52,'ASSEMBLED CERAMIC TABLE LAMP GRAY ',NULL,500.00,3,'Beautiful lamp',18,1,'2024-05-14 09:31:28','admin',NULL,NULL,'/img/lightType/20240514_163127_GUEST_43f55026-adf3-4fce-aed3-817994ff6d0e.jpg',1,NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productstatus`
--

DROP TABLE IF EXISTS `productstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productstatus` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productstatus`
--

LOCK TABLES `productstatus` WRITE;
/*!40000 ALTER TABLE `productstatus` DISABLE KEYS */;
INSERT INTO `productstatus` VALUES (1,'avaiable'),(2,'sale'),(3,'bestseller'),(4,'outofstock');
/*!40000 ALTER TABLE `productstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rate`
--

DROP TABLE IF EXISTS `rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint DEFAULT NULL,
  `productId` bigint DEFAULT NULL,
  `star` smallint DEFAULT NULL,
  `createAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Rate_userId_fk` (`userId`),
  KEY `Rate_productId_fk` (`productId`),
  CONSTRAINT `Rate_productId_fk` FOREIGN KEY (`productId`) REFERENCES `product` (`id`),
  CONSTRAINT `Rate_userId_fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rate`
--

LOCK TABLES `rate` WRITE;
/*!40000 ALTER TABLE `rate` DISABLE KEYS */;
/*!40000 ALTER TABLE `rate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Admin'),(2,'User');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `orderID` bigint DEFAULT NULL,
  `paymentID` bigint DEFAULT NULL,
  `total` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Transaction_orderID_fk` (`orderID`),
  KEY `Transaction_paymentID_fk` (`paymentID`),
  CONSTRAINT `Transaction_orderID_fk` FOREIGN KEY (`orderID`) REFERENCES `order` (`id`),
  CONSTRAINT `Transaction_paymentID_fk` FOREIGN KEY (`paymentID`) REFERENCES `payment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,13,2,2995.00),(2,14,2,3114.85),(3,15,1,5047.85),(4,16,1,5146.85),(5,17,2,6493.85),(6,18,2,7250.85),(7,19,1,9045.85),(8,20,1,9845.85),(9,21,1,10952.85),(10,22,1,1526.00),(11,23,2,2766.00),(12,24,2,897.00);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `RoleId` bigint DEFAULT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `dob` timestamp NULL DEFAULT NULL,
  `address` text,
  `verifyCode` varchar(6) DEFAULT NULL,
  `verifyAt` timestamp NULL DEFAULT NULL,
  `isActive` tinyint DEFAULT NULL,
  `createAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `User_RoleId_fk` (`RoleId`),
  CONSTRAINT `User_RoleId_fk` FOREIGN KEY (`RoleId`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'chicandlighting@gmail.com','0926539328','admin','263fec58861449aacc1c328a4aff64aff4c62df4a2d50b3f207fa89b6e242c9aa778e7a8baeffef85b6ca6d2e7dc16ff0a760d59c13c238f6bcdc32f8ce9cc62',1,'Admin',NULL,NULL,'Hanoi University, Nguyen Trai, Thanh Xuan, Ha Noi, Viet Nam',NULL,NULL,1,'2024-04-02 15:16:53'),(2,'sonnguyen10112003@gmail.com',NULL,'hoangson123','e13efc991a9bf44bbb4da87cdbb725240184585ccaf270523170e008cf2a3b85f45f86c3da647f69780fb9e971caf5437b3d06d418355a68c9760c70a31d05c7',2,'Son','Nguyen',NULL,'Pho Moi, Thi Tran Cho, Yen Phong, Bac Ninh','YJSPCE','2024-04-02 16:48:56',1,'2024-04-02 16:40:06'),(3,'sonhoang10112003@gmail.com',NULL,'hoangson1234','e13efc991a9bf44bbb4da87cdbb725240184585ccaf270523170e008cf2a3b85f45f86c3da647f69780fb9e971caf5437b3d06d418355a68c9760c70a31d05c7',2,'Nguyen Duc','Son',NULL,'Yen Phong','ISGGFZ','2024-05-13 02:02:18',1,'2024-05-13 02:01:53');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-14 19:57:37
