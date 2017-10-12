ALTER TABLE `DSM_State` CHANGE `WIPO` `Locked` CHAR(1);

-- VM and VS - voluntary NRP
-- XPA, XPI and XPV - transfer started
DELETE FROM `DSM_Transition_Action` WHERE `Transition_Id` IN (SELECT `Id` FROM `DSM_Transition` WHERE `Begin_State` IN (SELECT `State` FROM `DSM_State` WHERE `Locked` = 'Y' AND `NRP_Status` IN ('VM', 'VS', 'XPA', 'XPI', 'XPV')) OR `End_State` IN (SELECT `State` FROM `DSM_State` WHERE `Locked` = 'Y' AND `NRP_Status` IN ('VM', 'VS', 'XPA', 'XPI', 'XPV')));

DELETE FROM `DSM_Transition` WHERE `Begin_State` IN (SELECT `State` FROM `DSM_State` WHERE `Locked` = 'Y' AND `NRP_Status` IN ('VM', 'VS', 'XPA', 'XPI', 'XPV')) OR `End_State` IN (SELECT `State` FROM `DSM_State` WHERE `Locked` = 'Y' AND `NRP_Status` IN ('VM', 'VS', 'XPA', 'XPI', 'XPV'));

DELETE FROM `DSM_State` WHERE `Locked` = 'Y' AND `NRP_Status` IN ('VM', 'VS', 'XPA', 'XPI', 'XPV');

-- Involuntary NRP
DELETE FROM `DSM_Transition_Action` WHERE `Transition_Id` IN (SELECT `Id` FROM `DSM_Transition` WHERE `Event_Id` = 31 AND `Begin_State` IN (SELECT `State` FROM `DSM_State` WHERE `NRP_Status` <> 'A'));

DELETE FROM `DSM_Transition` WHERE `Event_Id` = 31 AND `Begin_State` IN (SELECT `State` FROM `DSM_State` WHERE `NRP_Status` <> 'A');

UPDATE `DSM_Event` SET `Name` = 'Lock' WHERE `Id` = 31;

UPDATE `DSM_Event` SET `Name` = 'Unlock' WHERE `Id` = 32;

-- CRS-867

ALTER TABLE `Domain`
ADD COLUMN `D_Locking_Dt` DATE NULL AFTER `D_Authc_Portal_Cnt`,
ADD COLUMN `D_LockingRenewal_Dt` DATE NULL AFTER `D_Locking_Dt`;

ALTER TABLE `DomainHist`
ADD COLUMN `D_Locking_Dt` DATE NULL AFTER `D_Authc_Portal_Cnt`,
ADD COLUMN `D_LockingRenewal_Dt` DATE NULL AFTER `D_Locking_Dt`;

-- CRS-874

UPDATE `Email` SET
`E_Text` = '\n\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nDear $BILL-C_NAME$\n\nDomain Name $DOMAIN$ has been LOCKED.\n\nThe details of the domain will follow in a separate email.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n',
`E_Subject` = 'Modification<LOCK Status> $DOMAIN$'
WHERE `E_ID` = 92;

INSERT INTO `Email`(`E_ID`, `E_Name`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `active`, `Html`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `EG_ID`, `E_Suppressible`, `E_Send_Reason`, `E_Non_Suppressible_Reason`, `E_Suppressed_By_Gaining`) VALUES
('189', '', '\n\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nDear $BILL-C_NAME$\n\nDomain Name $DOMAIN$ has been UNLOCKED.\n\nThe details of the domain will follow in a separate email.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Modification<LOCK Status> $DOMAIN$', '$BILL-C_EMAIL$', NULL, NULL, 'registrations@iedr.ie', '1', '0', NULL, 'registrations@iedr.ie', 'hostmaster-archive@domainregistry.ie, registrations@iedr.ie', '13', 'N', 'Contractual obligation to inform', '', 'N'),
('190', '', '\n\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nDear $BILL-C_NAME$\n\nThe Registry Locking Service has been activated for Domain Name $DOMAIN$\n\nThe details of the domain will follow in a separate email.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Modification<LOCKING SERVICE Status> $DOMAIN$', '$BILL-C_EMAIL$', NULL, NULL, 'registrations@iedr.ie', '1', '0', NULL, 'registrations@iedr.ie', 'hostmaster-archive@domainregistry.ie, registrations@iedr.ie', '13', 'N', 'Contractual obligation to inform', '', 'N'),
('191', '', '\n\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nDear $BILL-C_NAME$\n\nThe Registry Locking Service has not been renewed and will be deactivated for Domain Name $DOMAIN$\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Modification<LOCKING SERVICE Status> $DOMAIN$', '$BILL-C_EMAIL$', NULL, NULL, 'registrations@iedr.ie', '1', '0', NULL, 'registrations@iedr.ie', 'hostmaster-archive@domainregistry.ie, registrations@iedr.ie', '13', 'N', 'Contractual obligation to inform', '', 'N');

ALTER TABLE `DSM_Transition_Action`
    ADD COLUMN `Execute_After_DSM_Change` char(1) COLLATE utf8_irish_accent_ci NOT NULL DEFAULT 'N',
    DROP INDEX `Dsm_Transition_Action_NK`,
    ADD UNIQUE KEY `Dsm_Transition_Action_NK` (`Transition_Id`, `Order`, `Execute_After_DSM_Change`);

UPDATE `DSM_Transition_Action` SET `Execute_After_DSM_Change` = 'Y' WHERE `Transition_Id` IN (SELECT `Id` FROM `DSM_Transition` WHERE `Event_Id` = 31);
INSERT INTO `DSM_Transition_Action`(`Transition_Id`, `Order`, `Action_Id`, `Comment`, `Action_Param`, `Execute_After_DSM_Change`)
SELECT `Id`, 0, 2, NULL, 189, 'Y' FROM `DSM_Transition` WHERE `Event_Id` = 32;

-- CRS-871

INSERT INTO `Email`(`E_ID`, `E_Name`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `active`, `Html`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `EG_ID`, `E_Suppressible`, `E_Send_Reason`, `E_Non_Suppressible_Reason`, `E_Suppressed_By_Gaining`) VALUES
('192', '', '\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nUser $NIC_NAME$ has prolonged locking service renewal dates.\n\n$DOMAIN_LIST$\n\n\nKind Regards\nAccounts Services', '[LOCKING SERVICE] Confirmation: locking service renewal dates were rolled', NULL, NULL, NULL, 'do_not_reply@iedr.ie', '1', '0', 'accounts@iedr.ie', NULL, NULL, '14', 'N', 'Archivable confirmation', '', 'N');

-- CRS-885

UPDATE `DSM_Transition_Action` SET `Order` = 1 WHERE `Transition_Id` IN (SELECT `Id` FROM `DSM_Transition` WHERE `Event_Id` = 31) AND `Action_Id` = 2;

INSERT INTO `DSM_Action`(`Id`, `Action_Name`, `Comment`) VALUES (13, 'ClearAuthCode', NULL);
INSERT INTO `DSM_Transition_Action`(`Transition_Id`, `Order`, `Action_Id`, `Comment`, `Action_Param`, `Execute_After_DSM_Change`)
SELECT `Id`, 0, 13, NULL, NULL, 'N' FROM `DSM_Transition` WHERE `Event_Id` = 31;

-- CRS-881

-- Making transition id auto increment
ALTER TABLE `DSM_Transition_Action` DROP FOREIGN KEY `FK_dsm_transition_action_transitionId`;
ALTER TABLE `DSM_Transition` CHANGE COLUMN `Id` `Id` int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE `DSM_Transition_Action` ADD CONSTRAINT `FK_dsm_transition_action_transitionId` FOREIGN KEY(`Transition_Id`) REFERENCES `DSM_Transition`(`Id`);

-- Enabling PushQ for locked domains - creating DSM Transitions
INSERT INTO `DSM_Transition` (`Event_Id`, `Begin_State`, `End_State`)
SELECT T.`Event_Id`, Begin_Locked.`State`, End_Locked.`State`
FROM `DSM_Transition` T JOIN `DSM_State` Begin_Non_Locked ON Begin_Non_Locked.`State` = T.`Begin_State` JOIN `DSM_State` End_Non_Locked ON End_Non_Locked.`State` = T.`End_State`,
`DSM_State` Begin_Locked, `DSM_State` End_Locked
WHERE T.`Event_Id` IN (7, 8) AND Begin_Non_Locked.`Locked` = 'N' AND End_Non_Locked.`Locked` = 'N' AND Begin_Locked.`Locked` = 'Y' AND End_Locked.`Locked` = 'Y'
AND Begin_Locked.`D_Holder_Type` = Begin_Non_Locked.`D_Holder_Type` AND Begin_Locked.`Renewal_Mode` = Begin_Non_Locked.`Renewal_Mode` AND Begin_Locked.`Cust_Type` = Begin_Non_Locked.`Cust_Type` AND Begin_Locked.`NRP_Status` = Begin_Non_Locked.`NRP_Status`
AND End_Locked.`D_Holder_Type` = End_Non_Locked.`D_Holder_Type` AND End_Locked.`Renewal_Mode` = End_Non_Locked.`Renewal_Mode` AND End_Locked.`Cust_Type` = End_Non_Locked.`Cust_Type` AND End_Locked.`NRP_Status` = End_Non_Locked.`NRP_Status`;

INSERT INTO `DSM_Transition_Action` (`Transition_Id`, `Order`, `Action_Id`, `Action_Param`, `Execute_After_DSM_Change`)
SELECT T_Locked.`Id`, TA.`Order`, TA.`Action_Id`, TA.`Action_Param`, TA.`Execute_After_DSM_Change`
FROM `DSM_Transition_Action` TA JOIN `DSM_Transition` T_Non_Locked ON T_Non_Locked.`Id` = `Transition_Id` JOIN `DSM_State` Begin_Non_Locked ON Begin_Non_Locked.`State` = T_Non_Locked.`Begin_State` JOIN `DSM_State` End_Non_Locked ON End_Non_Locked.`State` = T_Non_Locked.`End_State`,
`DSM_Transition` T_Locked JOIN `DSM_State` Begin_Locked ON Begin_Locked.`State` = T_Locked.`Begin_State` JOIN `DSM_State` End_Locked ON End_Locked.`State` = T_Locked.`End_State`
WHERE T_Non_Locked.`Event_Id` IN (7, 8) AND T_Locked.`Event_Id` = T_Non_Locked.`Event_Id`
AND Begin_Non_Locked.`Locked` = 'N' AND End_Non_Locked.`Locked` = 'N' AND Begin_Locked.`Locked` = 'Y' AND End_Locked.`Locked` = 'Y'
AND Begin_Locked.`D_Holder_Type` = Begin_Non_Locked.`D_Holder_Type` AND Begin_Locked.`Renewal_Mode` = Begin_Non_Locked.`Renewal_Mode` AND Begin_Locked.`Cust_Type` = Begin_Non_Locked.`Cust_Type` AND Begin_Locked.`NRP_Status` = Begin_Non_Locked.`NRP_Status`
AND End_Locked.`D_Holder_Type` = End_Non_Locked.`D_Holder_Type` AND End_Locked.`Renewal_Mode` = End_Non_Locked.`Renewal_Mode` AND End_Locked.`Cust_Type` = End_Non_Locked.`Cust_Type` AND End_Locked.`NRP_Status` = End_Non_Locked.`NRP_Status`;

-- Transitions for domains to delete
INSERT INTO `DSM_Transition` (`Event_Id`, `Begin_State`, `End_State`)
SELECT 9, Locked.`State`, 385
FROM `DSM_Transition` T JOIN `DSM_State` Non_Locked ON Non_Locked.`State` = T.`Begin_State`, `DSM_State` Locked
WHERE T.`Event_Id` = 9 AND T.`End_State` = 385 AND Non_Locked.`Locked` = 'N' AND Locked.`Locked` = 'Y'
AND Locked.`D_Holder_Type` = Non_Locked.`D_Holder_Type` AND Locked.`Renewal_Mode` = Non_Locked.`Renewal_Mode` AND Locked.`Cust_Type` = Non_Locked.`Cust_Type` AND Locked.`NRP_Status` = Non_Locked.`NRP_Status`;

-- CRS-796 - Removing GIBO

DELETE FROM `DSM_Transition_Action` WHERE `Transition_Id` IN (SELECT `Id` FROM `DSM_Transition` WHERE `Begin_State` IN (7, 8, 23, 24, 39, 40, 55, 56, 71, 72, 87, 88) or `End_State` IN (7, 8, 23, 24, 39, 40, 55, 56, 71, 72, 87, 88));
DELETE FROM `DSM_Transition` WHERE `Begin_State` IN (7, 8, 23, 24, 39, 40, 55, 56, 71, 72, 87, 88) or `End_State` IN (7, 8, 23, 24, 39, 40, 55, 56, 71, 72, 87, 88);
DELETE FROM `DSM_State` WHERE `State` IN (7, 8, 23, 24, 39, 40, 55, 56, 71, 72, 87, 88);
DELETE FROM `DSM_Action` WHERE `Id` = 11;
DELETE FROM `DSM_Event` WHERE `Id` IN (5, 10, 11, 12, 13);

DELETE FROM `App_Config` WHERE `Cfg_Key` IN ('gibo_default_period', 'gibo_retry_timeout_hours');

ALTER TABLE `Domain` DROP COLUMN `D_GIBO_Retry_Timeout`;
ALTER TABLE `DomainHist` DROP COLUMN `D_GIBO_Retry_Timeout`;

-- CRS-889

DELETE FROM `DSM_Transition` WHERE `Event_id` IN (23, 24, 25) AND `Begin_State` IN (SELECT `State` FROM `DSM_State` WHERE `Locked` = 'Y');

UPDATE `DSM_Transition` SET `End_State` = 535 WHERE `Id` = 677;

UPDATE `DSM_Transition` SET `End_State` = 542 WHERE `Id` = 692;

-- CRS-902

INSERT INTO `Email`(`E_ID`, `E_Name`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `active`, `Html`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `EG_ID`, `E_Suppressible`, `E_Send_Reason`, `E_Non_Suppressible_Reason`, `E_Suppressed_By_Gaining`) VALUES
('193', '', '\n\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nDear $BILL-C_NAME$\n\nYou can see the WHOIS result for $DOMAIN$ below:\n\n$WHOIS$\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'WHOIS information for $DOMAIN$', '$BILL-C_EMAIL$', NULL, NULL, 'registrations@iedr.ie', '1', '0', NULL, 'registrations@iedr.ie', NULL, '13', 'Y', 'Customer WHOIS data requested', '', 'N');

-- CRS-766

ALTER TABLE `Account`
DROP COLUMN `Address`,
DROP COLUMN `County`,
DROP COLUMN `Country`,
DROP COLUMN `Phone`,
DROP COLUMN `Fax`,
DROP COLUMN `A_Tariff`,
DROP COLUMN `Credit_Limit`,
DROP COLUMN `Invoice_Freq`,
DROP COLUMN `Next_Invoice_Month`,
DROP COLUMN `Vat_Category`;

ALTER TABLE `AccountHist`
DROP COLUMN `Address`,
DROP COLUMN `County`,
DROP COLUMN `Country`,
DROP COLUMN `Phone`,
DROP COLUMN `Fax`,
DROP COLUMN `A_Tariff`,
DROP COLUMN `Credit_Limit`,
DROP COLUMN `Invoice_Freq`,
DROP COLUMN `Next_Invoice_Month`;

