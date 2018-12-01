CREATE TABLE Account (
  userId   int(11) NOT NULL AUTO_INCREMENT,
  username varchar(25) COLLATE utf8_bin NOT NULL, 
  password varchar(25) COLLATE utf8_bin NOT NULL, 
  role     varchar(20) COLLATE utf8_bin NOT NULL, 
  sex      char(1) COLLATE utf8_bin, 
  district varchar(20) NOT NULL, 
  PRIMARY KEY (userId)) DEFAULT CHARSET = utf8 COLLATE = utf8_bin;
CREATE TABLE Restaurant (
  restId      int(11) NOT NULL AUTO_INCREMENT,
  name        varchar(100) COLLATE utf8_bin NOT NULL, 
  ownerId     int(11) NOT NULL, 
  restIcon    varchar(200) COLLATE utf8_bin, 
  address     varchar(255) COLLATE utf8_bin NOT NULL, 
  description varchar(255), 
  viewCount   int(11) NOT NULL, 
  PRIMARY KEY (restId)) DEFAULT CHARSET = latin1;
CREATE TABLE Menu (
  RestaurantrestId int(11) NOT NULL, 
  imgId            int(11) NOT NULL, 
  imgName          varchar(100) NOT NULL, 
  menuType         varchar(20) NOT NULL, 
  menuPath         varchar(255) NOT NULL);
CREATE TABLE RestaurantTag (
  RestaurantrestId int(11) NOT NULL, 
  tagName          varchar(30) NOT NULL);
CREATE TABLE UserFavourite (
  AccountuserId int(11) NOT NULL, 
  favouriteId   int(11) NOT NULL, 
  favouriteType varchar(20) NOT NULL);
CREATE TABLE RestaurantComment (
  RestaurantrestId int(11) NOT NULL, 
  AccountuserId    int(11) NOT NULL, 
  Mood             varchar(10) NOT NULL, 
  contents         varchar(255));
CREATE TABLE RestViewCount (
  RestaurantrestId int(11) NOT NULL, 
  userId           int(11) NOT NULL, 
  `date`           date NOT NULL, 
  district         varchar(20) NOT NULL);
CREATE TABLE SearchHistory (
  keyword varchar(255) NOT NULL, 
  count   int(11) NOT NULL);
ALTER TABLE Account AUTO_INCREMENT=100;
ALTER TABLE Menu ADD CONSTRAINT FKMenu477873 FOREIGN KEY (RestaurantrestId) REFERENCES Restaurant (restId);
ALTER TABLE UserFavourite ADD CONSTRAINT FKUserFavour280162 FOREIGN KEY (AccountuserId) REFERENCES Account (userId);
ALTER TABLE RestaurantComment ADD CONSTRAINT FKRestaurant138993 FOREIGN KEY (RestaurantrestId) REFERENCES Restaurant (restId);
ALTER TABLE RestaurantComment ADD CONSTRAINT FKRestaurant417839 FOREIGN KEY (AccountuserId) REFERENCES Account (userId);
ALTER TABLE RestViewCount ADD CONSTRAINT FKRestViewCo544078 FOREIGN KEY (RestaurantrestId) REFERENCES Restaurant (restId);
ALTER TABLE RestaurantTag ADD CONSTRAINT FKRestaurant900971 FOREIGN KEY (RestaurantrestId) REFERENCES Restaurant (restId);
ALTER TABLE Restaurant ADD CONSTRAINT FKRestaurant429632 FOREIGN KEY (ownerId) REFERENCES Account (userId);
