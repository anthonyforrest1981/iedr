-- CRS-856

INSERT INTO `SchedulerConfig`(`name`, `schedule`, `Active`, `RunningNicHandle`) VALUES
    ('EmptyJob', '00 06 * * *', 'YES', 'IH4-IEDR'),
    ('EmptyJobWithError', '00 06 * * *', 'YES', 'IH4-IEDR'),
    ('EmptyFailingJob', '00 06 * * *', 'YES', 'IH4-IEDR');

-- CRS-932

INSERT INTO `NicHandleHist` VALUES(NULL,'JB007-IEDR','Finance Lead',2,'IEDR','NHAddress',14,121,'asd@iedr.ie','Active',CURDATE(),CURDATE(),CURDATE(),'NO',NULL,'PORTAL',NULL,NULL,'YES','PORTAL',CURDATE());
INSERT INTO `NicHandle` VALUES('JB007-IEDR','Finance Lead',2,'IEDR','NHAddress',14,121,'asd@iedr.ie','Active',CURDATE(),CURDATE(),CURDATE(),'NO',NULL,'PORTAL',NULL,NULL,'YES',(SELECT LAST_INSERT_ID()));
INSERT INTO `AccessHist` VALUES (NULL,(SELECT LAST_INSERT_ID()),'t1X7oZ2/bFDr0cyVoH442gyNoNSib/a',512,'NO','PORTAL',NOW(),'.Z805gDk7mXJJfbcIwZJde',CURDATE(),'NO',NULL);
INSERT INTO `Access` VALUES ('JB007-IEDR','t1X7oZ2/bFDr0cyVoH442gyNoNSib/a',512,'NO','.Z805gDk7mXJJfbcIwZJde',CURDATE(),'NO',NULL,(SELECT LAST_INSERT_ID()));
INSERT INTO `Telecom`(`Nic_Handle`, `Phone`, `Type`, `Order`) VALUES ('JB007-IEDR', '+000000000', 'Phone', 0);

-- CRS-996

UPDATE `Transaction` SET `Realex_Authcode` = 'authcode', `Realex_Passref` = 'passref' WHERE `ID` = 5;

-- CRS-963

UPDATE `DomainHist` SET `D_Del_Dt` = CURDATE() - interval 14 month where `D_Name` = 'deleteddomain1.ie' limit 1;
UPDATE `DomainHist` SET `D_Del_Dt` = CURDATE() - interval 11 month where `D_Name` = 'deleteddomain2.ie' limit 1;

-- CRS-962 (UTF-8 tests)

UPDATE `NicHandle` SET `Country_Id` = 998, `County_Id` = 163 WHERE `Nic_Handle` = 'IDË2-IEDR';
UPDATE `NicHandle` SET `Country_Id` = 999, `County_Id` = 164 WHERE `Nic_Handle` = CONCAT('IDL2-I', UNHEX('C38B'), 'DP');
UPDATE `Invoice` SET `Country_Id` = 998, `County_Id` = 163 WHERE `ID` IN (996, 999);
UPDATE `Invoice` SET `Country_Id` = 999, `County_Id` = 164 WHERE `ID` IN (997, 998);

-- CRS-987 (UTF-8 tests)

DELETE FROM `DepositHist` WHERE `Order_Id` LIKE 'BadUtf8%';

-- CRS-988

UPDATE Account A JOIN AccountHist AH ON AH.Chng_ID = A.Chng_ID SET
  A.A_Change_TS = A.A_Change_TS + INTERVAL 1 HOUR,
  AH.A_Change_TS = AH.A_Change_TS + INTERVAL 1 HOUR,
  AH.Chng_TS = AH.Chng_TS + INTERVAL 1 HOUR
  WHERE A.A_Number = 797;

-- CRS-989

UPDATE NicHandleHist SET Chng_NH = CONCAT('XYY', UNHEX('CC88'), '8-IEDR')
    WHERE Nic_Handle IN ('YYY9-IEDR', CONCAT('XYY', UNHEX('CC88'), '8-IEDR'));

-- CRS-991

UPDATE Ticket
  SET Ad_Status_TS = T_Change_TS, T_Status_TS = T_Change_TS, F_Status_TS = T_Change_TS,
    C_Status_TS = T_Change_TS
  WHERE T_Number IN (256744, 256745, 256754, 256814, 259924, 259926);
UPDATE TicketHist
  SET F_Status_TS = T_Change_TS, C_Status_TS = T_Change_TS
  WHERE T_Number IN (256744, 256745, 256754, 256814, 259924, 259926);
-- CRS-706

UPDATE `Domain` SET `D_Name` = 'transferred.ie' WHERE `D_Name` = 'transfered.ie';
UPDATE `DNS` SET `D_Name` = 'transferred.ie' WHERE `D_Name` = 'transfered.ie';
UPDATE `Contact` SET `D_Name` = 'transferred.ie' WHERE `D_Name` = 'transfered.ie';
UPDATE `TransfersHist` SET `D_Name` = 'transferred.ie' WHERE `D_Name` = 'transfered.ie';
UPDATE `Domain` SET `D_Name` = 'transferred2.ie' WHERE `D_Name` = 'transfered2.ie';
UPDATE `DNS` SET `D_Name` = 'transferred2.ie' WHERE `D_Name` = 'transfered2.ie';
UPDATE `Contact` SET `D_Name` = 'transferred2.ie' WHERE `D_Name` = 'transfered2.ie';
UPDATE `TransfersHist` SET `D_Name` = 'transferred2.ie' WHERE `D_Name` = 'transfered2.ie';

-- CRS-1000

INSERT INTO `TicketHist` VALUES (null, 401000, 'Registration', 'admin-new-domain.ie', 0, 'Holder', 0, 420, 0, 3, 5, 'Remark', 303,0,NULL,0,303,0,303,0,174,0, NOW(), 0, NOW(), 'NO', NULL, NOW() + interval 1 year, NOW(), 'Remark', NOW(), 'IH4-IEDR', 0, 0, NOW(), 'NO', 5, 'CHY123', 'NO', 0, NOW(), 0, NOW());
INSERT INTO `Ticket` VALUES (401000, 'Registration', 'admin-new-domain.ie', 0, 'Holder', 0, 122, 0, 3, 5, 'Remark','AHJ792-IEDR', 0, NULL, 0, 'AHJ792-IEDR', 0, 'AHJ792-IEDR', 0, 'AAI538-IEDR', 0, NOW(), 0, NOW(), 'NO', NULL, NOW() + interval 1 year, NOW(), 'Remark', 0, 0, NOW(), 'NO', 5, 'CHY123', 'NO', 0, NOW(), 0, NOW(), (SELECT LAST_INSERT_ID()));
INSERT INTO `TicketHist` VALUES (null, 401001, 'Registration', 'tech-new-domain.ie', 0, 'Holder', 0, 403, 0, 3, 5, 'Remark', 222, 0, NULL, 0, 222, 0, 222, 0, 1104, 0, NOW(), 0, NOW(), 'NO', NULL, NOW() + interval 1 year, NOW(), 'Remark', NOW(), 'IH4-IEDR', 0, 0, NOW(), 'NO', 5, 'CHY123', 'NO', 0, NOW(), 0, NOW());
INSERT INTO `Ticket` VALUES (401001, 'Registration', 'tech-new-domain.ie', 0, 'Holder', 0, 101, 0, 3, 5, 'Remark', 'AEI383-IEDR', 0, NULL, 0, 'AEI383-IEDR', 0, 'AEI383-IEDR', 0, 'TDI2-IEDR', 0, NOW(), 0, NOW(), 'NO', NULL, NOW() + interval 1 year, NOW(), 'Remark', 0, 0, NOW(), 'NO', 5, 'CHY123', 'NO', 0, NOW(), 0, NOW(), (SELECT LAST_INSERT_ID()));

UPDATE `Ticket` SET `Class_Id` = 1, `Category_Id` = 1 WHERE `T_Number` = 258195;
UPDATE `Ticket` SET `Class_Id` = 99, `Category_Id` = 99 WHERE `T_Number` = 400000;
UPDATE `Ticket` SET `Class_Id` = 98, `Category_Id` = 98 WHERE `T_Number` = 400001;
UPDATE `DomainHist` SET `Class_Id` = 1, `Category_Id` = 1 WHERE `Chng_Id` = 172;

-- CRS-1012

UPDATE `Domain` SET `D_Name` = 'thedomena-modpending.ie', `A_Number` = 101 WHERE `D_Name` = 'thedomena.modpending.ie';
UPDATE `DomainHist` SET `D_Name` = 'thedomena-modpending.ie', `Account_Chng_ID` = 403 WHERE `D_Name` = 'thedomena.modpending.ie';
UPDATE `Ticket` SET `D_Name` = 'thedomena-modpending.ie' WHERE `D_Name` = 'thedomena.modpending.ie';
UPDATE `TicketHist` SET `D_Name` = 'thedomena-modpending.ie' WHERE `D_Name` = 'thedomena.modpending.ie';

INSERT INTO `DNS` VALUES
('webwebweb.ie', 'dns1.ie', NULL, NULL, 1),
('webwebweb.ie', 'dns2.ie', NULL, NULL, 2),
('webwebweb2.ie', 'dns1.ie', NULL, NULL, 1),
('webwebweb2.ie', 'dns2.ie', NULL, NULL, 2);

UPDATE `Contact` SET `Contact_NH` = 'ABI289-IEDR' WHERE `D_Name` = 'tommy.ie' AND `Type` = 'Admin';

-- CRS-1047
-- Having a non-empty suspension date and an empty deletion date is data inconsistency
UPDATE `Domain` SET `D_Del_Dt` = '2008-01-31' WHERE `D_Name` IN ('suspendeddomain.ie', 'suspendedlockeddomain.ie');


-- (max Id in "TransactionHist" greater than in "Transaction" may cause an error)

UPDATE `ReservationHist` SET `Id` = 501, `Transaction_Id` = NULL  WHERE `Id` = 1001;
UPDATE `TransactionHist` SET `Id` = 501, `Invoice_Id` = NULL WHERE `Id` = 1001;
UPDATE `Invoice` SET `Id` = 501 WHERE `Id` = 1001;
UPDATE `TransactionHist` SET `Invoice_Id` = 501 WHERE `Id` = 501;
UPDATE `ReservationHist` SET `Transaction_Id` = 501 WHERE `Id` = 501;

-- CRS-1138 && CRS-1053 && CRS-1147

INSERT INTO `DomainHist`(`D_Name`, `D_Holder`, `Class_Id`, `Category_Id`, `Account_Chng_ID`, `D_Reg_TS`, `D_Ren_Dt`, `D_Change_TS`, `D_Remark`, `Chng_NH`, `Chng_TS`, `D_ClikPaid`, `DSM_State`, `D_Susp_Dt`, `D_Del_Dt`, `D_Transfer_TS`, `D_Authcode`, `D_Authc_Exp_Dt`, `D_Authc_Portal_Cnt`, `D_Locking_TS`, `D_LockingRenewal_Dt`) VALUES
  ('transferred.ie', 'Tommy Hilfiger Licensing, Inc.', 2, 4, 505, '2005-08-29 00:00:00', '2010-08-29', CURDATE() - INTERVAL 400 DAY, 'Transfer remark 1', 'IDL2-IEDR', CURDATE() - INTERVAL 400 DAY, 'NO', 17, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred.ie', 'Tommy Hilfiger Licensing, Inc.', 2, 4, 505, '2005-08-29 00:00:00', '2010-08-29', CURDATE() - INTERVAL 400 DAY, 'Transfer remark 2', 'IDL2-IEDR', CURDATE() - INTERVAL 400 DAY, 'NO', 390, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred.ie', 'Tommy Hilfiger Licensing, Inc.', 2, 4, 505, '2005-08-29 00:00:00', '2010-08-29', CURDATE() - INTERVAL 400 DAY, 'Transfer remark 3', 'IDL2-IEDR', CURDATE() - INTERVAL 400 DAY, 'NO', 390, NULL, NULL, CURDATE() - INTERVAL 400 DAY, NULL, NULL, '0', NULL, NULL),
  ('transferred.ie', 'Tommy Hilfiger Licensing, Inc.', 2, 4, 400, '2005-08-29 00:00:00', '2010-08-29', CURDATE() - INTERVAL 400 DAY, 'Transfer remark 4', 'IDL2-IEDR', CURDATE() - INTERVAL 400 DAY, 'NO', 25, NULL, NULL, CURDATE() - INTERVAL 400 DAY, NULL, NULL, '0', NULL, NULL),
  ('transferred2.ie', 'Tommy Hilfiger Licensing, Inc.', 2, 4, 400, '2005-08-29 00:00:00', '2010-08-29', CURDATE() - INTERVAL 2 YEAR + INTERVAL 1 DAY, 'Transfer remark 1', 'IDL2-IEDR', CURDATE() - INTERVAL 2 YEAR + INTERVAL 1 DAY, 'NO', 25, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred2.ie', 'Tommy Hilfiger Licensing, Inc.', 2, 4, 400, '2005-08-29 00:00:00', '2010-08-29', CURDATE() - INTERVAL 2 YEAR + INTERVAL 1 DAY, 'Transfer remark 2', 'IDL2-IEDR', CURDATE() - INTERVAL 2 YEAR + INTERVAL 1 DAY, 'NO', 391, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred2.ie', 'Tommy Hilfiger Licensing, Inc.', 2, 4, 400, '2005-08-29 00:00:00', '2010-08-29', CURDATE() - INTERVAL 2 YEAR + INTERVAL 1 DAY, 'Transfer remark 3', 'IDL2-IEDR', CURDATE() - INTERVAL 2 YEAR + INTERVAL 1 DAY, 'NO', 391, NULL, NULL, CURDATE() - INTERVAL 2 YEAR + INTERVAL 1 DAY, NULL, NULL, '0', NULL, NULL),
  ('transferred2.ie', 'Tommy Hilfiger Licensing, Inc.', 2, 4, 505, '2005-08-29 00:00:00', '2010-08-29', CURDATE() - INTERVAL 2 YEAR + INTERVAL 1 DAY, 'Transfer remark 4', 'IDL2-IEDR', CURDATE() - INTERVAL 2 YEAR + INTERVAL 1 DAY, 'NO', 17, NULL, NULL, CURDATE() - INTERVAL 2 YEAR + INTERVAL 1 DAY, NULL, NULL, '0', NULL, NULL),
  ('rough-dates.ie', 'Test Holder', 2, 5, 509, '2005-08-29 00:00:00', '2010-08-29', '2004-08-29 16:48:11', 'Transfer remark 1', 'IDL2-IEDR', '2004-08-29 16:48:11', 'NO', 17, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('rough-dates.ie', 'Test Holder', 2, 5, 509, '2005-08-29 00:00:00', '2010-08-29', '2004-08-29 16:48:11', 'Transfer remark 2', 'IDL2-IEDR', '2004-08-29 16:48:11', 'NO', 390, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('rough-dates.ie', 'Test Holder', 2, 5, 509, '2005-08-29 00:00:00', '2010-08-29', '2004-08-29 16:48:11', 'Transfer remark 3', 'IDL2-IEDR', '2004-08-29 16:48:11', 'NO', 390, NULL, NULL, CURDATE() - INTERVAL 400 DAY, NULL, NULL, '0', NULL, NULL),
  ('rough-dates.ie', 'Test Holder', 2, 5, 509, '2005-08-29 00:00:00', '2010-08-29', '2004-08-29 16:48:11', 'Transfer remark 4', 'IDL2-IEDR', '2004-08-29 16:48:11', 'NO', 17, NULL, NULL, CURDATE() - INTERVAL 400 DAY, NULL, NULL, '0', NULL, NULL),
  (CONCAT('badutf8', UNHEX('01'), '.ie'), 'Tommy Hilfiger Licensing, Inc.', 2, 4, 403, '2005-08-29 00:00:00', '2010-08-29', CURDATE() - INTERVAL 400 DAY, 'Transfer remark 1', 'IDL2-IEDR', CURDATE() - INTERVAL 400 DAY, 'NO', 17, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  (CONCAT('badutf8', UNHEX('01'), '.ie'), 'Tommy Hilfiger Licensing, Inc.', 2, 4, 403, '2005-08-29 00:00:00', '2010-08-29', CURDATE() - INTERVAL 400 DAY, 'Transfer remark 2', 'IDL2-IEDR', CURDATE() - INTERVAL 400 DAY, 'NO', 390, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  (CONCAT('badutf8', UNHEX('01'), '.ie'), 'Tommy Hilfiger Licensing, Inc.', 2, 4, 403, '2005-08-29 00:00:00', '2010-08-29', CURDATE() - INTERVAL 400 DAY, 'Transfer remark 3', 'IDL2-IEDR', CURDATE() - INTERVAL 400 DAY, 'NO', 390, NULL, NULL, CURDATE() - INTERVAL 400 DAY, NULL, NULL, '0', NULL, NULL),
  (CONCAT('badutf8', UNHEX('01'), '.ie'), 'Tommy Hilfiger Licensing, Inc.', 2, 4, 395, '2005-08-29 00:00:00', '2010-08-29', CURDATE() - INTERVAL 400 DAY, 'Transfer remark 4', 'IDL2-IEDR', CURDATE() - INTERVAL 400 DAY, 'NO', 17, NULL, NULL, CURDATE() - INTERVAL 400 DAY, NULL, NULL, '0', NULL, NULL);

INSERT INTO `ContactHist`(`Chng_ID`, `D_Name`, `Nic_Handle_Chng_ID`, `Type`) VALUES
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred2.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 1'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APITEST-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 1'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APITEST-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 1'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APITEST-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 1'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APITEST-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 2'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APITEST-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 2'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APITEST-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 2'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APITEST-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 2'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APITEST-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 3'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APITEST-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 3'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APITEST-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 3'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APITEST-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 3'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APITEST-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 4'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 4'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 4'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'rough-dates.ie' AND `D_Remark` = 'Transfer remark 4'), 'rough-dates.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 1'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('IDL2-IEDR', UNHEX('01'))), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 1'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('IDL2-IEDR', UNHEX('01'))), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 1'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('IDL2-IEDR', UNHEX('01'))), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 1'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('IDL2-IEDR', UNHEX('01'))), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 2'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('IDL2-IEDR', UNHEX('01'))), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 2'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('IDL2-IEDR', UNHEX('01'))), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 2'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('IDL2-IEDR', UNHEX('01'))), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 2'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('IDL2-IEDR', UNHEX('01'))), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 3'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('IDL2-IEDR', UNHEX('01'))), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 3'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('IDL2-IEDR', UNHEX('01'))), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 3'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('IDL2-IEDR', UNHEX('01'))), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 3'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('IDL2-IEDR', UNHEX('01'))), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 4'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('XXX7-IEDR', UNHEX('01'))), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 4'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('XXX7-IEDR', UNHEX('01'))), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 4'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('XXX7-IEDR', UNHEX('01'))), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie') AND `D_Remark` = 'Transfer remark 4'), CONCAT('badutf8', UNHEX('01'), '.ie'), (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = CONCAT('XXX7-IEDR', UNHEX('01'))), 'Creator');

UPDATE `Domain`
  SET `D_Transfer_TS` = CURDATE() - INTERVAL 400 DAY,
      `D_Change_TS` = CURDATE() - INTERVAL 400 DAY,
      `Chng_ID` = (SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'transferred.ie')
  WHERE `D_Name` = 'transferred.ie';
UPDATE `Domain`
  SET `D_Transfer_TS` = CURDATE() - INTERVAL 2 YEAR + INTERVAL 1 DAY,
      `D_Change_TS` = CURDATE() - INTERVAL 2 YEAR + INTERVAL 1 DAY,
      `Chng_ID` = (SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'transferred2.ie')
  WHERE `D_Name` = 'transferred2.ie';
UPDATE `Domain`
  SET `D_Transfer_TS` = '2004-08-29 16:48:11',
      `D_Change_TS` = '2004-08-29 16:48:11',
      `Chng_ID` = (SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'rough-dates.ie')
  WHERE `D_Name` = 'rough-dates.ie';
UPDATE `Domain`
  SET `D_Transfer_TS` = CURDATE() - INTERVAL 400 DAY,
      `D_Change_TS` = CURDATE() - INTERVAL 400 DAY,
      `Chng_ID` = (SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie'))
  WHERE `D_Name` = CONCAT('badutf8', UNHEX('01'), '.ie');

UPDATE `TransfersHist`
  SET `Transfer_TS` = CURDATE() - INTERVAL 400 DAY
  WHERE `Old_Nic_Handle` = 'ACV808-IEDR' AND `New_Nic_Handle` = 'ACB865-IEDR' AND `D_Name` = 'transferred.ie';
UPDATE `TransfersHist`
  SET `Transfer_TS` = CURDATE() - INTERVAL 2 YEAR + INTERVAL 1 DAY
  WHERE `Old_Nic_Handle` = 'ACB865-IEDR' AND `New_Nic_Handle` = 'ACV808-IEDR' AND `D_Name` = 'transferred2.ie';

-- CRS-1053

INSERT INTO `DomainHist`(`D_Name`, `D_Holder`, `Class_Id`, `Category_Id`, `Account_Chng_ID`, `D_Reg_TS`, `D_Ren_Dt`, `D_Change_TS`, `D_Remark`, `Chng_NH`, `Chng_TS`, `D_ClikPaid`, `DSM_State`, `D_Susp_Dt`, `D_Del_Dt`, `D_Transfer_TS`, `D_Authcode`, `D_Authc_Exp_Dt`, `D_Authc_Portal_Cnt`, `D_Locking_TS`, `D_LockingRenewal_Dt`) VALUES
  ('transferred3.ie', 'Holder transfer 1', 2, 3, 505, '2005-08-29 00:00:00', CURDATE() + INTERVAL 5 DAY, CURDATE() - INTERVAL 200 DAY, 'Transfer remark 1', 'IDL2-IEDR', CURDATE() - INTERVAL 200 DAY, 'NO', 17, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred3.ie', 'Holder transfer 2', 2, 3, 505, '2005-08-29 00:00:00', CURDATE() + INTERVAL 5 DAY, CURDATE() - INTERVAL 200 DAY, 'Transfer remark 2', 'IDL2-IEDR', CURDATE() - INTERVAL 200 DAY, 'NO', 390, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred3.ie', 'Holder transfer 3', 2, 3, 505, '2005-08-29 00:00:00', CURDATE() + INTERVAL 5 DAY, CURDATE() - INTERVAL 200 DAY, 'Transfer remark 3', 'IDL2-IEDR', CURDATE() - INTERVAL 200 DAY, 'NO', 390, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred3.ie', 'Holder transfer 4', 2, 3, 400, '2005-08-29 00:00:00', CURDATE() + INTERVAL 5 DAY, CURDATE() - INTERVAL 200 DAY, 'Transfer remark 4', 'IDL2-IEDR', CURDATE() - INTERVAL 200 DAY, 'NO', 25, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred3.ie', 'Holder transfer 5', 2, 3, 400, '2005-08-29 00:00:00', CURDATE() + INTERVAL 5 DAY, CURDATE() - INTERVAL 200 DAY, 'Transfer remark 5', 'IDL2-IEDR', CURDATE() - INTERVAL 200 DAY, 'NO', 25, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred3.ie', 'Holder transfer 6', 2, 3, 400, '2005-08-29 00:00:00', CURDATE() + INTERVAL 5 DAY, CURDATE() - INTERVAL 100 DAY, 'Transfer remark 6', 'IDL2-IEDR', CURDATE() - INTERVAL 100 DAY, 'NO', 391, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred3.ie', 'Holder transfer 7', 2, 3, 400, '2005-08-29 00:00:00', CURDATE() + INTERVAL 5 DAY, CURDATE() - INTERVAL 100 DAY, 'Transfer remark 7', 'IDL2-IEDR', CURDATE() - INTERVAL 100 DAY, 'NO', 391, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred3.ie', 'Holder transfer 8', 2, 3, 403, '2005-08-29 00:00:00', CURDATE() + INTERVAL 5 DAY, CURDATE() - INTERVAL 100 DAY, 'Transfer remark 8', 'IDL2-IEDR', CURDATE() - INTERVAL 100 DAY, 'NO', 17, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred3.ie', 'Holder transfer 9', 2, 3, 403, '2005-08-29 00:00:00', CURDATE() + INTERVAL 5 DAY, CURDATE() - INTERVAL 100 DAY, 'Transfer remark 9', 'IDL2-IEDR', CURDATE() - INTERVAL 100 DAY, 'NO', 17, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred4.ie', 'Holder transfer 1', 2, 4, 400, '2005-08-29 00:00:00', CURDATE() - INTERVAL 25 DAY, CURDATE() - INTERVAL 100 DAY, 'Transfer remark 1', 'IDL2-IEDR', CURDATE() - INTERVAL 100 DAY, 'NO', 25, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred4.ie', 'Holder transfer 2', 2, 4, 400, '2005-08-29 00:00:00', CURDATE() - INTERVAL 25 DAY, CURDATE() - INTERVAL 100 DAY, 'Transfer remark 2', 'IDL2-IEDR', CURDATE() - INTERVAL 100 DAY, 'NO', 391, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred4.ie', 'Holder transfer 3', 2, 4, 400, '2005-08-29 00:00:00', CURDATE() - INTERVAL 25 DAY, CURDATE() - INTERVAL 100 DAY, 'Transfer remark 3', 'IDL2-IEDR', CURDATE() - INTERVAL 100 DAY, 'NO', 391, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred4.ie', 'Holder transfer 4', 2, 4, 505, '2005-08-29 00:00:00', CURDATE() - INTERVAL 25 DAY, CURDATE() - INTERVAL 100 DAY, 'Transfer remark 4', 'IDL2-IEDR', CURDATE() - INTERVAL 100 DAY, 'NO', 17, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL),
  ('transferred4.ie', 'Holder transfer 5', 2, 4, 505, '2005-08-29 00:00:00', CURDATE() - INTERVAL 25 DAY, CURDATE() - INTERVAL 50 DAY, 'Transfer remark 5 - deleted', 'IDL2-IEDR', CURDATE() - INTERVAL 50 DAY, 'NO', 387, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `ContactHist`(`Chng_ID`, `D_Name`, `Nic_Handle_Chng_ID`, `Type`) VALUES
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACV808-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 5'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 5'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 5'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 5'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 6'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 6'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 6'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 6'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 7'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 7'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 7'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 7'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 8'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 8'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 8'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 8'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 9'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 9'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 9'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 9'), 'transferred3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 1'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 2'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 3'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'ACB865-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 4'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 5 - deleted'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Billing'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 5 - deleted'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 5 - deleted'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` from `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 5 - deleted'), 'transferred4.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandleHist` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Creator');

INSERT INTO `Domain`(`D_Name`, `D_Holder`, `Class_Id`, `Category_Id`, `A_Number`, `D_Reg_TS`, `D_Ren_Dt`, `D_Change_TS`, `D_Remark`, `D_ClikPaid`, `DSM_State`, `D_Susp_Dt`, `D_Del_Dt`, `D_Transfer_TS`, `D_Authcode`, `D_Authc_Exp_Dt`, `D_Authc_Portal_Cnt`, `D_Locking_TS`, `D_LockingRenewal_Dt`, `Chng_ID`) VALUES
  ('transferred3.ie', 'Tommy Hilfiger Licensing, Inc.', 2, 3, 101, '2005-08-29 00:00:00', CURDATE() + INTERVAL 5 DAY, CURDATE() - INTERVAL 100 DAY, 'Transfer remark 9', 'NO', 17, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL, (SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 9'));
INSERT INTO `Contact`(`D_Name`, `Contact_NH`, `Type`) VALUES
  ('transferred3.ie', 'IDL2-IEDR', 'Billing'),
  ('transferred3.ie', 'IDL2-IEDR', 'Admin'),
  ('transferred3.ie', 'IDL2-IEDR', 'Tech'),
  ('transferred3.ie', 'IDL2-IEDR', 'Creator');

INSERT INTO `TransfersHist`(`D_Name`, `Transfer_TS`, `Old_Nic_Handle`, `New_Nic_Handle`, `Transferred_Domain_Chng_ID`) VALUES
  ('transferred3.ie', CURDATE() - INTERVAL 200 DAY, 'ACV808-IEDR', 'ACB865-IEDR', (SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 3')),
  ('transferred3.ie', CURDATE() - INTERVAL 100 DAY, 'ACB865-IEDR', 'IDL2-IEDR', (SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'transferred3.ie' AND `D_Remark` = 'Transfer remark 7')),
  ('transferred4.ie', CURDATE() - INTERVAL 100 DAY, 'ACB865-IEDR', 'IDL2-IEDR', (SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'transferred4.ie' AND `D_Remark` = 'Transfer remark 3'));
