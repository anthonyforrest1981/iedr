INSERT INTO `Email` VALUES
  (903, 'Test Template 4', NULL, NULL, NULL, NULL, NULL, 'asd@iedr.ie', '1', '0', NULL, NULL, NULL, NULL, CURDATE() - interval 1 Day, 'Y', NULL, NULL, 'Y');

INSERT INTO `EmailDisabler` VALUES (903, 'IDL2-IEDR', 'Y', CURDATE() - interval 1 Day);

-- CRS-696

INSERT INTO `Domain` VALUES
-- for testing date ranges
('rough-dates.ie', 'Test Holder', 'Body Corporate (Ltd,PLC,Company)', 'Registered Business Name', 00000001, NULL, NULL, '2002-07-30 16:48:11', '2010-09-28 16:48:11', '2008-08-11 15:34:21', 'N', '', 'N', 'Test Remark', 'N', 17, NULL, '2012-10-27 16:48:11', '2013-11-26 16:48:11', '2004-08-29 16:48:11', 'ABC123456789', '2020-12-25 16:48:11', NULL);

INSERT INTO `DomainHist` VALUES
-- deleted domain for testing date ranges
(NULL, 'deleted-rough-dates.ie', 'Test Holder', 'Body Corporate (Ltd,PLC,Company)', 'Registered Business Name', 00000001, NULL, '2013-05-23 16:48:11', '2002-02-25 16:48:11', '2009-03-24 16:48:11', '2008-08-11 15:34:21', 'N', '', 'N', 'Test Remark', 'APIT1-IEDR', '2014-06-21 16:48:11', 'N', 387, NULL, '2012-04-23 16:48:11', CONCAT(CURDATE() - interval 100 Day, ' 16:48:11'), NULL, NULL, NULL, NULL);

INSERT INTO `TransfersHist` VALUES
(NULL, 'rough-dates.ie', '2004-08-29 16:48:11', 'APITEST-IEDR', 'APIT1-IEDR');

INSERT INTO `DNS` VALUES
('rough-dates.ie', 'dns1.myhostns.com', '', '', 1),
('rough-dates.ie', 'dns2.myhostns.com', '', '', 2);

INSERT INTO `DNSHist` VALUES
((SELECT `Chng_Id` FROM `DomainHist` WHERE `D_Name` = 'deleted-rough-dates.ie'), 'deleted-rough-dates.ie', 'dns1.myhostns.com', '', '', 1),
((SELECT `Chng_Id` FROM `DomainHist` WHERE `D_Name` = 'deleted-rough-dates.ie'), 'deleted-rough-dates.ie', 'dns2.myhostns.com', '', '', 2);

INSERT INTO `Contact` VALUES
('rough-dates.ie', 'APIT1-IEDR', 'A'),
('rough-dates.ie', 'APIT1-IEDR', 'B'),
('rough-dates.ie', 'IDL2-IEDR', 'C'),
('rough-dates.ie', 'APIT1-IEDR', 'T');

INSERT INTO `ContactHist` VALUES
((SELECT `Chng_Id` FROM `DomainHist` WHERE `D_Name` = 'deleted-rough-dates.ie'), 'deleted-rough-dates.ie', 'APIT1-IEDR', 'A'),
((SELECT `Chng_Id` FROM `DomainHist` WHERE `D_Name` = 'deleted-rough-dates.ie'), 'deleted-rough-dates.ie', 'APIT1-IEDR', 'B'),
((SELECT `Chng_Id` FROM `DomainHist` WHERE `D_Name` = 'deleted-rough-dates.ie'), 'deleted-rough-dates.ie', 'APIT1-IEDR', 'C'),
((SELECT `Chng_Id` FROM `DomainHist` WHERE `D_Name` = 'deleted-rough-dates.ie'), 'deleted-rough-dates.ie', 'APIT1-IEDR', 'T');

