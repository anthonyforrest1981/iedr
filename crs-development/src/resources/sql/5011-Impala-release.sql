-- crs-854
ALTER TABLE `Account` MODIFY COLUMN `A_TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `AccountHist` MODIFY COLUMN `A_TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `Account` MODIFY COLUMN `A_Status` ENUM('Active', 'Suspended', 'Deleted') NOT NULL;
ALTER TABLE `AccountHist` MODIFY COLUMN `A_Status` ENUM('Active', 'Suspended', 'Deleted') NOT NULL;
ALTER TABLE `Account` MODIFY COLUMN `A_TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE `AccountHist` MODIFY COLUMN `A_TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

UPDATE `DSM_State` SET `D_Holder_Type` = 'N/A' WHERE `D_Holder_Type` IS NULL;
UPDATE `DSM_State` SET `Renewal_Mode` = 'N/A'   WHERE `Renewal_Mode` IS NULL;
UPDATE `DSM_State` SET `Cust_Type` = 'N/A'     WHERE `Cust_Type` IS NULL;
UPDATE `DSM_State` SET `NRP_Status` = 'N/A'    WHERE `NRP_Status` IS NULL;
ALTER TABLE `DSM_State` MODIFY COLUMN `D_Holder_Type` ENUM('B', 'C', 'IU', 'IP', 'N', 'N/A') NOT NULL;
ALTER TABLE `DSM_State` MODIFY COLUMN `Renewal_Mode` ENUM('R', 'N', 'A', 'N/A') NOT NULL;
ALTER TABLE `DSM_State` MODIFY COLUMN `Cust_Type` ENUM('R', 'D', 'N/A') NOT NULL;
ALTER TABLE `DSM_State` MODIFY COLUMN `NRP_Status` ENUM('A', 'IM', 'VM', 'IS', 'VS', 'D', 'XPA', 'XPI', 'XPV', 'IMPP', 'ISPP', 'N/A') NOT NULL;

ALTER TABLE `NicHandle` MODIFY COLUMN `NH_TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `NicHandleHist` MODIFY COLUMN `NH_TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `NicHandle` MODIFY COLUMN `NH_Status` ENUM('Active', 'Suspended', 'Deleted') NOT NULL;
ALTER TABLE `NicHandleHist` MODIFY COLUMN `NH_Status` ENUM('Active', 'Suspended', 'Deleted') NOT NULL;
ALTER TABLE `NicHandle` MODIFY COLUMN `NH_TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE `NicHandleHist` MODIFY COLUMN `NH_TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE `Contact` MODIFY COLUMN `Type` VARCHAR(7);
UPDATE `Contact` SET `Type` = 'Admin'   WHERE `Type` = 'A';
UPDATE `Contact` SET `Type` = 'Billing' WHERE `Type` = 'B';
UPDATE `Contact` SET `Type` = 'Tech'    WHERE `Type` = 'T';
UPDATE `Contact` SET `Type` = 'Creator' WHERE `Type` = 'C';
ALTER TABLE `Contact` MODIFY COLUMN `Type` ENUM('Admin', 'Billing', 'Tech', 'Creator') NOT NULL;
ALTER TABLE `ContactHist` MODIFY COLUMN `Type` VARCHAR(7);
UPDATE `ContactHist` SET `Type` = 'Admin'   WHERE `Type` = 'A';
UPDATE `ContactHist` SET `Type` = 'Billing' WHERE `Type` = 'B';
UPDATE `ContactHist` SET `Type` = 'Tech'    WHERE `Type` = 'T';
UPDATE `ContactHist` SET `Type` = 'Creator' WHERE `Type` = 'C';
ALTER TABLE `ContactHist` MODIFY COLUMN `Type` ENUM('Admin', 'Billing', 'Tech', 'Creator') NOT NULL;

ALTER TABLE `Telecom` MODIFY COLUMN `Type` VARCHAR(5);
UPDATE `Telecom` SET `Type` = 'Fax'   WHERE `Type` = 'F';
UPDATE `Telecom` SET `Type` = 'Phone' WHERE `Type` != 'Fax';
ALTER TABLE `Telecom` MODIFY COLUMN `Type` ENUM('Phone', 'Fax') NOT NULL;
ALTER TABLE `TelecomHist` MODIFY COLUMN `Type` VARCHAR(5);
UPDATE `TelecomHist` SET `Type` = 'Fax'   WHERE `Type` = 'F';
UPDATE `TelecomHist` SET `Type` = 'Phone' WHERE `Type` != 'Fax';
ALTER TABLE `TelecomHist` MODIFY COLUMN `Type` ENUM('Phone', 'Fax') NOT NULL;

ALTER TABLE `INCOMING_DOC` MODIFY COLUMN `CR_DATE` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `INCOMING_DOC` MODIFY COLUMN `DOC_PURPOSE` ENUM('New Reg', 'Bill-C Transfer', 'Deletion', 'General Mod', 'Misc', 'Name Server Mod', 'Unnecessary') NOT NULL;
ALTER TABLE `INCOMING_DOC` MODIFY COLUMN `DOC_TYPE` ENUM('fax', 'paper', 'attachment') NOT NULL;
ALTER TABLE `INCOMING_DOC` MODIFY COLUMN `CR_DATE` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE `Reservation` MODIFY COLUMN `Operation_Type` ENUM('registration', 'renewal', 'transfer') NOT NULL;
ALTER TABLE `ReservationHist` MODIFY COLUMN `Operation_Type` ENUM('registration', 'renewal', 'transfer') NOT NULL;

ALTER TABLE `Deposit` MODIFY COLUMN `Trans_Type` ENUM('INIT', 'TOPUP', 'SETTLEMENT', 'MANUAL') NOT NULL;
UPDATE `DepositHist` SET `Trans_Type` = 'INIT' WHERE `Trans_Type` NOT IN ('TOPUP', 'SETTLEMENT', 'MANUAL');
ALTER TABLE `DepositHist` MODIFY COLUMN `Trans_Type` ENUM('INIT', 'TOPUP', 'SETTLEMENT', 'MANUAL') NOT NULL;

ALTER TABLE `Reservation` MODIFY COLUMN `Payment_Method` ENUM('Deposit', 'Credit Card', 'Debit Card') NOT NULL;
ALTER TABLE `ReservationHist` MODIFY COLUMN `Payment_Method` ENUM('Deposit', 'Credit Card', 'Debit Card') NOT NULL;

ALTER TABLE `SchedulerJob` MODIFY COLUMN `job_status` VARCHAR(8);
UPDATE `SchedulerJob` SET `job_status` = 'Running'  WHERE `job_status` = 'R';
UPDATE `SchedulerJob` SET `job_status` = 'Failed'   WHERE `job_status` = 'E';
UPDATE `SchedulerJob` SET `job_status` = 'Finished' WHERE `job_status` = 'F';
UPDATE `SchedulerJob` SET `job_status` = 'Active'   WHERE `job_status` NOT IN ('Running', 'Failed', 'Finished');
ALTER TABLE `SchedulerJob` MODIFY COLUMN `job_status` ENUM('Active', 'Running', 'Failed', 'Finished') NOT NULL;

ALTER TABLE `SchedulerJobHist` MODIFY COLUMN `job_status` VARCHAR(8);
UPDATE `SchedulerJobHist` SET `job_status` = 'Running'  WHERE `job_status` = 'R';
UPDATE `SchedulerJobHist` SET `job_status` = 'Failed'   WHERE `job_status` = 'E';
UPDATE `SchedulerJobHist` SET `job_status` = 'Finished' WHERE `job_status` = 'F';
UPDATE `SchedulerJobHist` SET `job_status` = 'Active'   WHERE `job_status` NOT IN ('Running', 'Failed', 'Finished');
ALTER TABLE `SchedulerJobHist` MODIFY COLUMN `job_status` ENUM('Active', 'Running', 'Failed', 'Finished') NOT NULL;

ALTER TABLE `Ticket` MODIFY COLUMN `T_TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `TicketHist` MODIFY COLUMN `T_TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `Ticket` MODIFY COLUMN `T_Type` VARCHAR(12);
UPDATE `Ticket` SET `T_Type` = 'Registration', `T_TStamp` = `T_TStamp` WHERE `T_Type` = 'R';
UPDATE `Ticket` SET `T_Type` = 'Deletion', `T_TStamp` = `T_TStamp`     WHERE `T_Type` = 'D';
UPDATE `Ticket` SET `T_Type` = 'Modification', `T_TStamp` = `T_TStamp` WHERE `T_Type` = 'M';
UPDATE `Ticket` SET `T_Type` = 'Transfer', `T_TStamp` = `T_TStamp`     WHERE `T_Type` = 'T';
ALTER TABLE `Ticket` MODIFY COLUMN `T_Type` ENUM('Registration', 'Deletion', 'Modification', 'Transfer') NOT NULL;
ALTER TABLE `TicketHist` MODIFY COLUMN `T_Type` VARCHAR(12);
UPDATE `TicketHist` SET `T_Type` = 'Registration', `T_TStamp` = `T_TStamp` WHERE `T_Type` = 'R';
UPDATE `TicketHist` SET `T_Type` = 'Deletion', `T_TStamp` = `T_TStamp`     WHERE `T_Type` = 'D';
UPDATE `TicketHist` SET `T_Type` = 'Modification', `T_TStamp` = `T_TStamp` WHERE `T_Type` = 'M';
UPDATE `TicketHist` SET `T_Type` = 'Transfer', `T_TStamp` = `T_TStamp`     WHERE `T_Type` = 'T';
ALTER TABLE `TicketHist` MODIFY COLUMN `T_Type` ENUM('Registration', 'Deletion', 'Modification', 'Transfer') NOT NULL;
ALTER TABLE `Ticket` MODIFY COLUMN `T_TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE `TicketHist` MODIFY COLUMN `T_TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- crs-774

ALTER TABLE Domain MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE DomainHist MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

update Contact              set D_Name = lower(D_Name) where hex(D_Name) != hex(lower(D_Name));
update ContactHist          set D_Name = lower(D_Name) where hex(D_Name) != hex(lower(D_Name));
update DNS                  set D_Name = lower(D_Name) where hex(D_Name) != hex(lower(D_Name));
update DNSHist              set D_Name = lower(D_Name) where hex(D_Name) != hex(lower(D_Name));
update DeleteListHist       set D_Name = lower(D_Name) where hex(D_Name) != hex(lower(D_Name));
update Domain               set D_Name = lower(D_Name) where hex(D_Name) != hex(lower(D_Name));
update DomainHist           set D_Name = lower(D_Name) where hex(D_Name) != hex(lower(D_Name));
update Domain_Notifications set D_Name = lower(D_Name) where hex(D_Name) != hex(lower(D_Name));
update Receipts             set D_Name = lower(D_Name) where hex(D_Name) != hex(lower(D_Name));
update ReceiptsHist         set D_Name = lower(D_Name) where hex(D_Name) != hex(lower(D_Name));
update Ticket               set D_Name = lower(D_Name), T_TStamp = T_TStamp where hex(D_Name) != hex(lower(D_Name));
update TicketHist           set D_Name = lower(D_Name), T_TStamp = T_TStamp where hex(D_Name) != hex(lower(D_Name));
update TransfersHist        set D_Name = lower(D_Name) where hex(D_Name) != hex(lower(D_Name));

ALTER TABLE Domain
    MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE DomainHist
    MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- CRS-797

DELETE FROM DSM_Transition_Action WHERE Action_Param = "PAY_METHOD,DC,69" LIMIT 4;
DELETE FROM DSM_Transition_Action WHERE Action_Param = "PAY_METHOD,DC,27" LIMIT 20;
DELETE FROM DSM_Transition_Action WHERE Action_Param = "PAY_METHOD,DC,70" LIMIT 6;

-- CRS-949

INSERT INTO `App_Config` (SELECT 'deposit_min_limit', 'INT', `MinDepositLimit` FROM `StaticTableName`);
INSERT INTO `App_Config` (SELECT 'deposit_max_limit', 'INT', `MaxDepositLimit` FROM `StaticTableName`);
DROP TABLE `StaticTableName`;

-- CRS-950

ALTER TABLE `NicHandle`
  ADD COLUMN `Vat_Reg_Id` tinytext AFTER `Vat_Category`,
  MODIFY COLUMN `NH_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `NicHandleHist`
  ADD COLUMN `Vat_Reg_Id` tinytext AFTER `Vat_Category`,
  MODIFY COLUMN `NH_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE `NicHandle` SET `Vat_Reg_Id` = (SELECT `VAT_Reg_ID` FROM `Payment` WHERE `Billing_Contact` = `Nic_Handle`);
UPDATE `NicHandleHist` NH SET `Vat_Reg_Id` = (SELECT `VAT_Reg_ID` FROM `PaymentHist` WHERE `Billing_Contact` = `Nic_Handle` AND `Chng_Id` = NH.`Chng_Id`);
UPDATE `NicHandle` SET `Vat_Reg_Id` = NULL WHERE `Vat_Reg_Id` = '';
UPDATE `NicHandleHist` SET `Vat_Reg_Id` = NULL WHERE `Vat_Reg_Id` = '';
ALTER TABLE `NicHandle`
  MODIFY COLUMN `NH_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE `NicHandleHist`
  MODIFY COLUMN `NH_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
DROP TABLE `Payment`;
DROP TABLE `PaymentHist`;

-- CRS-952

DROP TABLE `Auth_Code`;
DROP TABLE `CategoryPermission`;
DROP TABLE `Class_Category_Permission`;
DROP TABLE `GIBO_Payment`;
DROP TABLE `InvoiceHist`;
DROP TABLE `Levels_To_ApiPermissions`;
DROP TABLE `LoginAttemptFailure`;
DROP TABLE `Receipts`;
DROP TABLE `ReceiptsHist`;
DROP TABLE `SchedulerConfigPopulated`;
DROP TABLE `VatCountries`;
DROP TABLE `VatCountry`;

-- CRS-953

DROP TABLE `DeleteListHist`;

-- CRS-954

ALTER TABLE `App_Config` DROP KEY `Cfg_Key`;
ALTER TABLE `App_Config` ADD COLUMN `Id` INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;
ALTER TABLE `App_Config` ADD UNIQUE INDEX (`Cfg_Key`);

ALTER TABLE `Domain_Notifications` ADD COLUMN `Id` INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;

ALTER TABLE `INCOMING_DOC_DOMAINS` ADD COLUMN `Id` INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;

ALTER TABLE `ResetPass` DROP PRIMARY KEY;
ALTER TABLE `ResetPass` ADD COLUMN `Id` INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;

ALTER TABLE `Telecom` ADD COLUMN `Id` INT(10) FIRST;
ALTER TABLE `Telecom` DROP PRIMARY KEY;
ALTER TABLE `TelecomHist` ADD COLUMN `Id` INT(10) AFTER `Chng_Id`;
ALTER TABLE `TelecomHist` DROP FOREIGN KEY `fk_TelecomHist_NicHandleHist`;
ALTER TABLE `TelecomHist` DROP PRIMARY KEY;
-- Quick filling columns Id in both tables providing mutual uniqueness
ALTER TABLE `TelecomHist` MODIFY COLUMN `Id` int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY;
UPDATE `Telecom` SET `Id` = (SELECT MAX(`Id`)  + 1 FROM `TelecomHist`) LIMIT 1;
ALTER TABLE `Telecom` MODIFY COLUMN `Id` INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY;
ALTER TABLE `TelecomHist` MODIFY COLUMN `Id` INT(10) NOT NULL;
ALTER TABLE `TelecomHist` DROP PRIMARY KEY;
ALTER TABLE `TelecomHist` ADD PRIMARY KEY (`Chng_Id`, `Id`);
ALTER TABLE `TelecomHist` ADD CONSTRAINT `fk_TelecomHist_NicHandleHist` FOREIGN KEY (`Chng_ID`) REFERENCES `NicHandleHist` (`Chng_ID`) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE `UserPermissions` DROP PRIMARY KEY;
ALTER TABLE `UserPermissions` ADD COLUMN `Id` INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;

-- CRS-961

ALTER TABLE `Product` DROP PRIMARY KEY;
ALTER TABLE `Product` ADD COLUMN `Id` INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;
ALTER TABLE `Reservation` ADD COLUMN `Product_Id` INT(10) AFTER `Product_Code`;
UPDATE `Reservation` SET `Product_Id` = (SELECT `Id` FROM `Product` WHERE `P_Code` = `Product_Code`);
ALTER TABLE `Reservation` DROP FOREIGN KEY `FK_Product`;
ALTER TABLE `Reservation` DROP KEY `FK_Product`;
ALTER TABLE `Reservation` ADD CONSTRAINT `FK_Product` FOREIGN KEY (`Product_Id`) REFERENCES `Product` (`Id`);
ALTER TABLE `Reservation` DROP COLUMN `Product_Code`;
ALTER TABLE `ReservationHist` ADD COLUMN `Product_Id` INT(10) AFTER `Product_Code`;
UPDATE `ReservationHist` SET `Product_Id` = (SELECT `Id` FROM `Product` WHERE `P_Code` = `Product_Code`);
ALTER TABLE `ReservationHist` DROP FOREIGN KEY `FK_RH_Product`;
ALTER TABLE `ReservationHist` DROP KEY `FK_RH_Product`;
ALTER TABLE `ReservationHist` ADD CONSTRAINT `FK_RH_Product` FOREIGN KEY (`Product_Id`) REFERENCES `Product` (`Id`);
ALTER TABLE `ReservationHist` DROP COLUMN `Product_Code`;

-- CRS-975

UPDATE `App_Config` SET
  `Cfg_Key` = 'ticket_expiration_notification_periods',
  `Cfg_Type` = 'STRING',
  `Cfg_Value` = '1,2,3,4,5,10'
WHERE `Cfg_Key` = 'ticket_expiration_notification_period';

CREATE TABLE `Ticket_Notifications` (
  `T_Number` int(10) unsigned NOT NULL,
  `Notification_Period` int(11) NOT NULL COMMENT 'notification period',
  PRIMARY KEY (`T_Number`, `Notification_Period`));

UPDATE `Email` SET
`E_Subject` = 'Billing Transfer Hold-up Re: $DOMAIN$ <$TICKET_DAYS_REMAINING_WITH_DAYS_SUFFIX$ remaining>',
`E_Text` = '\n\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\n** This is Important ** Please read carefully **\n\nDear $ADMIN-C_NAME$\n\n$TICKET_DAYS_PASSED$ days have passed since an application was submitted to transfer $DOMAIN$ to $GAINING_BILL-C_NAME$.\n\nBilling transfers are usually held up in this way due to a problem with your domains\' hosting. If the transfer request is not completed within $TICKET_DAYS_REMAINING_WITH_DAYS_SUFFIX$, the request will expire.  The domain name will subsequently remain with your current Registrar $LOSING_BILL-C_NAME$.\n\nPlease contact your hosting provider or new registrar as soon as possible to ensure that any issues are resolved.\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n\n',
`E_TS` = `E_TS`
WHERE E_ID = 35;

UPDATE `Email` SET
`E_Subject` = 'Modification Hold-up Re: $DOMAIN$ <$TICKET_DAYS_REMAINING_WITH_DAYS_SUFFIX$ remaining>',
`E_Text` = '\n\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\n** This is Important ** Please read carefully **\n\nDear $ADMIN-C_NAME$\n\nThank you for your request to modify the domain name $DOMAIN$\n\n$TICKET_DAYS_PASSED$ days have passed since your request was submitted and we have not yet received all of the necessary requirements to complete the modification of this domain name.\n\nIf the requirements are not received within $TICKET_DAYS_REMAINING_WITH_DAYS_SUFFIX$, your request will expire and you will need to resubmit your modification.\n\nFor any queries regarding the requirements to complete your modification, please contact your registrar, $REGISTRAR_NAME$\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel: +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n\n',
`E_TS` = `E_TS`
WHERE E_ID = 41;

UPDATE `Email` SET
`E_Subject` = 'Registration Hold-up Re: $DOMAIN$ <$TICKET_DAYS_REMAINING_WITH_DAYS_SUFFIX$ remaining>',
`E_Text` = '\n\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\n** This is Important ** Please read carefully **\n\nDear $ADMIN-C_NAME$\n\nThank you for your application for the domain name $DOMAIN$\n\n$TICKET_DAYS_PASSED$ days have passed since your application was submitted and we have not yet received all of the necessary requirements to complete the registration of this domain name.\n\nIf the requirements are not received within $TICKET_DAYS_REMAINING_WITH_DAYS_SUFFIX$, your registration will expire and you will need to resubmit your application.\n\nFor any queries regarding the requirements to complete your registration, please contact your registrar, $REGISTRAR_NAME$\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n\n',
`E_TS` = `E_TS`
WHERE E_ID = 42;

-- CRS-748

ALTER TABLE `Ticket` ADD COLUMN `AutorenewMode` CHAR(1) NOT NULL DEFAULT 'N' AFTER `CharityCode`;
ALTER TABLE `TicketHist` ADD COLUMN `AutorenewMode` CHAR(1) NOT NULL DEFAULT 'N' AFTER `CharityCode`;

INSERT INTO `DSM_Event` (`Id`, `Name`) VALUES ('33', 'CreateBillableAutorenewedDomainRegistrar');
INSERT INTO `DSM_Transition` (`Event_Id`, `Begin_State`, `End_State`) VALUES ('33', '0', '81');
INSERT INTO `DSM_Transition_Action`(`Transition_Id`, `Order`, `Action_Id`, `Comment`, `Action_Param`, `Execute_After_DSM_Change`)
  SELECT t1.`Id`, ta.`Order`, ta.`Action_Id`, ta.`Comment`, ta.`Action_Param`, ta.`Execute_After_DSM_Change`
  FROM `DSM_Transition_Action` ta
    CROSS JOIN `DSM_Transition` t1
    LEFT JOIN `DSM_Transition` t2 ON t2.Id = ta.`Transition_Id`
  WHERE t1.`Event_Id` = 33 AND t1.`Begin_State` = t2.`Begin_State` AND t2.`Event_Id` = 1;

INSERT INTO `DSM_Event` (`Id`, `Name`) VALUES ('34', 'TransferWithAutorenewToRegistrar');
INSERT INTO `DSM_Transition` (`Event_Id`, `Begin_State`, `End_State`)
  SELECT '34', `Begin_State`, '81' FROM `DSM_Transition` WHERE `Event_Id` = 22 AND `End_State` = 17;
INSERT INTO `DSM_Transition` (`Event_Id`, `Begin_State`, `End_State`)
  SELECT '34', `Begin_State`, '542' FROM `DSM_Transition` WHERE `Event_Id` = 22 AND `End_State` = 534;
INSERT INTO `DSM_Transition` (`Event_Id`, `Begin_State`, `End_State`)
  SELECT '34', `Begin_State`, '590' FROM `DSM_Transition` WHERE `Event_Id` = 22 AND `End_State` = 582;

INSERT INTO `DSM_Transition_Action`(`Transition_Id`, `Order`, `Action_Id`, `Comment`, `Action_Param`, `Execute_After_DSM_Change`)
  SELECT t1.`Id`, ta.`Order`, ta.`Action_Id`, ta.`Comment`, ta.`Action_Param`, ta.`Execute_After_DSM_Change`
  FROM `DSM_Transition_Action` ta
    CROSS JOIN `DSM_Transition` t1
    LEFT JOIN `DSM_Transition` t2 ON t2.Id = ta.`Transition_Id`
  WHERE t1.`Event_Id` = 34 AND t1.`Begin_State` = t2.`Begin_State` AND t2.`Event_Id` = 22;

-- CRS-730

# Remove all ClearSuspensionDate and ClearDeletionDate actions from transitions that also perform
# RollRenewalDate action. Then add both those actions to all such transitions.
# This is to fill spots where they would be missing after removing this logic from RollRenewalDate
# action itself.

DELETE FROM `DSM_Transition_Action` WHERE `Transition_Id` IN
  (SELECT `tid` FROM
    (SELECT `Transition_Id` AS `tid` FROM `DSM_Transition_Action` WHERE `Action_Id` = 4) AS t
  )
AND `Action_Id` IN (5, 6);

INSERT INTO `DSM_Transition_Action` (`Transition_Id`, `Order`, `Action_Id`)
  SELECT `Transition_Id`, 1, 5 FROM `DSM_Transition_Action` WHERE `Action_Id` = 4;

INSERT INTO `DSM_Transition_Action` (`Transition_Id`, `Order`, `Action_Id`)
  SELECT `Transition_Id`, 2, 6 FROM `DSM_Transition_Action` WHERE `Action_Id` = 4;

# For each charity+NRP DSM state add a transition which will perform RollRenewalDateOne action when
# RenewalDatePasses event is triggered.

INSERT INTO `DSM_Transition` (`Event_Id`, `Begin_State`, `End_State`)
  SELECT 7, `State`, `State` FROM `DSM_State` WHERE `D_Holder_Type` = 'C' AND `NRP_Status` IN ('VM', 'VS', 'XPV');
INSERT INTO `DSM_Transition_Action` (`Transition_Id`, `Order`, `Action_Id`)
  SELECT `Id`, 0, 12 FROM `DSM_Transition` WHERE
    `Event_Id` = 7 AND
    `Begin_State` IN (SELECT `State` FROM `DSM_State` WHERE `D_Holder_Type` = 'C' AND `NRP_Status` IN ('VM', 'VS', 'XPV'));

-- CRS-856

INSERT INTO `NicHandle` VALUES
('AUTHCCLEAN','AuthCodeCleanupJob',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active',CURDATE(),CURDATE(),NOW(),'N',NULL,'AUTHCCLEAN',NULL,NULL,'Y'),
('AUTRENEWAL','AutorenewalJob',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active',CURDATE(),CURDATE(),NOW(),'N',NULL,'AUTRENEWAL',NULL,NULL,'Y'),
('DNSNOTIFY','DnsCheckFailureNotificationJob',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active',CURDATE(),CURDATE(),NOW(),'N',NULL,'DNSNOTIFY',NULL,NULL,'Y'),
('TICKETEXP','ExpiringTicketEmailJob',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active',CURDATE(),CURDATE(),NOW(),'N',NULL,'TICKETEXP',NULL,NULL,'Y'),
('INVOICING','InvoicingJob',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active',CURDATE(),CURDATE(),NOW(),'N',NULL,'INVOICING',NULL,NULL,'Y'),
('NHCLEAN','NicHandleCleanupJob',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active',CURDATE(),CURDATE(),NOW(),'N',NULL,'NHCLEAN',NULL,NULL,'Y'),
('PUSHQ','PushQ',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active',CURDATE(),CURDATE(),NOW(),'N',NULL,'PUSHQ',NULL,NULL,'Y'),
('RENNOTIFY','RenewalNotificationJob',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active',CURDATE(),CURDATE(),NOW(),'N',NULL,'RENNOTIFY',NULL,NULL,'Y'),
('TICKETCLEAN','TicketAndTransactionCleanupJob',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active',CURDATE(),CURDATE(),NOW(),'N',NULL,'TICKETCLEAN',NULL,NULL,'Y'),
('TRANSINVALID','TransactionInvalidationJob',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active',CURDATE(),CURDATE(),NOW(),'N',NULL,'TRANSINVALID',NULL,NULL,'Y'),
('TRIPLEPASS','TriplePassJob',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active',CURDATE(),CURDATE(),NOW(),'N',NULL,'TRIPLEPASS',NULL,NULL,'Y'),
('UPDATER','UpdaterJob',2,'IEDR','NHAddress','Co. Dublin','Ireland','asd@iedr.ie','Active',CURDATE(),CURDATE(),NOW(),'N',NULL,'UPDATER',NULL,NULL,'Y');

INSERT INTO `Access`(`Nic_Handle`, `NH_Level`) VALUES
('AUTHCCLEAN', 64),
('AUTRENEWAL', 64),
('DNSNOTIFY', 64),
('TICKETEXP', 64),
('INVOICING', 64),
('NHCLEAN', 64),
('PUSHQ', 64),
('RENNOTIFY', 64),
('TICKETCLEAN', 64),
('TRANSINVALID', 64),
('TRIPLEPASS', 64),
('UPDATER', 64);

INSERT INTO `Telecom`(`Nic_Handle`, `Phone`, `Type`) VALUES
('AUTHCCLEAN', '+000000000', 'Phone'),
('AUTRENEWAL', '+000000000', 'Phone'),
('DNSNOTIFY', '+000000000', 'Phone'),
('TICKETEXP', '+000000000', 'Phone'),
('INVOICING', '+000000000', 'Phone'),
('NHCLEAN', '+000000000', 'Phone'),
('PUSHQ', '+000000000', 'Phone'),
('RENNOTIFY', '+000000000', 'Phone'),
('TICKETCLEAN', '+000000000', 'Phone'),
('TRANSINVALID', '+000000000', 'Phone'),
('TRIPLEPASS', '+000000000', 'Phone'),
('UPDATER', '+000000000', 'Phone');

INSERT INTO `SchedulerConfig` (`name`, `schedule`, `Active`) VALUES ('ExpiringTicketEmail', '00 06 * * *', 'Y');

ALTER TABLE `SchedulerConfig` ADD COLUMN `RunningNicHandle` VARCHAR(12) COLLATE utf8_irish_accent_ci NOT NULL;
UPDATE `SchedulerConfig` SET `RunningNicHandle` = 'AUTHCCLEAN' WHERE `name` = 'AuthCodeCleanup';
UPDATE `SchedulerConfig` SET `RunningNicHandle` = 'AUTRENEWAL' WHERE `name` = 'Autorenewal';
UPDATE `SchedulerConfig` SET `RunningNicHandle` = 'DNSNOTIFY' WHERE `name` = 'DnsCheckFailureNotification';
UPDATE `SchedulerConfig` SET `RunningNicHandle` = 'TICKETEXP' WHERE `name` = 'ExpiringTicketEmail';
UPDATE `SchedulerConfig` SET `RunningNicHandle` = 'INVOICING' WHERE `name` = 'Invoicing';
UPDATE `SchedulerConfig` SET `RunningNicHandle` = 'NHCLEAN' WHERE `name` = 'NicHandleCleanup';
UPDATE `SchedulerConfig` SET `RunningNicHandle` = 'PUSHQ' WHERE `name` = 'PushQ';
UPDATE `SchedulerConfig` SET `RunningNicHandle` = 'RENNOTIFY' WHERE `name` = 'RenewalNotification';
UPDATE `SchedulerConfig` SET `RunningNicHandle` = 'TICKETCLEAN' WHERE `name` = 'TicketAndTransactionCleanup';
UPDATE `SchedulerConfig` SET `RunningNicHandle` = 'TRANSINVALID' WHERE `name` = 'TransactionInvalidation';
UPDATE `SchedulerConfig` SET `RunningNicHandle` = 'TRIPLEPASS' WHERE `name` = 'TriplePass';
UPDATE `SchedulerConfig` SET `RunningNicHandle` = 'UPDATER' WHERE `name` = 'Updater';
ALTER TABLE `SchedulerConfig` ADD CONSTRAINT `NicHandle` FOREIGN KEY(`RunningNicHandle`) REFERENCES `NicHandle`(`Nic_Handle`);

-- CRS-956

INSERT INTO `NicHandle` VALUES ('PORTAL','PortalUser',2,'','','Co. Dublin','Ireland','','Active',CURDATE(),CURDATE(),CURDATE(),'N',NULL,NULL,NULL,NULL,'Y');
INSERT INTO `App_Config` (`Cfg_Key`, `Cfg_Type`, `Cfg_Value`) VALUES ('portal_user_nic_handle', 'STRING', 'PORTAL');

-- CRS-851

ALTER TABLE `Domain` DROP `_D_Status_DECOM`, DROP `_D_Status_Dt_DECOM`;
ALTER TABLE `DomainHist` DROP `D_Status`, DROP `D_Status_Dt`;
ALTER TABLE `Ticket` DROP `_Fee_DECOM`, DROP `_VAT_DECOM`;
ALTER TABLE `TicketHist` DROP `Fee`, DROP `VAT`;

-- CRS-844

ALTER TABLE `nicHandleSeq` ENGINE = `InnoDB`;
ALTER TABLE `PersistedCommands` ENGINE = `InnoDB`;
ALTER TABLE `TokenBucket` ENGINE = `InnoDB`;

DROP TABLE `ApiPermissions`, `DomainStats`;

-- crs-843

ALTER TABLE TicketIndex ENGINE=InnoDB;
ALTER TABLE ReservationIndex ENGINE=InnoDB;
ALTER TABLE TransactionIndex ENGINE=InnoDB;
ALTER TABLE AccountVersionIndex ENGINE=InnoDB;
ALTER TABLE DoaVersionIndex ENGINE=InnoDB;

-- CRS-849

ALTER TABLE `DNS` MODIFY COLUMN DNS_Name VARCHAR(254) NOT NULL;
ALTER TABLE `DNSHist` MODIFY COLUMN DNS_Name VARCHAR(254) NOT NULL;
ALTER TABLE `DNS_Check_Notification` MODIFY COLUMN DNS_Name VARCHAR(254) NOT NULL;
ALTER TABLE `TicketNameserver` MODIFY COLUMN TN_Name VARCHAR(254) NOT NULL;
ALTER TABLE `TicketNameserverHist` MODIFY COLUMN TN_Name VARCHAR(254) NOT NULL;

-- CRS-996

UPDATE Email SET
E_Name='',
E_Text='Dear $BILL-C_CO_NAME$\n\nAccount Number $BILL-C_NIC$\n\nWe would like to inform you that due to a change in our product pricing or a change in the VAT rate applied, one or more of your tickets for domain name registrations and or transfer in, raised before the change was applied have failed the financial pass element of our triple pass process.\n\nIn order to get your ticket to pass, you should reauthorise the ticket over your console account.  To do so, log into your account number $BILL-C_NIC$ at the following link: https://console.iedr.ie/\n\nOnce you have logged in, you should follow this path and you can reauthorise any failed transaction/s from here: Account Management / Credit Card Tx / Reauthorise CC Transaction\n\nIf you have any questions in relation to this, please let us know.\n\nKind Regards\nAccounts Team\nIE Domain Registry Limited\n-------------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------------\n',
E_Subject='Credit Card transactions require reauthorisation',
E_To='$BILL-C_EMAIL$',
E_CC=null,
E_BCC=null,
E_From='receipts@iedr.ie',
active='1',
Html='1',
E_To_Internal=null,
E_CC_Internal='receipts@iedr.ie',
E_BCC_Internal='receipts@iedr.ie',
EG_ID='14',
E_Suppressible='N',
E_Suppressed_By_Gaining='N',
E_TS=E_TS
WHERE E_ID=57;

-- CRS-275

ALTER TABLE `Email` MODIFY COLUMN `E_TS` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `EmailHist` MODIFY COLUMN `E_TS` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `EmailGroup` MODIFY COLUMN `EG_TS` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `EmailGroupHist` MODIFY COLUMN `EG_TS` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `EmailDisabler` MODIFY COLUMN `ED_TS` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `EmailDisablerHist` MODIFY COLUMN `ED_TS` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE `EmailGroup` MODIFY COLUMN `EG_Visible` VARCHAR(3);
UPDATE `EmailGroup` SET `EG_Visible` = 'YES' WHERE `EG_Visible` IN ('1', 'Y');
UPDATE `EmailGroup` SET `EG_Visible` = 'NO'  WHERE `EG_Visible` != 'YES';
ALTER TABLE `EmailGroup` MODIFY COLUMN `EG_Visible` ENUM('YES', 'NO') NOT NULL DEFAULT 'YES';
ALTER TABLE `EmailGroupHist` MODIFY COLUMN `EG_Visible` VARCHAR(3);
UPDATE `EmailGroupHist` SET `EG_Visible` = 'YES' WHERE `EG_Visible` IN ('1', 'Y');
UPDATE `EmailGroupHist` SET `EG_Visible` = 'NO' WHERE `EG_Visible` != 'YES';
ALTER TABLE `EmailGroupHist` MODIFY COLUMN `EG_Visible` ENUM('YES', 'NO') NOT NULL;

ALTER TABLE `Email`
  MODIFY COLUMN `active` VARCHAR(3),
  MODIFY COLUMN `Html` VARCHAR(3),
  MODIFY COLUMN `E_Suppressible` VARCHAR(3),
  MODIFY COLUMN `E_Suppressed_By_Gaining` VARCHAR(3);
UPDATE `Email` SET `active` = 'YES' WHERE `active` IN ('1', 'Y');
UPDATE `Email` SET `active` = 'NO'  WHERE `active` != 'YES';
UPDATE `Email` SET `Html` = 'YES' WHERE `Html` IN ('1', 'Y');
UPDATE `Email` SET `Html` = 'NO'  WHERE `Html` != 'YES';
UPDATE `Email` SET `E_Suppressible` = 'YES' WHERE `E_Suppressible` IN ('1', 'Y');
UPDATE `Email` SET `E_Suppressible` = 'NO'  WHERE `E_Suppressible` != 'YES';
UPDATE `Email` SET `E_Suppressed_By_Gaining` = 'YES' WHERE `E_Suppressed_By_Gaining` IN ('1', 'Y');
UPDATE `Email` SET `E_Suppressed_By_Gaining` = 'NO'  WHERE `E_Suppressed_By_Gaining` != 'YES';
ALTER TABLE `Email`
  MODIFY COLUMN `active` ENUM('YES', 'NO') NOT NULL DEFAULT 'YES',
  MODIFY COLUMN `Html` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO',
  MODIFY COLUMN `E_Suppressible` ENUM('YES', 'NO') NOT NULL DEFAULT 'YES',
  MODIFY COLUMN `E_Suppressed_By_Gaining` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO';

ALTER TABLE `EmailHist`
  MODIFY COLUMN `active` VARCHAR(3),
  MODIFY COLUMN `Html` VARCHAR(3),
  MODIFY COLUMN `E_Suppressible` VARCHAR(3);
UPDATE `EmailHist` SET `active` = 'YES' WHERE `active` IN ('1', 'Y');
UPDATE `EmailHist` SET `active` = 'NO'  WHERE `active` != 'YES';
UPDATE `EmailHist` SET `Html` = 'YES' WHERE `Html` IN ('1', 'Y');
UPDATE `EmailHist` SET `Html` = 'NO'  WHERE `Html` != 'YES';
UPDATE `EmailHist` SET `E_Suppressible` = 'YES' WHERE `E_Suppressible` IN ('1', 'Y');
UPDATE `EmailHist` SET `E_Suppressible` = 'NO'  WHERE `E_Suppressible` != 'YES';
ALTER TABLE `EmailHist`
  MODIFY COLUMN `active` ENUM('YES', 'NO') NOT NULL,
  MODIFY COLUMN `Html` ENUM('YES', 'NO') NOT NULL,
  MODIFY COLUMN `E_Suppressible` ENUM('YES', 'NO') NOT NULL;

ALTER TABLE `EmailDisabler` MODIFY COLUMN `ED_Disabled` VARCHAR(3);
UPDATE `EmailDisabler` SET `ED_Disabled` = 'YES' WHERE `ED_Disabled` IN ('1', 'Y');
UPDATE `EmailDisabler` SET `ED_Disabled` = 'NO'  WHERE `ED_Disabled` != 'YES';
ALTER TABLE `EmailDisabler` MODIFY COLUMN `ED_Disabled` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO';
ALTER TABLE `EmailDisablerHist` MODIFY COLUMN `ED_Disabled` VARCHAR(3);
UPDATE `EmailDisablerHist` SET `ED_Disabled` = 'YES' WHERE `ED_Disabled` IN ('1', 'Y');
UPDATE `EmailDisablerHist` SET `ED_Disabled` = 'NO'  WHERE `ED_Disabled` != 'YES';
ALTER TABLE `EmailDisablerHist` MODIFY COLUMN `ED_Disabled` ENUM('YES', 'NO') NOT NULL;

ALTER TABLE `Email` MODIFY COLUMN `E_TS` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE `EmailHist` MODIFY COLUMN `E_TS` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE `EmailGroup` MODIFY COLUMN `EG_TS` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE `EmailGroupHist` MODIFY COLUMN `EG_TS` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE `EmailDisabler` MODIFY COLUMN `ED_TS` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE `EmailDisablerHist` MODIFY COLUMN `ED_TS` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;


ALTER TABLE `Transaction`
  MODIFY COLUMN `Settlement_Started` VARCHAR(3),
  MODIFY COLUMN `Settlement_Ended` VARCHAR(3),
  MODIFY COLUMN `Cancelled` VARCHAR(3),
  MODIFY COLUMN `Invalidated` VARCHAR(3);
UPDATE `Transaction` SET `Settlement_Started` = 'YES' WHERE `Settlement_Started` IN ('1', 'Y');
UPDATE `Transaction` SET `Settlement_Started` = 'NO'  WHERE `Settlement_Started` != 'YES';
UPDATE `Transaction` SET `Settlement_Ended` = 'YES' WHERE `Settlement_Ended` IN ('1', 'Y');
UPDATE `Transaction` SET `Settlement_Ended` = 'NO'  WHERE `Settlement_Ended` != 'YES';
UPDATE `Transaction` SET `Cancelled` = 'YES' WHERE `Cancelled` IN ('1', 'Y');
UPDATE `Transaction` SET `Cancelled` = 'NO'  WHERE `Cancelled` != 'YES';
UPDATE `Transaction` SET `Invalidated` = 'YES' WHERE `Invalidated` IN ('1', 'Y');
UPDATE `Transaction` SET `Invalidated` = 'NO'  WHERE `Invalidated` != 'YES';
ALTER TABLE `Transaction`
  MODIFY COLUMN `Settlement_Started` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO',
  MODIFY COLUMN `Settlement_Ended` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO',
  MODIFY COLUMN `Cancelled` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO',
  MODIFY COLUMN `Invalidated` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO';
ALTER TABLE `TransactionHist`
  MODIFY COLUMN `Settlement_Started` VARCHAR(3),
  MODIFY COLUMN `Settlement_Ended` VARCHAR(3),
  MODIFY COLUMN `Cancelled` VARCHAR(3),
  MODIFY COLUMN `Invalidated` VARCHAR(3);
UPDATE `TransactionHist` SET `Settlement_Started` = 'YES' WHERE `Settlement_Started` IN ('1', 'Y');
UPDATE `TransactionHist` SET `Settlement_Started` = 'NO'  WHERE `Settlement_Started` != 'YES';
UPDATE `TransactionHist` SET `Settlement_Ended` = 'YES' WHERE `Settlement_Ended` IN ('1', 'Y');
UPDATE `TransactionHist` SET `Settlement_Ended` = 'NO'  WHERE `Settlement_Ended` != 'YES';
UPDATE `TransactionHist` SET `Cancelled` = 'YES' WHERE `Cancelled` IN ('1', 'Y');
UPDATE `TransactionHist` SET `Cancelled` = 'NO'  WHERE `Cancelled` != 'YES';
UPDATE `TransactionHist` SET `Invalidated` = 'YES' WHERE `Invalidated` IN ('1', 'Y');
UPDATE `TransactionHist` SET `Invalidated` = 'NO'  WHERE `Invalidated` != 'YES';
ALTER TABLE `TransactionHist`
  MODIFY COLUMN `Settlement_Started` ENUM('YES', 'NO') NOT NULL,
  MODIFY COLUMN `Settlement_Ended` ENUM('YES', 'NO') NOT NULL,
  MODIFY COLUMN `Cancelled` ENUM('YES', 'NO') NOT NULL,
  MODIFY COLUMN `Invalidated` ENUM('YES', 'NO') NOT NULL;

ALTER TABLE `Reservation`
  MODIFY COLUMN `Ready_For_Settlement` VARCHAR(3),
  MODIFY COLUMN `Settled` VARCHAR(3);
UPDATE `Reservation` SET `Ready_For_Settlement` = 'YES' WHERE `Ready_For_Settlement` IN ('1', 'Y');
UPDATE `Reservation` SET `Ready_For_Settlement` = 'NO'  WHERE `Ready_For_Settlement` != 'YES';
UPDATE `Reservation` SET `Settled` = 'YES' WHERE `Settled` IN ('1', 'Y');
UPDATE `Reservation` SET `Settled` = 'NO'  WHERE `Settled` != 'YES';
ALTER TABLE `Reservation`
  MODIFY COLUMN `Ready_For_Settlement` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO',
  MODIFY COLUMN `Settled` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO';
ALTER TABLE `ReservationHist`
  MODIFY COLUMN `Ready_For_Settlement` VARCHAR(3),
  MODIFY COLUMN `Settled` VARCHAR(3);
UPDATE `ReservationHist` SET `Ready_For_Settlement` = 'YES' WHERE `Ready_For_Settlement` IN ('1', 'Y');
UPDATE `ReservationHist` SET `Ready_For_Settlement` = 'NO'  WHERE `Ready_For_Settlement` != 'YES';
UPDATE `ReservationHist` SET `Settled` = 'YES' WHERE `Settled` IN ('1', 'Y');
UPDATE `ReservationHist` SET `Settled` = 'NO'  WHERE `Settled` != 'YES';
ALTER TABLE `ReservationHist` MODIFY COLUMN `Ready_For_Settlement` ENUM('YES', 'NO') NOT NULL;
ALTER TABLE `ReservationHist` MODIFY COLUMN `Settled` ENUM('YES', 'NO') NOT NULL;

ALTER TABLE `Ticket`
  MODIFY COLUMN `CheckedOut` VARCHAR(3),
  MODIFY COLUMN `T_ClikPaid` VARCHAR(3),
  MODIFY COLUMN `AutorenewMode` VARCHAR(3),
  MODIFY COLUMN `T_TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  DROP COLUMN `Opt_Cert`;
UPDATE `Ticket` SET `CheckedOut` = 'YES', `T_TStamp` = `T_TStamp` WHERE `CheckedOut` IN ('1', 'Y');
UPDATE `Ticket` SET `CheckedOut` = 'NO',  `T_TStamp` = `T_TStamp` WHERE `CheckedOut` != 'YES';
UPDATE `Ticket` SET `T_ClikPaid` = 'YES', `T_TStamp` = `T_TStamp` WHERE `T_ClikPaid` IN ('1', 'Y');
UPDATE `Ticket` SET `T_ClikPaid` = 'NO',  `T_TStamp` = `T_TStamp` WHERE `T_ClikPaid` != 'YES';
UPDATE `Ticket` SET `AutorenewMode` = 'YES', `T_TStamp` = `T_TStamp` WHERE `AutorenewMode` IN ('1', 'Y');
UPDATE `Ticket` SET `AutorenewMode` = 'NO',  `T_TStamp` = `T_TStamp` WHERE `AutorenewMode` != 'YES';
ALTER TABLE `Ticket`
  MODIFY COLUMN `CheckedOut` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO',
  MODIFY COLUMN `T_ClikPaid` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO',
  MODIFY COLUMN `AutorenewMode` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO';
ALTER TABLE `TicketHist`
  MODIFY COLUMN `CheckedOut` VARCHAR(3),
  MODIFY COLUMN `T_ClikPaid` VARCHAR(3),
  MODIFY COLUMN `AutorenewMode` VARCHAR(3),
  DROP COLUMN `Opt_Cert`;
UPDATE `TicketHist` SET `CheckedOut` = 'YES', `T_TStamp` = `T_TStamp` WHERE `CheckedOut` IN ('1', 'Y');
UPDATE `TicketHist` SET `CheckedOut` = 'NO',  `T_TStamp` = `T_TStamp`  WHERE `CheckedOut` != 'YES';
UPDATE `TicketHist` SET `T_ClikPaid` = 'YES', `T_TStamp` = `T_TStamp` WHERE `T_ClikPaid` IN ('1', 'Y');
UPDATE `TicketHist` SET `T_ClikPaid` = 'NO',  `T_TStamp` = `T_TStamp`  WHERE `T_ClikPaid` != 'YES';
UPDATE `TicketHist` SET `AutorenewMode` = 'YES', `T_TStamp` = `T_TStamp` WHERE `AutorenewMode` IN ('1', 'Y');
UPDATE `TicketHist` SET `AutorenewMode` = 'NO',  `T_TStamp` = `T_TStamp`  WHERE `AutorenewMode` != 'YES';
ALTER TABLE `TicketHist`
  MODIFY COLUMN `CheckedOut` ENUM('YES', 'NO') NOT NULL,
  MODIFY COLUMN `T_ClikPaid` ENUM('YES', 'NO') NOT NULL,
  MODIFY COLUMN `AutorenewMode` ENUM('YES', 'NO') NOT NULL,
  MODIFY COLUMN `T_TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE `DSM_State`
  MODIFY COLUMN `Published` VARCHAR(3),
  MODIFY COLUMN `Locked` VARCHAR(3);
UPDATE `DSM_State` SET `Published` = 'YES' WHERE `Published` IN ('1', 'Y');
UPDATE `DSM_State` SET `Published` = 'NO'  WHERE `Published` IS NOT NULL && `Published` != 'YES';
UPDATE `DSM_State` SET `Locked` = 'YES' WHERE `Locked` IN ('1', 'Y');
UPDATE `DSM_State` SET `Locked` = 'NO'  WHERE `Locked` IS NOT NULL && `Locked` != 'YES';
ALTER TABLE `DSM_State`
  MODIFY COLUMN `Published` ENUM('YES', 'NO'),
  MODIFY COLUMN `Locked` ENUM('YES', 'NO');

ALTER TABLE `DSM_Transition_Action` MODIFY COLUMN `Execute_After_DSM_Change` VARCHAR(3);
UPDATE `DSM_Transition_Action` SET `Execute_After_DSM_Change` = 'YES' WHERE `Execute_After_DSM_Change` IN ('1', 'Y');
UPDATE `DSM_Transition_Action` SET `Execute_After_DSM_Change` = 'NO'  WHERE `Execute_After_DSM_Change` != 'YES';
ALTER TABLE `DSM_Transition_Action` MODIFY COLUMN `Execute_After_DSM_Change` ENUM('YES', 'NO') NOT NULL;

ALTER TABLE `SchedulerConfig` MODIFY COLUMN `Active` VARCHAR(3);
UPDATE `SchedulerConfig` SET `Active` = 'YES' WHERE `Active` IN ('1', 'Y');
UPDATE `SchedulerConfig` SET `Active` = 'NO'  WHERE `Active` != 'YES';
ALTER TABLE `SchedulerConfig` MODIFY COLUMN `Active` ENUM('YES', 'NO') NOT NULL DEFAULT 'YES';

ALTER TABLE `Product`
  MODIFY COLUMN `P_Default` VARCHAR(3),
  MODIFY COLUMN `P_Reg` VARCHAR(3),
  MODIFY COLUMN `P_Ren` VARCHAR(3),
  MODIFY COLUMN `P_Guest` VARCHAR(3),
  MODIFY COLUMN `P_Active` VARCHAR(3);
UPDATE `Product` SET `P_Default` = 'YES' WHERE `P_Default` IN ('1', 'Y');
UPDATE `Product` SET `P_Default` = 'NO'  WHERE `P_Default` != 'YES';
UPDATE `Product` SET `P_Reg` = 'YES' WHERE `P_Reg` IN ('1', 'Y');
UPDATE `Product` SET `P_Reg` = 'NO'  WHERE `P_Reg` != 'YES';
UPDATE `Product` SET `P_Ren` = 'YES' WHERE `P_Ren` IN ('1', 'Y');
UPDATE `Product` SET `P_Ren` = 'NO'  WHERE `P_Ren` != 'YES';
UPDATE `Product` SET `P_Guest` = 'YES' WHERE `P_Guest` IN ('1', 'Y');
UPDATE `Product` SET `P_Guest` = 'NO'  WHERE `P_Guest` != 'YES';
UPDATE `Product` SET `P_Active` = 'YES' WHERE `P_Active` IN ('1', 'Y');
UPDATE `Product` SET `P_Active` = 'NO'  WHERE `P_Active` != 'YES';
ALTER TABLE `Product`
  MODIFY COLUMN `P_Default` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO',
  MODIFY COLUMN `P_Reg` ENUM('YES', 'NO') NOT NULL DEFAULT 'YES',
  MODIFY COLUMN `P_Ren` ENUM('YES', 'NO') NOT NULL DEFAULT 'YES',
  MODIFY COLUMN `P_Guest` ENUM('YES', 'NO') NOT NULL DEFAULT 'YES',
  MODIFY COLUMN `P_Active` ENUM('YES', 'NO') NOT NULL DEFAULT 'YES';

ALTER TABLE `AccountFlags`
  MODIFY COLUMN `Agreement_Signed` VARCHAR(3),
  MODIFY COLUMN `Ticket_Edit` VARCHAR(3),
  MODIFY COLUMN `TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
UPDATE `AccountFlags` SET `Agreement_Signed` = 'YES' WHERE `Agreement_Signed` IN ('1', 'Y');
UPDATE `AccountFlags` SET `Agreement_Signed` = 'NO'  WHERE `Agreement_Signed` != 'YES';
UPDATE `AccountFlags` SET `Ticket_Edit` = 'YES' WHERE `Ticket_Edit` IN ('1', 'Y');
UPDATE `AccountFlags` SET `Ticket_Edit` = 'NO'  WHERE `Ticket_Edit` != 'YES';
ALTER TABLE `AccountFlags`
  MODIFY COLUMN `Agreement_Signed` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO',
  MODIFY COLUMN `Ticket_Edit` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO',
  MODIFY COLUMN `TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE `AccountFlagsHist`
  MODIFY COLUMN `Agreement_Signed` VARCHAR(3),
  MODIFY COLUMN `Ticket_Edit` VARCHAR(3),
  MODIFY COLUMN `TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
UPDATE `AccountFlagsHist` SET `Agreement_Signed` = 'YES' WHERE `Agreement_Signed` IN ('1', 'Y');
UPDATE `AccountFlagsHist` SET `Agreement_Signed` = 'NO'  WHERE `Agreement_Signed` != 'YES';
UPDATE `AccountFlagsHist` SET `Ticket_Edit` = 'YES' WHERE `Ticket_Edit` IN ('1', 'Y');
UPDATE `AccountFlagsHist` SET `Ticket_Edit` = 'NO'  WHERE `Ticket_Edit` != 'YES';
ALTER TABLE `AccountFlagsHist`
  MODIFY COLUMN `Agreement_Signed` ENUM('YES', 'NO') NOT NULL,
  MODIFY COLUMN `Ticket_Edit` ENUM('YES', 'NO') NOT NULL,
  MODIFY COLUMN `TStamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE `Access` MODIFY COLUMN `use_two_factor_authentication` VARCHAR(3);
UPDATE `Access` SET `use_two_factor_authentication` = 'YES' WHERE `use_two_factor_authentication` IN ('1', 'Y');
UPDATE `Access` SET `use_two_factor_authentication` = 'NO'  WHERE `use_two_factor_authentication` != 'YES';
ALTER TABLE `Access` MODIFY COLUMN `use_two_factor_authentication` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO';
ALTER TABLE `AccessHist` MODIFY COLUMN `use_two_factor_authentication` VARCHAR(3);
UPDATE `AccessHist` SET `use_two_factor_authentication` = 'YES' WHERE `use_two_factor_authentication` IN ('1', 'Y');
UPDATE `AccessHist` SET `use_two_factor_authentication` = 'NO'  WHERE `use_two_factor_authentication` != 'YES';
ALTER TABLE `AccessHist` MODIFY COLUMN `use_two_factor_authentication` ENUM('YES', 'NO') NOT NULL;

ALTER TABLE Domain MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE DomainHist MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `Domain`
  MODIFY COLUMN `D_ClikPaid` VARCHAR(3),
  DROP COLUMN `D_Discount`,
  DROP COLUMN `D_Bill_Status`,
  DROP COLUMN `D_Vat_Status`;
UPDATE `Domain` SET `D_ClikPaid` = 'YES' WHERE `D_ClikPaid` IN ('1', 'Y');
UPDATE `Domain` SET `D_ClikPaid` = 'NO'  WHERE `D_ClikPaid` != 'YES';
ALTER TABLE `Domain` MODIFY COLUMN `D_ClikPaid` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO';
ALTER TABLE `DomainHist`
  MODIFY COLUMN `D_ClikPaid` VARCHAR(3),
  DROP COLUMN `D_Discount`,
  DROP COLUMN `D_Bill_Status`,
  DROP COLUMN `D_Vat_Status`;
UPDATE `DomainHist` SET `D_ClikPaid` = 'YES' WHERE `D_ClikPaid` IN ('1', 'Y');
UPDATE `DomainHist` SET `D_ClikPaid` = 'NO'  WHERE `D_ClikPaid` != 'YES';
ALTER TABLE `DomainHist` MODIFY COLUMN `D_ClikPaid` ENUM('YES', 'NO') NOT NULL;
ALTER TABLE Domain
    MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE DomainHist
    MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE `NicHandle` MODIFY COLUMN `NH_Exported` VARCHAR(3);
ALTER TABLE `NicHandle` MODIFY COLUMN `NH_BillC_Ind` VARCHAR(3);
ALTER TABLE `NicHandle` MODIFY COLUMN `NH_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE `NicHandle` SET `NH_Exported` = 'YES' WHERE `NH_Exported` IN ('1', 'Y');
UPDATE `NicHandle` SET `NH_Exported` = 'NO'  WHERE `NH_Exported` != 'YES';
UPDATE `NicHandle` SET `NH_BillC_Ind` = 'YES' WHERE `NH_BillC_Ind` = 'Y';
UPDATE `NicHandle` SET `NH_BillC_Ind` = 'NO'  WHERE `NH_BillC_Ind` != 'YES';
ALTER TABLE `NicHandle` MODIFY COLUMN `NH_Exported` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO';
ALTER TABLE `NicHandle` MODIFY COLUMN `NH_BillC_Ind` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO';
ALTER TABLE `NicHandle` MODIFY COLUMN `NH_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE `NicHandleHist` MODIFY COLUMN `NH_BillC_Ind` VARCHAR(3);
ALTER TABLE `NicHandleHist` MODIFY COLUMN `NH_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE `NicHandleHist` SET `NH_BillC_Ind` = 'YES' WHERE `NH_BillC_Ind` = 'Y';
UPDATE `NicHandleHist` SET `NH_BillC_Ind` = 'NO'  WHERE `NH_BillC_Ind` != 'YES';
ALTER TABLE `NicHandleHist` MODIFY COLUMN `NH_BillC_Ind` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO';
ALTER TABLE `NicHandleHist` MODIFY COLUMN `NH_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE `Invoice` MODIFY COLUMN `Completed` VARCHAR(3);
UPDATE `Invoice` SET `Completed` = 'YES' WHERE `Completed` IN ('1', 'Y');
UPDATE `Invoice` SET `Completed` = 'NO'  WHERE `Completed` != 'YES';
ALTER TABLE `Invoice` MODIFY COLUMN `Completed` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO';

ALTER TABLE `ResetPass` MODIFY COLUMN `valid` VARCHAR(3);
UPDATE `ResetPass` SET `valid` = 'YES' WHERE `valid` IN ('1', 'Y');
UPDATE `ResetPass` SET `valid` = 'NO'  WHERE `valid` != 'YES';
ALTER TABLE `ResetPass` MODIFY COLUMN `valid` ENUM('YES', 'NO') NOT NULL DEFAULT 'YES';

ALTER TABLE `Vat` MODIFY COLUMN `Valid` VARCHAR(3);
UPDATE `Vat` SET `Valid` = 'YES' WHERE `Valid` IN ('1', 'Y');
UPDATE `Vat` SET `Valid` = 'NO'  WHERE `Valid` != 'YES';
ALTER TABLE `Vat` MODIFY COLUMN `Valid` ENUM('YES', 'NO') NOT NULL DEFAULT 'YES';

ALTER TABLE `Deposit` DROP COLUMN `Notification_Sent`;
ALTER TABLE `DepositHist` DROP COLUMN `Notification_Sent`;

-- CRS-962

ALTER TABLE `Countries` DROP PRIMARY KEY;
ALTER TABLE `Countries` CHANGE COLUMN `CountryCode` `Id` INT(3) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;
ALTER TABLE `Countries` CHANGE COLUMN `Country` `Name` VARCHAR(100) NOT NULL;
ALTER TABLE `Countries` ADD UNIQUE INDEX (`Name`);
ALTER TABLE `Countries` DROP KEY `CountryCode`;
ALTER TABLE `Countries` DROP KEY `CountryCode_2`;
ALTER TABLE `Counties` DROP PRIMARY KEY;
ALTER TABLE `Counties` ADD COLUMN `Id` INT(5) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;
ALTER TABLE `Counties` CHANGE COLUMN `CountryCode` `Country_Id` INT(3);
ALTER TABLE `Counties` CHANGE COLUMN `County` `Name` VARCHAR(100) NOT NULL;
ALTER TABLE `Counties` ADD UNIQUE INDEX (`Name`);
ALTER TABLE `Counties` DROP KEY `CountryCode`;
ALTER TABLE `Counties` DROP KEY `CountryCode_2`;

UPDATE `Counties` SET `Id` = 0 WHERE `Name` = 'N/A';

ALTER TABLE `NicHandle` MODIFY COLUMN `NH_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `NicHandleHist` MODIFY COLUMN `NH_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE `NicHandle` SET `NH_Country` = NULL WHERE `NH_Country` NOT IN (SELECT `Name` FROM `Countries`);
ALTER TABLE `NicHandle` ADD COLUMN `Country_Id` INT(5) AFTER `NH_Country`;
UPDATE `NicHandle` NH SET NH.`Country_Id` = (SELECT `Id` FROM `Countries` WHERE `Name` = NH.`NH_Country`);
ALTER TABLE `NicHandle` DROP COLUMN `NH_Country`;
UPDATE `NicHandle` NH SET NH.`NH_County` = 'N/A' WHERE NH.`NH_County` NOT IN (SELECT `Name` FROM `Counties` WHERE `Country_Id` = NH.`Country_Id`);
ALTER TABLE `NicHandle` ADD COLUMN `County_Id` INT(5) AFTER `NH_County`;
UPDATE `NicHandle` NH SET NH.`County_Id` = (SELECT `Id` FROM `Counties` WHERE `Name` = NH.`NH_County`);
ALTER TABLE `NicHandle` DROP COLUMN `NH_County`;
UPDATE `NicHandleHist` SET `NH_Country` = NULL WHERE `NH_Country` NOT IN (SELECT `Name` FROM `Countries`);
ALTER TABLE `NicHandleHist` ADD COLUMN `Country_Id` INT(5) AFTER `NH_Country`;
UPDATE `NicHandleHist` NH SET NH.`Country_Id` = (SELECT `Id` FROM `Countries` WHERE `Name` = NH.`NH_Country`);
ALTER TABLE `NicHandleHist` DROP COLUMN `NH_Country`;
UPDATE `NicHandleHist` NH SET NH.`NH_County` = 'N/A' WHERE NH.`NH_County` NOT IN (SELECT `Name` FROM `Counties` WHERE `Country_Id` = NH.`Country_Id`);
ALTER TABLE `NicHandleHist` ADD COLUMN `County_Id` INT(5) AFTER `NH_County`;
UPDATE `NicHandleHist` NH SET NH.`County_Id` = (SELECT `Id` FROM `Counties` WHERE `Name` = NH.`NH_County`);
ALTER TABLE `NicHandleHist` DROP COLUMN `NH_County`;
ALTER TABLE `NicHandle` MODIFY COLUMN `NH_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE `NicHandleHist` MODIFY COLUMN `NH_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

UPDATE `Invoice` SET `Country` = NULL WHERE `Country` NOT IN (SELECT `Name` FROM `Countries`);
ALTER TABLE `Invoice` ADD COLUMN `Country_Id` INT(5) AFTER `Country`;
UPDATE `Invoice` I SET I.`Country_Id` = (SELECT `Id` FROM `Countries` WHERE `Name` = I.`Country`);
ALTER TABLE `Invoice` DROP COLUMN `Country`;
UPDATE `Invoice` I SET I.`County` = NULL WHERE I.`County` NOT IN (SELECT `Name` FROM `Counties` WHERE `Country_Id` = I.`Country_Id` OR `Name` = 'N/A');
ALTER TABLE `Invoice` ADD COLUMN `County_Id` INT(5) AFTER `County`;
UPDATE `Invoice` I SET I.`County_Id` = (SELECT `Id` FROM `Counties` WHERE `Name` = I.`County`);
ALTER TABLE `Invoice` DROP COLUMN `County`;

-- CRS-987

ALTER TABLE `Invoice` MODIFY COLUMN `Total_Cost` DECIMAL(10, 2);
ALTER TABLE `Invoice` MODIFY COLUMN `Total_Net_Amount` DECIMAL(10, 2);
ALTER TABLE `Invoice` MODIFY COLUMN `Total_Vat_Amount` DECIMAL(10, 2);
UPDATE `Invoice` SET `Total_Cost` = `Total_Cost` * 0.01, `Total_Net_Amount` = `Total_Net_Amount` * 0.01, `Total_Vat_Amount` = `Total_Vat_Amount` * 0.01;

ALTER TABLE `Transaction` MODIFY COLUMN `Total_Cost` DECIMAL(10, 2);
ALTER TABLE `Transaction` MODIFY COLUMN `Total_Net_Amount` DECIMAL(10, 2);
ALTER TABLE `Transaction` MODIFY COLUMN `Total_Vat_Amount` DECIMAL(10, 2);
UPDATE `Transaction` SET `Total_Cost` = `Total_Cost` * 0.01, `Total_Net_Amount` = `Total_Net_Amount` * 0.01, `Total_Vat_Amount` = `Total_Vat_Amount` * 0.01;

ALTER TABLE `TransactionHist` MODIFY COLUMN `Total_Cost` DECIMAL(10, 2);
ALTER TABLE `TransactionHist` MODIFY COLUMN `Total_Net_Amount` DECIMAL(10, 2);
ALTER TABLE `TransactionHist` MODIFY COLUMN `Total_Vat_Amount` DECIMAL(10, 2);
UPDATE `TransactionHist` SET `Total_Cost` = `Total_Cost` * 0.01, `Total_Net_Amount` = `Total_Net_Amount` * 0.01, `Total_Vat_Amount` = `Total_Vat_Amount` * 0.01;

ALTER TABLE `Vat` MODIFY COLUMN `Rate` DECIMAL(10, 3);

-- CRS-999

ALTER TABLE `Email`
  MODIFY COLUMN `E_Name` VARCHAR(255),
  MODIFY COLUMN `E_Subject` VARCHAR(255),
  MODIFY COLUMN `E_To` VARCHAR(255),
  MODIFY COLUMN `E_CC` VARCHAR(255),
  MODIFY COLUMN `E_BCC` VARCHAR(255),
  MODIFY COLUMN `E_From` VARCHAR(255) NOT NULL,
  MODIFY COLUMN `E_To_Internal` VARCHAR(255),
  MODIFY COLUMN `E_CC_Internal` VARCHAR(255),
  MODIFY COLUMN `E_BCC_Internal` VARCHAR(255),
  MODIFY COLUMN `E_Send_Reason` VARCHAR(255),
  MODIFY COLUMN `E_Non_Suppressible_Reason` VARCHAR(255);

ALTER TABLE `EmailHist`
  MODIFY COLUMN `E_Name` VARCHAR(255),
  MODIFY COLUMN `E_Subject` VARCHAR(255),
  MODIFY COLUMN `E_To` VARCHAR(255),
  MODIFY COLUMN `E_CC` VARCHAR(255),
  MODIFY COLUMN `E_BCC` VARCHAR(255),
  MODIFY COLUMN `E_From` VARCHAR(255) NOT NULL,
  MODIFY COLUMN `E_To_Internal` VARCHAR(255),
  MODIFY COLUMN `E_CC_Internal` VARCHAR(255),
  MODIFY COLUMN `E_BCC_Internal` VARCHAR(255),
  MODIFY COLUMN `E_Send_Reason` VARCHAR(255),
  MODIFY COLUMN `E_Non_Suppressible_Reason` VARCHAR(255);

-- CRS-1025

ALTER TABLE `login_attempts` RENAME TO `LoginAttempts`;
ALTER TABLE `nicHandleSeq` RENAME TO `NicHandleSeq`;
ALTER TABLE `vat_category` RENAME TO `VatCategory`;

-- CRS-1021

INSERT INTO `App_Config` (`Cfg_Key`, `Cfg_Type`, `Cfg_Value`) VALUES ('reset_password_cleanup_period', 'INT', '30');

INSERT INTO `NicHandle` VALUES
('RESETPCLEAN', 'ResetPassCleanupJob', 2, 'IEDR', 'NHAddress', 14, 121, 'asd@iedr.ie', 'Active', CURDATE(), CURDATE(), NOW(), 'NO', NULL, 'RESETPCLEAN', NULL, NULL, 'YES');

INSERT INTO `Access`(`Nic_Handle`, `NH_Level`) VALUES ('RESETPCLEAN', 64);

INSERT INTO `Telecom`(`Nic_Handle`, `Phone`, `Type`) VALUES ('RESETPCLEAN', '+000000000', 'Phone');

INSERT INTO `SchedulerConfig` (`name`, `schedule`, `Active`, `RunningNicHandle`) VALUES
('ResetPassCleanup', '20 00 * * *', 'YES', 'RESETPCLEAN');

-- CRS-1049

INSERT INTO `App_Config` (`Cfg_Key`, `Cfg_Type`, `Cfg_Value`) VALUES ('login_attempt_cleanup_period', 'INT', '30');

INSERT INTO `NicHandle` VALUES
('LOGATTPCLEAN', 'LoginAttemptCleanupJob', 2, 'IEDR', 'NHAddress', 14, 121, 'asd@iedr.ie', 'Active', CURDATE(), CURDATE(), NOW(), 'NO', NULL, 'LOGATTPCLEAN', NULL, NULL, 'YES');

INSERT INTO `Access`(`Nic_Handle`, `NH_Level`) VALUES ('LOGATTPCLEAN', 64);

INSERT INTO `Telecom`(`Nic_Handle`, `Phone`, `Type`) VALUES ('LOGATTPCLEAN', '+000000000', 'Phone');

INSERT INTO `SchedulerConfig` (`name`, `schedule`, `Active`, `RunningNicHandle`) VALUES
('LoginAttemptCleanup', '30 00 * * *', 'YES', 'LOGATTPCLEAN');

-- CRS-988

-- Move Agreement_Signed, Ticket_Edit columns from AccountFlags/AccountFlagsHist to
-- Account/AccountHist.
ALTER TABLE Account
ADD COLUMN Agreement_Signed ENUM('YES', 'NO') NOT NULL DEFAULT 'NO' AFTER `A_Reg_Dt`,
ADD COLUMN Ticket_Edit ENUM('YES', 'NO') NOT NULL DEFAULT 'NO' AFTER `Agreement_Signed`;

ALTER TABLE AccountHist
ADD COLUMN Agreement_Signed ENUM('YES', 'NO') NOT NULL DEFAULT 'NO' AFTER `A_Reg_Dt`,
ADD COLUMN Ticket_Edit ENUM('YES', 'NO') NOT NULL DEFAULT 'NO' AFTER `Agreement_Signed`;

ALTER TABLE Account MODIFY COLUMN A_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE AccountHist MODIFY COLUMN A_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

UPDATE Account a INNER JOIN AccountFlags af ON a.A_Number = af.A_Number
SET a.Agreement_Signed = af.Agreement_Signed, a.Ticket_Edit = af.Ticket_Edit;

UPDATE AccountHist ah INNER JOIN AccountFlagsHist afh ON ah.Chng_ID = afh.Chng_ID
SET ah.Agreement_Signed = afh.Agreement_Signed, ah.Ticket_Edit = afh.Ticket_Edit;

ALTER TABLE Account
  MODIFY COLUMN A_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE AccountHist
  MODIFY COLUMN A_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

DROP TABLE AccountFlagsHist, AccountFlags;

-- CRS-989

ALTER TABLE Telecom ADD COLUMN `Order` int NOT NULL;
ALTER TABLE TelecomHist ADD COLUMN `Order` int NOT NULL;

CREATE TEMPORARY TABLE orderedTelecomHist AS (SELECT
  Chng_ID,
  Id,
  @order := IF(@chngId = Chng_ID AND @type = Type, @order + 1, 0) AS `Order`,
  @chngId := Chng_ID,
  @type := Type
  FROM
    TelecomHist,
    (SELECT @order := -1) AS o,
    (SELECT @chngId := NULL) AS chngId,
    (SELECT @type := NULL) AS type
  ORDER BY Chng_ID, Type, Id);
UPDATE TelecomHist TH1
  LEFT JOIN orderedTelecomHist TH2 ON TH1.Chng_ID = TH2.Chng_ID and TH1.Id = TH2.Id
  SET TH1.Order = TH2.Order;

CREATE TEMPORARY TABLE orderedTelecom AS (SELECT
  Id,
  @order := IF(@nh = Nic_Handle AND @type = Type, @order + 1, 0) AS `Order`,
  @nh := Nic_Handle,
  @type := Type
  FROM
    Telecom,
    (SELECT @order := -1) AS o,
    (SELECT @nh := NULL) AS nh,
    (SELECT @type := NULL) AS type
  ORDER BY Nic_Handle, Type, Id);
UPDATE Telecom T1
  LEFT JOIN orderedTelecom T2 ON T1.Id = T2.Id
  SET T1.Order = T2.Order;

DROP TABLE orderedTelecomHist;
DROP TABLE orderedTelecom;

ALTER TABLE Telecom
  DROP COLUMN Id,
  ADD PRIMARY KEY (`Nic_Handle`, `Type`, `Order`);
ALTER TABLE TelecomHist
  DROP COLUMN Id,
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`Chng_ID`, `Type`, `Order`);

-- CRS-990

CREATE TEMPORARY TABLE affectedTransitionActions AS
SELECT TA1.Id FROM DSM_Transition_Action TA1
    LEFT JOIN DSM_Transition_Action TA2 ON (TA2.Transition_Id = TA1.Transition_Id AND TA2.Action_Id = 9)
    WHERE TA1.`Order` > TA2.`Order`;
DELETE FROM DSM_Transition_Action WHERE Action_Id = 9;
UPDATE DSM_Transition_Action TA
    JOIN affectedTransitionActions aId ON aId.Id = TA.Id
    SET `Order` = `Order` - 1;
DELETE FROM DSM_Action WHERE Id = 9;

-- CRS-1072

ALTER TABLE Access DROP COLUMN Answer;
ALTER TABLE AccessHist DROP COLUMN Answer;
ALTER TABLE Access ADD COLUMN Internal ENUM('YES', 'NO') NOT NULL DEFAULT 'NO' AFTER IP_Address;
ALTER TABLE AccessHist ADD COLUMN Internal ENUM('YES', 'NO') NOT NULL DEFAULT 'NO' AFTER IP_Address;
UPDATE Access SET Internal = 'YES' WHERE IP_Address IS NOT NULL;
UPDATE AccessHist SET Internal = 'YES' WHERE IP_Address IS NOT NULL;
ALTER TABLE Access DROP COLUMN IP_Address;
ALTER TABLE AccessHist DROP COLUMN IP_Address;
ALTER TABLE AccessHist ADD COLUMN password_changed date AFTER Salt;
UPDATE AccessHist SET password_changed = Chng_Dt;

-- CRS-860
--
-- Migration scripts for each table will be divided into two parts. First will cover fixing
-- historical data. The main task is to insert current data into historical tables, that currently
-- don't hold the present data. Another is to fix Chng_Dt and Chng_NH columns, which due to the
-- current order of update/write operations are not correct. There are other issues to fix, like
-- empty Reg_Dt, which might be specific to each table.
-- Second part will cover modifying other historical tables, that use IDs of historical objects from
-- the currently revised historical table. For example if a historical domain has an account number,
-- we want it to hold the Chng_ID of this account instead. This way we can easily obtain information
-- on how this account looked like, when the change to the domain was made.
--
-- Notice: When writing a script for the real migration, this can be used, but it isn't perfect. For
-- example, this assumes there are no current values of accounts in AccountHist, but for some rows
-- it looks like there are. It also assumes that A_TStamp is reliable as the timeline for an
-- account, but for some rows it's duplicated (which is perfectly possible, but also might be an
-- error). These should probably be taken into account during the real migration.
-- There might be other corner cases.

--
-- PART 1
--

-- CRS-988

-- We'll be changing all rows in Account and AccountHist. We don't want to update timestamps.
ALTER TABLE Account MODIFY COLUMN A_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE AccountHist MODIFY COLUMN A_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Insert current value of Account to AccountHist. For now set Chng_NH as null and Chng_Dt as NOW().
INSERT INTO
  AccountHist (A_Number, A_Name, Billing_NH, Web_Address, A_Status, A_Status_Dt, A_Reg_Dt,
    Agreement_Signed, Ticket_Edit, A_TStamp, A_Remarks, Chng_Dt, Chng_NH)
SELECT A_Number, A_Name, Billing_NH, Web_Address, A_Status, A_Status_Dt, A_Reg_Dt, Agreement_Signed,
  Ticket_Edit, A_TStamp, A_Remarks, NOW(), NULL
  FROM Account ORDER BY A_Number;

-- Add new column to Account table which will point to the newly created historical entry.
SET @firstNewChangeId = LAST_INSERT_ID() - 1;
ALTER TABLE Account ADD COLUMN Chng_ID int(10) unsigned;
UPDATE Account SET Chng_ID = @firstNewChangeId := @firstNewChangeId + 1 ORDER BY A_Number;

-- Move Chng_NH column "1 up" in AccountHist for each row. Currently Chng_NH in AccountHist is
-- invalid. If someone made a change to an account the current value from Account was first moved to
-- history, before applying the change to Account table. This resulted in the person authoring the
-- previous change, not the current one. Moving Chng_NH column "1 up" will fix this. For the oldest
-- record use "IH4-IEDR".
-- The same holds for Chng_Dt column, which currently is the date of the next change. It also hase
-- to be moved "1 up". For the oldest record use the least value of A_Status_Dt, A_Reg_Dt, A_TStamp
-- and Chng_Dt.

CREATE TEMPORARY TABLE accountsPreviousChng AS
SELECT
  AH1.Chng_ID,
  (SELECT AH2.Chng_ID FROM AccountHist AH2
    WHERE AH2.A_Number = AH1.A_Number
      AND (AH2.Chng_Dt < AH1.Chng_Dt OR (AH2.Chng_Dt = AH1.Chng_Dt AND AH2.Chng_ID < AH1.Chng_ID))
      AND AH2.Chng_ID != AH1.Chng_ID
    ORDER BY AH2.Chng_Dt DESC, AH2.Chng_ID DESC LIMIT 1) as prevChngId
  FROM AccountHist AH1;

UPDATE AccountHist AH1
  LEFT JOIN accountsPreviousChng APC ON AH1.Chng_ID = APC.Chng_ID
  LEFT JOIN AccountHist AH2 ON AH2.Chng_ID = APC.prevChngId
  SET AH1.Chng_NH = AH2.Chng_NH, AH1.Chng_Dt = AH2.Chng_Dt
  WHERE APC.prevChngId IS NOT NULL;
UPDATE AccountHist AH1 LEFT JOIN accountsPreviousChng APC ON AH1.Chng_ID = APC.Chng_ID
  SET AH1.Chng_NH = 'IH4-IEDR',
    AH1.Chng_Dt = LEAST(
      IFNULL(AH1.A_Status_Dt, NOW()),
      IFNULL(AH1.A_Reg_Dt, NOW()),
      IFNULL(AH1.A_TStamp, NOW()),
      AH1.Chng_Dt)
  WHERE APC.prevChngId IS NULL;

DROP TABLE accountsPreviousChng;

ALTER TABLE Account
  MODIFY COLUMN A_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE AccountHist
  MODIFY COLUMN A_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- CRS-989

ALTER TABLE NicHandle MODIFY COLUMN NH_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE NicHandleHist MODIFY COLUMN NH_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- It's hard to fill this column properly. All records existing before introducing this column in
-- NicHandle table should have 'YES' value, because the procedure of creating a nic handle included
-- its export at that time. Later on, we would have to check if they had any registration/transfer
-- ticket. All in all, it's possible, but probably not worth it. Let's leave it at 'NO' for all
-- existing records.
ALTER TABLE NicHandleHist ADD COLUMN NH_Exported ENUM('YES', 'NO') NOT NULL DEFAULT 'NO';

-- Change order of columns in historical table. Move Chng_NH and Chng_Dt to the end.
ALTER TABLE NicHandleHist
  MODIFY COLUMN Chng_NH VARCHAR(12) COLLATE utf8_irish_accent_ci NOT NULL DEFAULT '' AFTER NH_Exported,
  MODIFY COLUMN Chng_Dt TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' AFTER Chng_NH;

-- Insert current value of NicHandle to NicHandleHist. Set Chng_NH as IH4-IEDR and Chng_Dt as NOW().
INSERT INTO
  NicHandleHist (Nic_Handle, NH_Name, A_Number, Co_Name, NH_Address, County_Id, Country_Id,
    NH_Email, NH_Status, NH_Status_Dt, NH_Reg_Dt, NH_TStamp, NH_BillC_Ind, NH_Remark, NH_Creator,
    Vat_Category, Vat_Reg_Id, NH_Exported, Chng_NH, Chng_Dt)
  SELECT NH.Nic_Handle, NH.NH_Name, NH.A_Number, NH.Co_Name, NH.NH_Address, NH.County_Id,
    NH.Country_Id, NH.NH_Email, NH.NH_Status, NH.NH_Status_Dt, NH.NH_Reg_Dt, NH.NH_TStamp,
    NH.NH_BillC_Ind, NH.NH_Remark, NH.NH_Creator, NH.Vat_Category, NH.Vat_Reg_Id, NH.NH_Exported,
    'IH4-IEDR', NOW()
  FROM NicHandle NH ORDER BY NH.Nic_Handle;

-- Add new column to NicHandle table which will point to the newly created historical entry.
SET @firstNewChangeId = LAST_INSERT_ID() - 1;
ALTER TABLE NicHandle ADD COLUMN Chng_ID int(10) unsigned;
UPDATE NicHandle SET Chng_ID = @firstNewChangeId := @firstNewChangeId + 1 ORDER BY Nic_Handle;

-- Insert current values of Telecom to TelecomHist.
INSERT INTO
  TelecomHist (Chng_ID, Nic_Handle, Phone, Type, `Order`)
  SELECT NH.Chng_ID, T.Nic_Handle, T.Phone, T.Type, T.`Order`
  FROM Telecom T LEFT JOIN NicHandle NH ON NH.Nic_Handle = T.Nic_Handle;

-- Multiple nic handles have NH_Reg_Dt = 0000-00-00. Now that we have the current value in the
-- historical table we can find the minimal value of NH_TStamp and NH_Status_Dt for each nic handle
-- and use the earlier one to populate NH_Reg_Dt. Then we have to update the main table with this
-- value.
UPDATE NicHandleHist NHH1
  LEFT JOIN
    (SELECT NHH.Nic_Handle, MIN(NHH.Chng_Dt) AS minChngDt, MIN(NHH.NH_Status_Dt) AS minStatusDt
      FROM NicHandleHist NHH WHERE NHH.NH_Reg_Dt = '0000-00-00' GROUP BY NHH.Nic_Handle) AS NHH2
    ON NHH1.Nic_Handle LIKE NHH2.Nic_Handle
    SET NHH1.NH_Reg_Dt = LEAST(NHH2.minChngDt, NHH2.minStatusDt)
  WHERE NHH2.Nic_Handle IS NOT NULL;
UPDATE NicHandle NH
  LEFT JOIN NicHandleHist NHH ON NHH.Chng_ID = NH.Chng_ID
  SET NH.NH_Reg_Dt = NHH.NH_Reg_Dt
  WHERE NH.NH_Reg_Dt = '0000-00-00';

-- Similarly to Account, move Chng_NH and Chng_Dt columns "1 up".
CREATE TEMPORARY TABLE nhPreviousChng AS
SELECT
  NHH1.Chng_ID,
  (SELECT NHH2.Chng_ID FROM NicHandleHist NHH2
    WHERE NHH2.Nic_Handle LIKE NHH1.Nic_Handle
      AND (NHH2.Chng_Dt < NHH1.Chng_Dt OR (NHH2.Chng_Dt = NHH1.Chng_Dt AND NHH2.Chng_ID < NHH1.Chng_ID))
      AND NHH2.Chng_ID != NHH1.Chng_ID
    ORDER BY NHH2.Chng_Dt DESC, NHH2.Chng_ID DESC LIMIT 1) as prevChngId
  FROM NicHandleHist NHH1;

UPDATE NicHandleHist NHH1
  LEFT JOIN nhPreviousChng NHPC ON NHH1.Chng_ID = NHPC.Chng_ID
  LEFT JOIN NicHandleHist NHH2 ON NHH2.Chng_ID = NHPC.prevChngId
  SET NHH1.Chng_NH = NHH2.Chng_NH, NHH1.Chng_Dt = NHH2.Chng_Dt
  WHERE NHPC.prevChngId IS NOT NULL;
UPDATE NicHandleHist NHH1 LEFT JOIN nhPreviousChng NHPC ON NHH1.Chng_ID = NHPC.Chng_ID
  SET NHH1.Chng_NH = 'IH4-IEDR',
    NHH1.Chng_Dt = LEAST(
      IFNULL(NHH1.NH_Status_Dt, NOW()),
      IFNULL(NHH1.NH_Reg_Dt, NOW()),
      IFNULL(NHH1.NH_TStamp, NOW()),
      NHH1.Chng_Dt)
  WHERE NHPC.prevChngId IS NULL;

DROP TABLE nhPreviousChng;

ALTER TABLE NicHandle
  MODIFY COLUMN NH_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE NicHandleHist
  MODIFY COLUMN NH_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- CRS-990

ALTER TABLE Domain MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE DomainHist MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Insert current value of Domain to DomainHist. Set Chng_NH as IH4-IEDR and Chng_Dt as NOW().
INSERT INTO
  DomainHist (D_Name, D_Holder, D_Class, D_Category, A_Number, D_Reg_Dt, D_Ren_Dt,
    D_TStamp, D_Remark, D_ClikPaid, DSM_State, D_Susp_Dt, D_Del_Dt, D_Transfer_Dt, D_Authcode,
    D_Authc_Exp_Dt, D_Authc_Portal_Cnt, D_Locking_Dt, D_LockingRenewal_Dt, Chng_NH, Chng_Dt)
  SELECT D.D_Name, D.D_Holder, D.D_Class, D.D_Category, D.A_Number, D.D_Reg_Dt, D.D_Ren_Dt,
    D.D_TStamp, D.D_Remark, D.D_ClikPaid, D.DSM_State, D.D_Susp_Dt, D.D_Del_Dt, D.D_Transfer_Dt,
    D.D_Authcode, D.D_Authc_Exp_Dt, D.D_Authc_Portal_Cnt, D.D_Locking_Dt, D.D_LockingRenewal_Dt,
    'IH4-IEDR', NOW()
  FROM Domain D ORDER BY D.D_Name;

-- Add new column to Domain table which will point to the newly created historical entry.
SET @firstNewChangeId = LAST_INSERT_ID() - 1;
ALTER TABLE Domain ADD COLUMN Chng_ID int(10) unsigned;
UPDATE Domain SET Chng_ID = @firstNewChangeId := @firstNewChangeId + 1 ORDER BY D_Name;

-- Insert current values of DNS to DNSHist.
INSERT INTO
  DNSHist (Chng_ID, D_Name, DNS_Name, DNS_IPv4_Addr, DNS_IPv6_Addr, DNS_Order)
  SELECT D.Chng_ID, DNS.D_Name, DNS.DNS_Name, DNS.DNS_IPv4_Addr, DNS.DNS_IPv6_Addr, DNS.DNS_Order
  FROM DNS DNS LEFT JOIN Domain D ON D.D_Name = DNS.D_Name;

-- Insert current values of Contact to ContactHist.
INSERT INTO
  ContactHist (Chng_ID, D_Name, Contact_NH, Type)
  SELECT D.Chng_ID, C.D_Name, C.Contact_NH, C.Type
  FROM Contact C LEFT JOIN Domain D ON D.D_Name = C.D_Name;

-- Move Chng_NH and Chng_Dt columns "1 up".
CREATE TEMPORARY TABLE dPreviousChng AS
SELECT
  DH1.Chng_ID,
  (SELECT DH2.Chng_ID FROM DomainHist DH2
    WHERE DH2.D_Name LIKE DH1.D_Name
      AND (DH2.Chng_Dt < DH1.Chng_Dt OR (DH2.Chng_Dt = DH1.Chng_Dt AND DH2.Chng_ID < DH1.Chng_ID))
      AND DH2.Chng_ID != DH1.Chng_ID
    ORDER BY DH2.Chng_Dt DESC, DH2.Chng_ID DESC LIMIT 1) as prevChngId
  FROM DomainHist DH1;

UPDATE DomainHist DH1
  LEFT JOIN dPreviousChng DPC ON DH1.Chng_ID = DPC.Chng_ID
  LEFT JOIN DomainHist DH2 ON DH2.Chng_ID = DPC.prevChngId
  SET DH1.Chng_NH = DH2.Chng_NH, DH1.Chng_Dt = DH2.Chng_Dt
  WHERE DPC.prevChngId IS NOT NULL;
UPDATE DomainHist DH1 LEFT JOIN dPreviousChng DPC ON DH1.Chng_ID = DPC.Chng_ID
  SET DH1.Chng_NH = 'IH4-IEDR',
    DH1.Chng_Dt = LEAST(
      IFNULL(DH1.D_Reg_Dt, NOW()),
      IFNULL(DH1.D_TStamp, NOW()),
      DH1.Chng_Dt)
  WHERE DPC.prevChngId IS NULL;

DROP TABLE dPreviousChng;

ALTER TABLE DomainHist
  MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE DomainHist
  MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- CRS-991

ALTER TABLE Ticket MODIFY COLUMN T_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE TicketHist MODIFY COLUMN T_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Insert current value of Ticket to TicketHist, but only for tickets without any history. If
-- history is present for a ticket, it includes the current value. For created entries, use
-- Creator_NH as Chng_NH and T_TStamp as Chng_Dt.
INSERT INTO
  TicketHist (T_Number, T_Type, D_Name, DN_Fail_Cd, D_Holder, DH_Fail_Cd, A_Number, AC_Fail_Cd,
    T_Class, T_Category, T_Remark, Admin_NH1, ANH1_Fail_Cd, Admin_NH2, ANH2_Fail_Cd, Tech_NH,
    TNH_Fail_Cd, Bill_NH, BNH_Fail_Cd, Creator_NH, Admin_Status, Ad_StatusDt, Tech_Status,
    T_Status_Dt, CheckedOut, CheckedOutTo, T_Reg_Dt, T_Ren_Dt, T_TStamp, H_Remark, Chng_Dt, Chng_NH,
    T_Class_Fail_Cd, T_Category_Fail_Cd, T_Created_TS, T_ClikPaid, Period, CharityCode,
    AutorenewMode, Financial_Status, F_Status_Dt, Customer_Status, C_Status_Dt)
  SELECT T.T_Number, T.T_Type, T.D_Name, T.DN_Fail_Cd, T.D_Holder, T.DH_Fail_Cd, T.A_Number,
    T.AC_Fail_Cd, T.T_Class, T.T_Category, T.T_Remark, T.Admin_NH1, T.ANH1_Fail_Cd, T.Admin_NH2,
    T.ANH2_Fail_Cd, T.Tech_NH, T.TNH_Fail_Cd, T.Bill_NH, T.BNH_Fail_Cd, T.Creator_NH,
    T.Admin_Status, T.Ad_StatusDt, T.Tech_Status, T.T_Status_Dt, T.CheckedOut, T.CheckedOutTo,
    T.T_Reg_Dt, T.T_Ren_Dt, T.T_TStamp, T.H_Remark, T.T_TStamp, T.Creator_NH, T.T_Class_Fail_Cd,
    T.T_Category_Fail_Cd, T.T_Created_TS, T.T_ClikPaid, T.Period, T.CharityCode, T.AutorenewMode,
    T.Financial_Status, T.F_Status_Dt, T.Customer_Status, T.C_Status_Dt
  FROM Ticket T
  LEFT JOIN TicketHist TH ON TH.T_Number = T.T_Number
  WHERE TH.T_Number IS NULL
  ORDER BY T.T_Number;

-- Connect tickets with their copies in the historical table.
CREATE TEMPORARY TABLE currentTicketHistory AS
SELECT T.T_Number,
  (SELECT Chng_ID FROM TicketHist
    WHERE T_Number = T.T_Number
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) AS Chng_ID
  FROM Ticket T;

ALTER TABLE Ticket ADD COLUMN Chng_ID int(10) unsigned;

UPDATE Ticket T
  LEFT JOIN currentTicketHistory cTH ON cTH.T_Number = T.T_Number
  SET T.Chng_ID = cTH.Chng_ID;

DROP TABLE currentTicketHistory;

-- Insert current values from TicketNameserver to TicketNameserverHist if they are missing.
INSERT INTO
  TicketNameserverHist (Chng_ID, T_Number, TN_Name, TN_Name_Fail_Cd, TN_IPv4, TN_IPv4_Fail_Cd,
    TN_IPv6, TN_IPv6_Fail_Cd, TN_Order, TN_TS)
  SELECT T.Chng_ID, TN.T_Number, TN.TN_Name, TN.TN_Name_Fail_Cd, TN.TN_IPv4, TN.TN_IPv4_Fail_Cd,
    TN.TN_IPv6, TN.TN_IPv6_Fail_Cd, TN.TN_Order, TN.TN_TS
  FROM TicketNameserver TN
    LEFT JOIN Ticket T ON T.T_Number = TN.T_Number
    LEFT JOIN TicketNameserverHist TNH ON TNH.Chng_ID = T.Chng_ID
  WHERE TNH.Chng_ID IS NULL;

ALTER TABLE TicketHist
  MODIFY COLUMN T_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE TicketHist
  MODIFY COLUMN T_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- CRS-993

-- Insert current value of Deposit to DepositHist. Use deposits's Trans_Dt as the new Chng_Dt.
INSERT INTO
  DepositHist (Nic_Handle, Trans_Dt, Open_Bal, Close_Bal, Order_ID, Trans_Amount, Trans_Type,
    Corrector_NH, Remark, Op_Dt)
  SELECT Nic_Handle, Trans_Dt, Open_Bal, Close_Bal, Order_ID, Trans_Amount, Trans_Type,
    Corrector_NH, Remark, Trans_Dt
  FROM Deposit ORDER BY Nic_Handle;

-- Add new column to Deposit table which will point to the newly created historical entry.
SET @firstNewChangeId = LAST_INSERT_ID() - 1;
ALTER TABLE Deposit ADD COLUMN Chng_ID int(10) unsigned;
UPDATE Deposit SET Chng_ID = @firstNewChangeId := @firstNewChangeId + 1 ORDER BY Nic_Handle;

-- Update Op_Dt of each historical deposit based on Trans_Dt, which is the actual point in time when
-- the change was made.
UPDATE DepositHist SET Op_Dt = Trans_Dt;

-- CRS-992

CREATE TEMPORARY TABLE EmailGroupChngID
  SELECT DISTINCT EG_ID FROM EmailGroupHist;
INSERT INTO EmailGroupHist (EG_ID, EG_Name, EG_Visible, EG_TS, Chng_NH, Chng_Dt)
  SELECT
    EG_ID, EG_Name, EG_Visible, EG_TS,
    'IH4-IEDR',
    EG_TS
  FROM EmailGroup
  WHERE EG_ID NOT IN (SELECT EG_ID FROM EmailGroupChngID);
DROP TABLE EmailGroupChngID;

CREATE TEMPORARY TABLE EmailChngID AS
  SELECT DISTINCT E_ID AS E_ID FROM EmailHist;
INSERT INTO EmailHist (
  E_ID,
  E_Name,
  E_Text,
  E_Subject,
  E_TS,
  E_To,
  E_CC,
  E_BCC,
  E_To_Internal,
  E_CC_Internal,
  E_BCC_Internal,
  E_From,
  active,
  Html,
  E_Suppressible,
  E_Send_Reason,
  E_Non_Suppressible_Reason,
  EG_ID,
  Chng_NH,
  Chng_Dt)
SELECT E_ID,
  E_Name,
  E_Text,
  E_Subject,
  E_TS,
  E_To,
  E_CC,
  E_BCC,
  E_To_Internal,
  E_CC_Internal,
  E_BCC_Internal,
  E_From,
  active,
  Html,
  E_Suppressible,
  E_Send_Reason,
  E_Non_Suppressible_Reason,
  EG_ID,
  'IH4-IEDR',
  E_TS
FROM Email WHERE E_ID NOT IN (SELECT E_ID FROM EmailChngID);
DROP TABLE EmailChngID;

ALTER TABLE Email ADD COLUMN Chng_ID INT(10) UNSIGNED;
CREATE TEMPORARY TABLE EmailChngID AS
  SELECT E_ID, max(Chng_ID) AS Chng_ID FROM EmailHist
  GROUP BY E_ID;
UPDATE Email LEFT JOIN EmailChngID eh ON Email.E_ID = eh.E_ID
  SET Email.Chng_ID = eh.Chng_ID, E_TS = E_TS;
DROP TABLE EmailChngID;

CREATE TEMPORARY TABLE EmailDisablerChngID
  SELECT ED_Email_E_ID, ED_Nic_Handle FROM EmailDisablerHist;
INSERT INTO EmailDisablerHist (ED_Email_E_ID, ED_Nic_Handle, ED_Disabled, ED_TS, Chng_NH, Chng_Dt)
  SELECT
    ed.ED_Email_E_ID, ed.ED_Nic_Handle, ed.ED_Disabled, ed.ED_TS,
    'IH4-IEDR',
    ed.ED_TS
  FROM EmailDisabler ed
    LEFT JOIN EmailDisablerChngID ted ON ted.ED_Email_E_ID = ed.ED_Email_E_ID AND ted.ED_Nic_Handle = ed.ED_Nic_Handle
  WHERE ted.ED_Email_E_ID IS NULL;
DROP TABLE EmailDisablerChngID;

ALTER TABLE EmailGroup ADD COLUMN Chng_ID INT(10) UNSIGNED;
CREATE TEMPORARY TABLE EmailGroupChngID AS
  SELECT EG_ID, max(Chng_ID) AS Chng_ID FROM EmailGroupHist
  GROUP BY EG_ID;
UPDATE EmailGroup LEFT JOIN EmailGroupChngID ON EmailGroup.EG_ID = EmailGroupChngID.EG_ID
  SET EmailGroup.Chng_ID = EmailGroupChngID.Chng_ID, EG_TS = EG_TS;
DROP TABLE EmailGroupChngID;

ALTER TABLE EmailDisabler ADD COLUMN Chng_ID INT(10) UNSIGNED;
CREATE TEMPORARY TABLE EmailDisablerChngID AS
  SELECT ED_Email_E_ID, ED_Nic_Handle, max(Chng_ID) AS Chng_ID FROM EmailDisablerHist
  GROUP BY ED_Email_E_ID, ED_Nic_Handle;
UPDATE EmailDisabler ed
  LEFT JOIN EmailDisablerChngID ted ON ted.ED_Email_E_ID = ed.ED_Email_E_ID AND ted.ED_Nic_Handle = ed.ED_Nic_Handle
  SET ed.Chng_ID = ted.Chng_ID, ED_TS = ED_TS;
DROP TABLE EmailDisablerChngID;

-- CRS-1072

-- Insert current value of Access to AccessHist.
-- On production, history for Access should be present, but in test data we have only one record.
INSERT INTO
  AccessHist (Nic_Handle, NH_Password, NH_Level, Internal, Salt, password_changed,
    use_two_factor_authentication, secret, Chng_NH, Chng_Dt)
  SELECT Nic_Handle, NH_Password, NH_Level, Internal, Salt, password_changed,
    use_two_factor_authentication, secret, 'IH4-IEDR', IFNULL(password_changed, CURDATE())
  FROM Access ORDER BY Nic_Handle;

-- Add new column to Access table which will point to the newly created historical entry.
SET @firstNewChangeId = LAST_INSERT_ID() - 1;
ALTER TABLE Access ADD COLUMN Chng_ID int(10) unsigned;
UPDATE Access SET Chng_ID = @firstNewChangeId := @firstNewChangeId + 1 ORDER BY Nic_Handle;

--
-- PART 2
--

-- CRS-988

-- For each historical ticket find historical account (AccountHist now also has the current value),
-- which was active at the time of the change being made. If such account doesn't exist, use the
-- oldest one. Then update A_Number column to reference AccountHist.Chng_ID.
CREATE TEMPORARY TABLE ticketToAccountMapping AS
(SELECT
  TH.Chng_ID as ticketChngId,
  (SELECT Chng_ID FROM AccountHist
    WHERE A_Number=TH.A_Number AND Chng_Dt <= TH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as accountChngId,
  (SELECT Chng_ID FROM AccountHist
    WHERE A_Number=TH.A_Number
    ORDER BY Chng_Dt ASC, Chng_ID ASC LIMIT 1) as oldestAccountChngId
  FROM TicketHist TH);

ALTER TABLE TicketHist
  ADD COLUMN Account_Chng_ID int(10) unsigned NOT NULL AFTER A_Number,
  MODIFY COLUMN T_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE TicketHist TH LEFT JOIN ticketToAccountMapping M ON TH.Chng_ID = M.ticketChngId
SET TH.Account_Chng_ID = IFNULL(M.accountChngId, M.oldestAccountChngId);
ALTER TABLE TicketHist
  DROP COLUMN A_Number,
  MODIFY COLUMN T_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

DROP TABLE ticketToAccountMapping;

-- Do the same for historical nic handles.

CREATE TEMPORARY TABLE nichandleToAccountMapping AS
(SELECT
  NHH.Chng_ID as nichandleChngId,
  (SELECT Chng_ID FROM AccountHist
    WHERE A_Number=NHH.A_Number AND Chng_Dt <= NHH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as accountChngId,
  (SELECT Chng_ID FROM AccountHist
    WHERE A_Number=NHH.A_Number
    ORDER BY Chng_Dt ASC, Chng_ID ASC LIMIT 1) as oldestAccountChngId
  FROM NicHandleHist NHH);

ALTER TABLE NicHandleHist
  ADD COLUMN Account_Chng_ID int(10) unsigned NOT NULL AFTER A_Number,
  MODIFY COLUMN NH_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE NicHandleHist NHH LEFT JOIN nichandleToAccountMapping M ON NHH.Chng_ID = M.nichandleChngId
SET NHH.Account_Chng_ID = IFNULL(M.accountChngId, M.oldestAccountChngId);
ALTER TABLE NicHandleHist
  DROP COLUMN A_Number,
  MODIFY COLUMN NH_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

DROP TABLE nichandleToAccountMapping;

-- Do the same for historical domains

CREATE TEMPORARY TABLE domainToAccountMapping AS
(SELECT
  DH.Chng_ID as domainChngId,
  (SELECT Chng_ID FROM AccountHist
    WHERE A_Number=DH.A_Number AND Chng_Dt <= DH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as accountChngId,
  (SELECT Chng_ID FROM AccountHist
    WHERE A_Number=DH.A_Number
    ORDER BY Chng_Dt ASC, Chng_ID ASC LIMIT 1) as oldestAccountChngId
  FROM DomainHist DH);

ALTER TABLE DomainHist
  ADD COLUMN Account_Chng_ID int(10) unsigned NOT NULL AFTER A_Number,
  MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE DomainHist DH LEFT JOIN domainToAccountMapping M ON DH.Chng_ID = M.domainChngId
SET DH.Account_Chng_ID = IFNULL(M.accountChngId, M.oldestAccountChngId);
ALTER TABLE DomainHist
  DROP COLUMN A_Number,
  MODIFY COLUMN D_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

DROP TABLE domainToAccountMapping;

-- CRS-989, CRS-1072, CRS-1074

-- Migrate DepositHist

ALTER TABLE DepositHist
  CHANGE COLUMN id Chng_ID int(10) NOT NULL AUTO_INCREMENT FIRST,
  CHANGE COLUMN Op_Dt Chng_Dt datetime NOT NULL DEFAULT '0000-00-00 00:00:00' AFTER Remark;

CREATE TEMPORARY TABLE depositToNicHandleMapping AS
(SELECT
  DH.Chng_ID as depositChngId,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like DH.Nic_Handle AND Chng_Dt <= DH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as nicHandleChngId,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like DH.Nic_Handle
    ORDER BY Chng_Dt ASC, Chng_ID ASC LIMIT 1) as oldestNicHandleChngId
  FROM DepositHist DH);

ALTER TABLE DepositHist ADD COLUMN Nic_Handle_Chng_ID int(10) unsigned NOT NULL AFTER Nic_Handle;

UPDATE DepositHist DH LEFT JOIN depositToNicHandleMapping M ON DH.Chng_ID = M.depositChngId
SET DH.Nic_Handle_Chng_ID = IFNULL(M.nicHandleChngId, M.oldestNicHandleChngId);

ALTER TABLE DepositHist DROP COLUMN Nic_Handle;

DROP TABLE depositToNicHandleMapping;

-- Migrate ContactHist

-- The are historical contacts with empty nic handle. But in many cases those are admin contacts to
-- domains, that already have an admin contact (or rather had at that point in history). Instead of
-- providing some random NicHandle let's just remove them.
DELETE CH1.*
  FROM ContactHist CH1 LEFT JOIN ContactHist CH2
    ON CH1.Chng_ID = CH2.Chng_ID AND CH1.Type = CH2.Type AND CH2.Contact_NH != ''
  WHERE CH1.Contact_NH = '' AND CH2.Contact_NH IS NOT NULL;

-- For those left, let's use the current value from Contact table.
UPDATE
  ContactHist CH
    LEFT JOIN Contact C ON CH.D_Name = C.D_Name AND CH.Type = C.Type
  SET CH.Contact_NH = C.Contact_NH
  WHERE CH.Contact_NH = '';

CREATE TEMPORARY TABLE contactToChngDtMapping AS
(SELECT
  CH.Chng_ID as Chng_ID,
  CH.Contact_NH as Contact_NH,
  DH.Chng_Dt as Chng_Dt
  FROM ContactHist CH LEFT JOIN DomainHist DH ON DH.Chng_ID = CH.Chng_ID);

CREATE TEMPORARY TABLE contactToNicHandleMapping AS
(SELECT
  CH.Chng_ID as contactChngId,
  CH.Contact_NH as contactNh,
  CH.Chng_Dt as contactChngDt,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like CH.Contact_NH AND Chng_Dt <= CH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as nicHandleChngId,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like CH.Contact_NH
    ORDER BY Chng_Dt ASC, Chng_ID ASC LIMIT 1) as oldestNicHandleChngId
  FROM contactToChngDtMapping CH);

ALTER TABLE ContactHist ADD COLUMN Nic_Handle_Chng_ID int(10) unsigned NOT NULL AFTER Contact_NH;

UPDATE ContactHist CH
  LEFT JOIN contactToNicHandleMapping M
    ON (CH.Chng_ID = M.contactChngId AND CH.Contact_NH like M.contactNh)
  SET CH.Nic_Handle_Chng_ID = IFNULL(M.nicHandleChngId, M.oldestNicHandleChngId);

ALTER TABLE ContactHist
  DROP PRIMARY KEY,
  DROP COLUMN Contact_NH,
  ADD PRIMARY KEY(Chng_ID, D_Name, Nic_Handle_Chng_ID, Type);

DROP TABLE contactToChngDtMapping, contactToNicHandleMapping;

-- Migrate TicketHist

ALTER TABLE Ticket MODIFY COLUMN T_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE TicketHist MODIFY COLUMN T_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

UPDATE Ticket SET Admin_NH1 = TRIM(Admin_NH1);
UPDATE Ticket SET Admin_NH2 = TRIM(Admin_NH2);
UPDATE Ticket SET Tech_NH = TRIM(Tech_NH);
UPDATE Ticket SET Bill_NH = TRIM(Bill_NH);
UPDATE Ticket SET Creator_NH = TRIM(Creator_NH);
UPDATE Ticket SET CheckedOutTo = TRIM(CheckedOutTo);
UPDATE TicketHist SET Admin_NH1 = TRIM(Admin_NH1);
UPDATE TicketHist SET Admin_NH2 = TRIM(Admin_NH2);
UPDATE TicketHist SET Tech_NH = TRIM(Tech_NH);
UPDATE TicketHist SET Bill_NH = TRIM(Bill_NH);
UPDATE TicketHist SET Creator_NH = TRIM(Creator_NH);
UPDATE TicketHist SET CheckedOutTo = TRIM(CheckedOutTo);

UPDATE Ticket SET Admin_NH2 = NULL WHERE Admin_NH2 = '';
UPDATE Ticket SET CheckedOutTo = NULL WHERE CheckedOutTo = '';
UPDATE TicketHist SET Admin_NH2 = NULL WHERE Admin_NH2 = '';
UPDATE TicketHist SET Creator_NH = Bill_NH WHERE Creator_NH = '';
UPDATE TicketHist SET CheckedOutTo = NULL WHERE CheckedOutTo = '';

CREATE TEMPORARY TABLE oldestNicHandle1 AS
(SELECT NHH.Nic_Handle,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle LIKE NHH.Nic_Handle
    ORDER BY Chng_Dt ASC, Chng_ID ASC LIMIT 1) AS Chng_ID
  FROM NicHandleHist NHH);
CREATE TEMPORARY TABLE oldestNicHandle2 AS SELECT * FROM oldestNicHandle1;
CREATE TEMPORARY TABLE oldestNicHandle3 AS SELECT * FROM oldestNicHandle1;
CREATE TEMPORARY TABLE oldestNicHandle4 AS SELECT * FROM oldestNicHandle1;
CREATE TEMPORARY TABLE oldestNicHandle5 AS SELECT * FROM oldestNicHandle1;
CREATE TEMPORARY TABLE oldestNicHandle6 AS SELECT * FROM oldestNicHandle1;

CREATE TEMPORARY TABLE ticketToNicHandleMapping AS
(SELECT
  TH.Chng_ID as ticketChngId,
  TH.Admin_NH1,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like TH.Admin_NH1 AND Chng_Dt <= TH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as ac1ChngId,
  ANH1.Chng_ID as ac1OldestChngId,
  TH.Admin_NH2,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like TH.Admin_NH2 AND Chng_Dt <= TH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as ac2ChngId,
  ANH2.Chng_ID as ac2OldestChngId,
  TH.Tech_NH,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like TH.Tech_NH AND Chng_Dt <= TH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as tcChngId,
  TNH.Chng_ID as tcOldestChngId,
  TH.Bill_NH,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like TH.Bill_NH AND Chng_Dt <= TH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as bcChngId,
  BNH.Chng_ID as bcOldestChngId,
  TH.Creator_NH,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like TH.Creator_NH AND Chng_Dt <= TH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as creatorChngId,
  CNH.Chng_ID as creatorOldestChngId,
  TH.CheckedOutTo,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like TH.CheckedOutTo AND Chng_Dt <= TH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as cotChngId,
  CotNH.Chng_ID as cotOldestChngId
  FROM TicketHist TH
    LEFT JOIN oldestNicHandle1 ANH1 ON ANH1.Nic_Handle like TH.Admin_NH1
    LEFT JOIN oldestNicHandle2 ANH2 ON ANH2.Nic_Handle like TH.Admin_NH2
    LEFT JOIN oldestNicHandle3 TNH ON TNH.Nic_Handle like TH.Tech_NH
    LEFT JOIN oldestNicHandle4 BNH ON BNH.Nic_Handle like TH.Bill_NH
    LEFT JOIN oldestNicHandle5 CNH ON CNH.Nic_Handle like TH.Creator_NH
    LEFT JOIN oldestNicHandle6 CotNH ON CotNH.Nic_Handle like TH.CheckedOutTo);

DROP TABLE oldestNicHandle1, oldestNicHandle2, oldestNicHandle3, oldestNicHandle4, oldestNicHandle5, oldestNicHandle6;

ALTER TABLE TicketHist
  ADD COLUMN Admin_NH1_Chng_ID int(10) unsigned NOT NULL AFTER Admin_NH1,
  ADD COLUMN Admin_NH2_Chng_ID int(10) unsigned AFTER Admin_NH2,
  ADD COLUMN Tech_NH_Chng_ID int(10) unsigned NOT NULL AFTER Tech_NH,
  ADD COLUMN Bill_NH_Chng_ID int(10) unsigned NOT NULL AFTER Bill_NH,
  ADD COLUMN Creator_NH_Chng_ID int(10) unsigned NOT NULL AFTER Creator_NH,
  ADD COLUMN CheckedOutTo_NH_Chng_ID int(10) unsigned AFTER CheckedOutTo;

UPDATE TicketHist TH LEFT JOIN ticketToNicHandleMapping M ON TH.Chng_ID = M.ticketChngId
SET
  TH.Admin_NH1_Chng_ID = IFNULL(M.ac1ChngId, M.ac1OldestChngId),
  TH.Admin_NH2_Chng_ID = IFNULL(M.ac2ChngId, M.ac2OldestChngId),
  TH.Tech_NH_Chng_ID = IFNULL(M.tcChngId, M.tcOldestChngId),
  TH.Bill_NH_Chng_ID = IFNULL(M.bcChngId, M.bcOldestChngId),
  TH.Creator_NH_Chng_ID = IFNULL(M.creatorChngId, M.creatorOldestChngId),
  TH.CheckedOutTo_NH_Chng_ID = IFNULL(M.cotChngId, M.cotOldestChngId);

ALTER TABLE TicketHist
  DROP COLUMN Admin_NH1,
  DROP COLUMN Admin_NH2,
  DROP COLUMN Tech_NH,
  DROP COLUMN Bill_NH,
  DROP COLUMN Creator_NH,
  DROP COLUMN CheckedOutTo;

DROP TABLE ticketToNicHandleMapping;

ALTER TABLE Ticket
  MODIFY COLUMN T_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE TicketHist
  MODIFY COLUMN T_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- Migrate AccountHist

ALTER TABLE AccountHist MODIFY COLUMN A_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

CREATE TEMPORARY TABLE accountToNicHandleMapping AS
(SELECT
  AH.Chng_ID as accountChngId,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like AH.Billing_NH AND Chng_Dt <= AH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as nicHandleChngId,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like AH.Billing_NH
    ORDER BY Chng_Dt ASC, Chng_ID ASC LIMIT 1) as oldestNicHandleChngId
  FROM AccountHist AH);

ALTER TABLE AccountHist ADD COLUMN Billing_NH_Chng_ID int(10) unsigned NOT NULL AFTER Billing_NH;

UPDATE AccountHist AH LEFT JOIN accountToNicHandleMapping M ON AH.Chng_ID = M.accountChngId
SET AH.Billing_NH_Chng_ID = IFNULL(M.nicHandleChngId, M.oldestNicHandleChngId);

ALTER TABLE AccountHist DROP COLUMN Billing_NH;

DROP TABLE accountToNicHandleMapping;

ALTER TABLE AccountHist
  MODIFY COLUMN A_TStamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- Migrate AccessHist

CREATE TEMPORARY TABLE accessToNicHandleMapping AS
(SELECT
  AH.Chng_ID as accessChngId,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like AH.Nic_Handle AND Chng_Dt <= AH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as nicHandleChngId,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like AH.Nic_Handle
    ORDER BY Chng_Dt ASC, Chng_ID ASC LIMIT 1) as oldestNicHandleChngId
  FROM AccessHist AH);

ALTER TABLE AccessHist ADD COLUMN Nic_Handle_Chng_ID int(10) unsigned NOT NULL AFTER Nic_Handle;

UPDATE AccessHist AH LEFT JOIN accessToNicHandleMapping M ON AH.Chng_ID = M.accessChngId
SET AH.Nic_Handle_Chng_ID = IFNULL(M.nicHandleChngId, M.oldestNicHandleChngId);

ALTER TABLE AccessHist DROP COLUMN Nic_Handle;

DROP TABLE accessToNicHandleMapping;

-- CRS-990

-- Migrate TransfersHist
-- This is only to migrate our test data. The real migration has to be done before modifying Chng_Dt
-- in DomainHist table and should depend on that column when joining TransfersHist with DomainHist.
-- This way we'll be sure to match transfers records to those historical domains, that would have
-- been displayed before CRS-860.

CREATE TEMPORARY TABLE transferToDomainChngId AS
SELECT TH.Chng_ID as transferChngId, DH.Chng_ID as domainChngId
    FROM TransfersHist TH
    LEFT JOIN DomainHist DH ON DH.D_Name = TH.D_Name
    WHERE DH.Chng_Id = (
        SELECT Chng_Id
        FROM DomainHist
        WHERE D_Name = TH.D_Name AND DATEDIFF(D_Transfer_Dt, TH.Tr_Date) = 0
        ORDER BY Chng_Dt DESC, Chng_Id DESC LIMIT 1
    );

ALTER TABLE TransfersHist ADD COLUMN Transferred_Domain_Chng_ID int(10) unsigned;

UPDATE TransfersHist TH
    LEFT JOIN transferToDomainChngId D ON D.transferChngId = TH.Chng_ID
    SET TH.Transferred_Domain_Chng_ID = D.domainChngId;

DROP TABLE transferToDomainChngId;

-- We don't want to keep transfers, that don't have domain history.
DELETE FROM TransfersHist WHERE Transferred_Domain_Chng_ID IS NULL;

-- CRS-992

CREATE TEMPORARY TABLE EmailHistEmailGroupChngID AS
  SELECT
    EH.Chng_ID as emailChngID,
    (SELECT Chng_ID FROM EmailGroupHist
    WHERE EmailGroupHist.EG_ID = EH.EG_ID AND Chng_Dt <= EH.Chng_Dt
     ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as egChngId,
    (SELECT Chng_ID FROM EmailGroupHist
    WHERE EmailGroupHist.EG_ID = EH.EG_ID
     ORDER BY Chng_Dt ASC, Chng_ID ASC LIMIT 1) as egOldestChngId
  FROM EmailHist EH
  WHERE EH.EG_ID IS NOT NULL;
ALTER TABLE EmailHist ADD COLUMN EG_Chng_ID int(10) unsigned NULL AFTER EG_ID;
UPDATE EmailHist EH LEFT JOIN EmailHistEmailGroupChngID TEH ON EH.Chng_ID = TEH.emailChngID
  SET EH.EG_Chng_ID = IFNULL(TEH.egChngId, TEH.egOldestChngId),
  E_TS = E_TS;
ALTER TABLE EmailHist DROP COLUMN EG_ID;
DROP TABLE EmailHistEmailGroupChngID;

CREATE TEMPORARY TABLE EmailDisablerHistNicHandleChngID AS
  SELECT
    EDH.Chng_ID as disablerChngId,
    EDH.ED_Nic_Handle as nicHandle,
    (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like EDH.ED_Nic_Handle AND Chng_Dt <= EDH.Chng_Dt
     ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as nhChngId,
    (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like EDH.ED_Nic_Handle
     ORDER BY Chng_Dt ASC, Chng_ID ASC LIMIT 1) as nhOldestChngId
  FROM EmailDisablerHist EDH;
ALTER TABLE EmailDisablerHist ADD COLUMN ED_Nic_Handle_Chng_ID int(10) unsigned NOT NULL AFTER ED_Nic_Handle;
UPDATE EmailDisablerHist EDH LEFT JOIN EmailDisablerHistNicHandleChngID TEDH ON EDH.Chng_ID = TEDH.disablerChngId
  SET EDH.ED_Nic_Handle_Chng_ID = IFNULL(TEDH.nhChngId, TEDH.nhOldestChngId),
  ED_TS = ED_TS;
ALTER TABLE EmailDisablerHist DROP COLUMN ED_Nic_Handle;
DROP TABLE EmailDisablerHistNicHandleChngID;

CREATE TEMPORARY TABLE EmailDisablerEmailChngID AS
  SELECT
    EDH.Chng_ID as disablerChngID,
    EDH.ED_Email_E_ID as emailID,
    (SELECT Chng_ID FROM EmailHist
    WHERE EmailHist.E_ID = EDH.ED_Email_E_ID AND Chng_Dt <= EDH.Chng_Dt
     ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as eChngId,
    (SELECT Chng_ID FROM EmailHist
    WHERE EmailHist.E_ID = EDH.ED_Email_E_ID
     ORDER BY Chng_Dt ASC, Chng_ID ASC LIMIT 1) as eOldestChngId
  FROM EmailDisablerHist EDH;
ALTER TABLE EmailDisablerHist
ADD COLUMN ED_Email_Chng_ID int(10) unsigned NULL AFTER ED_Email_E_ID;
UPDATE EmailDisablerHist EDH LEFT JOIN EmailDisablerEmailChngID TEDH ON EDH.Chng_ID = TEDH.disablerChngID
  SET EDH.ED_Email_Chng_ID = IFNULL(TEDH.eChngId, TEDH.eOldestChngId),
  ED_TS = ED_TS;
ALTER TABLE EmailDisablerHist DROP COLUMN ED_Email_E_ID;
DROP TABLE EmailDisablerEmailChngID;

-- CRS-994

-- Migrate ReservationHist

CREATE TEMPORARY TABLE reservationHistWithDate AS
(SELECT
  ID,
  Nic_Handle,
  Domain_Name,
  Ticket_ID,
  IFNULL(Settled_Date, Creation_Date) AS Chng_Dt
  FROM ReservationHist);

CREATE TEMPORARY TABLE reservationMapping AS
(SELECT
  RH.ID as reservationId,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like RH.Nic_Handle AND Chng_Dt <= RH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as nicHandleChngId,
  (SELECT Chng_ID FROM NicHandleHist
    WHERE Nic_Handle like RH.Nic_Handle
    ORDER BY Chng_Dt ASC, Chng_ID ASC LIMIT 1) as oldestNicHandleChngId,
  (SELECT Chng_ID FROM DomainHist
    WHERE D_Name like RH.Domain_Name AND Chng_Dt <= RH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as domainChngId,
  (SELECT Chng_ID FROM DomainHist
    WHERE D_Name like RH.Domain_Name
    ORDER BY Chng_Dt ASC, Chng_ID ASC LIMIT 1) as oldestDomainChngId,
  (SELECT Chng_ID FROM TicketHist
    WHERE T_Number = RH.Ticket_ID AND Chng_Dt <= RH.Chng_Dt
    ORDER BY Chng_Dt DESC, Chng_ID DESC LIMIT 1) as ticketChngId,
  (SELECT Chng_ID FROM TicketHist
    WHERE T_Number = RH.Ticket_ID
    ORDER BY Chng_Dt ASC, Chng_ID ASC LIMIT 1) as oldestTicketChngId
  FROM reservationHistWithDate RH);

ALTER TABLE ReservationHist
  ADD COLUMN Nic_Handle_Chng_ID int(10) unsigned NOT NULL AFTER Nic_Handle,
  ADD COLUMN Domain_Chng_ID int(10) unsigned NOT NULL AFTER Domain_Name,
  ADD COLUMN Ticket_Chng_ID int(10) unsigned AFTER Ticket_ID;

UPDATE ReservationHist RH
LEFT JOIN reservationMapping M ON RH.ID = M.reservationId
SET
  RH.Nic_Handle_Chng_ID = IFNULL(M.nicHandleChngId, M.oldestNicHandleChngId),
  RH.Domain_Chng_ID = IFNULL(M.domainChngId, M.oldestDomainChngId),
  RH.Ticket_Chng_ID = IFNULL(M.ticketChngId, M.oldestTicketChngId);

ALTER TABLE ReservationHist
  DROP COLUMN Nic_Handle,
  DROP COLUMN Domain_Name,
  DROP COLUMN Ticket_ID;

DROP TABLE reservationHistWithDate, reservationMapping;

-- CRS-853 (composed 1028-34)
-- TIMESTAMPS that have NOT NULL use CURRENT_TIMESTAMP as default because we cannot turn on
--   --explicit_defaults_for_timestamps for mysql. So even though it would be better to
--   actually have "NOT NULL;" we have to give it a default value. So I'm setting it to
--   CURRENT_TIMESTAMP.

SET @timestamp_start = FROM_UNIXTIME(1);

ALTER TABLE `Access` CHANGE COLUMN `password_changed` `password_change_TS` TIMESTAMP NULL;
UPDATE `AccessHist` SET `Chng_Dt` = @timestamp_start WHERE `Chng_Dt` < @timestamp_start;
ALTER TABLE `AccessHist` CHANGE COLUMN `Chng_Dt` `Chng_TS` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `AccessHist` CHANGE COLUMN `password_changed` `password_change_TS` TIMESTAMP NULL;

ALTER TABLE `Account` CHANGE COLUMN `A_TStamp` `A_Change_TS` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `Account` MODIFY COLUMN `A_Status_Dt` DATETIME;
ALTER TABLE `Account` MODIFY COLUMN `A_Reg_Dt` DATETIME;
UPDATE `Account` SET `A_Status_Dt` = @timestamp_start WHERE `A_Status_Dt` < @timestamp_start;
UPDATE `Account` SET `A_Reg_Dt` = @timestamp_start WHERE `A_Reg_Dt` < @timestamp_start;
UPDATE `Account` SET `A_Change_TS` = CURRENT_TIMESTAMP WHERE `A_Change_TS` IS NULL;
ALTER TABLE `Account` CHANGE COLUMN `A_Status_Dt` `A_Status_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `Account` CHANGE COLUMN `A_Reg_Dt` `A_Reg_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `AccountHist` CHANGE COLUMN `A_TStamp` `A_Change_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `AccountHist` MODIFY COLUMN `A_Status_Dt` DATETIME;
ALTER TABLE `AccountHist` MODIFY COLUMN `A_Reg_Dt` DATETIME;
UPDATE `AccountHist` SET `A_Status_Dt` = @timestamp_start WHERE `A_Status_Dt` < @timestamp_start OR `A_Status_Dt` IS NULL;
UPDATE `AccountHist` SET `A_Reg_Dt` = @timestamp_start WHERE `A_Reg_Dt` < @timestamp_start OR `A_Reg_Dt` IS NULL;
UPDATE `AccountHist` SET `A_Change_TS` = CURRENT_TIMESTAMP WHERE `A_Change_TS` IS NULL;
UPDATE `AccountHist` SET `Chng_Dt` = @timestamp_start WHERE `Chng_Dt` < @timestamp_start OR `Chng_Dt` IS NULL;
ALTER TABLE `AccountHist` CHANGE COLUMN `A_Status_Dt` `A_Status_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `AccountHist` CHANGE COLUMN `A_Reg_Dt` `A_Reg_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `AccountHist` CHANGE COLUMN `Chng_Dt` `Chng_TS` TIMESTAMP NOT NULL DEFAULT now();

ALTER TABLE `Bulk_Transfer` CHANGE COLUMN `Completed` `Completed_TS` TIMESTAMP NULL;
ALTER TABLE `Bulk_Transfer_Domain` CHANGE COLUMN `Transfer_Dt` `Transfer_TS` TIMESTAMP NULL;

UPDATE `Deposit` SET `Trans_Dt` = @timestamp_start WHERE `Trans_Dt` < @timestamp_start;
ALTER TABLE `Deposit` CHANGE COLUMN `Trans_Dt` `Trans_TS` TIMESTAMP NOT NULL DEFAULT now();
UPDATE `DepositHist` SET `Trans_Dt` = @timestamp_start WHERE `Trans_Dt` < @timestamp_start;
UPDATE `DepositHist` SET `Chng_Dt` = @timestamp_start WHERE `Chng_Dt` < @timestamp_start;
ALTER TABLE `DepositHist` CHANGE COLUMN `Trans_Dt` `Trans_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `DepositHist` CHANGE COLUMN `Chng_Dt` `Chng_TS` TIMESTAMP NOT NULL DEFAULT now();

ALTER TABLE `DNS_Check_Notification_Date` CHANGE COLUMN `Next_Notification_Date` `Next_Notification_Dt` DATE NOT NULL;

ALTER TABLE `Domain` CHANGE COLUMN `D_TStamp` `D_Change_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `Domain` MODIFY COLUMN `D_Reg_Dt` DATETIME NOT NULL DEFAULT now();
UPDATE `Domain` SET `D_Reg_Dt` = @timestamp_start WHERE `D_Reg_Dt` < @timestamp_start;
UPDATE `Domain` SET `D_Ren_Dt` = @timestamp_start WHERE `D_Ren_Dt` IS NULL;
ALTER TABLE `Domain` CHANGE COLUMN `D_Reg_Dt` `D_Reg_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `Domain` MODIFY COLUMN `D_Ren_Dt` DATE NOT NULL;
ALTER TABLE `Domain` CHANGE COLUMN `D_Locking_Dt` `D_Locking_TS` TIMESTAMP NULL;
ALTER TABLE `Domain` CHANGE COLUMN `D_Transfer_Dt` `D_Transfer_TS` TIMESTAMP NULL;
ALTER TABLE `DomainHist` CHANGE COLUMN `D_TStamp` `D_Change_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `DomainHist` MODIFY COLUMN `D_Reg_Dt` DATETIME NOT NULL DEFAULT now();
UPDATE `DomainHist` SET `D_Reg_Dt` = @timestamp_start WHERE `D_Reg_Dt` < @timestamp_start;
UPDATE `DomainHist` SET `D_Ren_Dt` = @timestamp_start WHERE `D_Ren_Dt` IS NULL;
ALTER TABLE `DomainHist` CHANGE COLUMN `D_Reg_Dt` `D_Reg_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `DomainHist` MODIFY COLUMN `D_Ren_Dt` DATE NOT NULL;
ALTER TABLE `DomainHist` CHANGE COLUMN `D_Locking_Dt` `D_Locking_TS` TIMESTAMP NULL;
ALTER TABLE `DomainHist` CHANGE COLUMN `D_Transfer_Dt` `D_Transfer_TS` TIMESTAMP NULL;
ALTER TABLE `DomainHist` CHANGE COLUMN `Chng_Dt` `Chng_TS` TIMESTAMP NOT NULL DEFAULT now();

UPDATE `Domain_Notifications` SET `Exp_Date` = @timestamp_start WHERE `Exp_Date` IS NULL;
ALTER TABLE `Domain_Notifications` CHANGE COLUMN `Exp_Date` `Expiry_Dt` DATE NOT NULL;

ALTER TABLE `Email` CHANGE COLUMN `E_TS` `E_Change_TS` TIMESTAMP DEFAULT now();
UPDATE `EmailHist` SET `Chng_Dt` = @timestamp_start WHERE `Chng_Dt` < @timestamp_start;
ALTER TABLE `EmailHist` CHANGE COLUMN `E_TS` `E_Change_TS` TIMESTAMP DEFAULT now();
ALTER TABLE `EmailHist` CHANGE COLUMN `Chng_Dt` `Chng_TS` TIMESTAMP DEFAULT now();

ALTER TABLE `EmailDisabler` CHANGE COLUMN `ED_TS` `ED_Change_TS` TIMESTAMP DEFAULT now();
UPDATE `EmailDisablerHist` SET `Chng_Dt` = @timestamp_start WHERE `Chng_Dt` < @timestamp_start;
ALTER TABLE `EmailDisablerHist` CHANGE COLUMN `ED_TS` `ED_Change_TS` TIMESTAMP DEFAULT now();
ALTER TABLE `EmailDisablerHist` CHANGE COLUMN `Chng_Dt` `Chng_TS` TIMESTAMP DEFAULT now();

ALTER TABLE `EmailGroup` CHANGE COLUMN `EG_TS` `EG_Change_TS` TIMESTAMP DEFAULT now();
UPDATE `EmailGroupHist` SET `Chng_Dt` = @timestamp_start WHERE `Chng_Dt` < @timestamp_start;
ALTER TABLE `EmailGroupHist` CHANGE COLUMN `EG_TS` `EG_Change_TS` TIMESTAMP DEFAULT now();
ALTER TABLE `EmailGroupHist` CHANGE COLUMN `Chng_Dt` `Chng_TS` TIMESTAMP DEFAULT now();

ALTER TABLE `INCOMING_DOC` CHANGE COLUMN `CR_DATE` `Create_TS` TIMESTAMP NOT NULL DEFAULT now();

UPDATE `Invoice` SET `Invoice_Date` = @timestamp_start WHERE `Invoice_Date` IS NULL;
ALTER TABLE `Invoice` CHANGE COLUMN `Invoice_Date` `Invoice_TS` TIMESTAMP NOT NULL DEFAULT now();

ALTER TABLE `LoginAttempts` CHANGE COLUMN `attempted` `attempted_TS` TIMESTAMP NOT NULL DEFAULT now();

ALTER TABLE `NicHandle` MODIFY COLUMN `NH_TStamp` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `NicHandle` MODIFY COLUMN `NH_Status_Dt` DATETIME NOT NULL;
ALTER TABLE `NicHandle` MODIFY COLUMN `NH_Reg_Dt` DATETIME NOT NULL;
UPDATE `NicHandle` SET `NH_Status_Dt` = @timestamp_start WHERE `NH_Status_Dt` < @timestamp_start;
UPDATE `NicHandle` SET `NH_Reg_Dt` = @timestamp_start WHERE `NH_Reg_Dt` < @timestamp_start OR `NH_Reg_Dt` IS NULL;
ALTER TABLE `NicHandle` CHANGE COLUMN `NH_Status_Dt` `NH_Status_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `NicHandle` CHANGE COLUMN `NH_Reg_Dt` `NH_Reg_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `NicHandle` CHANGE COLUMN `NH_TStamp` `NH_Change_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `NicHandleHist` CHANGE COLUMN `NH_TStamp` `NH_Change_TS`TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `NicHandleHist` MODIFY COLUMN `NH_Status_Dt` DATETIME NOT NULL;
ALTER TABLE `NicHandleHist` MODIFY COLUMN `NH_Reg_Dt` DATETIME NOT NULL;
UPDATE `NicHandleHist` SET `NH_Change_TS` = @timestamp_start WHERE `NH_Change_TS` < @timestamp_start;
UPDATE `NicHandleHist` SET `NH_Status_Dt` = @timestamp_start WHERE `NH_Status_Dt` < @timestamp_start;
UPDATE `NicHandleHist` SET `NH_Reg_Dt` = @timestamp_start WHERE `NH_Reg_Dt` < @timestamp_start;
ALTER TABLE `NicHandleHist` CHANGE COLUMN `NH_Status_Dt` `NH_Status_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `NicHandleHist` CHANGE COLUMN `NH_Reg_Dt` `NH_Reg_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `NicHandleHist` CHANGE COLUMN `Chng_Dt` `Chng_TS`TIMESTAMP NOT NULL DEFAULT now();

UPDATE `Product` SET `P_Valid_From`='1970-01-01' WHERE `P_Valid_From` < '1970-01-01';
UPDATE `Product` SET `P_Valid_To`='1970-01-02' WHERE `P_Valid_To` < '1970-01-02';
ALTER TABLE `Product` CHANGE COLUMN `P_Valid_From` `P_Valid_From_Dt` DATE NULL;
ALTER TABLE `Product` CHANGE COLUMN `P_Valid_To` `P_Valid_To_Dt` DATE NULL;

ALTER TABLE `PersistedCommands` CHANGE COLUMN `P_CreateDate` `P_Create_TS` TIMESTAMP NOT NULL DEFAULT now();

ALTER TABLE `ResellerDefaultNameservers` MODIFY COLUMN `RDN_TS` TIMESTAMP NOT NULL DEFAULT now();

UPDATE `Reservation` SET `Creation_Date` = @timestamp_start WHERE `Creation_Date` < @timestamp_start;
ALTER TABLE `Reservation` CHANGE COLUMN `Creation_Date` `Creation_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `Reservation` CHANGE COLUMN `Settled_Date` `Settled_TS` TIMESTAMP NULL;
ALTER TABLE `Reservation` CHANGE COLUMN `Period_Start` `Period_Start_Dt` DATE NULL;
ALTER TABLE `Reservation` CHANGE COLUMN `Period_End` `Period_End_Dt` DATE NULL;
UPDATE `ReservationHist` SET `Creation_Date` = @timestamp_start WHERE `Creation_Date` < @timestamp_start;
ALTER TABLE `ReservationHist` CHANGE COLUMN `Creation_Date` `Creation_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `ReservationHist` CHANGE COLUMN `Settled_Date` `Settled_TS` TIMESTAMP NULL;
ALTER TABLE `ReservationHist` CHANGE COLUMN `Period_Start` `Period_Start_Dt` DATE NULL;
ALTER TABLE `ReservationHist` CHANGE COLUMN `Period_End` `Period_End_Dt` DATE NULL;

UPDATE `ResetPass` SET `Time_Stamp` = CURRENT_TIMESTAMP WHERE `Time_Stamp` < @timestamp_start;
ALTER TABLE `ResetPass` CHANGE COLUMN `Time_Stamp` `Expires_TS` TIMESTAMP NOT NULL DEFAULT now();

UPDATE `SchedulerJob` SET `start_date` = CURRENT_TIMESTAMP WHERE `start_date` IS NULL;
ALTER TABLE `SchedulerJob` CHANGE COLUMN `start_date` `start_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `SchedulerJob` CHANGE COLUMN `end_date` `end_TS` TIMESTAMP NULL;
UPDATE `SchedulerJobHist` SET `start_date` = CURRENT_TIMESTAMP WHERE `start_date` IS NULL;
ALTER TABLE `SchedulerJobHist` CHANGE COLUMN `start_date` `start_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `SchedulerJobHist` CHANGE COLUMN `end_date` `end_TS` TIMESTAMP NULL;

ALTER TABLE `Ticket` CHANGE COLUMN `T_TStamp` `T_Change_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `Ticket` MODIFY COLUMN `Ad_StatusDt` DATETIME NOT NULL;
ALTER TABLE `Ticket` MODIFY COLUMN `T_Status_Dt` DATETIME NOT NULL;
UPDATE `Ticket` SET `T_Change_TS` = @timestamp_start WHERE `T_Change_TS` < @timestamp_start;
UPDATE `Ticket` SET `T_Created_TS` = @timestamp_start WHERE `T_Created_TS` < @timestamp_start;
UPDATE `Ticket` SET `T_Ren_Dt` = NULL WHERE `T_Ren_Dt` = '0000-00-00';
UPDATE `Ticket` SET `Ad_StatusDt` = `T_Created_TS` WHERE `Ad_StatusDt` < @timestamp_start;
UPDATE `Ticket` SET `T_Status_Dt` = `T_Created_TS` WHERE `T_Status_Dt` < @timestamp_start;
UPDATE `Ticket` SET `F_Status_Dt` = `T_Created_TS` WHERE `F_Status_Dt` < @timestamp_start;
UPDATE `Ticket` SET `C_Status_Dt` = `T_Created_TS` WHERE `C_Status_Dt` < @timestamp_start;
ALTER TABLE `Ticket` DROP COLUMN `T_Reg_Dt`;
ALTER TABLE `Ticket` MODIFY COLUMN `T_Created_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `Ticket` CHANGE COLUMN `Ad_StatusDt` `Ad_Status_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `Ticket` CHANGE COLUMN `T_Status_Dt` `T_Status_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `Ticket` CHANGE COLUMN `F_Status_Dt` `F_Status_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `Ticket` CHANGE COLUMN `C_Status_Dt` `C_Status_TS`TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `TicketHist` CHANGE COLUMN `T_TStamp` `T_Change_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `TicketHist` MODIFY COLUMN `Ad_StatusDt` DATETIME NOT NULL;
ALTER TABLE `TicketHist` MODIFY COLUMN `T_Status_Dt` DATETIME NOT NULL;
UPDATE `TicketHist` SET `T_Change_TS` = @timestamp_start WHERE `T_Change_TS` < @timestamp_start;
UPDATE `TicketHist` SET `T_Created_TS` = @timestamp_start WHERE `T_Created_TS` < @timestamp_start;
UPDATE `TicketHist` Set `T_Ren_Dt` = NULL WHERE `T_Ren_Dt` = '0000-00-00';
UPDATE `TicketHist` SET `Ad_StatusDt` = `T_Created_TS` WHERE `Ad_StatusDt` < @timestamp_start;
UPDATE `TicketHist` SET `T_Status_Dt` = `T_Created_TS` WHERE `T_Status_Dt` < @timestamp_start;
UPDATE `TicketHist` SET `F_Status_Dt` = `T_Created_TS` WHERE `F_Status_Dt` < @timestamp_start;
UPDATE `TicketHist` SET `C_Status_Dt` = `T_Created_TS` WHERE `C_Status_Dt` < @timestamp_start;
UPDATE `TicketHist` SET `Chng_Dt` = `T_Change_TS` WHERE `Chng_Dt` < @timestamp_start;
ALTER TABLE `TicketHist` DROP COLUMN `T_Reg_Dt`;
ALTER TABLE `TicketHist` MODIFY COLUMN `T_Created_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `TicketHist` CHANGE COLUMN `Ad_StatusDt` `Ad_Status_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `TicketHist` CHANGE COLUMN `T_Status_Dt` `T_Status_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `TicketHist` CHANGE COLUMN `F_Status_Dt` `F_Status_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `TicketHist` CHANGE COLUMN `C_Status_Dt` `C_Status_TS`TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `TicketHist` CHANGE COLUMN `Chng_Dt` `Chng_TS` TIMESTAMP NOT NULL DEFAULT now();

ALTER TABLE `Transaction` CHANGE COLUMN `Cancelled_Date` `Cancelled_TS` TIMESTAMP NULL;
ALTER TABLE `Transaction` CHANGE COLUMN `Financially_Passed_Date` `Financially_Passed_TS` TIMESTAMP NULL;
ALTER TABLE `Transaction` CHANGE COLUMN `Invalidated_Date` `Invalidated_TS` TIMESTAMP NULL;
ALTER TABLE `TransactionHist` CHANGE COLUMN `Cancelled_Date` `Cancelled_TS` TIMESTAMP NULL;
ALTER TABLE `TransactionHist` CHANGE COLUMN `Financially_Passed_Date` `Financially_Passed_TS` TIMESTAMP NULL;
ALTER TABLE `TransactionHist` CHANGE COLUMN `Invalidated_Date` `Invalidated_TS` TIMESTAMP NULL;

ALTER TABLE `TicketNameserver` MODIFY COLUMN `TN_TS` TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE `TicketNameserverHist` MODIFY COLUMN `TN_TS` TIMESTAMP NOT NULL DEFAULT now();

UPDATE `TransfersHist` SET `Tr_Date` = @timestamp_start WHERE `Tr_Date` < @timestamp_start;
ALTER TABLE `TransfersHist` CHANGE COLUMN `Tr_Date` `Transfer_TS` TIMESTAMP NOT NULL DEFAULT now();

UPDATE `Vat` SET `From_Date` = @timestamp_start WHERE `From_Date` < @timestamp_start;
ALTER TABLE `Vat` CHANGE COLUMN `From_Date` `From_Dt` DATE NOT NULL;

-- CRS-1022

DROP TABLE `Tariff`;

-- CRS-1000

ALTER TABLE `Domain` MODIFY COLUMN `D_Class` VARCHAR(255) NULL;
ALTER TABLE `Domain` ADD COLUMN `Class_Id` INT(11) NOT NULL AFTER `D_Class`;
UPDATE `Domain` D SET D.`Class_Id` = (SELECT `id` FROM `Class` WHERE `name` = D.`D_Class`);
ALTER TABLE `Domain` DROP COLUMN `D_Class`;
ALTER TABLE `Domain` MODIFY COLUMN `D_Category` VARCHAR(255) NULL;
ALTER TABLE `Domain` ADD COLUMN `Category_Id` INT(11) NOT NULL AFTER `D_Category`;
UPDATE `Domain` D SET D.`Category_Id` = (SELECT `id` FROM `Category` WHERE `name` = D.`D_Category`);
ALTER TABLE `Domain` DROP COLUMN `D_Category`;
ALTER TABLE `DomainHist` MODIFY COLUMN `D_Class` VARCHAR(255) NULL;
ALTER TABLE `DomainHist` ADD COLUMN `Class_Id` INT(11) NOT NULL AFTER `D_Class`;
UPDATE `DomainHist` D SET D.`Class_Id` = (SELECT `id` FROM `Class` WHERE `name` = D.`D_Class`);
ALTER TABLE `DomainHist` DROP COLUMN `D_Class`;
ALTER TABLE `DomainHist` MODIFY COLUMN `D_Category` VARCHAR(255) NULL;
ALTER TABLE `DomainHist` ADD COLUMN `Category_Id` INT(11) NOT NULL AFTER `D_Category`;
UPDATE `DomainHist` D SET D.`Category_Id` = (SELECT `id` FROM `Category` WHERE `name` = D.`D_Category`);
ALTER TABLE `DomainHist` DROP COLUMN `D_Category`;

ALTER TABLE `Ticket` MODIFY COLUMN `T_Class` VARCHAR(255) NULL;
ALTER TABLE `Ticket` ADD COLUMN `Class_Id` INT(11) NOT NULL AFTER `T_Class`;
UPDATE `Ticket` T SET T.`Class_Id` = (SELECT `id` FROM `Class` WHERE `name` = T.`T_Class`);
ALTER TABLE `Ticket` DROP COLUMN `T_Class`;
ALTER TABLE `Ticket` MODIFY COLUMN `T_Category` VARCHAR(255) NULL;
ALTER TABLE `Ticket` ADD COLUMN `Category_Id` INT(11) NOT NULL AFTER `T_Category`;
UPDATE `Ticket` T SET T.`Category_Id` = (SELECT `id` FROM `Category` WHERE `name` = T.`T_Category`);
ALTER TABLE `Ticket` DROP COLUMN `T_Category`;
ALTER TABLE `TicketHist` MODIFY COLUMN `T_Class` VARCHAR(255) NULL;
ALTER TABLE `TicketHist` ADD COLUMN `Class_Id` INT(11) NOT NULL AFTER `T_Class`;
UPDATE `TicketHist` T SET T.`Class_Id` = (SELECT `id` FROM `Class` WHERE `name` = T.`T_Class`);
ALTER TABLE `TicketHist` DROP COLUMN `T_Class`;
ALTER TABLE `TicketHist` MODIFY COLUMN `T_Category` VARCHAR(255) NULL;
ALTER TABLE `TicketHist` ADD COLUMN `Category_Id` INT(11) NOT NULL AFTER `T_Category`;
UPDATE `TicketHist` T SET T.`Category_Id` = (SELECT `id` FROM `Category` WHERE `name` = T.`T_Category`);
ALTER TABLE `TicketHist` DROP COLUMN `T_Category`;

ALTER TABLE `Ticket` CHANGE COLUMN `T_Class_Fail_Cd` `Class_Id_Fail_Cd` TINYINT(3);
ALTER TABLE `Ticket` CHANGE COLUMN `T_Category_Fail_Cd` `Category_Id_Fail_Cd` TINYINT(3);
ALTER TABLE `TicketHist` CHANGE COLUMN `T_Class_Fail_Cd` `Class_Id_Fail_Cd` TINYINT(3);
ALTER TABLE `TicketHist` CHANGE COLUMN `T_Category_Fail_Cd` `Category_Id_Fail_Cd` TINYINT(3);

-- CRS-1062

ALTER TABLE `AccountVersionIndex` RENAME `ModVersionIndex`;

-- CRS-1054

ALTER TABLE `Product` MODIFY COLUMN `P_Valid_From_Dt` DATE NOT NULL;
ALTER TABLE `Product` MODIFY COLUMN `P_Valid_To_Dt` DATE NOT NULL;
ALTER TABLE `Product` DROP COLUMN `P_Short_Desc`;
ALTER TABLE `Product` CHANGE COLUMN `P_Long_Desc` `P_Description` VARCHAR(255) NOT NULL;

-- CRS-1047

-- Temporarily - to simplify modification
ALTER TABLE `DSM_Transition_Action` DROP FOREIGN KEY `FK_dsm_transition_action_transitionId`;
ALTER TABLE `DSM_Transition_Action` DROP KEY `Dsm_Transition_Action_NK`;

-- Removing execution of "SetDeletionDate" for states that the date has been already set
DELETE FROM `DSM_Transition_Action` WHERE `Action_Id` = 10;

-- Separeting "SetDeletionDate" action from "SetSuspensionDate" - adding the former to all transitions containing the latter
-- "SetSuspensionDate" is run always as first - setting "SetSuspensionDate" directly after and moving other actions +1 in order
UPDATE `DSM_Transition_Action` SET `Order` = `Order` + 1
WHERE `Transition_Id` IN (SELECT * FROM (SELECT `Transition_Id` FROM `DSM_Transition_Action` WHERE `Action_Id` = 8) TA) AND `Order` > 0;
INSERT INTO `DSM_Transition_Action`
(SELECT NULL, `Transition_Id`, 1, 10, NULL, NULL, 'NO'
FROM `DSM_Transition_Action` WHERE `Action_Id` = 8);

-- Removing execution of "ClearSuspensionDate" and "ClearDeletionDate" for non-NRP states (both dates are empty anyway)
DELETE FROM `DSM_Transition_Action`
WHERE `Action_Id` IN (5, 6) AND `Transition_Id` IN
(SELECT `Id` FROM `DSM_Transition` JOIN `DSM_State` ON `State` = `Begin_State`
WHERE `NRP_Status` IN ('A', 'XPA', 'XPI', 'XPV'));

-- Removing execution of "ClearSuspensionDate" and "ClearDeletionDate" for suspended NRP states (the date is already empty)
DELETE FROM `DSM_Transition_Action`
WHERE `Action_Id` = 5 AND `Transition_Id` IN
(SELECT `Id` FROM `DSM_Transition` JOIN `DSM_State` ON `State` = `Begin_State`
WHERE `NRP_Status` IN ('IS', 'VS', 'ISPP'));

-- Inserting missing execution of "ClearSuspensionDate" for transitions from NRP (non-suspended) to non-NRP states (always as the first action)
-- moving other actions +1 in order
UPDATE `DSM_Transition_Action` SET `Order` = `Order` + 1
WHERE `Transition_Id` IN
(SELECT T.`Id` FROM `DSM_Transition` T
WHERE T.`Begin_State` IN
(SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('IM', 'VM', 'IMPP'))
AND T.`End_State` IN
(SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('A', 'XPA', 'XPI', 'XPV'))
AND T.`Id` NOT IN
(SELECT * FROM (SELECT `Transition_Id` FROM `DSM_Transition_Action`
WHERE `Action_Id` = 5) TA));

INSERT INTO `DSM_Transition_Action`
(SELECT NULL, T.`Id`, 0, 5, NULL, NULL, 'NO'
FROM `DSM_Transition` T
WHERE T.`Begin_State` IN
(SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('IM', 'VM', 'IMPP'))
AND T.`End_State` IN
(SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('A', 'XPA', 'XPI', 'XPV'))
AND T.`Id` NOT IN
(SELECT * FROM (SELECT `Transition_Id` FROM `DSM_Transition_Action`
WHERE `Action_Id` = 5) TA));

-- Inserting missing execution of "ClearDeletionDate" for transitions from NRP (non-suspended) to non-NRP states (always as the second action, after "ClearSuspensionDate")
-- moving other actions +1 in order
UPDATE `DSM_Transition_Action` SET `Order` = `Order` + 1
WHERE `Transition_Id` IN
(SELECT T.`Id` FROM `DSM_Transition` T
WHERE T.`Begin_State` IN
(SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('IM', 'VM', 'IMPP'))
AND T.`End_State` IN
(SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('A', 'XPA', 'XPI', 'XPV'))
AND T.`Id` NOT IN
(SELECT * FROM (SELECT `Transition_Id` FROM `DSM_Transition_Action`
WHERE `Action_Id` = 6) TA))
AND `Order` > 0;

INSERT INTO `DSM_Transition_Action`
(SELECT NULL, T.`Id`, 1, 6, NULL, NULL, 'NO'
FROM `DSM_Transition` T
WHERE T.`Begin_State` IN
(SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('IM', 'VM', 'IMPP'))
AND T.`End_State` IN
(SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('A', 'XPA', 'XPI', 'XPV'))
AND T.`Id` NOT IN
(SELECT * FROM (SELECT `Transition_Id` FROM `DSM_Transition_Action`
WHERE `Action_Id` = 6) TA));

-- Inserting missing execution of "ClearDeletionDate" for transitions from suspended NRP to non-NRP states (always as the first action; "ClearSuspensionDate" is not needed as the date is already empty for suspended domains)
-- moving other actions +1 in order
UPDATE `DSM_Transition_Action` SET `Order` = `Order` + 1
WHERE `Transition_Id` IN
(SELECT T.`Id` FROM `DSM_Transition` T
WHERE T.`Begin_State` IN
(SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('IS', 'VS', 'ISPP'))
AND T.`End_State` IN
(SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('A', 'XPA', 'XPI', 'XPV'))
AND T.`Id` NOT IN
(SELECT * FROM (SELECT `Transition_Id` FROM `DSM_Transition_Action`
WHERE `Action_Id` = 6) TA));

INSERT INTO `DSM_Transition_Action`
(SELECT NULL, T.`Id`, 0, 6, NULL, NULL, 'NO'
FROM `DSM_Transition` T
WHERE T.`Begin_State` IN
(SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('IS', 'VS', 'ISPP'))
AND T.`End_State` IN
(SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('A', 'XPA', 'XPI', 'XPV'))
AND T.`Id` NOT IN
(SELECT * FROM (SELECT `Transition_Id` FROM `DSM_Transition_Action`
WHERE `Action_Id` = 6) TA));

-- Recreating
ALTER TABLE `DSM_Transition_Action` ADD UNIQUE KEY `Dsm_Transition_Action_NK` (`Transition_Id`,`Order`,`Execute_After_DSM_Change`);
ALTER TABLE `DSM_Transition_Action` ADD CONSTRAINT `FK_dsm_transition_action_transitionId` FOREIGN KEY (`Transition_Id`) REFERENCES `DSM_Transition` (`Id`);

-- CRS-1001
ALTER TABLE `Reseller_Defaults` MODIFY COLUMN `email_invoice_format` ENUM('pdf', 'xml', 'both') NOT NULL;

-- CRS-380

INSERT INTO `DSM_Event` VALUES (35, 'BulkTransfer', NULL);

INSERT INTO `DSM_Transition` (`Event_Id`, `Begin_State`, `End_State`)
(SELECT 35, `Begin`.`State`, `End`.`State` FROM `DSM_State` `Begin` JOIN `DSM_State` `End` ON `End`.`D_Holder_Type` = `Begin`.`D_Holder_Type` AND `End`.`NRP_Status` = `Begin`.`NRP_Status` AND `End`.`Cust_Type` = 'R' AND `End`.`Locked` = 'NO' AND `End`.`Renewal_Mode` = 'N' WHERE `Begin`.`NRP_Status` NOT IN ('D', 'XPA', 'XPI', 'XPV', 'N/A') AND `Begin`.`Cust_Type` = 'R' AND `Begin`.`Locked` = 'NO');


-- Removing differences in the order of columns between tables "Reservation" and "ReservationHist"

ALTER TABLE `Reservation` MODIFY COLUMN `Duration_Months` TINYINT(4) NOT NULL DEFAULT 0 AFTER `Domain_Name`;
ALTER TABLE `Reservation` MODIFY COLUMN `Ready_For_Settlement` enum('YES', 'NO') NOT NULL DEFAULT 'NO' AFTER `Vat_Amount`;
ALTER TABLE `Reservation` MODIFY COLUMN `Product_Id` INT(10) AFTER `Transaction_ID`;

