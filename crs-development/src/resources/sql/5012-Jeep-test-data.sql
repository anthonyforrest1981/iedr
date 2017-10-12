-- CRS-1036

UPDATE Domain SET
  D_Transfer_TS = NOW() - INTERVAL 1 YEAR
  WHERE D_Name = 'thedomena.totransfer1.ie';

-- CRS-1098

INSERT INTO `AccountSegment` VALUES (1, 'Test Segment');

UPDATE `Account` SET `Segment_ID` = 1 WHERE `A_Number` = 104;
UPDATE `AccountHist` SET `Segment_ID` = 1 WHERE `Chng_ID` = (SELECT `Chng_ID` FROM `Account` WHERE `A_Number` = 104);

-- CRS-1139

-- ticket with its tech status "stalled" - a notification email should be sent
UPDATE `DNS_Check_Notification` SET `Domain_Name` = 'tandl.ie' WHERE `Domain_Name` = 'domain1.ie';

-- ticket with its tech status "passed" - a notification email should not be sent
UPDATE `DNS_Check_Notification` SET `Domain_Name` = 'presb.ie' WHERE `Domain_Name` = 'domain2.ie';

-- no ticket - a notification email should not be sent
UPDATE `DNS_Check_Notification` SET `Domain_Name` = 'ygmhk.ie' WHERE `Domain_Name` = 'domain3.ie';

-- CRS-1127

-- Test for searching for locked domains
UPDATE `Domain` SET `DSM_State` = 1, `D_Locking_TS` = NOW() - interval 6 month, `D_LockingRenewal_Dt` = CURDATE() + interval 6 month WHERE `D_Name` IN ('autocreated2.ie', 'chriswilson.ie');
UPDATE `Domain` SET `DSM_State` = 25 WHERE `D_Name` = 'klatt.ie';
UPDATE `DomainHist` SET `DSM_State` = 1, `D_Locking_TS` = NOW() - interval 6 month, `D_LockingRenewal_Dt` = CURDATE() + interval 6 month WHERE `D_Name` IN ('autocreated2.ie', 'chriswilson.ie');
UPDATE `DomainHist` SET `DSM_State` = 25 WHERE `D_Name` = 'klatt.ie';

-- CRS-1180

INSERT INTO DomainHist VALUES (NULL, 'twoadmincontacts.ie', 'Test Holder 0001', 3, 2, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 666),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'IDL2-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('twoadmincontacts.ie', 'Test Holder 0001', 3, 2, 666,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'twoadmincontacts.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'twoadmincontacts.ie'), 'twoadmincontacts.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'APIT1-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'twoadmincontacts.ie'), 'twoadmincontacts.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'APIT2-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'twoadmincontacts.ie'), 'twoadmincontacts.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'APIT2-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'twoadmincontacts.ie'), 'twoadmincontacts.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'APIT2-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'twoadmincontacts.ie'), 'twoadmincontacts.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'APIT2-IEDR'), 'Tech');
INSERT INTO Contact VALUES
('twoadmincontacts.ie', 'APIT1-IEDR', 'Admin'),
('twoadmincontacts.ie', 'APIT2-IEDR', 'Admin'),
('twoadmincontacts.ie', 'APIT2-IEDR', 'Billing'),
('twoadmincontacts.ie', 'APIT2-IEDR', 'Creator'),
('twoadmincontacts.ie', 'APIT2-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'twoadmincontacts.ie'), 'twoadmincontacts.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'twoadmincontacts.ie'), 'twoadmincontacts.ie', 'ns2.dns.ie', NULL, NULL, '2');
INSERT INTO DNS VALUES
('twoadmincontacts.ie', 'ns1.dns.ie', NULL, NULL, '1'),
('twoadmincontacts.ie', 'ns2.dns.ie', NULL, NULL, '2');

-- CRS-1183

UPDATE `Domain` SET `D_Holder` = 'DSQ GROUP LIMITED' WHERE `D_Name` = 'thedomena.668.ie';

UPDATE `Ticket` SET `A_Number` = 101 WHERE `D_Name` = 'thedomena.668.ie';

-- CRS-1190

-- Fix for Account 102 missing it's billing NH - it's needed now that SecondaryMarket does enforce
-- foreign keys constraints

INSERT INTO `NicHandle`
(`Nic_Handle`,
 `NH_Name`,
 `A_Number`,
 `Co_Name`,
 `NH_Address`,
 `County_Id`,
 `Country_Id`,
 `NH_Email`,
 `NH_Status`,
 `NH_Status_TS`,
 `NH_Reg_TS`,
 `NH_Change_TS`,
 `NH_BillC_Ind`,
 `NH_Remark`,
 `NH_Creator`,
 `Vat_Category`,
 `Vat_Reg_Id`,
 `NH_Exported`,
 `Chng_ID`)
SELECT
  `Nic_Handle`,
  `NH_Name`,
  `A_Number`,
  `Co_Name`,
  `NH_Address`,
  `County_Id`,
  `Country_Id`,
  `NH_Email`,
  `NH_Status`,
  `NH_Status_TS`,
  `NH_Reg_TS`,
  `NH_Change_TS`,
  `NH_BillC_Ind`,
  `NH_Remark`,
  `NH_Creator`,
  `Vat_Category`,
  `Vat_Reg_Id`,
  `NH_Exported`,
  `NicHandleHist`.`Chng_ID`
FROM `NicHandleHist` LEFT JOIN `AccountHist` ON `NicHandleHist`.`Account_Chng_ID` = `AccountHist`.`Chng_ID`
WHERE Nic_Handle = 'IOA3-IEDR';

INSERT INTO `Domain` VALUES
  ('sec-mrkt-domain-1.ie', 'Secondary Market Tester', 5, 10, 101, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('sec-mrkt-domain-2.ie', 'Secondary Market Tester', 2, 11, 102, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('sec-mrkt-domain-3.ie', 'Secondary Market Tester', 2, 11, 101, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('sec-mrkt-domain-4.ie', 'Secondary Market Tester', 2, 11, 104, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `DNS` VALUES
  ('sec-mrkt-domain-1.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-domain-1.ie', 'dns2.ie', NULL, NULL, 2),
  ('sec-mrkt-domain-2.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-domain-2.ie', 'dns2.ie', NULL, NULL, 2),
  ('sec-mrkt-domain-3.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-domain-3.ie', 'dns2.ie', NULL, NULL, 2),
  ('sec-mrkt-domain-4.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-domain-4.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `Contact` VALUES
  ('sec-mrkt-domain-1.ie', 'IDL2-IEDR', 'Admin'),
  ('sec-mrkt-domain-1.ie', 'TDI2-IEDR', 'Creator'),
  ('sec-mrkt-domain-1.ie', 'TDI2-IEDR', 'Tech'),
  ('sec-mrkt-domain-1.ie', 'IDL2-IEDR', 'Billing'),
  ('sec-mrkt-domain-2.ie', 'IOA3-IEDR', 'Admin'),
  ('sec-mrkt-domain-2.ie', 'IOA3-IEDR', 'Creator'),
  ('sec-mrkt-domain-2.ie', 'IOA3-IEDR', 'Tech'),
  ('sec-mrkt-domain-2.ie', 'IOA3-IEDR', 'Billing'),
  ('sec-mrkt-domain-3.ie', 'IOA3-IEDR', 'Admin'),
  ('sec-mrkt-domain-3.ie', 'IOA3-IEDR', 'Creator'),
  ('sec-mrkt-domain-3.ie', 'IOA3-IEDR', 'Tech'),
  ('sec-mrkt-domain-3.ie', 'IOA3-IEDR', 'Billing'),
  ('sec-mrkt-domain-4.ie', 'NNL2-IEDR', 'Admin'),
  ('sec-mrkt-domain-4.ie', 'NNL2-IEDR', 'Creator'),
  ('sec-mrkt-domain-4.ie', 'NNL2-IEDR', 'Tech'),
  ('sec-mrkt-domain-4.ie', 'NNL2-IEDR', 'Billing');

INSERT INTO `DomainHist` VALUES
  (NULL, 'sec-mrkt-domain-1.ie', 'Secondary Market Tester', 5, 10, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 101), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'IDL2-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  (NULL, 'sec-mrkt-domain-2.ie', 'Secondary Market Tester', 5, 10, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 102), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'IDL2-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  (NULL, 'sec-mrkt-domain-3.ie', 'Secondary Market Tester', 5, 10, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 101), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'IDL2-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  (NULL, 'sec-mrkt-domain-4.ie', 'Secondary Market Tester', 5, 10, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 104), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'IDL2-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `DNSHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-1.ie'), 'sec-mrkt-domain-1.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-1.ie'), 'sec-mrkt-domain-1.ie', 'dns2.ie', NULL, NULL, 2),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-2.ie'), 'sec-mrkt-domain-2.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-2.ie'), 'sec-mrkt-domain-2.ie', 'dns2.ie', NULL, NULL, 2),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-3.ie'), 'sec-mrkt-domain-3.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-3.ie'), 'sec-mrkt-domain-3.ie', 'dns2.ie', NULL, NULL, 2),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-4.ie'), 'sec-mrkt-domain-4.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-4.ie'), 'sec-mrkt-domain-4.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `ContactHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-1.ie'), 'sec-mrkt-domain-1.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-1.ie'), 'sec-mrkt-domain-1.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'TDI2-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-1.ie'), 'sec-mrkt-domain-1.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'TDI2-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-1.ie'), 'sec-mrkt-domain-1.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-2.ie'), 'sec-mrkt-domain-2.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IOA3-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-2.ie'), 'sec-mrkt-domain-2.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IOA3-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-2.ie'), 'sec-mrkt-domain-2.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IOA3-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-2.ie'), 'sec-mrkt-domain-2.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IOA3-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-3.ie'), 'sec-mrkt-domain-3.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IOA3-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-3.ie'), 'sec-mrkt-domain-3.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IOA3-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-3.ie'), 'sec-mrkt-domain-3.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IOA3-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-3.ie'), 'sec-mrkt-domain-3.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IOA3-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-4.ie'), 'sec-mrkt-domain-4.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'NNL2-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-4.ie'), 'sec-mrkt-domain-4.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'NNL2-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-4.ie'), 'sec-mrkt-domain-4.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'NNL2-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-4.ie'), 'sec-mrkt-domain-4.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'NNL2-IEDR'), 'Billing');

UPDATE `Domain` LEFT JOIN `DomainHist` USING(`D_Name`)
  SET `Domain`.`Chng_ID` = `DomainHist`.`Chng_ID`
  WHERE `D_Name` IN ('sec-mrkt-domain-1.ie', 'sec-mrkt-domain-2.ie', 'sec-mrkt-domain-3.ie', 'sec-mrkt-domain-4.ie');

INSERT INTO `SecondaryMarketBuyRequestHist` VALUES
  (1, NOW(), 'IDL2-IEDR', 2, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-domain-1.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Hist holder', NULL, 1, NULL, 1, NULL, 'Hist remark', 'Hist hostmaster remark', 'Hist admin name', NULL, 'ha@a.ie', NULL, 'Hist Test Inc.', NULL, 'Hist Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW(), NULL, NULL, 'New',
   (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR')),
  (2, NOW(), 'IDL2-IEDR', 3, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-domain-2.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IOA3-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 102), 'Hist holder', NULL, 1, NULL, 1, NULL, 'Hist remark', 'Hist hostmaster remark', 'Hist admin name', NULL, 'hb@b.ie', NULL, 'Hist Test Inc.', NULL, 'Hist Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW(), 'TESTAUTHCODE', NOW() - INTERVAL 1 DAY, 'Passed',
   (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR')),
  (3, NOW(), 'IDL2-IEDR', 4, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-domain-3.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Hist holder', NULL, 1, NULL, 1, NULL, 'Hist remark', 'Hist hostmaster remark', 'Hist admin name', NULL, 'hc@c.ie', NULL, 'Hist Test Inc.', NULL, 'Hist Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW(), 'AUTHCODEFOR3', NOW() + INTERVAL 1 DAY, 'Passed',
   (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR')),
  (4, NOW(), 'IDL2-IEDR', 1, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-domain-4.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'NNL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 104), 'Hist holder', NULL, 1, NULL, 1, NULL, 'Hist remark', 'Hist hostmaster remark', 'Hist admin name', NULL, 'hd@d.ie', NULL, 'Hist Test Inc.', NULL, 'Hist Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW(), NULL, NULL, 'New',
   (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'));
INSERT INTO `SecondaryMarketBuyRequestTelecomHist` VALUES
  (1, '123456789', 'Phone', 0),
  (2, '987654321', 'Phone', 0),
  (3, '123654345', 'Phone', 0),
  (4, '654321643', 'Phone', 0);
INSERT INTO `SecondaryMarketBuyRequest` VALUES
  (2, 'sec-mrkt-domain-1.ie', NULL, 'IDL2-IEDR', 101, 'First holder', NULL, 1, NULL, 1, NULL, 'First remark', 'First hostmaster remark', 'Admin1 name', NULL, 'a@a.ie', NULL, 'Test1 Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 20 DAY, 'TESTAUTHCODE', NOW() - INTERVAL 1 DAY, 'Passed', 'IDL2-IEDR', 1),
  (3, 'sec-mrkt-domain-2.ie', NULL, 'IOA3-IEDR', 102, 'Second holder', NULL, 1, NULL, 1, NULL, 'Second remark', 'Second hostmaster remark', 'Admin2 name', NULL, 'b@b.ie', NULL, 'Test2 Inc.', NULL, 'Test Street 2', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 10 DAY, 'AUTHCODEFOR3', NOW() + INTERVAL 1 DAY, 'Passed', 'TDI2-IEDR', 2),
  (4, 'sec-mrkt-domain-3.ie', NULL, 'IDL2-IEDR', 101, 'Third holder', NULL, 1, NULL, 1, NULL, 'Third remark', 'Third hostmaster remark', 'Admin3 name', NULL, 'c@c.ie', NULL, 'Test3 Inc.', NULL, 'Test Street 3', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 60 DAY, 'ANOTHERAUTHC', NOW() - INTERVAL 60 DAY, 'Passed', NULL, 3);
INSERT INTO `SecondaryMarketBuyRequestTelecom` VALUES
  (2, '123456789', 'Phone', 0),
  (3, '987654321', 'Phone', 0),
  (4, '123654345', 'Phone', 0);

-- CRS-1191

INSERT INTO `SecondaryMarketSellRequestHist` VALUES
  (1, DATE_FORMAT(CURDATE() - INTERVAL 1 DAY, '%Y-%m-%d 16:19:26'), 'IDL2-IEDR', 2, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), DATE_FORMAT(CURDATE() - INTERVAL 1 DAY, '%Y-%m-%d 12:54:01'), 'Processing', 1),
  (2, NOW() - INTERVAL 2 DAY, 'IOA3-IEDR', 3, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'IOA3-IEDR'), NOW() - INTERVAL 3 DAY, 'Processing', 2),
  (3, NOW() - INTERVAL 3 DAY, 'IDL2-IEDR', 1, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), DATE_FORMAT(CURDATE() - INTERVAL 10 DAY, '%Y-%m-%d 9:54:01'), 'Processing', 4);

INSERT INTO `SecondaryMarketSellRequest` VALUES
  (2, 'IDL2-IEDR', DATE_FORMAT(CURDATE() - INTERVAL 1 DAY, '%Y-%m-%d 12:54:01'), 'Processing', 2, 1),
  (3, 'IOA3-IEDR', NOW() - INTERVAL 2 DAY, 'Processing', 3, 2);

-- CRS-1188

UPDATE `Domain` SET `DSM_State` = `DSM_State` + 1024 WHERE `D_Name` IN
    (SELECT `D_Name` FROM `SecondaryMarketBuyRequest`);
UPDATE `Domain` SET `DSM_State` = `DSM_State` + 1024 WHERE `D_Name` IN
    (SELECT BR.`D_Name` FROM `SecondaryMarketSellRequest` SR LEFT JOIN `SecondaryMarketBuyRequest` BR ON BR.`Id` = SR.`BuyRequest_Id`);

-- CRS-1212

INSERT INTO `Domain` VALUES
  ('sec-mrkt-dir-from-reg.ie', 'Secondary Market Tester', 2, 5, 666, '2008-04-30', '2009-04-30', NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 1041, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('sec-mrkt-dir-from-dir.ie', 'Secondary Market Tester', 2, 5, 1, '2008-04-30', '2009-04-30', NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 1049, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('sec-mrkt-nrp.ie', 'Secondary Market Tester', 2, 5, 101, '2008-04-30', '2009-04-30', NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 1042, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `DNS` VALUES
  ('sec-mrkt-dir-from-reg.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-dir-from-reg.ie', 'dns2.ie', NULL, NULL, 2),
  ('sec-mrkt-dir-from-dir.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-dir-from-dir.ie', 'dns2.ie', NULL, NULL, 2),
  ('sec-mrkt-nrp.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-nrp.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `Contact` VALUES
  ('sec-mrkt-dir-from-reg.ie', 'APIT1-IEDR', 'Admin'),
  ('sec-mrkt-dir-from-reg.ie', 'APITEST-IEDR', 'Creator'),
  ('sec-mrkt-dir-from-reg.ie', 'APIT2-IEDR', 'Tech'),
  ('sec-mrkt-dir-from-reg.ie', 'APITEST-IEDR', 'Billing'),
  ('sec-mrkt-dir-from-dir.ie', 'AAG061-IEDR', 'Admin'),
  ('sec-mrkt-dir-from-dir.ie', 'AAG061-IEDR', 'Creator'),
  ('sec-mrkt-dir-from-dir.ie', 'AAG061-IEDR', 'Tech'),
  ('sec-mrkt-dir-from-dir.ie', 'AAG061-IEDR', 'Billing'),
  ('sec-mrkt-nrp.ie', 'IDL2-IEDR', 'Admin'),
  ('sec-mrkt-nrp.ie', 'IDL2-IEDR', 'Creator'),
  ('sec-mrkt-nrp.ie', 'TDI2-IEDR', 'Tech'),
  ('sec-mrkt-nrp.ie', 'IDL2-IEDR', 'Billing');

INSERT INTO `DomainHist` VALUES
  (NULL, 'sec-mrkt-dir-from-reg.ie', 'Secondary Market Tester', 5, 10, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 666), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'IDL2-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 1041, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  (NULL, 'sec-mrkt-dir-from-dir.ie', 'Secondary Market Tester', 5, 10, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 1), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'AAG061-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 1049, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  (NULL, 'sec-mrkt-nrp.ie', 'Secondary Market Tester', 5, 10, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 101), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'APITEST-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 1042, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `DNSHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-reg.ie'), 'sec-mrkt-dir-from-reg.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-reg.ie'), 'sec-mrkt-dir-from-reg.ie', 'dns2.ie', NULL, NULL, 2),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-dir.ie'), 'sec-mrkt-dir-from-dir.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-dir.ie'), 'sec-mrkt-dir-from-dir.ie', 'dns2.ie', NULL, NULL, 2),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-nrp.ie'), 'sec-mrkt-nrp.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-nrp.ie'), 'sec-mrkt-nrp.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `ContactHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-reg.ie'), 'sec-mrkt-dir-from-reg.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-reg.ie'), 'sec-mrkt-dir-from-reg.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'APITEST-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-reg.ie'), 'sec-mrkt-dir-from-reg.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'APIT2-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-reg.ie'), 'sec-mrkt-dir-from-reg.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'APITEST-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-dir.ie'), 'sec-mrkt-dir-from-dir.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAG061-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-dir.ie'), 'sec-mrkt-dir-from-dir.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAG061-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-dir.ie'), 'sec-mrkt-dir-from-dir.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAG061-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-dir.ie'), 'sec-mrkt-dir-from-dir.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAG061-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-nrp.ie'), 'sec-mrkt-nrp.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-nrp.ie'), 'sec-mrkt-nrp.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-nrp.ie'), 'sec-mrkt-nrp.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'TDI2-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-nrp.ie'), 'sec-mrkt-nrp.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Billing');

UPDATE `Domain` LEFT JOIN `DomainHist` USING(`D_Name`)
  SET `Domain`.`Chng_ID` = `DomainHist`.`Chng_ID`
  WHERE `D_Name` IN ('sec-mrkt-dir-from-reg.ie', 'sec-mrkt-dir-from-dir.ie', 'sec-mrkt-nrp.ie');

INSERT INTO `SecondaryMarketBuyRequestHist` VALUES
  (5, NOW(), 'AAG061-IEDR', 5, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-dir-from-reg.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAG061-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEFOR5', NOW(), 'Passed', NULL),
  (6, NOW(), 'AAU809-IEDR', 6, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-dir-from-dir.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAU809-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 1), 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEFOR6', NOW(), 'Passed', NULL),
  (7, NOW(), 'IDL2-IEDR', 7, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-nrp.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEFOR7', NOW(), 'Passed', NULL);
INSERT INTO `SecondaryMarketBuyRequestTelecomHist` VALUES
  (5, '123456789', 'Phone', 0),
  (6, '987654321', 'Phone', 0),
  (7, '987654321', 'Phone', 0);
INSERT INTO `SecondaryMarketBuyRequest` VALUES
  (5, 'sec-mrkt-dir-from-reg.ie', NULL, 'AAG061-IEDR', 101, 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEFOR5', NOW(), 'Passed', NULL, 5),
  (6, 'sec-mrkt-dir-from-dir.ie', NULL, 'AAU809-IEDR', 1, 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEFOR6', NOW(), 'Passed', NULL, 6),
  (7, 'sec-mrkt-nrp.ie', NULL, 'IDL2-IEDR', 101, 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEFOR7', NOW(), 'Passed', NULL, 7);
INSERT INTO `SecondaryMarketBuyRequestTelecom` VALUES
  (5, '123456789', 'Phone', 0),
  (6, '987654321', 'Phone', 0),
  (7, '987654321', 'Phone', 0);

-- CRS-1200

INSERT INTO `SecondaryMarketBuyRequestHist` VALUES
  (11, NOW(), 'IDL2-IEDR', 11, (SELECT Chng_ID FROM Domain WHERE D_Name = 'directdomain.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAU809-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 1), 'Hist holder', NULL, 1, NULL, 1, NULL, 'Hist remark', 'Hist hostmaster remark', 'Hist admin name', NULL, 'ha@a.ie', NULL, 'Hist Test Inc.', NULL, 'Hist Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 23 DAY, NULL, NULL, 'New',
   (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR')),
  (12, NOW(), 'IDL2-IEDR', 12, (SELECT Chng_ID FROM Domain WHERE D_Name = 'directdomain.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AHM344-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 1), 'Hist holder', NULL, 1, NULL, 1, NULL, 'Hist remark', 'Hist hostmaster remark', 'Hist admin name', NULL, 'hb@b.ie', NULL, 'Hist Test Inc.', NULL, 'Hist Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 23 DAY, NULL, NULL, 'New',
   (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR')),
  (13, NOW(), 'IDL2-IEDR', 13, (SELECT Chng_ID FROM Domain WHERE D_Name = 'webwebweb.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAA22-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 262), 'Another Hist holder', NULL, 1, NULL, 1, NULL, 'Hist remark', 'Hist hostmaster remark', 'Hist admin name', NULL, 'hc@c.ie', NULL, 'Hist Test Inc.', NULL, 'Hist Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 80 DAY, 'AUTHCODFOR13', NOW() - INTERVAL 80 DAY, 'Passed',
   (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'));
INSERT INTO `SecondaryMarketBuyRequestTelecomHist` VALUES
  (11, '123456789', 'Phone', 0),
  (12, '987654321', 'Phone', 0),
  (13, '123654345', 'Phone', 0);
INSERT INTO `SecondaryMarketBuyRequest` VALUES
  (11, 'directdomain.ie', NULL, 'AAU809-IEDR', 1, 'direct1 holder', NULL, 1, NULL, 1, NULL, 'direct1 remark', 'direct1 hostmaster remark', 'direct1 admin', NULL, 'direct1@a.ie', NULL, 'Direct1 Inc.', NULL, 'Direct Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 23 DAY, NULL, NULL, 'New', NULL, 11),
  (12, 'directdomain.ie', NULL, 'AHM344-IEDR', 1, 'direct2 holder', NULL, 1, NULL, 1, NULL, 'direct2 remark', 'direct2 hostmaster remark', 'direct2 admin', NULL, 'direct2@a.ie', NULL, 'Direct2 Inc.', NULL, 'Direct Street 2', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 23 DAY, NULL, NULL, 'New', NULL, 12),
  (13, 'webwebweb.ie', NULL, 'AAA22-IEDR', 262, 'reseller holder', NULL, 1, NULL, 1, NULL, 'Third remark', 'Third hostmaster remark', 'Admin3 name', NULL, 'reseller@a.ie', NULL, 'Reseller3 Inc.', NULL, 'Reseller Street 3', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 80 DAY, 'AUTHCODFOR13', NOW() - INTERVAL 80 DAY, 'Passed', NULL, 13);
INSERT INTO `SecondaryMarketBuyRequestTelecom` VALUES
  (11, '123456789', 'Phone', 0),
  (12, '987654321', 'Phone', 0),
  (13, '123654345', 'Phone', 0);

UPDATE Domain SET DSM_State = 1041 WHERE D_Name = 'webwebweb.ie';
UPDATE Domain SET DSM_State = 1049 WHERE D_Name = 'directdomain.ie';

-- CRS-1198

INSERT INTO `SecondaryMarketBuyRequestHist` VALUES
  (21, NOW(), 'IDL2-IEDR', 21, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-domain-3.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Hist holder', NULL, 1, NULL, 1, NULL, 'Hist remark', 'Hist hostmaster remark', 'Hist admin name', NULL, 'ha@a.ie', NULL, 'Hist Test Inc.', NULL, 'Hist Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 40 DAY, NULL, NULL, 'New',
   (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR')),
  (22, NOW(), 'IDL2-IEDR', 22, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-domain-3.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Hist holder', NULL, 1, NULL, 1, NULL, 'Hist remark', 'Hist hostmaster remark', 'Hist admin name', NULL, 'hb@b.ie', NULL, 'Hist Test Inc.', NULL, 'Hist Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 40 DAY, NULL, NULL, 'New',
   (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR')),
  (23, NOW(), 'IDL2-IEDR', 23, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-domain-3.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Hist holder', NULL, 1, NULL, 1, NULL, 'Hist remark', 'Hist hostmaster remark', 'Hist admin name', NULL, 'hc@c.ie', NULL, 'Hist Test Inc.', NULL, 'Hist Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 27 DAY, NULL, NULL, 'New',
   (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'));
INSERT INTO `SecondaryMarketBuyRequestTelecomHist` VALUES
  (21, '111111111', 'Phone', 0),
  (22, '222222222', 'Phone', 0),
  (23, '333333333', 'Phone', 0);
INSERT INTO `SecondaryMarketBuyRequest` VALUES
  (21, 'sec-mrkt-domain-3.ie', NULL, 'IDL2-IEDR', 101, '21 holder', NULL, 1, NULL, 1, NULL, '21 remark', '21 hostmaster remark', 'Admin 21 name', NULL, 'a21@a.ie', NULL, 'Test1 Inc.', NULL, 'Test Street 21', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 40 DAY, NULL, NULL, 'New', 'IDL2-IEDR', 21),
  (22, 'sec-mrkt-domain-3.ie', NULL, 'IDL2-IEDR', 101, '22 holder', NULL, 1, NULL, 1, NULL, '22 remark', '22 hostmaster remark', 'Admin 22 name', NULL, 'a22@a.ie', NULL, 'Test2 Inc.', NULL, 'Test Street 22', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 40 DAY, NULL, NULL, 'New', 'IH4-IEDR', 22),
  (23, 'sec-mrkt-domain-3.ie', NULL, 'IDL2-IEDR', 101, '23 holder', NULL, 1, NULL, 1, NULL, '23 remark', '23 hostmaster remark', 'Admin 23 name', NULL, 'a23@a.ie', NULL, 'Test3 Inc.', NULL, 'Test Street 23', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 27 DAY, NULL, NULL, 'New', NULL, 23);
INSERT INTO `SecondaryMarketBuyRequestTelecom` VALUES
  (21, '111111111', 'Phone', 0),
  (22, '222222222', 'Phone', 0),
  (23, '333333333', 'Phone', 0);

-- CRS-1211

INSERT INTO `Domain` VALUES
  ('sec-mrkt-dir-from-dir-2.ie', 'Secondary Market Tester', 2, 5, 1, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 2073, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `DNS` VALUES
  ('sec-mrkt-dir-from-dir-2.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-dir-from-dir-2.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `Contact` VALUES
  ('sec-mrkt-dir-from-dir-2.ie', 'AAU809-IEDR', 'Admin'),
  ('sec-mrkt-dir-from-dir-2.ie', 'AAU809-IEDR', 'Creator'),
  ('sec-mrkt-dir-from-dir-2.ie', 'AAU809-IEDR', 'Tech'),
  ('sec-mrkt-dir-from-dir-2.ie', 'AAU809-IEDR', 'Billing');

INSERT INTO `DomainHist` VALUES
  (NULL, 'sec-mrkt-dir-from-dir-2.ie', 'Secondary Market Tester', 5, 10, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 1), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'AAU809-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 2073, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `DNSHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-dir-2.ie'), 'sec-mrkt-dir-from-dir-2.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-dir-2.ie'), 'sec-mrkt-dir-from-dir-2.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `ContactHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-dir-2.ie'), 'sec-mrkt-dir-from-dir-2.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-dir-2.ie'), 'sec-mrkt-dir-from-dir-2.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-dir-2.ie'), 'sec-mrkt-dir-from-dir-2.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-from-dir-2.ie'), 'sec-mrkt-dir-from-dir-2.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Billing');

UPDATE `Domain` LEFT JOIN `DomainHist` USING(`D_Name`)
  SET `Domain`.`Chng_ID` = `DomainHist`.`Chng_ID`
  WHERE `D_Name` = 'sec-mrkt-dir-from-dir-2.ie';

INSERT INTO `SecondaryMarketBuyRequestHist` VALUES
  (8, DATE_FORMAT(CURDATE(), '%Y-%m-%d 10:15:28'), 'AAG061-IEDR', 8, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-dir-from-dir-2.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAG061-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 1), 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, DATE_FORMAT(CURDATE() - INTERVAL 14 DAY, '%Y-%m-%d 17:08:18'), 'AUTHCODEFOR8', NOW(), 'Passed', NULL);
INSERT INTO `SecondaryMarketBuyRequestTelecomHist` VALUES
  (8, '987654321', 'Phone', 0);
INSERT INTO `SecondaryMarketBuyRequest` VALUES
  (8, 'sec-mrkt-dir-from-dir-2.ie', NULL, 'AAG061-IEDR', 1, 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, DATE_FORMAT(CURDATE() - INTERVAL 14 DAY, '%Y-%m-%d 17:08:18'), 'AUTHCODEFOR8', NOW(), 'Passed', NULL, 8);
INSERT INTO `SecondaryMarketBuyRequestTelecom` VALUES
  (8, '987654321', 'Phone', 0);

INSERT INTO `SecondaryMarketSellRequestHist` VALUES
  (8, NOW() - INTERVAL 2 DAY, 'AAU809-IEDR', 8, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), NOW() - INTERVAL 10 DAY, 'Processing', 8),
  (13, NOW() - INTERVAL 2 DAY, 'AAA22-IEDR', 13, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AAA22-IEDR'), NOW() - INTERVAL 10 DAY, 'Processing', 13);

INSERT INTO `SecondaryMarketSellRequest` VALUES
  (8, 'AAU809-IEDR', NOW() - INTERVAL 2 DAY, 'Processing', 8, 8),
  (13, 'AAA22-IEDR', NOW() - INTERVAL 2 DAY, 'Processing', 13, 13);

UPDATE Domain SET DSM_State = 2073 WHERE D_Name = 'webwebweb.ie';

-- CRS-1216

INSERT INTO `SecondaryMarketSellRequestHist` VALUES
  (4, NOW() - INTERVAL 3 DAY, 'IDL2-IEDR', 1, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), DATE_FORMAT(CURDATE() - INTERVAL 10 DAY, '%Y-%m-%d 9:54:01'), 'Cancelled', 4);

-- CRS-1209

INSERT INTO `Domain` VALUES
  ('sec-mrkt-domain-5.ie', 'Secondary Market Tester', 2, 11, 101, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 1041, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `DNS` VALUES
  ('sec-mrkt-domain-5.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-domain-5.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `Contact` VALUES
  ('sec-mrkt-domain-5.ie', 'NNL2-IEDR', 'Admin'),
  ('sec-mrkt-domain-5.ie', 'NNL2-IEDR', 'Creator'),
  ('sec-mrkt-domain-5.ie', 'NNL2-IEDR', 'Tech'),
  ('sec-mrkt-domain-5.ie', 'NNL2-IEDR', 'Billing');

INSERT INTO `DomainHist` VALUES
  (NULL, 'sec-mrkt-domain-5.ie', 'Secondary Market Tester', 5, 10, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 101), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'IDL2-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `DNSHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-5.ie'), 'sec-mrkt-domain-5.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-5.ie'), 'sec-mrkt-domain-5.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `ContactHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-5.ie'), 'sec-mrkt-domain-5.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'NNL2-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-5.ie'), 'sec-mrkt-domain-5.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'NNL2-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-5.ie'), 'sec-mrkt-domain-5.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'NNL2-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-domain-5.ie'), 'sec-mrkt-domain-5.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'NNL2-IEDR'), 'Billing');

UPDATE `Domain` LEFT JOIN `DomainHist` USING(`D_Name`)
  SET `Domain`.`Chng_ID` = `DomainHist`.`Chng_ID`
  WHERE `D_Name` = 'sec-mrkt-domain-5.ie';

INSERT INTO `SecondaryMarketBuyRequestHist` VALUES
  (24, NOW(), 'IDL2-IEDR', 24, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-domain-5.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Hist holder', NULL, 1, NULL, 1, NULL, 'Hist remark', 'Hist hostmaster remark', 'Hist admin name', NULL, 'ha@a.ie', NULL, 'Hist Test Inc.', NULL, 'Hist Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW(), NULL, NULL, 'Hold Update', NULL);
INSERT INTO `SecondaryMarketBuyRequestTelecomHist` VALUES
  (24, '111111111', 'Phone', 0);
INSERT INTO `SecondaryMarketBuyRequest` VALUES
  (24, 'sec-mrkt-domain-5.ie', NULL, 'IDL2-IEDR', 101, '24 holder', NULL, 1, NULL, 1, NULL, '24 remark', '24 hostmaster remark', 'Admin 24 name', NULL, 'a24@a.ie', NULL, 'Test1 Inc.', NULL, 'Test Street 24', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 40 DAY, NULL, NULL, 'Hold Update', NULL, 24);
INSERT INTO `SecondaryMarketBuyRequestTelecom` VALUES
  (24, '111111111', 'Phone', 0);

-- CRS-1219

INSERT INTO `Domain` VALUES
  ('sec-mrkt-complete.ie', 'Secondary Market Tester', 2, 5, 1, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Sell Request completed', 'YES', 25, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `DNS` VALUES
  ('sec-mrkt-complete.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-complete.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `Contact` VALUES
  ('sec-mrkt-complete.ie', 'AAG061-IEDR', 'Admin'),
  ('sec-mrkt-complete.ie', 'AAG061-IEDR', 'Creator'),
  ('sec-mrkt-complete.ie', 'AAG061-IEDR', 'Tech'),
  ('sec-mrkt-complete.ie', 'AAG061-IEDR', 'Billing');

INSERT INTO `DomainHist` VALUES
  (NULL, 'sec-mrkt-complete.ie', 'Secondary Market Tester', 5, 10, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 1), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'New domain registered', 'AAU809-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 25, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  (NULL, 'sec-mrkt-complete.ie', 'Secondary Market Tester', 5, 10, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 1), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Buy Request registered', 'AAU809-IEDR', NOW() - INTERVAL 5 MONTH, 'YES', 1049, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  (NULL, 'sec-mrkt-complete.ie', 'Secondary Market Tester', 5, 10, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 1), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Sell Request registered', 'AAG061-IEDR', NOW() - INTERVAL 3 MONTH, 'YES', 2073, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  (NULL, 'sec-mrkt-complete.ie', 'Secondary Market Tester', 5, 10, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 1), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Sell Request completed', 'AAG061-IEDR', NOW() - INTERVAL 2 MONTH, 'YES', 25, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `DNSHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'New domain registered'), 'sec-mrkt-complete.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'New domain registered'), 'sec-mrkt-complete.ie', 'dns2.ie', NULL, NULL, 2),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Buy Request registered'), 'sec-mrkt-complete.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Buy Request registered'), 'sec-mrkt-complete.ie', 'dns2.ie', NULL, NULL, 2),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Sell Request registered'), 'sec-mrkt-complete.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Sell Request registered'), 'sec-mrkt-complete.ie', 'dns2.ie', NULL, NULL, 2),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Sell Request completed'), 'sec-mrkt-complete.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Sell Request completed'), 'sec-mrkt-complete.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `ContactHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'New domain registered'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'New domain registered'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'New domain registered'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'New domain registered'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Buy Request registered'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Buy Request registered'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Buy Request registered'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Buy Request registered'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Sell Request registered'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Sell Request registered'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Sell Request registered'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Sell Request registered'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Sell Request completed'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAG061-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Sell Request completed'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAG061-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Sell Request completed'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAG061-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-complete.ie' AND `D_Remark` = 'Sell Request completed'), 'sec-mrkt-complete.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAG061-IEDR'), 'Billing');

UPDATE `Domain` SET `Chng_ID` = (SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `DomainHist`.`D_Name` = `Domain`.`D_Name`)
  WHERE `D_Name` = 'sec-mrkt-complete.ie';

INSERT INTO `SecondaryMarketBuyRequestHist` VALUES
  (15, NOW() - INTERVAL 5 MONTH, 'AAG061-IEDR', 15, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-complete.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAG061-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 1), 'Holder', NULL, 1, NULL, 1, NULL, 'Buy Request registered', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 5 MONTH, NULL, NULL, 'New', NULL),
  (16, NOW() - INTERVAL 4 MONTH, 'AAG061-IEDR', 15, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-complete.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAG061-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 1), 'Holder', NULL, 1, NULL, 1, NULL, 'Buy Request registered', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 5 MONTH, 'AUTHCODEN016', NOW() - INTERVAL 4 MONTH, 'Passed', NULL);
INSERT INTO `SecondaryMarketBuyRequestTelecomHist` VALUES
  (15, '123456789', 'Phone', 0),
  (16, '123456789', 'Phone', 0);
INSERT INTO `SecondaryMarketSellRequestHist` VALUES
  (15, NOW() - INTERVAL 3 MONTH, 'AAU809-IEDR', 16, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), NOW() - INTERVAL 3 MONTH, 'Processing', 16),
  (16, NOW() - INTERVAL 2 MONTH, 'AAU809-IEDR', 16, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), NOW() - INTERVAL 3 MONTH, 'Completed', 16);

-- CRS-1215

UPDATE `Ticket` SET `CheckedOutTo` = 'APITEST-IEDR' WHERE `T_Number` = 259928;

-- CRS-1220

INSERT INTO `Domain` VALUES
  ('sec-mrkt-dir-dir-to-be-completed.ie', 'Secondary Market Tester', 2, 5, 1, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 2073, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('sec-mrkt-dir-reg-to-be-completed.ie', 'Secondary Market Tester', 2, 5, 101, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 2065, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('sec-mrkt-reg-reg-to-be-completed.ie', 'Secondary Market Tester', 2, 5, 101, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 2065, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `DNS` VALUES
  ('sec-mrkt-dir-dir-to-be-completed.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-dir-dir-to-be-completed.ie', 'dns2.ie', NULL, NULL, 2),
  ('sec-mrkt-dir-reg-to-be-completed.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-dir-reg-to-be-completed.ie', 'dns2.ie', NULL, NULL, 2),
  ('sec-mrkt-reg-reg-to-be-completed.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-reg-reg-to-be-completed.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `Contact` VALUES
  ('sec-mrkt-dir-dir-to-be-completed.ie', 'AAG061-IEDR', 'Admin'),
  ('sec-mrkt-dir-dir-to-be-completed.ie', 'AAG061-IEDR', 'Creator'),
  ('sec-mrkt-dir-dir-to-be-completed.ie', 'AAG061-IEDR', 'Tech'),
  ('sec-mrkt-dir-dir-to-be-completed.ie', 'AAG061-IEDR', 'Billing'),
  ('sec-mrkt-dir-reg-to-be-completed.ie', 'IDL2-IEDR', 'Admin'),
  ('sec-mrkt-dir-reg-to-be-completed.ie', 'IDL2-IEDR', 'Creator'),
  ('sec-mrkt-dir-reg-to-be-completed.ie', 'TDI2-IEDR', 'Tech'),
  ('sec-mrkt-dir-reg-to-be-completed.ie', 'IDL2-IEDR', 'Billing'),
  ('sec-mrkt-reg-reg-to-be-completed.ie', 'AHK693-IEDR', 'Admin'),
  ('sec-mrkt-reg-reg-to-be-completed.ie', 'IDL2-IEDR', 'Creator'),
  ('sec-mrkt-reg-reg-to-be-completed.ie', 'TDI2-IEDR', 'Tech'),
  ('sec-mrkt-reg-reg-to-be-completed.ie', 'IDL2-IEDR', 'Billing');

INSERT INTO `DomainHist` VALUES
  (NULL, 'sec-mrkt-dir-dir-to-be-completed.ie', 'Secondary Market Tester', 2, 5, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 1), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'AAG061-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 2073, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  (NULL, 'sec-mrkt-dir-reg-to-be-completed.ie', 'Secondary Market Tester', 2, 5, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 101), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'IDL2-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 2065, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  (NULL, 'sec-mrkt-reg-reg-to-be-completed.ie', 'Secondary Market Tester', 2, 5, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 101), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'IDL2-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 2065, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `DNSHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-dir-to-be-completed.ie'), 'sec-mrkt-dir-dir-to-be-completed.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-dir-to-be-completed.ie'), 'sec-mrkt-dir-dir-to-be-completed.ie', 'dns2.ie', NULL, NULL, 2),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-reg-to-be-completed.ie'), 'sec-mrkt-dir-reg-to-be-completed.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-reg-to-be-completed.ie'), 'sec-mrkt-dir-reg-to-be-completed.ie', 'dns2.ie', NULL, NULL, 2),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-reg-reg-to-be-completed.ie'), 'sec-mrkt-reg-reg-to-be-completed.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-reg-reg-to-be-completed.ie'), 'sec-mrkt-reg-reg-to-be-completed.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `ContactHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-dir-to-be-completed.ie'), 'sec-mrkt-dir-dir-to-be-completed.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAG061-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-dir-to-be-completed.ie'), 'sec-mrkt-dir-dir-to-be-completed.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAG061-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-dir-to-be-completed.ie'), 'sec-mrkt-dir-dir-to-be-completed.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAG061-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-dir-to-be-completed.ie'), 'sec-mrkt-dir-dir-to-be-completed.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AAG061-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-reg-to-be-completed.ie'), 'sec-mrkt-dir-reg-to-be-completed.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-reg-to-be-completed.ie'), 'sec-mrkt-dir-reg-to-be-completed.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-reg-to-be-completed.ie'), 'sec-mrkt-dir-reg-to-be-completed.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'TDI2-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-dir-reg-to-be-completed.ie'), 'sec-mrkt-dir-reg-to-be-completed.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-reg-reg-to-be-completed.ie'), 'sec-mrkt-reg-reg-to-be-completed.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'AHK693-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-reg-reg-to-be-completed.ie'), 'sec-mrkt-reg-reg-to-be-completed.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-reg-reg-to-be-completed.ie'), 'sec-mrkt-reg-reg-to-be-completed.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'TDI2-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-reg-reg-to-be-completed.ie'), 'sec-mrkt-reg-reg-to-be-completed.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Billing');

UPDATE `Domain` LEFT JOIN `DomainHist` USING(`D_Name`)
  SET `Domain`.`Chng_ID` = `DomainHist`.`Chng_ID`
  WHERE `D_Name` IN ('sec-mrkt-dir-dir-to-be-completed.ie', 'sec-mrkt-dir-reg-to-be-completed.ie', 'sec-mrkt-reg-reg-to-be-completed.ie');

INSERT INTO `SecondaryMarketBuyRequestHist` VALUES
  (17, NOW(), 'AAU809-IEDR', 17, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-dir-dir-to-be-completed.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAG061-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 1), 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN017', NOW(), 'Passed', NULL),
  (18, NOW(), 'AAU809-IEDR', 18, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-dir-reg-to-be-completed.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAU809-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN018', NOW(), 'Passed', NULL),
  (19, NOW(), 'IDL2-IEDR', 19, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-reg-reg-to-be-completed.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN019', NOW(), 'Passed', NULL),
  (20, NOW(), 'IDL2-IEDR', 20, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-reg-reg-to-be-completed.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN020', NOW(), 'Passed', NULL);
INSERT INTO `SecondaryMarketBuyRequestTelecomHist` VALUES
  (17, '123456789', 'Phone', 0),
  (18, '987654321', 'Phone', 0),
  (19, '987654321', 'Phone', 0),
  (20, '987654321', 'Phone', 0);
INSERT INTO `SecondaryMarketBuyRequest` VALUES
  (17, 'sec-mrkt-dir-dir-to-be-completed.ie', NULL, 'AAU809-IEDR', 1, 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN017', NOW(), 'Passed', NULL, 17),
  (18, 'sec-mrkt-dir-reg-to-be-completed.ie', NULL, 'AAU809-IEDR', 101, 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN018', NOW(), 'Passed', NULL, 18),
  (19, 'sec-mrkt-reg-reg-to-be-completed.ie', NULL, 'IDL2-IEDR', 101, 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN019', NOW(), 'Passed', NULL, 19),
  (20, 'sec-mrkt-reg-reg-to-be-completed.ie', NULL, 'IDL2-IEDR', 101, 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN020', NOW(), 'Passed', NULL, 20);
INSERT INTO `SecondaryMarketBuyRequestTelecom` VALUES
  (17, '123456789', 'Phone', 0),
  (18, '987654321', 'Phone', 0),
  (19, '987654321', 'Phone', 0),
  (20, '987654321', 'Phone', 0);

INSERT INTO `SecondaryMarketSellRequestHist` VALUES
  (17, NOW() - INTERVAL 3 DAY, 'AAG061-IEDR', 17, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AAG061-IEDR'), NOW() - INTERVAL 3 DAY, 'Processing', 17),
  (18, NOW() - INTERVAL 4 DAY, 'IDL2-IEDR', 18, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), NOW() - INTERVAL 4 DAY, 'Processing', 18),
  (19, NOW() - INTERVAL 11 DAY, 'IDL2-IEDR', 19, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), NOW() - INTERVAL 11 DAY, 'Processing', 19);

INSERT INTO `SecondaryMarketSellRequest` VALUES
  (17, 'AAG061-IEDR', NOW() - INTERVAL 3 DAY, 'Processing', 17, 17),
  (18, 'IDL2-IEDR', NOW() - INTERVAL 4 DAY, 'Processing', 18, 18),
  (19, 'IDL2-IEDR', NOW() - INTERVAL 11 DAY, 'Processing', 19, 19);

-- CRS-1222

INSERT INTO `Domain` VALUES
  ('sec-mrkt-buy-req-to-be-cleaned.ie', 'Secondary Market Tester', 2, 5, 101, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('sec-mrkt-new-sale.ie', 'Secondary Market Tester', 2, 5, 101, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 2065, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('sec-mrkt-authcode-expired.ie', 'Secondary Market Tester', 2, 5, 101, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 1041, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `DNS` VALUES
  ('sec-mrkt-buy-req-to-be-cleaned.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-buy-req-to-be-cleaned.ie', 'dns2.ie', NULL, NULL, 2),
  ('sec-mrkt-new-sale.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-new-sale.ie', 'dns2.ie', NULL, NULL, 2),
  ('sec-mrkt-authcode-expired.ie', 'dns1.ie', NULL, NULL, 1),
  ('sec-mrkt-authcode-expired.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `Contact` VALUES
  ('sec-mrkt-buy-req-to-be-cleaned.ie', 'IDL2-IEDR', 'Admin'),
  ('sec-mrkt-buy-req-to-be-cleaned.ie', 'IDL2-IEDR', 'Creator'),
  ('sec-mrkt-buy-req-to-be-cleaned.ie', 'TDI2-IEDR', 'Tech'),
  ('sec-mrkt-buy-req-to-be-cleaned.ie', 'IDL2-IEDR', 'Billing'),
  ('sec-mrkt-new-sale.ie', 'IDL2-IEDR', 'Admin'),
  ('sec-mrkt-new-sale.ie', 'IDL2-IEDR', 'Creator'),
  ('sec-mrkt-new-sale.ie', 'TDI2-IEDR', 'Tech'),
  ('sec-mrkt-new-sale.ie', 'IDL2-IEDR', 'Billing'),
  ('sec-mrkt-authcode-expired.ie', 'IDL2-IEDR', 'Admin'),
  ('sec-mrkt-authcode-expired.ie', 'IDL2-IEDR', 'Creator'),
  ('sec-mrkt-authcode-expired.ie', 'TDI2-IEDR', 'Tech'),
  ('sec-mrkt-authcode-expired.ie', 'IDL2-IEDR', 'Billing');

INSERT INTO `DomainHist` VALUES
  (NULL, 'sec-mrkt-buy-req-to-be-cleaned.ie', 'Secondary Market Tester', 2, 5, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 101), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'IDL2-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  (NULL, 'sec-mrkt-new-sale.ie', 'Secondary Market Tester', 2, 5, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 101), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'IDL2-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 2065, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  (NULL, 'sec-mrkt-authcode-expired.ie', 'Secondary Market Tester', 2, 5, (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 101), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'IDL2-IEDR', NOW() - INTERVAL 6 MONTH, 'YES', 1041, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `DNSHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-buy-req-to-be-cleaned.ie'), 'sec-mrkt-buy-req-to-be-cleaned.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-buy-req-to-be-cleaned.ie'), 'sec-mrkt-buy-req-to-be-cleaned.ie', 'dns2.ie', NULL, NULL, 2),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-new-sale.ie'), 'sec-mrkt-new-sale.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-new-sale.ie'), 'sec-mrkt-new-sale.ie', 'dns2.ie', NULL, NULL, 2),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-authcode-expired.ie'), 'sec-mrkt-authcode-expired.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-authcode-expired.ie'), 'sec-mrkt-authcode-expired.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `ContactHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-buy-req-to-be-cleaned.ie'), 'sec-mrkt-buy-req-to-be-cleaned.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-buy-req-to-be-cleaned.ie'), 'sec-mrkt-buy-req-to-be-cleaned.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-buy-req-to-be-cleaned.ie'), 'sec-mrkt-buy-req-to-be-cleaned.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'TDI2-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-buy-req-to-be-cleaned.ie'), 'sec-mrkt-buy-req-to-be-cleaned.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-new-sale.ie'), 'sec-mrkt-new-sale.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-new-sale.ie'), 'sec-mrkt-new-sale.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-new-sale.ie'), 'sec-mrkt-new-sale.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'TDI2-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-new-sale.ie'), 'sec-mrkt-new-sale.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-authcode-expired.ie'), 'sec-mrkt-authcode-expired.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-authcode-expired.ie'), 'sec-mrkt-authcode-expired.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-authcode-expired.ie'), 'sec-mrkt-authcode-expired.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'TDI2-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'sec-mrkt-authcode-expired.ie'), 'sec-mrkt-authcode-expired.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Billing');

UPDATE `Domain` LEFT JOIN `DomainHist` USING(`D_Name`)
  SET `Domain`.`Chng_ID` = `DomainHist`.`Chng_ID`
  WHERE `D_Name` IN ('sec-mrkt-buy-req-to-be-cleaned.ie', 'sec-mrkt-new-sale.ie', 'sec-mrkt-authcode-expired.ie');

INSERT INTO `SecondaryMarketBuyRequestHist` VALUES
  (31, NOW(), 'IDL2-IEDR', 31, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-buy-req-to-be-cleaned.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN031', NOW() - INTERVAL 14 DAY, 'Passed', NULL),
  (32, NOW(), 'IDL2-IEDR', 32, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-buy-req-to-be-cleaned.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN032', NOW() - INTERVAL 14 DAY, 'Passed', NULL),
  (34, NOW(), 'IDL2-IEDR', 34, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-new-sale.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN034', NOW() - INTERVAL 14 DAY, 'Passed', NULL),
  (35, NOW(), 'IDL2-IEDR', 35, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-new-sale.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN035', NOW() - INTERVAL 14 DAY, 'Passed', NULL),
  (37, NOW(), 'IDL2-IEDR', 37, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-new-sale.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN037', NOW() - INTERVAL 14 DAY, 'Passed', NULL),
  (38, NOW(), 'IDL2-IEDR', 38, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-authcode-expired.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 90 DAY, 'AUTHCODEN038', NOW() - INTERVAL 90 DAY, 'Passed', NULL);
INSERT INTO `SecondaryMarketBuyRequestTelecomHist` VALUES
  (31, '987654331', 'Phone', 0),
  (32, '987654332', 'Phone', 0),
  (34, '987654334', 'Phone', 0),
  (35, '987654335', 'Phone', 0),
  (37, '987654337', 'Phone', 0),
  (38, '987654338', 'Phone', 0);
INSERT INTO `SecondaryMarketBuyRequest` VALUES
  (31, 'sec-mrkt-buy-req-to-be-cleaned.ie', NULL, 'IDL2-IEDR', 101, 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN031', NOW() - INTERVAL 14 DAY, 'Passed', NULL, 31),
  (34, 'sec-mrkt-new-sale.ie', NULL, 'IDL2-IEDR', 101, 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN034', NOW() - INTERVAL 14 DAY, 'Passed', NULL, 34),
  (37, 'sec-mrkt-new-sale.ie', NULL, 'IDL2-IEDR', 101, 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN037', NOW() - INTERVAL 14 DAY, 'Passed', NULL, 37),
  (38, 'sec-mrkt-authcode-expired.ie', NULL, 'IDL2-IEDR', 101, 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 90 DAY, 'AUTHCODEN038', NOW() - INTERVAL 90 DAY, 'Passed', NULL, 38);
INSERT INTO `SecondaryMarketBuyRequestTelecom` VALUES
  (31, '987654331', 'Phone', 0),
  (34, '987654334', 'Phone', 0),
  (37, '987654337', 'Phone', 0),
  (38, '987654338', 'Phone', 0);

INSERT INTO `SecondaryMarketSellRequestHist` VALUES
  (32, NOW() - INTERVAL 8 DAY, 'IDL2-IEDR', 32, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), NOW() - INTERVAL 8 DAY, 'Processing', 32),
  (33, NOW() - INTERVAL 5 DAY, 'IDL2-IEDR', 32, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), NOW() - INTERVAL 5 DAY, 'Completed', 32),
  (35, NOW() - INTERVAL 8 DAY, 'IDL2-IEDR', 35, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), NOW() - INTERVAL 8 DAY, 'Processing', 35),
  (36, NOW() - INTERVAL 5 DAY, 'IDL2-IEDR', 35, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), NOW() - INTERVAL 5 DAY, 'Completed', 35),
  (37, NOW() - INTERVAL 2 DAY, 'IDL2-IEDR', 37, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), NOW() - INTERVAL 2 DAY, 'Processing', 37);

INSERT INTO `SecondaryMarketSellRequest` VALUES
  (37, 'IDL2-IEDR', NOW() - INTERVAL 2 DAY, 'Processing', 37, 37);

-- CRS-1227

UPDATE INCOMING_DOC SET BuyRequestId = '21' WHERE DOC_ID = '2';
UPDATE INCOMING_DOC SET BuyRequestId = '22' WHERE DOC_ID = '5';
UPDATE INCOMING_DOC SET BuyRequestId = '21' WHERE DOC_ID = '8';

-- CRS-1266

INSERT INTO `SecondaryMarketBuyRequestHist` VALUES
  (39, NOW(), 'AAG061-IEDR', 39, (SELECT Chng_ID FROM Domain WHERE D_Name = 'webwebweb.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAG061-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 262), 'Test Holder', NULL, 1, NULL, 1, NULL, 'Test remark', 'Test hostmaster remark', 'Test Admin Name', NULL, 'test@email.ie', NULL, 'Test Inc.', NULL, 'Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 23 DAY, NULL, NULL, 'New', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'));
INSERT INTO `SecondaryMarketBuyRequestTelecomHist` VALUES
  (39, '123654345', 'Phone', 0);
INSERT INTO `SecondaryMarketBuyRequest` VALUES
  (39, 'webwebweb.ie', NULL, 'AAG061-IEDR', 262, 'Test Holder', NULL, 1, NULL, 1, NULL, 'Test remark', 'Test hostmaster remark', 'Test Admin Name', NULL, 'reseller@a.ie', NULL, 'Test Inc.', NULL, 'Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 23 DAY, NULL, NULL, 'New', NULL, 39);
INSERT INTO `SecondaryMarketBuyRequestTelecom` VALUES
  (39, '123654345', 'Phone', 0);

-- CRS-1261

INSERT INTO `AccessHist` VALUES (NULL, (SELECT MAX(`Chng_Id`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ABM592-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', NOW(), 'YES', 'NO', '');
INSERT INTO Access VALUES ('ABM592-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', '.Z805gDk7mXJJfbcIwZJde', NOW(), 'YES', 'NO', '', (SELECT MAX(A.`Chng_ID`) FROM `AccessHist` A JOIN `NicHandleHist` NH ON A.`Nic_Handle_Chng_ID` = NH.`Chng_ID` WHERE NH.`Nic_Handle` = 'XDD274-IEDR'));

