ALTER TABLE NicHandle MODIFY COLUMN NH_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE NicHandleHist MODIFY COLUMN NH_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

UPDATE NicHandle SET A_Number = 1 WHERE Nic_Handle IN ('A007-NASK', 'ABI156-IEDR');

INSERT INTO NicHandle VALUES ('X-IEDR','Test',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2008-08-10','2008-08-10','2008-08-10 00:00:00','N',NULL,'PORTAL',NULL,'Y');

INSERT INTO NicHandleHist VALUES (NULL,'AAH014-IEDR','Test',1,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2003-11-05','2003-11-05','2003-11-05 00:00:00','N',NULL,'PORTAL','2003-11-05 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'AAN639-IEDR','Test',122,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2004-12-01','2004-12-01','2004-12-01 00:00:00','N',NULL,'PORTAL','2004-12-01 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'AAP941-IEDR','Test',122,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2007-05-10','2007-05-10','2007-05-10 00:00:00','N',NULL,'PORTAL','2007-05-10 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'AAY565-IEDR','Test',266,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2008-08-11','2008-08-11','2008-08-11 00:00:00','N',NULL,'PORTAL','2008-08-11 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'ABI153-IEDR','Test',116,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2005-05-05','2005-05-05','2005-05-05 00:00:00','N',NULL,'PORTAL','2005-05-05 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'ALICE-IEDR','Test',116,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2008-04-18','2008-04-18','2008-04-18 00:00:00','N',NULL,'PORTAL','2008-04-18 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'AN24-IEDR','Test',1,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2003-03-06','2003-03-06','2003-03-06 00:00:00','N',NULL,'PORTAL','2003-03-06 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'CIARA-IEDR','Test',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2003-05-13','2003-05-13','2003-05-13 00:00:00','N',NULL,'PORTAL','2003-05-13 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'CID2-IEDR','Test',216,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2003-05-12','2003-05-12','2003-05-12 00:00:00','N',NULL,'PORTAL','2003-05-12 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'EI3-IEDR','Test',1,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2003-03-06','2003-03-06','2003-03-06 00:00:00','N',NULL,'PORTAL','2003-03-06 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'EMAIL-IEDR','Test',1,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2003-06-04','2003-06-04','2003-06-04 00:00:00','N',NULL,'PORTAL','2003-06-04 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'EW33-IEDR','Test',122,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2003-04-08','2003-04-08','2003-04-08 00:00:00','N',NULL,'PORTAL','2003-04-08 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'EWB2-IEDR','Test',122,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2003-04-08','2003-04-08','2003-04-08 00:00:00','N',NULL,'PORTAL','2003-04-08 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,CONCAT('IDL2-IEDR', UNHEX('01')),'Test',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2015-01-01','2015-01-01','2015-01-01 00:00:00','N',NULL,'PORTAL','2015-01-01 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'JHB17-IEDR','Test',1,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2003-03-06','2003-03-06','2003-03-06 00:00:00','N',NULL,'PORTAL','2003-03-06 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'LORIN-IEDR','Test',109,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2008-08-11','2008-08-11','2008-08-11 00:00:00','N',NULL,'PORTAL','2008-08-11 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'MHR5-IEDR','Test',109,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2003-04-09','2003-04-09','2003-04-09 00:00:00','N',NULL,'PORTAL','2003-04-09 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'PAUL-IEDR','Test',109,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2008-06-16','2008-06-16','2008-06-16 00:00:00','N',NULL,'PORTAL','2008-06-16 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'RC119-IEDR','Test',109,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2003-04-09','2003-04-09','2003-04-09 00:00:00','N',NULL,'PORTAL','2003-04-09 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'RCI1-IEDR','Test',116,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2005-05-05','2005-05-05','2005-05-05 00:00:00','N',NULL,'PORTAL','2005-05-05 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'RIS3-IEDR','Test',122,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2003-04-08','2003-04-08','2003-04-08 00:00:00','N',NULL,'PORTAL','2003-04-08 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,'SOUAD-IEDR','Test',116,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2005-05-05','2005-05-05','2005-05-05 00:00:00','N',NULL,'PORTAL','2005-05-05 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,CONCAT('XXX7-IEDR', UNHEX('01')),'Test',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2005-05-05','2005-05-05','2005-05-05 00:00:00','N',NULL,'PORTAL','2005-05-05 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,CONCAT('AA10-IEDR', UNHEX('01')),'Test',103,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2003-11-05','2003-11-05','2003-11-05 00:00:00','N',NULL,'PORTAL','2003-11-05 00:00:00','PORTAL',NULL);
INSERT INTO NicHandleHist VALUES (NULL,CONCAT('IDL3-IEDR', UNHEX('01')),'Test',103,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active','2003-11-05','2003-11-05','2003-11-05 00:00:00','N',NULL,'PORTAL','2003-11-05 00:00:00','PORTAL',NULL);

CREATE TEMPORARY TABLE missingBillingNicHandles AS
SELECT AH.Billing_NH, AH.A_Number FROM AccountHist AH
    LEFT JOIN NicHandle NH ON NH.Nic_Handle LIKE AH.Billing_NH
    LEFT JOIN NicHandleHist NHH ON NHH.Nic_Handle LIKE AH.Billing_NH
    WHERE NH.Nic_Handle IS NULL AND NHH.Nic_Handle IS NULL
UNION
SELECT A.Billing_NH, A.A_Number FROM Account A
    LEFT JOIN NicHandle NH ON NH.Nic_Handle LIKE A.Billing_NH
    LEFT JOIN NicHandleHist NHH ON NHH.Nic_Handle LIKE A.Billing_NH
    WHERE NH.Nic_Handle IS NULL AND NHH.Nic_Handle IS NULL;

INSERT INTO NicHandleHist SELECT
    NULL,MBNH.Billing_NH,'Test',MBNH.A_Number,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active',CURDATE(),CURDATE(),NOW(),'N',NULL,'PORTAL',NOW(),'PORTAL',NULL
    FROM missingBillingNicHandles MBNH;

DROP TABLE missingBillingNicHandles;

ALTER TABLE NicHandle MODIFY COLUMN NH_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE NicHandleHist MODIFY COLUMN NH_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE Domain MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE DomainHist MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

UPDATE DomainHist SET
    D_TStamp = CURDATE() - INTERVAL 3 MONTH,
    D_Transfer_Dt = CURDATE() - INTERVAL 3 MONTH
    WHERE D_Name like 'badutf%';
UPDATE Domain SET D_TStamp = D_Transfer_Dt WHERE D_TStamp < D_Transfer_Dt;

INSERT INTO Domain VALUES ('1registerdomaincc.ie','Holder','Natural Person','Personal Name',666,'Active',NOW(),NOW(),NOW(),NOW(),'N','Y','Y','','N',17,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL);

INSERT INTO DomainHist VALUES (NULL,'äpaymentutf8daotest.ie','Holder','Natural Person','Personal Name',1,'Active',NOW(),NOW(),NOW(),NOW(),'N','Y','Y','','PORTAL',NOW(),'N',25,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL);
INSERT INTO DomainHist VALUES (NULL,'1payDomain.ie','Holder','Natural Person','Personal Name',1,'Active',NOW(),NOW(),NOW(),NOW(),'N','Y','Y','','PORTAL',NOW(),'N',25,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL);
INSERT INTO DomainHist VALUES (NULL,'1payDomain2.ie','Holder','Natural Person','Personal Name',1,'Active',NOW(),NOW(),NOW(),NOW(),'N','Y','Y','','PORTAL',NOW(),'N',25,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL);
INSERT INTO DomainHist VALUES (NULL,'1payDomain4.ie','Holder','Natural Person','Personal Name',1,'Active',NOW(),NOW(),NOW(),NOW(),'N','Y','Y','','PORTAL',NOW(),'N',25,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL);
INSERT INTO DomainHist VALUES (NULL,'1payDomain5.ie','Holder','Natural Person','Personal Name',1,'Active',NOW(),NOW(),NOW(),NOW(),'N','Y','Y','','PORTAL',NOW(),'N',25,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL);
INSERT INTO DomainHist VALUES (NULL,'1payDomainNRPIMAutorenew.ie','Holder','Natural Person','Personal Name',1,'Active',NOW(),NOW(),NOW(),NOW(),'N','Y','Y','','PORTAL',NOW(),'N',25,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL);
INSERT INTO DomainHist VALUES (NULL,'reservationToInvoiceTest.ie','Holder','Natural Person','Personal Name',1,'Active',NOW(),NOW(),NOW(),NOW(),'N','Y','Y','','PORTAL',NOW(),'N',25,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL);

ALTER TABLE Domain MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE DomainHist MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

UPDATE AccessHist SET Nic_Handle = CONCAT('AA10-IEDR', UNHEX('01')) WHERE Nic_Handle LIKE 'AA11-IEDR%';

UPDATE Reservation SET Domain_Name = '1registerdomaincc.ie' WHERE Domain_Name = '1registerDomainCC.ie';
UPDATE ReservationHist SET Domain_Name = CONCAT('badutf8', UNHEX('01'), '.ie') WHERE Domain_Name like 'badutf8domainname%';

INSERT INTO Ticket(T_Number, T_Type, D_Name, D_Holder, A_Number, T_Class, T_Category, Admin_NH1,
  Tech_NH, Bill_NH, Creator_NH, Admin_Status, Ad_StatusDt, Tech_Status, T_Status_Dt, CheckedOut,
  T_TStamp, T_Created_TS, T_ClikPaid, Financial_Status, F_Status_Dt, Customer_Status, C_Status_Dt)
  VALUES (359697, 'R', '1registerdomaincc.ie', 'Holder', 666, 'Natural Person', 'Personal Name',
    'APITEST-IEDR', 'APITEST-IEDR', 'APITEST-IEDR', 'APITEST-IEDR', 0, NOW(), 0, NOW(), 'N', NOW(),
    NOW(), 'N', 0, NOW(), 0, NOW());

-- CRS-1000

UPDATE `Domain` SET `D_Class` = 'Sole Trader', D_TStamp = D_TStamp
  WHERE `D_Class` NOT IN (SELECT `name` FROM `Class`);
UPDATE `Domain` D SET D.`D_Category` = 'Registered Business Name', D_TStamp = D_TStamp
  WHERE D.`D_Category` NOT IN (SELECT `name` FROM `Category`);
UPDATE `DomainHist` SET `D_Class` = 'Sole Trader', D_TStamp = D_TStamp
  WHERE `D_Class` NOT IN (SELECT `name` FROM `Class`);
UPDATE `DomainHist` D SET D.`D_Category` = 'Registered Business Name', D_TStamp = D_TStamp
  WHERE D.`D_Category` NOT IN (SELECT `name` FROM `Category`);

UPDATE `Ticket` SET `T_Class` = 'Sole Trader', T_TStamp = T_TStamp
  WHERE `T_Class` NOT IN (SELECT `name` FROM `Class`);
UPDATE `Ticket` T SET T.`T_Category` = 'Registered Business Name', T_TStamp = T_TStamp
  WHERE T.`T_Category` NOT IN (SELECT `name` FROM `Category`);
UPDATE `TicketHist` SET `T_Class` = 'Sole Trader', T_TStamp = T_TStamp
  WHERE `T_Class` NOT IN (SELECT `name` FROM `Class`);
UPDATE `TicketHist` T SET T.`T_Category` = 'Registered Business Name', T_TStamp = T_TStamp
  WHERE T.`T_Category` NOT IN (SELECT `name` FROM `Category`);

-- CRS-1054

UPDATE `Product` SET `P_Long_Desc` = '1 Yr Direct' WHERE `P_Code` = 'Std1Year';

