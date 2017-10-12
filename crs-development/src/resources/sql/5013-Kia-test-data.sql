-- crs-1308

INSERT INTO INCOMING_DOC VALUES
  (22, NOW() - INTERVAL 5 DAY, 'attachment', 'non-existing-1.jpg', 'Misc', 'email', 101, 'test', 37),
  (23, NOW() - INTERVAL 5 DAY, 'attachment', 'non-existing-2.jpg', 'Misc', 'email', 101, 'test', 37);

-- CRS-1233

UPDATE Ticket SET D_Name = 'listonmotorhomes2.ie' WHERE T_Number = 259925;
UPDATE TicketHist SET D_Name = 'listonmotorhomes2.ie' WHERE T_Number = 259925;

-- CRS-1324

UPDATE Transaction SET Realex_Pasref = CONCAT('A', UNHEX('CC88'), 'RealexPasref') WHERE ID = 998;
UPDATE Transaction SET Realex_Pasref = 'ÄRealexPasref' WHERE ID = 999;
UPDATE Transaction SET Realex_Pasref = 'pasref' WHERE ID IN (2, 3, 5);

-- CRS-1256

UPDATE Product SET P_Code = 'ÄRM11Yr', P_Description = 'ÄRegistration for 11 Year', P_Duration = 11, P_Guest = 'NO' WHERE Id = 1;
UPDATE Product SET P_Code = CONCAT('A', UNHEX('CC88'), 'Std11Year'), P_Description = CONCAT('A', UNHEX('CC88'), 'Registration of your domain for 11 years'), P_Duration = 11, P_Guest = 'YES' WHERE Id = 2;

INSERT INTO Product(P_Code, P_Description, P_Price, P_Duration, P_Active, P_Default, P_Valid_From_Dt, P_Valid_To_Dt, P_Reg, P_Ren, P_Guest, P_Disp_Order) VALUES
  ('Std3Year', 'Registration of your domain for 3 years', 120.00, 3, 'YES', 'NO', CURDATE(), CURDATE() + Interval 1 Year, 'YES', 'YES', 'YES', 0),
  ('Std4Year', 'Registration of your domain for 4 years', 160.00, 4, 'YES', 'NO', CURDATE(), CURDATE() + Interval 1 Year, 'YES', 'YES', 'YES', 0),
  ('Std6Year', 'Registration of your domain for 6 years', 240.00, 6, 'YES', 'NO', CURDATE(), CURDATE() + Interval 1 Year, 'YES', 'YES', 'YES', 0),
  ('Std7Year', 'Registration of your domain for 7 years', 280.00, 7, 'YES', 'NO', CURDATE(), CURDATE() + Interval 1 Year, 'YES', 'YES', 'YES', 0),
  ('Std8Year', 'Registration of your domain for 8 years', 320.00, 8, 'YES', 'NO', CURDATE(), CURDATE() + Interval 1 Year, 'YES', 'YES', 'YES', 0),
  ('Std9Year', 'Registration of your domain for 9 years', 360.00, 9, 'YES', 'NO', CURDATE(), CURDATE() + Interval 1 Year, 'YES', 'YES', 'YES', 0),
  ('Std10Year', 'Registration of your domain for 10 years', 400.00, 10, 'YES', 'NO', CURDATE(), CURDATE() + Interval 1 Year, 'YES', 'YES', 'YES', 0);

UPDATE Product SET P_Description = 'Registration of your domain for 1 year', P_Guest = 'YES' WHERE P_Code = 'Std1Year';
UPDATE Product SET P_Default = 'NO' WHERE P_Code = 'Std2Year';
UPDATE Product SET P_Default = 'YES' WHERE P_Code = 'RM1Yr';
UPDATE Product SET P_Valid_From_Dt = CURDATE() - Interval P_Duration Year, P_Valid_To_Dt = CURDATE() + Interval P_Duration Year;

INSERT INTO Product(P_Code, P_Description, P_Price, P_Duration, P_Active, P_Default, P_Valid_From_Dt, P_Valid_To_Dt, P_Reg, P_Ren, P_Guest, P_Disp_Order) VALUES
  ('Std1YearInactive', 'Inactive product', 40.00, 1, 'NO', 'NO', CURDATE(), CURDATE() + Interval 1 Year, 'YES', 'YES', 'YES', 0);

-- CTD-78

INSERT INTO `Transaction` VALUES (101, 'NO', 'NO', 24.28, 'NO', NULL, NULL, NULL, 20, 4.28, '19700101142701-C-4810681', 'NO', NULL, NULL, '132711', '134028162214561');
INSERT INTO `Reservation` VALUES (101, 'AAA22-IEDR', 'listonmotorhomes.ie', 12, CURDATE(), 24.28, 3, 4.28, 'NO', 'NO', NULL, NULL, 101, 4, 'renewal', NULL, NULL, 'Deposit');
INSERT INTO `Transaction` VALUES (102, 'NO', 'NO', 78.91, 'NO', NULL, NULL, NULL, 65, 13.91, '19700101142701-C-4810682', 'NO', NULL, NULL, '132712', '134028162214562');
INSERT INTO `Reservation` VALUES (102, 'AAU809-IEDR', 'directdomain.ie', 12, CURDATE(), 78.91, 3, 4.28, 'NO', 'NO', NULL, NULL, 102, 13, 'renewal', NULL, NULL, 'Deposit');

INSERT INTO `DNS` VALUES
  ('paydomainlocked.ie', 'dns1.ie', NULL, NULL, 1),
  ('paydomainlocked.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `Contact` VALUES
  ('paydomainlocked.ie', 'APIT1-IEDR', 'Admin'),
  ('paydomainlocked.ie', 'APIT2-IEDR', 'Creator'),
  ('paydomainlocked.ie', 'APIT2-IEDR', 'Tech'),
  ('paydomainlocked.ie', 'APIT2-IEDR', 'Billing');

INSERT INTO `DNSHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'paydomainlocked.ie'), 'paydomainlocked.ie', 'dns1.ie', NULL, NULL, 1),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'paydomainlocked.ie'), 'paydomainlocked.ie', 'dns2.ie', NULL, NULL, 2);
INSERT INTO `ContactHist` VALUES
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'paydomainlocked.ie'), 'paydomainlocked.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'paydomainlocked.ie'), 'paydomainlocked.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'APIT2-IEDR'), 'Creator'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'paydomainlocked.ie'), 'paydomainlocked.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'APIT2-IEDR'), 'Tech'),
  ((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` = 'paydomainlocked.ie'), 'paydomainlocked.ie', (SELECT `Chng_ID` FROM `NicHandle` WHERE `Nic_Handle` = 'APIT2-IEDR'), 'Billing');

-- CRS-1234 - Data inconsistency - adding missing contacts and DNS's to domains

UPDATE `Domain` SET `D_Name` = CONCAT('thedomain', SUBSTR(D_Name, 10)) WHERE `D_Name` like 'thedomena%.ie';
UPDATE `Domain` SET `D_Name` = CONCAT('thedomain-', SUBSTR(D_Name, 11)) WHERE `D_Name` like 'thedomain.%.ie';
UPDATE `DomainHist` SET `D_Name` = CONCAT('thedomain', SUBSTR(D_Name, 10)) WHERE `D_Name` like 'thedomena%.ie';
UPDATE `DomainHist` SET `D_Name` = CONCAT('thedomain-', SUBSTR(D_Name, 11)) WHERE `D_Name` like 'thedomain.%.ie';
UPDATE `DNS` SET `D_Name` = CONCAT('thedomain', SUBSTR(D_Name, 10)) WHERE `D_Name` like 'thedomena%.ie';
UPDATE `DNS` SET `D_Name` = CONCAT('thedomain-', SUBSTR(D_Name, 11)) WHERE `D_Name` like 'thedomain.%.ie';
UPDATE `DNSHist` SET `D_Name` = CONCAT('thedomain', SUBSTR(D_Name, 10)) WHERE `D_Name` like 'thedomena%.ie';
UPDATE `DNSHist` SET `D_Name` = CONCAT('thedomain-', SUBSTR(D_Name, 11)) WHERE `D_Name` like 'thedomain.%.ie';
UPDATE `Contact` SET `D_Name` = CONCAT('thedomain', SUBSTR(D_Name, 10)) WHERE `D_Name` like 'thedomena%.ie';
UPDATE `Contact` SET `D_Name` = CONCAT('thedomain-', SUBSTR(D_Name, 11)) WHERE `D_Name` like 'thedomain.%.ie';
UPDATE `ContactHist` SET `D_Name` = CONCAT('thedomain', SUBSTR(D_Name, 10)) WHERE `D_Name` like 'thedomena%.ie';
UPDATE `ContactHist` SET `D_Name` = CONCAT('thedomain-', SUBSTR(D_Name, 11)) WHERE `D_Name` like 'thedomain.%.ie';
UPDATE `Ticket` SET `D_Name` = CONCAT('thedomain', SUBSTR(D_Name, 10)) WHERE `D_Name` like 'thedomena%.ie';
UPDATE `Ticket` SET `D_Name` = CONCAT('thedomain-', SUBSTR(D_Name, 11)) WHERE `D_Name` like 'thedomain.%.ie';
UPDATE `TicketHist` SET `D_Name` = CONCAT('thedomain', SUBSTR(D_Name, 10)) WHERE `D_Name` like 'thedomena%.ie';
UPDATE `TicketHist` SET `D_Name` = CONCAT('thedomain-', SUBSTR(D_Name, 11)) WHERE `D_Name` like 'thedomain.%.ie';

UPDATE `Domain` SET `A_Number` = 666 WHERE `D_Name` IN ('autocreated2.ie', 'deleteddomain.ie', 'suspendeddomain.ie', 'deletedlockeddomain.ie', 'suspendedlockeddomain.ie');
UPDATE `DomainHist` SET `Account_Chng_ID` = (SELECT MAX(`Chng_ID`) FROM `Account` WHERE `A_Number` = 666) WHERE `D_Name` IN ('autocreated2.ie', 'deleteddomain.ie', 'suspendeddomain.ie', 'deletedlockeddomain.ie', 'suspendedlockeddomain.ie');

INSERT INTO `DNS` VALUES
  ('dsm0.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('dsm0.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('transferred.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('transferred.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('transferred2.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('transferred2.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('transferred3.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('transferred3.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('pizzaonline2.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('pizzaonline2.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('deleteddomain.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('deleteddomain.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('suspendeddomain.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('suspendeddomain.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('theweb2.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('theweb2.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('äpaymentutf8daotest.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('äpaymentutf8daotest.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('generate-invoice-test.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('generate-invoice-test.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('1registerdomaincc.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('1registerdomaincc.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('createccdomain.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('createccdomain.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('createdomainregistrarbasic.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('createdomainregistrarbasic.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('paydomainlockedautorenew.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('paydomainlockedautorenew.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('paydomainnrpimautorenew.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('paydomainnrpimautorenew.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('reservationwithtransaction.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('reservationwithtransaction.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('thedomain2.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('thedomain2.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('thedomain4.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('thedomain4.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('thedomain-modpending.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('thedomain-modpending.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('thedomain-delpending.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('thedomain-delpending.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('thedomain-msdpending.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('thedomain-msdpending.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('thedomain-renewtoearly.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('thedomain-renewtoearly.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('thedomain-totransfer1.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('thedomain-totransfer1.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('thedomain-668.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('thedomain-668.ie', 'dns2.example.ie', NULL, NULL, 1),
  ('thedomain-668locked.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('thedomain-668locked.ie', 'dns2.example.ie', NULL, NULL, 1);

INSERT INTO `DNSHist` VALUES
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'dsm0.ie'), 'dsm0.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'dsm0.ie'), 'dsm0.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'transferred.ie'), 'transferred.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'transferred.ie'), 'transferred.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'transferred2.ie'), 'transferred2.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'transferred2.ie'), 'transferred2.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'transferred3.ie'), 'transferred3.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'transferred3.ie'), 'transferred3.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'pizzaonline2.ie'), 'pizzaonline2.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'pizzaonline2.ie'), 'pizzaonline2.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'deleteddomain.ie'), 'deleteddomain.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'deleteddomain.ie'), 'deleteddomain.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'suspendeddomain.ie'), 'suspendeddomain.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'suspendeddomain.ie'), 'suspendeddomain.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'theweb2.ie'), 'theweb2.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'theweb2.ie'), 'theweb2.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'äpaymentutf8daotest.ie'), 'äpaymentutf8daotest.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'äpaymentutf8daotest.ie'), 'äpaymentutf8daotest.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'generate-invoice-test.ie'), 'generate-invoice-test.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'generate-invoice-test.ie'), 'generate-invoice-test.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = '1registerdomaincc.ie'), '1registerdomaincc.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = '1registerdomaincc.ie'), '1registerdomaincc.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'createccdomain.ie'), 'createccdomain.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'createccdomain.ie'), 'createccdomain.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'createdomainregistrarbasic.ie'), 'createdomainregistrarbasic.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'createdomainregistrarbasic.ie'), 'createdomainregistrarbasic.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'paydomainlockedautorenew.ie'), 'paydomainlockedautorenew.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'paydomainlockedautorenew.ie'), 'paydomainlockedautorenew.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'paydomainnrpimautorenew.ie'), 'paydomainnrpimautorenew.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'paydomainnrpimautorenew.ie'), 'paydomainnrpimautorenew.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'reservationwithtransaction.ie'), 'reservationwithtransaction.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'reservationwithtransaction.ie'), 'reservationwithtransaction.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain2.ie'), 'thedomain2.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain2.ie'), 'thedomain2.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain4.ie'), 'thedomain4.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain4.ie'), 'thedomain4.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-modpending.ie'), 'thedomain-modpending.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-modpending.ie'), 'thedomain-modpending.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-delpending.ie'), 'thedomain-delpending.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-delpending.ie'), 'thedomain-delpending.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-msdpending.ie'), 'thedomain-msdpending.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-msdpending.ie'), 'thedomain-msdpending.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-renewtoearly.ie'), 'thedomain-renewtoearly.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-renewtoearly.ie'), 'thedomain-renewtoearly.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-totransfer1.ie'), 'thedomain-totransfer1.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-totransfer1.ie'), 'thedomain-totransfer1.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-668.ie'), 'thedomain-668.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-668.ie'), 'thedomain-668.ie', 'dns2.example.ie', NULL, NULL, 1),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-668locked.ie'), 'thedomain-668locked.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-668locked.ie'), 'thedomain-668locked.ie', 'dns2.example.ie', NULL, NULL, 1);

INSERT INTO `Contact` VALUES
  ('dsm0.ie', 'AAU809-IEDR', 'Admin'),
  ('dsm0.ie', 'AAU809-IEDR', 'Creator'),
  ('dsm0.ie', 'AAU809-IEDR', 'Tech'),
  ('dsm0.ie', 'AAU809-IEDR', 'Billing'),
  ('pizzaonline2.ie', 'AGZ082-IEDR', 'Admin'),
  ('pizzaonline2.ie', 'IDL2-IEDR', 'Creator'),
  ('pizzaonline2.ie', 'AGZ082-IEDR', 'Tech'),
  ('pizzaonline2.ie', 'IDL2-IEDR', 'Billing'),
  ('thedomain-modpending.ie', 'AGZ082-IEDR', 'Admin'),
  ('thedomain-modpending.ie', 'IDL2-IEDR', 'Creator'),
  ('thedomain-modpending.ie', 'AGZ082-IEDR', 'Tech'),
  ('thedomain-modpending.ie', 'IDL2-IEDR', 'Billing'),
  ('theweb2.ie', 'ABC718-IEDR', 'Admin'),
  ('theweb2.ie', 'NTG1-IEDR', 'Creator'),
  ('theweb2.ie', 'NTG1-IEDR', 'Tech'),
  ('theweb2.ie', 'NTG1-IEDR', 'Billing'),
  ('theweb3.ie', 'ABC718-IEDR', 'Admin'),
  ('theweb3.ie', 'NTG1-IEDR', 'Creator'),
  ('theweb3.ie', 'NTG1-IEDR', 'Tech'),
  ('theweb3.ie', 'NTG1-IEDR', 'Billing'),
  ('generate-invoice-test.ie', 'AHK695-IEDR', 'Admin'),
  ('generate-invoice-test.ie', 'AAP368-IEDR', 'Creator'),
  ('generate-invoice-test.ie', 'ABG704-IEDR', 'Tech'),
  ('generate-invoice-test.ie', 'AAP368-IEDR', 'Billing'),
  ('äpaymentutf8daotest.ie', 'AHK695-IEDR', 'Admin'),
  ('äpaymentutf8daotest.ie', 'AAP368-IEDR', 'Creator'),
  ('äpaymentutf8daotest.ie', 'ABG704-IEDR', 'Tech'),
  ('äpaymentutf8daotest.ie', 'AAP368-IEDR', 'Billing'),
  ('deleteddomain.ie', 'APIT1-IEDR', 'Admin'),
  ('deleteddomain.ie', 'APIT1-IEDR', 'Creator'),
  ('deleteddomain.ie', 'APIT1-IEDR', 'Tech'),
  ('deleteddomain.ie', 'APIT1-IEDR', 'Billing'),
  ('suspendeddomain.ie', 'APIT1-IEDR', 'Admin'),
  ('suspendeddomain.ie', 'APIT1-IEDR', 'Creator'),
  ('suspendeddomain.ie', 'APIT1-IEDR', 'Tech'),
  ('suspendeddomain.ie', 'APIT1-IEDR', 'Billing'),
  ('1registerdomaincc.ie', 'APIT1-IEDR', 'Admin'),
  ('1registerdomaincc.ie', 'APIT1-IEDR', 'Creator'),
  ('1registerdomaincc.ie', 'APIT1-IEDR', 'Tech'),
  ('1registerdomaincc.ie', 'APIT1-IEDR', 'Billing'),
  ('createccdomain.ie', 'APIT1-IEDR', 'Admin'),
  ('createccdomain.ie', 'APIT1-IEDR', 'Creator'),
  ('createccdomain.ie', 'APIT1-IEDR', 'Tech'),
  ('createccdomain.ie', 'APIT1-IEDR', 'Billing'),
  ('createdomainregistrarbasic.ie', 'APIT1-IEDR', 'Admin'),
  ('createdomainregistrarbasic.ie', 'APIT1-IEDR', 'Creator'),
  ('createdomainregistrarbasic.ie', 'APIT1-IEDR', 'Tech'),
  ('createdomainregistrarbasic.ie', 'APIT1-IEDR', 'Billing'),
  ('paydomainlockedautorenew.ie', 'APIT1-IEDR', 'Admin'),
  ('paydomainlockedautorenew.ie', 'APIT1-IEDR', 'Creator'),
  ('paydomainlockedautorenew.ie', 'APIT1-IEDR', 'Tech'),
  ('paydomainlockedautorenew.ie', 'APIT1-IEDR', 'Billing'),
  ('paydomainnrpimautorenew.ie', 'APIT1-IEDR', 'Admin'),
  ('paydomainnrpimautorenew.ie', 'APIT1-IEDR', 'Creator'),
  ('paydomainnrpimautorenew.ie', 'APIT1-IEDR', 'Tech'),
  ('paydomainnrpimautorenew.ie', 'APIT1-IEDR', 'Billing'),
  ('reservationwithtransaction.ie', 'APIT1-IEDR', 'Admin'),
  ('reservationwithtransaction.ie', 'APIT1-IEDR', 'Creator'),
  ('reservationwithtransaction.ie', 'APIT1-IEDR', 'Tech'),
  ('reservationwithtransaction.ie', 'APIT1-IEDR', 'Billing'),
  ('thedomain-delpending.ie', 'APIT1-IEDR', 'Admin'),
  ('thedomain-delpending.ie', 'APIT1-IEDR', 'Creator'),
  ('thedomain-delpending.ie', 'APIT1-IEDR', 'Tech'),
  ('thedomain-delpending.ie', 'APIT1-IEDR', 'Billing'),
  ('thedomain-msdpending.ie', 'APIT1-IEDR', 'Admin'),
  ('thedomain-msdpending.ie', 'APIT1-IEDR', 'Creator'),
  ('thedomain-msdpending.ie', 'APIT1-IEDR', 'Tech'),
  ('thedomain-msdpending.ie', 'APIT1-IEDR', 'Billing'),
  ('thedomain-renewtoearly.ie', 'APIT1-IEDR', 'Admin'),
  ('thedomain-renewtoearly.ie', 'APIT1-IEDR', 'Creator'),
  ('thedomain-renewtoearly.ie', 'APIT1-IEDR', 'Tech'),
  ('thedomain-renewtoearly.ie', 'APIT1-IEDR', 'Billing'),
  ('thedomain-totransfer1.ie', 'APIT1-IEDR', 'Admin'),
  ('thedomain-totransfer1.ie', 'APIT1-IEDR', 'Creator'),
  ('thedomain-totransfer1.ie', 'APIT1-IEDR', 'Tech'),
  ('thedomain-totransfer1.ie', 'APIT1-IEDR', 'Billing'),
  ('thedomain2.ie', 'APIT1-IEDR', 'Admin'),
  ('thedomain2.ie', 'APIT1-IEDR', 'Creator'),
  ('thedomain2.ie', 'APIT1-IEDR', 'Tech'),
  ('thedomain2.ie', 'APIT1-IEDR', 'Billing'),
  ('thedomain-668locked.ie', 'APIT4-IEDR', 'Admin'),
  ('thedomain-668locked.ie', 'APIT4-IEDR', 'Creator'),
  ('thedomain-668locked.ie', 'APIT4-IEDR', 'Tech'),
  ('thedomain-668locked.ie', 'APIT4-IEDR', 'Billing');

INSERT INTO `ContactHist` VALUES
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'dsm0.ie'), 'dsm0.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'dsm0.ie'), 'dsm0.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'dsm0.ie'), 'dsm0.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'dsm0.ie'), 'dsm0.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AAU809-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'pizzaonline2.ie'), 'pizzaonline2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AGZ082-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'pizzaonline2.ie'), 'pizzaonline2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'pizzaonline2.ie'), 'pizzaonline2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AGZ082-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'pizzaonline2.ie'), 'pizzaonline2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-modpending.ie'), 'thedomain-modpending.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AGZ082-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-modpending.ie'), 'thedomain-modpending.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-modpending.ie'), 'thedomain-modpending.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AGZ082-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-modpending.ie'), 'thedomain-modpending.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'IDL2-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'theweb2.ie'), 'theweb2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'ABC718-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'theweb2.ie'), 'theweb2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'NTG1-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'theweb2.ie'), 'theweb2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'NTG1-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'theweb2.ie'), 'theweb2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'NTG1-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'theweb3.ie'), 'theweb3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'ABC718-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'theweb3.ie'), 'theweb3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'NTG1-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'theweb3.ie'), 'theweb3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'NTG1-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'theweb3.ie'), 'theweb3.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'NTG1-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'generate-invoice-test.ie'), 'generate-invoice-test.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AHK695-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'generate-invoice-test.ie'), 'generate-invoice-test.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AAP368-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'generate-invoice-test.ie'), 'generate-invoice-test.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'ABG704-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'generate-invoice-test.ie'), 'generate-invoice-test.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AAP368-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'äpaymentutf8daotest.ie'), 'äpaymentutf8daotest.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AHK695-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'äpaymentutf8daotest.ie'), 'äpaymentutf8daotest.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AAP368-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'äpaymentutf8daotest.ie'), 'äpaymentutf8daotest.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'ABG704-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'äpaymentutf8daotest.ie'), 'äpaymentutf8daotest.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'AAP368-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'deleteddomain.ie'), 'deleteddomain.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'deleteddomain.ie'), 'deleteddomain.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'deleteddomain.ie'), 'deleteddomain.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'deleteddomain.ie'), 'deleteddomain.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'suspendeddomain.ie'), 'suspendeddomain.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'suspendeddomain.ie'), 'suspendeddomain.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'suspendeddomain.ie'), 'suspendeddomain.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'suspendeddomain.ie'), 'suspendeddomain.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = '1registerdomaincc.ie'), '1registerdomaincc.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = '1registerdomaincc.ie'), '1registerdomaincc.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = '1registerdomaincc.ie'), '1registerdomaincc.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = '1registerdomaincc.ie'), '1registerdomaincc.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'createccdomain.ie'), 'createccdomain.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'createccdomain.ie'), 'createccdomain.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'createccdomain.ie'), 'createccdomain.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'createccdomain.ie'), 'createccdomain.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'createdomainregistrarbasic.ie'), 'createdomainregistrarbasic.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'createdomainregistrarbasic.ie'), 'createdomainregistrarbasic.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'createdomainregistrarbasic.ie'), 'createdomainregistrarbasic.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'createdomainregistrarbasic.ie'), 'createdomainregistrarbasic.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'paydomainlockedautorenew.ie'), 'paydomainlockedautorenew.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'paydomainlockedautorenew.ie'), 'paydomainlockedautorenew.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'paydomainlockedautorenew.ie'), 'paydomainlockedautorenew.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'paydomainlockedautorenew.ie'), 'paydomainlockedautorenew.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'paydomainnrpimautorenew.ie'), 'paydomainnrpimautorenew.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'paydomainnrpimautorenew.ie'), 'paydomainnrpimautorenew.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'paydomainnrpimautorenew.ie'), 'paydomainnrpimautorenew.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'paydomainnrpimautorenew.ie'), 'paydomainnrpimautorenew.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'reservationwithtransaction.ie'), 'reservationwithtransaction.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'reservationwithtransaction.ie'), 'reservationwithtransaction.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'reservationwithtransaction.ie'), 'reservationwithtransaction.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'reservationwithtransaction.ie'), 'reservationwithtransaction.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-delpending.ie'), 'thedomain-delpending.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-delpending.ie'), 'thedomain-delpending.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-delpending.ie'), 'thedomain-delpending.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-delpending.ie'), 'thedomain-delpending.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-msdpending.ie'), 'thedomain-msdpending.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-msdpending.ie'), 'thedomain-msdpending.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-msdpending.ie'), 'thedomain-msdpending.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-msdpending.ie'), 'thedomain-msdpending.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-renewtoearly.ie'), 'thedomain-renewtoearly.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-renewtoearly.ie'), 'thedomain-renewtoearly.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-renewtoearly.ie'), 'thedomain-renewtoearly.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-renewtoearly.ie'), 'thedomain-renewtoearly.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-totransfer1.ie'), 'thedomain-totransfer1.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-totransfer1.ie'), 'thedomain-totransfer1.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-totransfer1.ie'), 'thedomain-totransfer1.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-totransfer1.ie'), 'thedomain-totransfer1.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain2.ie'), 'thedomain2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain2.ie'), 'thedomain2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain2.ie'), 'thedomain2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain2.ie'), 'thedomain2.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT1-IEDR'), 'Billing'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-668locked.ie'), 'thedomain-668locked.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT4-IEDR'), 'Admin'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-668locked.ie'), 'thedomain-668locked.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT4-IEDR'), 'Creator'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-668locked.ie'), 'thedomain-668locked.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT4-IEDR'), 'Tech'),
  ((SELECT MAX(`Chng_ID`) FROM `DomainHist` WHERE `D_Name` = 'thedomain-668locked.ie'), 'thedomain-668locked.ie', (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'APIT4-IEDR'), 'Billing');

-- CRS-1234 (Fix to CRS-706)

UPDATE `DomainHist` SET `D_Name` = 'transferred.ie' WHERE `D_Name` = 'transfered.ie';
UPDATE `DNSHist` SET `D_Name` = 'transferred.ie' WHERE `D_Name` = 'transfered.ie';
UPDATE `ContactHist` SET `D_Name` = 'transferred.ie' WHERE `D_Name` = 'transfered.ie';
UPDATE `DomainHist` SET `D_Name` = 'transferred2.ie' WHERE `D_Name` = 'transfered2.ie';
UPDATE `DNSHist` SET `D_Name` = 'transferred2.ie' WHERE `D_Name` = 'transfered2.ie';
UPDATE `ContactHist` SET `D_Name` = 'transferred2.ie' WHERE `D_Name` = 'transfered2.ie';

-- CRS-1324

INSERT INTO Transaction VALUES (103, 'YES', 'NO', 24.28, 'NO', NULL, NOW(), NULL, 20, 4.28, '19700101142701-C-4810681', 'NO', NULL, NULL, '132711', '134028162214561');
INSERT INTO Reservation VALUES (103, 'APITEST-IEDR', 'imppdomain.ie', 12, CURDATE(), 24.28, 3, 4.28, 'YES', 'NO', NULL, NULL, 103, 4, 'renewal', CURDATE() - INTERVAL 1 MONTH, CURDATE() + INTERVAL 1 YEAR - INTERVAL 1 MONTH, 'Credit Card');
INSERT INTO DomainHist VALUES (NULL, 'imppdomain.ie', 'Tester', 5, 10, NULL, (SELECT MAX(Chng_ID) FROM Account WHERE A_Number = 666), NOW() - INTERVAL 1 YEAR - INTERVAL 1 MONTH, NOW() - INTERVAL 1 MONTH, NOW() - INTERVAL 1 MONTH, 'Remark', 'IDL2-IEDR', NOW() - INTERVAL 1 MONTH, 'YES', 534, NOW() + INTERVAL 1 MONTH, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO Domain VALUES ('imppdomain.ie', 'Tester', 5, 10, NULL, 666, NOW() - INTERVAL 1 YEAR - INTERVAL 1 MONTH, NOW() - INTERVAL 1 MONTH, NOW() - INTERVAL 1 MONTH, 'Remark', 'YES', 534, NOW() + INTERVAL 1 MONTH, NULL, NULL, NULL, NULL, NULL, NULL, NULL, (SELECT LAST_INSERT_ID()));
INSERT INTO DNSHist VALUES
  ((SELECT Chng_ID FROM Domain WHERE D_Name = 'imppdomain.ie'), 'imppdomain.ie', 'dns1.ie', NULL, NULL, 0),
  ((SELECT Chng_ID FROM Domain WHERE D_Name = 'imppdomain.ie'), 'imppdomain.ie', 'dns2.ie', NULL, NULL, 1);
INSERT INTO DNS VALUES
  ('imppdomain.ie', 'dns1.ie', NULL, NULL, 0),
  ('imppdomain.ie', 'dns2.ie', NULL, NULL, 1);
INSERT INTO ContactHist VALUES
  ((SELECT Chng_ID FROM Domain WHERE D_Name = 'imppdomain.ie'), 'imppdomain.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'APITEST-IEDR'), 'Admin'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name = 'imppdomain.ie'), 'imppdomain.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'APITEST-IEDR'), 'Creator'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name = 'imppdomain.ie'), 'imppdomain.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'APITEST-IEDR'), 'Tech'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name = 'imppdomain.ie'), 'imppdomain.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'APITEST-IEDR'), 'Billing');
INSERT INTO Contact VALUES
  ('imppdomain.ie', 'APITEST-IEDR', 'Admin'),
  ('imppdomain.ie', 'APITEST-IEDR', 'Creator'),
  ('imppdomain.ie', 'APITEST-IEDR', 'Tech'),
  ('imppdomain.ie', 'APITEST-IEDR', 'Billing');

-- CRS-1152

INSERT INTO DomainHist VALUES (NULL, 'semi-regular.ie', 'Semi Regular Direct', 1, 1, NULL, (SELECT MAX(Chng_ID) FROM Account WHERE A_Number = 1), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'AAU809-IEDR', NOW() - INTERVAL 1 MONTH, 'YES', 25, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO Domain VALUES ('semi-regular.ie', 'Semi Regular Direct', 1, 1, NULL, 1, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 25, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, (SELECT LAST_INSERT_ID()));
INSERT INTO DNSHist VALUES
  ((SELECT Chng_ID FROM Domain WHERE D_Name = 'semi-regular.ie'), 'semi-regular.ie', 'dns1.example.ie', NULL, NULL, 0),
  ((SELECT Chng_ID FROM Domain WHERE D_Name = 'semi-regular.ie'), 'semi-regular.ie', 'dns2.example.ie', NULL, NULL, 1);
INSERT INTO DNS VALUES
  ('semi-regular.ie', 'dns1.example.ie', NULL, NULL, 0),
  ('semi-regular.ie', 'dns2.example.ie', NULL, NULL, 1);
INSERT INTO ContactHist VALUES
  ((SELECT Chng_ID FROM Domain WHERE D_Name = 'semi-regular.ie'), 'semi-regular.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAP582-IEDR'), 'Admin'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name = 'semi-regular.ie'), 'semi-regular.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAU809-IEDR'), 'Admin'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name = 'semi-regular.ie'), 'semi-regular.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAU809-IEDR'), 'Creator'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name = 'semi-regular.ie'), 'semi-regular.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAP582-IEDR'), 'Tech'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name = 'semi-regular.ie'), 'semi-regular.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAU809-IEDR'), 'Billing');
INSERT INTO Contact VALUES
  ('semi-regular.ie', 'AAP582-IEDR', 'Admin'),
  ('semi-regular.ie', 'AAU809-IEDR', 'Admin'),
  ('semi-regular.ie', 'AAU809-IEDR', 'Creator'),
  ('semi-regular.ie', 'AAP582-IEDR', 'Tech'),
  ('semi-regular.ie', 'AAU809-IEDR', 'Billing');

-- CRS-1389

INSERT INTO `SecondaryMarketBuyRequestHist` VALUES
  (40, NOW(), 'AAG061-IEDR', 40, (SELECT Chng_ID FROM Domain WHERE D_Name = 'webwebweb.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'AAG061-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 262), 'Test Holder', NULL, 1, NULL, 1, NULL, 'Test remark', 'Test hostmaster remark', 'Test Admin Name', NULL, 'test@email.ie', NULL, 'Test Inc.', NULL, 'Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 23 DAY, 'AUTHCODEN040', NOW(), 'New', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'));
INSERT INTO `SecondaryMarketBuyRequestTelecomHist` VALUES
  (40, '123654345', 'Phone', 0);
INSERT INTO `SecondaryMarketBuyRequest` VALUES
  (40, 'webwebweb.ie', NULL, 'AAG061-IEDR', 262, 'Test Holder', NULL, 1, NULL, 1, NULL, 'Test remark', 'Test hostmaster remark', 'Test Admin Name', NULL, 'reseller@a.ie', NULL, 'Test Inc.', NULL, 'Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 23 DAY, 'AUTHCODEN040', NOW(), 'New', NULL, 40);
INSERT INTO `SecondaryMarketBuyRequestTelecom` VALUES
  (40, '123654345', 'Phone', 0);

-- CRS-1356

INSERT INTO DomainHist VALUES (NULL, 'domain-with-subcategory.ie', 'Holder', 1, 11, 1, (SELECT Chng_ID FROM Account WHERE A_Number=101), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'IDL2-IEDR', NOW(), 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO Domain VALUES ('domain-with-subcategory.ie', 'Holder', 1, 11, 1, 101, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, (SELECT LAST_INSERT_ID()));
INSERT INTO ContactHist VALUES
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-subcategory.ie'), 'domain-with-subcategory.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Admin'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-subcategory.ie'), 'domain-with-subcategory.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Tech'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-subcategory.ie'), 'domain-with-subcategory.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Billing'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-subcategory.ie'), 'domain-with-subcategory.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Creator');
INSERT INTO Contact VALUES
  ('domain-with-subcategory.ie', 'IDL2-IEDR', 'Admin'),
  ('domain-with-subcategory.ie', 'IDL2-IEDR', 'Tech'),
  ('domain-with-subcategory.ie', 'IDL2-IEDR', 'Billing'),
  ('domain-with-subcategory.ie', 'IDL2-IEDR', 'Creator');
INSERT INTO DNSHist VALUES
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-subcategory.ie'), 'domain-with-subcategory.ie', 'ns1.dns.ie', NULL, NULL, 0),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-subcategory.ie'), 'domain-with-subcategory.ie', 'ns2.dns.ie', NULL, NULL, 1);
INSERT INTO DNS VALUES
  ('domain-with-subcategory.ie', 'ns1.dns.ie', NULL, NULL, 0),
  ('domain-with-subcategory.ie', 'ns2.dns.ie', NULL, NULL, 1);

INSERT INTO DomainHist VALUES (NULL, 'domain-with-subcategory-to-transfer.ie', 'Holder', 1, 11, 1, (SELECT Chng_ID FROM Account WHERE A_Number=666), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'APITEST-IEDR', NOW(), 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO Domain VALUES ('domain-with-subcategory-to-transfer.ie', 'Holder', 1, 11, 1, 666, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, (SELECT LAST_INSERT_ID()));
INSERT INTO ContactHist VALUES
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-subcategory-to-transfer.ie'), 'domain-with-subcategory-to-transfer.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='APITEST-IEDR'), 'Admin'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-subcategory-to-transfer.ie'), 'domain-with-subcategory-to-transfer.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='APITEST-IEDR'), 'Tech'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-subcategory-to-transfer.ie'), 'domain-with-subcategory-to-transfer.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='APITEST-IEDR'), 'Billing'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-subcategory-to-transfer.ie'), 'domain-with-subcategory-to-transfer.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='APITEST-IEDR'), 'Creator');
INSERT INTO Contact VALUES
  ('domain-with-subcategory-to-transfer.ie', 'APITEST-IEDR', 'Admin'),
  ('domain-with-subcategory-to-transfer.ie', 'APITEST-IEDR', 'Tech'),
  ('domain-with-subcategory-to-transfer.ie', 'APITEST-IEDR', 'Billing'),
  ('domain-with-subcategory-to-transfer.ie', 'APITEST-IEDR', 'Creator');
INSERT INTO DNSHist VALUES
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-subcategory-to-transfer.ie'), 'domain-with-subcategory-to-transfer.ie', 'ns1.dns.ie', NULL, NULL, 0),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-subcategory-to-transfer.ie'), 'domain-with-subcategory-to-transfer.ie', 'ns2.dns.ie', NULL, NULL, 1);
INSERT INTO DNS VALUES
  ('domain-with-subcategory-to-transfer.ie', 'ns1.dns.ie', NULL, NULL, 0),
  ('domain-with-subcategory-to-transfer.ie', 'ns2.dns.ie', NULL, NULL, 1);

INSERT INTO DomainHist VALUES (NULL, 'domain-with-transfer-ticket-subcategory-changed.ie', 'Holder', 1, 1, NULL, (SELECT Chng_ID FROM Account WHERE A_Number=101), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'IDL2-IEDR', NOW(), 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO Domain VALUES ('domain-with-transfer-ticket-subcategory-changed.ie', 'Holder', 1, 1, NULL, 101, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, (SELECT LAST_INSERT_ID()));
INSERT INTO ContactHist VALUES
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-transfer-ticket-subcategory-changed.ie'), 'domain-with-transfer-ticket-subcategory-changed.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Admin'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-transfer-ticket-subcategory-changed.ie'), 'domain-with-transfer-ticket-subcategory-changed.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Tech'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-transfer-ticket-subcategory-changed.ie'), 'domain-with-transfer-ticket-subcategory-changed.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Billing'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-transfer-ticket-subcategory-changed.ie'), 'domain-with-transfer-ticket-subcategory-changed.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Creator');
INSERT INTO Contact VALUES
  ('domain-with-transfer-ticket-subcategory-changed.ie', 'IDL2-IEDR', 'Admin'),
  ('domain-with-transfer-ticket-subcategory-changed.ie', 'IDL2-IEDR', 'Tech'),
  ('domain-with-transfer-ticket-subcategory-changed.ie', 'IDL2-IEDR', 'Billing'),
  ('domain-with-transfer-ticket-subcategory-changed.ie', 'IDL2-IEDR', 'Creator');
INSERT INTO DNSHist VALUES
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-transfer-ticket-subcategory-changed.ie'), 'domain-with-transfer-ticket-subcategory-changed.ie', 'ns1.dns.ie', NULL, NULL, 0),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-transfer-ticket-subcategory-changed.ie'), 'domain-with-transfer-ticket-subcategory-changed.ie', 'ns2.dns.ie', NULL, NULL, 1);
INSERT INTO DNS VALUES
  ('domain-with-transfer-ticket-subcategory-changed.ie', 'ns1.dns.ie', NULL, NULL, 0),
  ('domain-with-transfer-ticket-subcategory-changed.ie', 'ns2.dns.ie', NULL, NULL, 1);

INSERT INTO DomainHist VALUES (NULL, 'domain-with-modification-ticket-subcategory-changed.ie', 'Holder', 1, 1, NULL, (SELECT Chng_ID FROM Account WHERE A_Number=101), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'IDL2-IEDR', NOW(), 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO Domain VALUES ('domain-with-modification-ticket-subcategory-changed.ie', 'Holder', 1, 1, NULL, 101, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, (SELECT LAST_INSERT_ID()));
INSERT INTO ContactHist VALUES
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-modification-ticket-subcategory-changed.ie'), 'domain-with-modification-ticket-subcategory-changed.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Admin'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-modification-ticket-subcategory-changed.ie'), 'domain-with-modification-ticket-subcategory-changed.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Tech'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-modification-ticket-subcategory-changed.ie'), 'domain-with-modification-ticket-subcategory-changed.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Billing'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-modification-ticket-subcategory-changed.ie'), 'domain-with-modification-ticket-subcategory-changed.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Creator');
INSERT INTO Contact VALUES
  ('domain-with-modification-ticket-subcategory-changed.ie', 'IDL2-IEDR', 'Admin'),
  ('domain-with-modification-ticket-subcategory-changed.ie', 'IDL2-IEDR', 'Tech'),
  ('domain-with-modification-ticket-subcategory-changed.ie', 'IDL2-IEDR', 'Billing'),
  ('domain-with-modification-ticket-subcategory-changed.ie', 'IDL2-IEDR', 'Creator');
INSERT INTO DNSHist VALUES
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-modification-ticket-subcategory-changed.ie'), 'domain-with-modification-ticket-subcategory-changed.ie', 'ns1.dns.ie', NULL, NULL, 0),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='domain-with-modification-ticket-subcategory-changed.ie'), 'domain-with-modification-ticket-subcategory-changed.ie', 'ns2.dns.ie', NULL, NULL, 1);
INSERT INTO DNS VALUES
  ('domain-with-modification-ticket-subcategory-changed.ie', 'ns1.dns.ie', NULL, NULL, 0),
  ('domain-with-modification-ticket-subcategory-changed.ie', 'ns2.dns.ie', NULL, NULL, 1);

INSERT INTO DomainHist VALUES (NULL, 'sec-mrkt-with-subcategory-to-be-completed.ie', 'Holder', 1, 11, 1, (SELECT Chng_ID FROM Account WHERE A_Number=101), NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'IDL2-IEDR', NOW(), 'YES', 2065, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO Domain VALUES ('sec-mrkt-with-subcategory-to-be-completed.ie', 'Holder', 1, 11, 1, 101, NOW() - INTERVAL 6 MONTH, NOW() + INTERVAL 6 MONTH, NOW() - INTERVAL 6 MONTH, 'Remark', 'YES', 2065, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, (SELECT LAST_INSERT_ID()));
INSERT INTO ContactHist VALUES
  ((SELECT Chng_ID FROM Domain WHERE D_Name='sec-mrkt-with-subcategory-to-be-completed.ie'), 'sec-mrkt-with-subcategory-to-be-completed.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Admin'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='sec-mrkt-with-subcategory-to-be-completed.ie'), 'sec-mrkt-with-subcategory-to-be-completed.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Tech'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='sec-mrkt-with-subcategory-to-be-completed.ie'), 'sec-mrkt-with-subcategory-to-be-completed.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Billing'),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='sec-mrkt-with-subcategory-to-be-completed.ie'), 'sec-mrkt-with-subcategory-to-be-completed.ie', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Creator');
INSERT INTO Contact VALUES
  ('sec-mrkt-with-subcategory-to-be-completed.ie', 'IDL2-IEDR', 'Admin'),
  ('sec-mrkt-with-subcategory-to-be-completed.ie', 'IDL2-IEDR', 'Tech'),
  ('sec-mrkt-with-subcategory-to-be-completed.ie', 'IDL2-IEDR', 'Billing'),
  ('sec-mrkt-with-subcategory-to-be-completed.ie', 'IDL2-IEDR', 'Creator');
INSERT INTO DNSHist VALUES
  ((SELECT Chng_ID FROM Domain WHERE D_Name='sec-mrkt-with-subcategory-to-be-completed.ie'), 'sec-mrkt-with-subcategory-to-be-completed.ie', 'ns1.dns.ie', NULL, NULL, 0),
  ((SELECT Chng_ID FROM Domain WHERE D_Name='sec-mrkt-with-subcategory-to-be-completed.ie'), 'sec-mrkt-with-subcategory-to-be-completed.ie', 'ns2.dns.ie', NULL, NULL, 1);
INSERT INTO DNS VALUES
  ('sec-mrkt-with-subcategory-to-be-completed.ie', 'ns1.dns.ie', NULL, NULL, 0),
  ('sec-mrkt-with-subcategory-to-be-completed.ie', 'ns2.dns.ie', NULL, NULL, 1);

INSERT INTO Transaction VALUES (104, 'NO', 'NO', 522.75, 'NO', NULL, NULL, NULL, 425.00, 97.75, '19700101142701-C-4810682', 'NO', NULL, NULL, '132712', '134028162214562');
INSERT INTO Reservation VALUES (104, 'SAM-IEDR', 'registration-with-subcategory.ie', 12, CURDATE(), 522.75, 3, 97.75, 'NO', 'NO', NULL, 400002, 104, 15, 'registration', NULL, NULL, 'Credit Card');
INSERT INTO TicketHist VALUES (NULL, 400002, 'Registration', 'registration-with-subcategory.ie', NULL, 'Holder', NULL, (SELECT Chng_ID FROM Account WHERE A_Number=1), NULL, 1, 11, 1, 'Remark', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='SAM-IEDR'), NULL, NULL, NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='SAM-IEDR'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='SAM-IEDR'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='SAM-IEDR'), 'Passed', NOW(), 'Passed', NOW(), 'NO', NULL, NOW() + INTERVAL 1 YEAR, NOW(), 'Remark', NOW(), 'SAM-IEDR', NULL, NULL, NOW(), 'NO', 5, NULL, 'NO', 'New', NOW(), 'New', NOW());
INSERT INTO Ticket VALUES (400002, 'Registration', 'registration-with-subcategory.ie', NULL, 'Holder', NULL, 1, NULL, 1, 11, 1, 'Remark', 'SAM-IEDR', NULL, NULL, NULL, 'SAM-IEDR', NULL, 'SAM-IEDR', NULL, 'SAM-IEDR', 'Passed', NOW(), 'Passed', NOW(), 'NO', NULL, NOW() + INTERVAL 1 YEAR, NOW(), 'Remark', NULL, NULL, NOW(), 'NO', 5, NULL, 'NO', 'New', NOW(), 'New', NOW(), (SELECT LAST_INSERT_ID()));
INSERT INTO TicketNameserverHist VALUES
  ((SELECT Chng_ID FROM Ticket WHERE T_Number=400002), 400002, 'ns1.dns.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
  ((SELECT Chng_ID FROM Ticket WHERE T_Number=400002), 400002, 'ns2.dns.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());
INSERT INTO TicketNameserver VALUES
  (400002, 'ns1.dns.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
  (400002, 'ns2.dns.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());

INSERT INTO TicketHist VALUES (NULL, 400003, 'Transfer', 'domain-with-transfer-ticket-subcategory-changed.ie', NULL, 'Holder', NULL, (SELECT Chng_ID FROM Account WHERE A_Number=1), NULL, 1, 11, 2, 'Remark', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='SAM-IEDR'), NULL, NULL, NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='SAM-IEDR'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='SAM-IEDR'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='SAM-IEDR'), 'Passed', NOW(), 'Passed', NOW(), 'NO', NULL, NOW() + INTERVAL 1 YEAR, NOW(), 'Remark', NOW(), 'SAM-IEDR', NULL, NULL, NOW(), 'NO', 5, NULL, 'NO', 'New', NOW(), 'New', NOW());
INSERT INTO Ticket VALUES (400003, 'Transfer', 'domain-with-transfer-ticket-subcategory-changed.ie', NULL, 'Holder', NULL, 1, NULL, 2, 11, 2, 'Remark', 'SAM-IEDR', NULL, NULL, NULL, 'SAM-IEDR', NULL, 'SAM-IEDR', NULL, 'SAM-IEDR', 'Passed', NOW(), 'Passed', NOW(), 'NO', NULL, NOW() + INTERVAL 1 YEAR, NOW(), 'Remark', NULL, NULL, NOW(), 'NO', 5, NULL, 'NO', 'New', NOW(), 'New', NOW(), (SELECT LAST_INSERT_ID()));
INSERT INTO TicketNameserverHist VALUES
  ((SELECT Chng_ID FROM Ticket WHERE T_Number=400003), 400003, 'ns1.dns.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
  ((SELECT Chng_ID FROM Ticket WHERE T_Number=400003), 400003, 'ns2.dns.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());
INSERT INTO TicketNameserver VALUES
  (400003, 'ns1.dns.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
  (400003, 'ns2.dns.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());

INSERT INTO TicketHist VALUES (NULL, 400004, 'Modification', 'domain-with-modification-ticket-subcategory-changed.ie', NULL, 'Holder', NULL, (SELECT Chng_ID FROM Account WHERE A_Number=101), NULL, 1, 11, 2, 'Remark', (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), NULL, NULL, NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle='IDL2-IEDR'), 'Passed', NOW(), 'Passed', NOW(), 'NO', NULL, NOW() + INTERVAL 1 YEAR, NOW(), 'Remark', NOW(), 'IDL2-IEDR', NULL, NULL, NOW(), 'NO', 5, NULL, 'NO', 'New', NOW(), 'New', NOW());
INSERT INTO Ticket VALUES (400004, 'Modification', 'domain-with-modification-ticket-subcategory-changed.ie', NULL, 'Holder', NULL, 101, NULL, 2, 11, 2, 'Remark', 'IDL2-IEDR', NULL, NULL, NULL, 'IDL2-IEDR', NULL, 'IDL2-IEDR', NULL, 'IDL2-IEDR', 'Passed', NOW(), 'Passed', NOW(), 'NO', NULL, NOW() + INTERVAL 1 YEAR, NOW(), 'Remark', NULL, NULL, NOW(), 'NO', 5, NULL, 'NO', 'New', NOW(), 'New', NOW(), (SELECT LAST_INSERT_ID()));
INSERT INTO TicketNameserverHist VALUES
  ((SELECT Chng_ID FROM Ticket WHERE T_Number=400004), 400004, 'ns1.dns.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
  ((SELECT Chng_ID FROM Ticket WHERE T_Number=400004), 400004, 'ns2.dns.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());
INSERT INTO TicketNameserver VALUES
  (400004, 'ns1.dns.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
  (400004, 'ns2.dns.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());

INSERT INTO SecondaryMarketBuyRequestHist VALUES
  (41, NOW(), 'IDL2-IEDR', 41, (SELECT Chng_ID FROM Domain WHERE D_Name = 'sec-mrkt-with-subcategory-to-be-completed.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 101), 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN041', NOW(), 'Passed', NULL);
INSERT INTO SecondaryMarketBuyRequestTelecomHist VALUES
  (41, '987654321', 'Phone', 0);
INSERT INTO SecondaryMarketBuyRequest VALUES
  (41, 'sec-mrkt-with-subcategory-to-be-completed.ie', NULL, 'IDL2-IEDR', 101, 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'AUTHCODEN041', NOW(), 'Passed', NULL, 20);
INSERT INTO SecondaryMarketBuyRequestTelecom VALUES
  (41, '987654321', 'Phone', 0);
INSERT INTO SecondaryMarketSellRequestHist VALUES
  (41, NOW() - INTERVAL 9 DAY, 'IDL2-IEDR', 41, (SELECT MAX(Chng_ID) FROM NicHandle WHERE Nic_Handle = 'IDL2-IEDR'), NOW() - INTERVAL 2 DAY, 'Processing', 41);
INSERT INTO SecondaryMarketSellRequest VALUES
  (41, 'IDL2-IEDR', NOW() - INTERVAL 2 DAY, 'Processing', 41, 41);
