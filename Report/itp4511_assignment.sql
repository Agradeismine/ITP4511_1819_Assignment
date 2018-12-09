-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- 主機: localhost
-- 產生時間： 2018 年 12 月 09 日 11:03
-- 伺服器版本: 10.1.36-MariaDB
-- PHP 版本： 7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `itp4511_assignment`
--

-- --------------------------------------------------------

--
-- 資料表結構 `Account`
--

CREATE TABLE `Account` (
  `userId` int(11) NOT NULL,
  `username` varchar(25) COLLATE utf8_bin NOT NULL,
  `password` varchar(25) COLLATE utf8_bin NOT NULL,
  `role` varchar(20) COLLATE utf8_bin NOT NULL,
  `sex` char(1) COLLATE utf8_bin DEFAULT NULL,
  `district` varchar(20) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- 資料表的匯出資料 `Account`
--

INSERT INTO `Account` (`userId`, `username`, `password`, `role`, `sex`, `district`) VALUES
(1, 'admin1', '123456', 'admin', 'm', 'Islands'),
(2, 'owner2', '234567', 'owner', 'm', 'Kwai Tsing'),
(3, 'user3', '345678', 'user', 'f', 'North'),
(4, 'owner4', '456789', 'owner', 'm', 'Sai Kung'),
(5, 'owner5', '567890', 'owner', 'f', 'Southern'),
(6, 'user6', '678901', 'user', 'm', 'Southern');

-- --------------------------------------------------------

--
-- 資料表結構 `Menu`
--

CREATE TABLE `Menu` (
  `RestaurantrestId` int(11) NOT NULL,
  `imgId` int(11) NOT NULL,
  `imgName` varchar(100) NOT NULL,
  `menuType` varchar(20) NOT NULL,
  `menuPath` varchar(255) NOT NULL,
  `menuStartTime` date DEFAULT NULL,
  `menuEndTime` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 資料表的匯出資料 `Menu`
--

INSERT INTO `Menu` (`RestaurantrestId`, `imgId`, `imgName`, `menuType`, `menuPath`, `menuStartTime`, `menuEndTime`) VALUES
(1, 8, 'Test Menu ', 'General', '0001SME7D6BEAC5C607A38kx.jpg', NULL, NULL),
(1, 9, 'Test Menu 2', 'Special', '1522192083.jpg', '2018-12-01', '2018-12-08'),
(1, 10, 'Test Menu 3', 'General', 'mcdonalds-fries-cheese-bacon-loaded-809.jpg', NULL, NULL),
(1, 11, 'Test Menu 4', 'General', 'mcdonalds-e1367993250945.jpg', NULL, NULL),
(1, 12, 'dog', 'General', 'IMG_7999.JPG', NULL, NULL);

-- --------------------------------------------------------

--
-- 資料表結構 `Restaurant`
--

CREATE TABLE `Restaurant` (
  `restId` int(11) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ownerId` int(11) NOT NULL,
  `restIcon` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `tel` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 資料表的匯出資料 `Restaurant`
--

INSERT INTO `Restaurant` (`restId`, `name`, `ownerId`, `restIcon`, `address`, `description`, `tel`) VALUES
(1, 'McDonald', 2, 'Mcdonalds-300x300.png', 'McDonald Address', 'McDonald Description', 12345678),
(2, 'KFC', 4, 'kfc-logo.jpg', 'KFC Address', 'KFC Description', 23456789),
(3, 'Chinese Restaurant', 5, 'the-best-chinese-restaurant.jpg', 'Chinese Restaurant Address', 'Chinese Restaurant Description', 34567890);

-- --------------------------------------------------------

--
-- 資料表結構 `RestaurantComment`
--

CREATE TABLE `RestaurantComment` (
  `RestaurantrestId` int(11) NOT NULL,
  `AccountuserId` int(11) NOT NULL,
  `Mood` tinyint(1) NOT NULL,
  `contents` varchar(255) DEFAULT NULL,
  `title` varchar(30) NOT NULL,
  `mealDate` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 資料表的匯出資料 `RestaurantComment`
--

INSERT INTO `RestaurantComment` (`RestaurantrestId`, `AccountuserId`, `Mood`, `contents`, `title`, `mealDate`) VALUES
(1, 1, 1, 'This is an Test Contents.', 'This is an Comment Title', '2018-12-07'),
(2, 3, 0, 'No Comment', 'This is an test comment fo KFC', '2018-11-01'),
(3, 3, 1, 'Hello', 'Test comment for Dim Sum', '2018-12-07'),
(3, 3, 1, 'Food is good', 'Good food', '2018-11-02'),
(1, 1, 0, '', 'Garbage', '2018-09-01');

-- --------------------------------------------------------

--
-- 資料表結構 `RestaurantTag`
--

CREATE TABLE `RestaurantTag` (
  `RestaurantrestId` int(11) NOT NULL,
  `tagName` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 資料表的匯出資料 `RestaurantTag`
--

INSERT INTO `RestaurantTag` (`RestaurantrestId`, `tagName`) VALUES
(1, 'burger'),
(1, 'fast food'),
(2, 'fast food'),
(3, 'chinese food'),
(3, 'dim sum');

-- --------------------------------------------------------

--
-- 資料表結構 `RestViewCount`
--

CREATE TABLE `RestViewCount` (
  `RestaurantrestId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `date` date NOT NULL,
  `district` varchar(20) DEFAULT NULL,
  `count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 資料表的匯出資料 `RestViewCount`
--

INSERT INTO `RestViewCount` (`RestaurantrestId`, `userId`, `date`, `district`, `count`) VALUES
(1, 1, '2018-12-09', 'Islands', 1),
(3, 1, '2018-12-09', 'Islands', 1),
(2, 1, '2018-12-09', 'Islands', 1),
(1, 3, '2018-12-09', 'North', 9),
(2, 3, '2018-12-09', 'North', 3),
(3, 3, '2018-12-09', 'North', 2),
(1, 0, '2018-12-09', NULL, 56);

-- --------------------------------------------------------

--
-- 資料表結構 `SearchHistory`
--

CREATE TABLE `SearchHistory` (
  `keyword` varchar(255) NOT NULL,
  `count` int(11) NOT NULL,
  `date` date NOT NULL,
  `district` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 資料表的匯出資料 `SearchHistory`
--

INSERT INTO `SearchHistory` (`keyword`, `count`, `date`, `district`) VALUES
('Dim', 1, '2018-12-08', 'Islands'),
('sum', 3, '2018-12-08', 'Islands'),
('kfc', 6, '2018-12-08', 'Islands'),
('mcd', 18, '2018-12-08', 'Islands'),
('mcd', 2, '2018-12-08', 'noDistrict'),
('m', 6, '2018-12-08', 'noDistrict'),
('f', 1, '2018-12-09', 'Islands'),
('ckic', 3, '2018-12-09', 'Islands'),
('kin', 1, '2018-12-09', 'Islands'),
('ch', 7, '2018-12-09', 'Islands'),
('chic', 2, '2018-12-09', 'Islands'),
('chicken', 5, '2018-12-09', 'Islands'),
('fast food', 2, '2018-12-09', 'Islands'),
('burger', 2, '2018-12-09', 'Islands'),
('fas', 1, '2018-12-09', 'Islands'),
('kf', 7, '2018-12-09', 'Islands'),
('mc', 4, '2018-12-09', 'Islands'),
('bur', 1, '2018-12-09', 'Islands'),
('cke', 1, '2018-12-09', 'Islands'),
('mcd', 1, '2018-12-09', 'North'),
('hi', 3, '2018-12-09', 'Islands'),
('m', 9, '2018-12-09', 'Islands');

-- --------------------------------------------------------

--
-- 資料表結構 `UserFavourite`
--

CREATE TABLE `UserFavourite` (
  `AccountuserId` int(11) NOT NULL,
  `favouriteId` int(11) NOT NULL,
  `favouriteType` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 資料表的匯出資料 `UserFavourite`
--

INSERT INTO `UserFavourite` (`AccountuserId`, `favouriteId`, `favouriteType`) VALUES
(1, 1, 'restaurant'),
(1, 10, 'menu'),
(3, 3, 'restaurant');

--
-- 已匯出資料表的索引
--

--
-- 資料表索引 `Account`
--
ALTER TABLE `Account`
  ADD PRIMARY KEY (`userId`);

--
-- 資料表索引 `Menu`
--
ALTER TABLE `Menu`
  ADD PRIMARY KEY (`imgId`),
  ADD KEY `FKMenu477873` (`RestaurantrestId`);

--
-- 資料表索引 `Restaurant`
--
ALTER TABLE `Restaurant`
  ADD PRIMARY KEY (`restId`),
  ADD KEY `FKRestaurant429632` (`ownerId`);

--
-- 資料表索引 `RestaurantComment`
--
ALTER TABLE `RestaurantComment`
  ADD KEY `FKRestaurant138993` (`RestaurantrestId`),
  ADD KEY `FKRestaurant417839` (`AccountuserId`);

--
-- 資料表索引 `RestaurantTag`
--
ALTER TABLE `RestaurantTag`
  ADD KEY `FKRestaurant900971` (`RestaurantrestId`);

--
-- 資料表索引 `RestViewCount`
--
ALTER TABLE `RestViewCount`
  ADD KEY `FKRestViewCo544078` (`RestaurantrestId`);

--
-- 資料表索引 `UserFavourite`
--
ALTER TABLE `UserFavourite`
  ADD KEY `FKUserFavour280162` (`AccountuserId`);

--
-- 在匯出的資料表使用 AUTO_INCREMENT
--

--
-- 使用資料表 AUTO_INCREMENT `Account`
--
ALTER TABLE `Account`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- 使用資料表 AUTO_INCREMENT `Menu`
--
ALTER TABLE `Menu`
  MODIFY `imgId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- 使用資料表 AUTO_INCREMENT `Restaurant`
--
ALTER TABLE `Restaurant`
  MODIFY `restId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- 已匯出資料表的限制(Constraint)
--

--
-- 資料表的 Constraints `Menu`
--
ALTER TABLE `Menu`
  ADD CONSTRAINT `FKMenu477873` FOREIGN KEY (`RestaurantrestId`) REFERENCES `Restaurant` (`restId`);

--
-- 資料表的 Constraints `Restaurant`
--
ALTER TABLE `Restaurant`
  ADD CONSTRAINT `FKRestaurant429632` FOREIGN KEY (`ownerId`) REFERENCES `Account` (`userId`);

--
-- 資料表的 Constraints `RestaurantComment`
--
ALTER TABLE `RestaurantComment`
  ADD CONSTRAINT `FKRestaurant138993` FOREIGN KEY (`RestaurantrestId`) REFERENCES `Restaurant` (`restId`),
  ADD CONSTRAINT `FKRestaurant417839` FOREIGN KEY (`AccountuserId`) REFERENCES `Account` (`userId`);

--
-- 資料表的 Constraints `RestaurantTag`
--
ALTER TABLE `RestaurantTag`
  ADD CONSTRAINT `FKRestaurant900971` FOREIGN KEY (`RestaurantrestId`) REFERENCES `Restaurant` (`restId`);

--
-- 資料表的 Constraints `RestViewCount`
--
ALTER TABLE `RestViewCount`
  ADD CONSTRAINT `FKRestViewCo544078` FOREIGN KEY (`RestaurantrestId`) REFERENCES `Restaurant` (`restId`);

--
-- 資料表的 Constraints `UserFavourite`
--
ALTER TABLE `UserFavourite`
  ADD CONSTRAINT `FKUserFavour280162` FOREIGN KEY (`AccountuserId`) REFERENCES `Account` (`userId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
