-- CRS-244

ALTER TABLE `Email` ADD COLUMN (`E_Suppressed_By_Gaining` CHAR(1) NOT NULL DEFAULT 'N');

UPDATE `Email` SET `E_Suppressed_By_Gaining` = 'Y' WHERE `E_ID` IN (33, 35, 36, 38, 83, 84, 184, 186);

-- CRS-679

UPDATE `Email` SET `E_Text` = 'The limit of attempts of sending the email was reached, the following email was not sent:\n\n$EMAIL_TEXT$\n\n Check the logs for the details. \n\n$EMAIL_LOGS$\n\n' WHERE `E_ID` = 185;

-- CRS-723

ALTER TABLE `NicHandle`
ADD COLUMN `NH_Exported` CHAR(1) NOT NULL DEFAULT 'N';
UPDATE `NicHandle` NH
SET NH.NH_Exported = 'Y'
-- directs
WHERE (NH.A_Number = 1 AND NH.Nic_Handle IN
(SELECT A.Nic_Handle FROM `Access` A WHERE A.NH_Level = 2048))
-- registrars
OR NH.Nic_Handle = (SELECT AC.Billing_NH FROM `Account` AC WHERE AC.A_Number = NH.A_Number);

-- CRS-710

INSERT INTO `Counties` VALUES (254, 'Bristol');

-- CRS-724

UPDATE SchedulerConfig SET name='TicketAndTransactionCleanup' WHERE id=8;
INSERT INTO `App_Config` (`Cfg_Key`, `Cfg_Type`, `Cfg_Value`) VALUES ('reservation_expiration_period', 'INT', '28');

-- CRS-698

INSERT INTO `Email`(`E_ID`, `E_Name`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `active`, `Html`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `EG_ID`, `E_TS`, `E_Suppressible`, `E_Send_Reason`, `E_Non_Suppressible_Reason`, `E_Suppressed_By_Gaining`) VALUES
('187', 'DocumentUploadSomeDomainsUnknownConfirmation', 'PLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nDear $CREATOR-C_EMAIL$,\nYou have submitted documents to the IEDR for review.\n$INFO$\n\nPlease note that the following domains listed in your email could not be identified:\n\n$UNKNOWN_DOMAIN_NAMES$\n\nThese are not registered domain names and there are no applications pending for them.\n\nThe IEDR will try to determine if they could relate to any other domain names, but if you do not hear from us regarding other domains you may wish to register or modify, please contact us for more information.\n\nFiles marked “file saved” have been received and will be reviewed by our Registrations Services Administrators.\n\nFiles marked “discarded” have not been received and cannot be processed. Please submit alternative documentation instead.\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n', 'Document Upload Report', '$CREATOR-C_EMAIL$', NULL, NULL, 'registrations@iedr.ie', '1', '0', 'registrations@iedr.ie', NULL, NULL, '13', '2015-05-11 10:53:59', 'Y', NULL, NULL, 'N'),
('188', 'DocumentUploadAllDomainsUnknownConfirmation', 'PLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nDear $CREATOR-C_EMAIL$,\nYou have submitted documents to the IEDR for review.\n$INFO$\n\nPlease note that the domain(s) listed in your email could not be identified:\n\n$UNKNOWN_DOMAIN_NAMES$\n\nThese are not registered domain names and there are no applications pending for them.\n\nThe IEDR will try to determine if they could relate to any other domain names, but if you do not hear from us regarding other domains you may wish to register or modify, please contact us for more information.\n\nFiles marked “file saved” have been received and will be reviewed by our Registrations Services Administrators.\n\nFiles marked “discarded” have not been received and cannot be processed. Please submit alternative documentation instead.\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n', 'Document Upload Report', '$CREATOR-C_EMAIL$', NULL, NULL, 'registrations@iedr.ie', '1', '0', 'registrations@iedr.ie', NULL, NULL, '13', '2015-05-11 10:53:59', 'Y', NULL, NULL, 'N');

