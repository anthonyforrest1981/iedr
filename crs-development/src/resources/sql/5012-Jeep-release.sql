-- CRS-1129

DELETE FROM `DSM_Transition_Action` WHERE `Transition_Id` IN (SELECT `Id` FROM `DSM_Transition` WHERE `Event_Id` IN (20, 21, 22, 34) AND `Begin_State` NOT IN (SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('XPA', 'XPI', 'XPV')));

DELETE FROM `DSM_Transition` WHERE `Event_Id` IN (20, 21, 22, 34) AND `Begin_State` NOT IN (SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('XPA', 'XPI', 'XPV'));

-- CRS-1098

CREATE TABLE `AccountSegment` (
  `ID` INT(8) unsigned NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_irish_accent_ci;

ALTER TABLE `Account` ADD COLUMN `Segment_ID` INT(8) unsigned AFTER `A_Remarks`;
ALTER TABLE `Account` ADD CONSTRAINT `FK_accountsegment` FOREIGN KEY (`Segment_ID`) REFERENCES `AccountSegment` (`ID`);
ALTER TABLE `AccountHist` ADD COLUMN `Segment_ID` INT(8) unsigned AFTER `A_Remarks`;
ALTER TABLE `AccountHist` ADD CONSTRAINT `FK_accountsegmenthist` FOREIGN KEY (`Segment_ID`) REFERENCES `AccountSegment` (`ID`);

-- CRS-1148

DELETE FROM `DSM_Transition_Action` WHERE `Transition_Id` IN (SELECT `Id` FROM `DSM_Transition` WHERE `Event_Id` = 14 AND `Begin_State` IN (SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('XPA', 'XPI', 'XPV')));

DELETE FROM `DSM_Transition` WHERE `Event_Id` = 14 AND `Begin_State` IN (SELECT `State` FROM `DSM_State` WHERE `NRP_Status` IN ('XPA', 'XPI', 'XPV'));

-- CRS-1060

ALTER TABLE `Ticket` MODIFY COLUMN `Admin_Status` VARCHAR(23);
UPDATE `Ticket` SET `Admin_Status` = 'New'                     WHERE `Admin_Status` = '0';
UPDATE `Ticket` SET `Admin_Status` = 'Passed'                  WHERE `Admin_Status` = '1';
UPDATE `Ticket` SET `Admin_Status` = 'Hold Update'             WHERE `Admin_Status` = '2';
UPDATE `Ticket` SET `Admin_Status` = 'Hold Paperwork'          WHERE `Admin_Status` = '3';
UPDATE `Ticket` SET `Admin_Status` = 'Stalled'                 WHERE `Admin_Status` = '4';
UPDATE `Ticket` SET `Admin_Status` = 'Renew'                   WHERE `Admin_Status` = '5';
UPDATE `Ticket` SET `Admin_Status` = 'Finance Holdup'          WHERE `Admin_Status` = '6';
UPDATE `Ticket` SET `Admin_Status` = 'Cancelled'               WHERE `Admin_Status` = '9';
UPDATE `Ticket` SET `Admin_Status` = 'Hold Registrar Approval' WHERE `Admin_Status` = '10';
UPDATE `Ticket` SET `Admin_Status` = 'Documents Submitted'     WHERE `Admin_Status` = '11';
ALTER TABLE `Ticket` MODIFY COLUMN `Admin_Status` ENUM('New', 'Passed', 'Hold Update', 'Hold Paperwork',
                                                       'Stalled', 'Renew', 'Finance Holdup', 'Cancelled',
                                                       'Hold Registrar Approval', 'Documents Submitted') NOT NULL;
ALTER TABLE `Ticket` MODIFY COLUMN `Tech_Status` VARCHAR(7);
UPDATE `Ticket` SET `Tech_Status` = 'New'     WHERE `Tech_Status` = '0';
UPDATE `Ticket` SET `Tech_Status` = 'Passed'  WHERE `Tech_Status` = '1';
UPDATE `Ticket` SET `Tech_Status` = 'Stalled' WHERE `Tech_Status` = '2';
ALTER TABLE `Ticket` MODIFY COLUMN `Tech_Status` ENUM('New', 'Passed', 'Stalled') NOT NULL;
ALTER TABLE `Ticket` MODIFY COLUMN `Financial_Status` VARCHAR(7);
UPDATE `Ticket` SET `Financial_Status` = 'New'     WHERE `Financial_Status` = '0';
UPDATE `Ticket` SET `Financial_Status` = 'Passed'  WHERE `Financial_Status` = '1';
UPDATE `Ticket` SET `Financial_Status` = 'Stalled' WHERE `Financial_Status` = '2';
ALTER TABLE `Ticket` MODIFY COLUMN `Financial_Status` ENUM('New', 'Passed', 'Stalled') NOT NULL;
ALTER TABLE `Ticket` MODIFY COLUMN `Customer_Status` VARCHAR(9);
UPDATE `Ticket` SET `Customer_Status` = 'New'       WHERE `Customer_Status` = '0';
UPDATE `Ticket` SET `Customer_Status` = 'Cancelled' WHERE `Customer_Status` = '9';
ALTER TABLE `Ticket` MODIFY COLUMN `Customer_Status` ENUM('New', 'Cancelled') NOT NULL;

ALTER TABLE `TicketHist` MODIFY COLUMN `Admin_Status` VARCHAR(23);
UPDATE `TicketHist` SET `Admin_Status` = 'New'                     WHERE `Admin_Status` = '0';
UPDATE `TicketHist` SET `Admin_Status` = 'Passed'                  WHERE `Admin_Status` = '1';
UPDATE `TicketHist` SET `Admin_Status` = 'Hold Update'             WHERE `Admin_Status` = '2';
UPDATE `TicketHist` SET `Admin_Status` = 'Hold Paperwork'          WHERE `Admin_Status` = '3';
UPDATE `TicketHist` SET `Admin_Status` = 'Stalled'                 WHERE `Admin_Status` = '4';
UPDATE `TicketHist` SET `Admin_Status` = 'Renew'                   WHERE `Admin_Status` = '5';
UPDATE `TicketHist` SET `Admin_Status` = 'Finance Holdup'          WHERE `Admin_Status` = '6';
UPDATE `TicketHist` SET `Admin_Status` = 'Cancelled'               WHERE `Admin_Status` = '9';
UPDATE `TicketHist` SET `Admin_Status` = 'Hold Registrar Approval' WHERE `Admin_Status` = '10';
UPDATE `TicketHist` SET `Admin_Status` = 'Documents Submitted'     WHERE `Admin_Status` = '11';
ALTER TABLE `TicketHist` MODIFY COLUMN `Admin_Status` ENUM('New', 'Passed', 'Hold Update', 'Hold Paperwork',
                                                       'Stalled', 'Renew', 'Finance Holdup', 'Cancelled',
                                                       'Hold Registrar Approval', 'Documents Submitted') NOT NULL;
ALTER TABLE `TicketHist` MODIFY COLUMN `Tech_Status` VARCHAR(7);
UPDATE `TicketHist` SET `Tech_Status` = 'New'     WHERE `Tech_Status` = '0';
UPDATE `TicketHist` SET `Tech_Status` = 'Passed'  WHERE `Tech_Status` = '1';
UPDATE `TicketHist` SET `Tech_Status` = 'Stalled' WHERE `Tech_Status` = '2';
ALTER TABLE `TicketHist` MODIFY COLUMN `Tech_Status` ENUM('New', 'Passed', 'Stalled') NOT NULL;
ALTER TABLE `TicketHist` MODIFY COLUMN `Financial_Status` VARCHAR(7);
UPDATE `TicketHist` SET `Financial_Status` = 'New'     WHERE `Financial_Status` = '0';
UPDATE `TicketHist` SET `Financial_Status` = 'Passed'  WHERE `Financial_Status` = '1';
UPDATE `TicketHist` SET `Financial_Status` = 'Stalled' WHERE `Financial_Status` = '2';
ALTER TABLE `TicketHist` MODIFY COLUMN `Financial_Status` ENUM('New', 'Passed', 'Stalled') NOT NULL;
ALTER TABLE `TicketHist` MODIFY COLUMN `Customer_Status` VARCHAR(9);
UPDATE `TicketHist` SET `Customer_Status` = 'New'       WHERE `Customer_Status` = '0';
UPDATE `TicketHist` SET `Customer_Status` = 'Cancelled' WHERE `Customer_Status` = '9';
ALTER TABLE `TicketHist` MODIFY COLUMN `Customer_Status` ENUM('New', 'Cancelled') NOT NULL;

ALTER TABLE `Ticket`
  MODIFY COLUMN `DN_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `DH_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `AC_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `ANH1_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `ANH2_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `TNH_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `BNH_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `Class_Id_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `Category_Id_Fail_Cd` VARCHAR(9);
UPDATE `Ticket` SET `DN_Fail_Cd` = NULL                 WHERE `DN_Fail_Cd` = '0';
UPDATE `Ticket` SET `DN_Fail_Cd` = 'Incorrect'          WHERE `DN_Fail_Cd` IS NOT NULL;
UPDATE `Ticket` SET `DH_Fail_Cd` = NULL                 WHERE `DH_Fail_Cd` = '0';
UPDATE `Ticket` SET `DH_Fail_Cd` = 'Incorrect'          WHERE `DH_Fail_Cd` IS NOT NULL;
UPDATE `Ticket` SET `AC_Fail_Cd` = NULL                 WHERE `AC_Fail_Cd` = '0';
UPDATE `Ticket` SET `AC_Fail_Cd` = 'Incorrect'          WHERE `AC_Fail_Cd` IS NOT NULL;
UPDATE `Ticket` SET `ANH1_Fail_Cd` = NULL               WHERE `ANH1_Fail_Cd` = '0';
UPDATE `Ticket` SET `ANH1_Fail_Cd` = 'Incorrect'        WHERE `ANH1_Fail_Cd` IS NOT NULL;
UPDATE `Ticket` SET `ANH2_Fail_Cd` = NULL               WHERE `ANH2_Fail_Cd` = '0';
UPDATE `Ticket` SET `ANH2_Fail_Cd` = 'Incorrect'        WHERE `ANH2_Fail_Cd` IS NOT NULL;
UPDATE `Ticket` SET `TNH_Fail_Cd` = NULL                WHERE `TNH_Fail_Cd` = '0';
UPDATE `Ticket` SET `TNH_Fail_Cd` = 'Incorrect'         WHERE `TNH_Fail_Cd` IS NOT NULL;
UPDATE `Ticket` SET `BNH_Fail_Cd` = NULL                WHERE `BNH_Fail_Cd` = '0';
UPDATE `Ticket` SET `BNH_Fail_Cd` = 'Incorrect'         WHERE `BNH_Fail_Cd` IS NOT NULL;
UPDATE `Ticket` SET `Class_Id_Fail_Cd` = NULL           WHERE `Class_Id_Fail_Cd` = '0';
UPDATE `Ticket` SET `Class_Id_Fail_Cd` = 'Incorrect'    WHERE `Class_Id_Fail_Cd` IS NOT NULL;
UPDATE `Ticket` SET `Category_Id_Fail_Cd` = NULL        WHERE `Category_Id_Fail_Cd` = '0';
UPDATE `Ticket` SET `Category_Id_Fail_Cd` = 'Incorrect' WHERE `Category_Id_Fail_Cd` IS NOT NULL;
ALTER TABLE `Ticket`
  MODIFY COLUMN `DN_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `DH_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `AC_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `ANH1_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `ANH2_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `TNH_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `BNH_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `Class_Id_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `Category_Id_Fail_Cd` ENUM('Incorrect') DEFAULT NULL;
ALTER TABLE `TicketHist`
  MODIFY COLUMN `DN_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `DH_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `AC_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `ANH1_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `ANH2_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `TNH_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `BNH_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `Class_Id_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `Category_Id_Fail_Cd` VARCHAR(9);
UPDATE `TicketHist` SET `DN_Fail_Cd` = NULL                 WHERE `DN_Fail_Cd` = '0';
UPDATE `TicketHist` SET `DN_Fail_Cd` = 'Incorrect'          WHERE `DN_Fail_Cd` IS NOT NULL;
UPDATE `TicketHist` SET `DH_Fail_Cd` = NULL                 WHERE `DH_Fail_Cd` = '0';
UPDATE `TicketHist` SET `DH_Fail_Cd` = 'Incorrect'          WHERE `DH_Fail_Cd` IS NOT NULL;
UPDATE `TicketHist` SET `AC_Fail_Cd` = NULL                 WHERE `AC_Fail_Cd` = '0';
UPDATE `TicketHist` SET `AC_Fail_Cd` = 'Incorrect'          WHERE `AC_Fail_Cd` IS NOT NULL;
UPDATE `TicketHist` SET `ANH1_Fail_Cd` = NULL               WHERE `ANH1_Fail_Cd` = '0';
UPDATE `TicketHist` SET `ANH1_Fail_Cd` = 'Incorrect'        WHERE `ANH1_Fail_Cd` IS NOT NULL;
UPDATE `TicketHist` SET `ANH2_Fail_Cd` = NULL               WHERE `ANH2_Fail_Cd` = '0';
UPDATE `TicketHist` SET `ANH2_Fail_Cd` = 'Incorrect'        WHERE `ANH2_Fail_Cd` IS NOT NULL;
UPDATE `TicketHist` SET `TNH_Fail_Cd` = NULL                WHERE `TNH_Fail_Cd` = '0';
UPDATE `TicketHist` SET `TNH_Fail_Cd` = 'Incorrect'         WHERE `TNH_Fail_Cd` IS NOT NULL;
UPDATE `TicketHist` SET `BNH_Fail_Cd` = NULL                WHERE `BNH_Fail_Cd` = '0';
UPDATE `TicketHist` SET `BNH_Fail_Cd` = 'Incorrect'         WHERE `BNH_Fail_Cd` IS NOT NULL;
UPDATE `TicketHist` SET `Class_Id_Fail_Cd` = NULL           WHERE `Class_Id_Fail_Cd` = '0';
UPDATE `TicketHist` SET `Class_Id_Fail_Cd` = 'Incorrect'    WHERE `Class_Id_Fail_Cd` IS NOT NULL;
UPDATE `TicketHist` SET `Category_Id_Fail_Cd` = NULL        WHERE `Category_Id_Fail_Cd` = '0';
UPDATE `TicketHist` SET `Category_Id_Fail_Cd` = 'Incorrect' WHERE `Category_Id_Fail_Cd` IS NOT NULL;
ALTER TABLE `TicketHist`
  MODIFY COLUMN `DN_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `DH_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `AC_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `ANH1_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `ANH2_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `TNH_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `BNH_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `Class_Id_Fail_Cd` ENUM('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `Category_Id_Fail_Cd` ENUM('Incorrect') DEFAULT NULL;

ALTER TABLE `TicketNameserver`
  MODIFY COLUMN `TN_Name_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `TN_IPv4_Fail_Cd` VARCHAR(30),
  MODIFY COLUMN `TN_IPv6_Fail_Cd` VARCHAR(30);
UPDATE `TicketNameserver` SET `TN_Name_Fail_Cd` = 'Incorrect' WHERE `TN_Name_Fail_Cd` IS NOT NULL;
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'Incorrect' WHERE `TN_IPv4_Fail_Cd` IS NOT NULL
  AND `TN_IPv4_Fail_Cd` NOT IN ('21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', '33', '34',
                                '35', '36', '37', '38', '39', '40', '41', '42', '43', '50', '51', '52', '53');
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP buffer too small' WHERE `TN_IPv4_Fail_Cd` = '21';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP Destination net unreachable' WHERE `TN_IPv4_Fail_Cd` = '22';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP dest host unreachable' WHERE `TN_IPv4_Fail_Cd` = '23';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP dest port unreachable' WHERE `TN_IPv4_Fail_Cd` = '24';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP dest port unreachable' WHERE `TN_IPv4_Fail_Cd` = '25';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP no resources' WHERE `TN_IPv4_Fail_Cd` = '26';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP bad option' WHERE `TN_IPv4_Fail_Cd` = '27';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP hw_error' WHERE `TN_IPv4_Fail_Cd` = '28';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP packet too_big' WHERE `TN_IPv4_Fail_Cd` = '29';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP req timed out' WHERE `TN_IPv4_Fail_Cd` = '30';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP bad req' WHERE `TN_IPv4_Fail_Cd` = '31';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP bad route' WHERE `TN_IPv4_Fail_Cd` = '32';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP ttl expired transit' WHERE `TN_IPv4_Fail_Cd` = '33';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP ttl expired reassem' WHERE `TN_IPv4_Fail_Cd` = '34';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP param_problem' WHERE `TN_IPv4_Fail_Cd` = '35';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP source quench' WHERE `TN_IPv4_Fail_Cd` = '36';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP option too_big' WHERE `TN_IPv4_Fail_Cd` = '37';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP bad destination' WHERE `TN_IPv4_Fail_Cd` = '38';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP addr deleted' WHERE `TN_IPv4_Fail_Cd` = '39';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP spec mtu change' WHERE `TN_IPv4_Fail_Cd` = '40';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP mtu_change' WHERE `TN_IPv4_Fail_Cd` = '41';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP unload' WHERE `TN_IPv4_Fail_Cd` = '42';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP addr added' WHERE `TN_IPv4_Fail_Cd` = '43';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP general failure' WHERE `TN_IPv4_Fail_Cd` = '50';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'IP pending' WHERE `TN_IPv4_Fail_Cd` = '51';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'Ping timeout' WHERE `TN_IPv4_Fail_Cd` = '52';
UPDATE `TicketNameserver` SET `TN_IPv4_Fail_Cd` = 'Unknown msg returned' WHERE `TN_IPv4_Fail_Cd` = '53';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'Incorrect' WHERE `TN_IPv6_Fail_Cd` IS NOT NULL
  AND `TN_IPv6_Fail_Cd` NOT IN ('21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', '33', '34',
                                '35', '36', '37', '38', '39', '40', '41', '42', '43', '50', '51', '52', '53');
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP buffer too small' WHERE `TN_IPv6_Fail_Cd` = '21';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP Destination net unreachable' WHERE `TN_IPv6_Fail_Cd` = '22';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP dest host unreachable' WHERE `TN_IPv6_Fail_Cd` = '23';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP dest port unreachable' WHERE `TN_IPv6_Fail_Cd` = '24';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP dest port unreachable' WHERE `TN_IPv6_Fail_Cd` = '25';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP no resources' WHERE `TN_IPv6_Fail_Cd` = '26';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP bad option' WHERE `TN_IPv6_Fail_Cd` = '27';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP hw_error' WHERE `TN_IPv6_Fail_Cd` = '28';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP packet too_big' WHERE `TN_IPv6_Fail_Cd` = '29';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP req timed out' WHERE `TN_IPv6_Fail_Cd` = '30';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP bad req' WHERE `TN_IPv6_Fail_Cd` = '31';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP bad route' WHERE `TN_IPv6_Fail_Cd` = '32';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP ttl expired transit' WHERE `TN_IPv6_Fail_Cd` = '33';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP ttl expired reassem' WHERE `TN_IPv6_Fail_Cd` = '34';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP param_problem' WHERE `TN_IPv6_Fail_Cd` = '35';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP source quench' WHERE `TN_IPv6_Fail_Cd` = '36';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP option too_big' WHERE `TN_IPv6_Fail_Cd` = '37';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP bad destination' WHERE `TN_IPv6_Fail_Cd` = '38';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP addr deleted' WHERE `TN_IPv6_Fail_Cd` = '39';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP spec mtu change' WHERE `TN_IPv6_Fail_Cd` = '40';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP mtu_change' WHERE `TN_IPv6_Fail_Cd` = '41';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP unload' WHERE `TN_IPv6_Fail_Cd` = '42';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP addr added' WHERE `TN_IPv6_Fail_Cd` = '43';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP general failure' WHERE `TN_IPv6_Fail_Cd` = '50';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'IP pending' WHERE `TN_IPv6_Fail_Cd` = '51';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'Ping timeout' WHERE `TN_IPv6_Fail_Cd` = '52';
UPDATE `TicketNameserver` SET `TN_IPv6_Fail_Cd` = 'Unknown msg returned' WHERE `TN_IPv6_Fail_Cd` = '53';
ALTER TABLE `TicketNameserver`
  MODIFY COLUMN `TN_Name_Fail_Cd` ENUM ('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `TN_IPv4_Fail_Cd` ENUM ('Incorrect', 'IP buffer too small', 'IP Destination net unreachable',
    'IP dest host unreachable', 'IP dest port unreachable', 'IP no resources', 'IP bad option', 'IP hw_error',
    'IP packet too_big', 'IP req timed out', 'IP bad req', 'IP bad route', 'IP ttl expired transit',
    'IP ttl expired reassem', 'IP param_problem', 'IP source quench', 'IP option too_big',
    'IP bad destination', 'IP addr deleted', 'IP spec mtu change', 'IP mtu_change', 'IP unload', 'IP addr added',
    'IP general failure', 'IP pending', 'Ping timeout', 'Unknown msg returned') DEFAULT NULL,
  MODIFY COLUMN `TN_IPv6_Fail_Cd` ENUM ('Incorrect', 'IP buffer too small', 'IP Destination net unreachable',
    'IP dest host unreachable', 'IP dest port unreachable', 'IP no resources', 'IP bad option', 'IP hw_error',
    'IP packet too_big', 'IP req timed out', 'IP bad req', 'IP bad route', 'IP ttl expired transit',
    'IP ttl expired reassem', 'IP param_problem', 'IP source quench', 'IP option too_big',
    'IP bad destination', 'IP addr deleted', 'IP spec mtu change', 'IP mtu_change', 'IP unload', 'IP addr added',
    'IP general failure', 'IP pending', 'Ping timeout', 'Unknown msg returned') DEFAULT NULL;
ALTER TABLE `TicketNameserverHist`
  MODIFY COLUMN `TN_Name_Fail_Cd` VARCHAR(9),
  MODIFY COLUMN `TN_IPv4_Fail_Cd` VARCHAR(30),
  MODIFY COLUMN `TN_IPv6_Fail_Cd` VARCHAR(30);
UPDATE `TicketNameserverHist` SET `TN_Name_Fail_Cd` = 'Incorrect' WHERE `TN_Name_Fail_Cd` IS NOT NULL;
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'Incorrect' WHERE `TN_IPv4_Fail_Cd` IS NOT NULL
  AND `TN_IPv4_Fail_Cd` NOT IN ('21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', '33', '34',
                                '35', '36', '37', '38', '39', '40', '41', '42', '43', '50', '51', '52', '53');
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP buffer too small' WHERE `TN_IPv4_Fail_Cd` = '21';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP Destination net unreachable' WHERE `TN_IPv4_Fail_Cd` = '22';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP dest host unreachable' WHERE `TN_IPv4_Fail_Cd` = '23';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP dest port unreachable' WHERE `TN_IPv4_Fail_Cd` = '24';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP dest port unreachable' WHERE `TN_IPv4_Fail_Cd` = '25';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP no resources' WHERE `TN_IPv4_Fail_Cd` = '26';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP bad option' WHERE `TN_IPv4_Fail_Cd` = '27';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP hw_error' WHERE `TN_IPv4_Fail_Cd` = '28';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP packet too_big' WHERE `TN_IPv4_Fail_Cd` = '29';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP req timed out' WHERE `TN_IPv4_Fail_Cd` = '30';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP bad req' WHERE `TN_IPv4_Fail_Cd` = '31';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP bad route' WHERE `TN_IPv4_Fail_Cd` = '32';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP ttl expired transit' WHERE `TN_IPv4_Fail_Cd` = '33';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP ttl expired reassem' WHERE `TN_IPv4_Fail_Cd` = '34';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP param_problem' WHERE `TN_IPv4_Fail_Cd` = '35';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP source quench' WHERE `TN_IPv4_Fail_Cd` = '36';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP option too_big' WHERE `TN_IPv4_Fail_Cd` = '37';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP bad destination' WHERE `TN_IPv4_Fail_Cd` = '38';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP addr deleted' WHERE `TN_IPv4_Fail_Cd` = '39';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP spec mtu change' WHERE `TN_IPv4_Fail_Cd` = '40';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP mtu_change' WHERE `TN_IPv4_Fail_Cd` = '41';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP unload' WHERE `TN_IPv4_Fail_Cd` = '42';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP addr added' WHERE `TN_IPv4_Fail_Cd` = '43';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP general failure' WHERE `TN_IPv4_Fail_Cd` = '50';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'IP pending' WHERE `TN_IPv4_Fail_Cd` = '51';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'Ping timeout' WHERE `TN_IPv4_Fail_Cd` = '52';
UPDATE `TicketNameserverHist` SET `TN_IPv4_Fail_Cd` = 'Unknown msg returned' WHERE `TN_IPv4_Fail_Cd` = '53';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'Incorrect' WHERE `TN_IPv6_Fail_Cd` IS NOT NULL
  AND `TN_IPv6_Fail_Cd` NOT IN ('21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', '33', '34',
                                '35', '36', '37', '38', '39', '40', '41', '42', '43', '50', '51', '52', '53');
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP buffer too small' WHERE `TN_IPv6_Fail_Cd` = '21';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP Destination net unreachable' WHERE `TN_IPv6_Fail_Cd` = '22';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP dest host unreachable' WHERE `TN_IPv6_Fail_Cd` = '23';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP dest port unreachable' WHERE `TN_IPv6_Fail_Cd` = '24';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP dest port unreachable' WHERE `TN_IPv6_Fail_Cd` = '25';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP no resources' WHERE `TN_IPv6_Fail_Cd` = '26';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP bad option' WHERE `TN_IPv6_Fail_Cd` = '27';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP hw_error' WHERE `TN_IPv6_Fail_Cd` = '28';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP packet too_big' WHERE `TN_IPv6_Fail_Cd` = '29';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP req timed out' WHERE `TN_IPv6_Fail_Cd` = '30';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP bad req' WHERE `TN_IPv6_Fail_Cd` = '31';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP bad route' WHERE `TN_IPv6_Fail_Cd` = '32';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP ttl expired transit' WHERE `TN_IPv6_Fail_Cd` = '33';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP ttl expired reassem' WHERE `TN_IPv6_Fail_Cd` = '34';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP param_problem' WHERE `TN_IPv6_Fail_Cd` = '35';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP source quench' WHERE `TN_IPv6_Fail_Cd` = '36';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP option too_big' WHERE `TN_IPv6_Fail_Cd` = '37';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP bad destination' WHERE `TN_IPv6_Fail_Cd` = '38';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP addr deleted' WHERE `TN_IPv6_Fail_Cd` = '39';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP spec mtu change' WHERE `TN_IPv6_Fail_Cd` = '40';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP mtu_change' WHERE `TN_IPv6_Fail_Cd` = '41';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP unload' WHERE `TN_IPv6_Fail_Cd` = '42';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP addr added' WHERE `TN_IPv6_Fail_Cd` = '43';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP general failure' WHERE `TN_IPv6_Fail_Cd` = '50';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'IP pending' WHERE `TN_IPv6_Fail_Cd` = '51';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'Ping timeout' WHERE `TN_IPv6_Fail_Cd` = '52';
UPDATE `TicketNameserverHist` SET `TN_IPv6_Fail_Cd` = 'Unknown msg returned' WHERE `TN_IPv6_Fail_Cd` = '53';
ALTER TABLE `TicketNameserverHist`
  MODIFY COLUMN `TN_Name_Fail_Cd` ENUM ('Incorrect') DEFAULT NULL,
  MODIFY COLUMN `TN_IPv4_Fail_Cd` ENUM ('Incorrect', 'IP buffer too small', 'IP Destination net unreachable',
    'IP dest host unreachable', 'IP dest port unreachable', 'IP no resources', 'IP bad option', 'IP hw_error',
    'IP packet too_big', 'IP req timed out', 'IP bad req', 'IP bad route', 'IP ttl expired transit',
    'IP ttl expired reassem', 'IP param_problem', 'IP source quench', 'IP option too_big',
    'IP bad destination', 'IP addr deleted', 'IP spec mtu change', 'IP mtu_change', 'IP unload', 'IP addr added',
    'IP general failure', 'IP pending', 'Ping timeout', 'Unknown msg returned') DEFAULT NULL,
  MODIFY COLUMN `TN_IPv6_Fail_Cd` ENUM ('Incorrect', 'IP buffer too small', 'IP Destination net unreachable',
    'IP dest host unreachable', 'IP dest port unreachable', 'IP no resources', 'IP bad option', 'IP hw_error',
    'IP packet too_big', 'IP req timed out', 'IP bad req', 'IP bad route', 'IP ttl expired transit',
    'IP ttl expired reassem', 'IP param_problem', 'IP source quench', 'IP option too_big',
    'IP bad destination', 'IP addr deleted', 'IP spec mtu change', 'IP mtu_change', 'IP unload', 'IP addr added',
    'IP general failure', 'IP pending', 'Ping timeout', 'Unknown msg returned') DEFAULT NULL;

DROP TABLE TicketTechStatus;
DROP TABLE TicketAdminStatus;
DROP TABLE TicketFailCd;

-- SECONDARY MARKET --

-- CRS-1189

ALTER TABLE `Reservation` MODIFY COLUMN `Operation_Type` ENUM('registration', 'renewal', 'transfer', 'buy request', 'sell request') NOT NULL;
ALTER TABLE `ReservationHist` MODIFY COLUMN `Operation_Type` ENUM('registration', 'renewal', 'transfer', 'buy request', 'sell request') NOT NULL;

CREATE TABLE `SecondaryMarketPrice` (
  `ID` INT(8) unsigned NOT NULL AUTO_INCREMENT,
  `Request_Type` ENUM('buy request', 'sell request') NOT NULL,
  `Customer_Type` ENUM('Direct', 'Registrar') NOT NULL,
  `Amount` DECIMAL(5, 2) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_irish_accent_ci;

INSERT INTO `SecondaryMarketPrice` VALUES
(NULL, 'buy request', 'Direct', 25),
(NULL, 'buy request', 'Registrar', 14),
(NULL, 'sell request', 'Direct', 50),
(NULL, 'sell request', 'Registrar', 25);

-- CRS-1190

CREATE TABLE `SecondaryMarketBuyRequest` (
  `Id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `D_Name` VARCHAR(66) NOT NULL,
  `Creator_NH` VARCHAR(12) NOT NULL,
  `A_Number` INT(8) UNSIGNED NOT NULL,
  `D_Holder` VARCHAR(255) NOT NULL,
  `Class_Id` INT(11) NOT NULL,
  `Category_Id` INT(11) NOT NULL,
  `Remark` TEXT,
  `H_Remark` TEXT,
  `Admin_Name` VARCHAR(255) NOT NULL,
  `Admin_Email` VARCHAR(255) NOT NULL,
  `Admin_Co_Name` VARCHAR(255) NOT NULL,
  `Admin_Address` VARCHAR(255) NOT NULL,
  `Admin_Country_Id` INT(3) UNSIGNED NOT NULL,
  `Admin_County_Id` INT(5) UNSIGNED NOT NULL,
  `Created_TS` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Authcode` CHAR(12) UNIQUE,
  `Authcode_TS` TIMESTAMP NULL,
  `Status` ENUM('New', 'Passed', 'Hold Update', 'Hold Paperwork', 'Stalled', 'Renew', 'Finance Holdup', 'Cancelled',
                'Hold Registrar Approval', 'Documents Submitted') NOT NULL DEFAULT 'New',
  `CheckedOutTo_NH` VARCHAR(12),
  `Chng_ID` INT(10) UNSIGNED,
  FOREIGN KEY (`D_Name`) REFERENCES `Domain`(`D_Name`) ON DELETE RESTRICT,
  FOREIGN KEY (`Creator_NH`) REFERENCES `NicHandle`(`Nic_Handle`) ON DELETE RESTRICT,
  FOREIGN KEY (`A_Number`) REFERENCES `Account`(`A_Number`) ON DELETE RESTRICT,
  FOREIGN KEY (`Class_Id`) REFERENCES `Class`(`id`) ON DELETE RESTRICT,
  FOREIGN KEY (`Category_Id`) REFERENCES `Category`(`id`) ON DELETE RESTRICT,
  FOREIGN KEY (`Admin_Country_Id`) REFERENCES `Countries`(`Id`) ON DELETE RESTRICT,
  FOREIGN KEY (`Admin_County_Id`) REFERENCES `Counties`(`Id`) ON DELETE RESTRICT,
  FOREIGN KEY (`CheckedOutTo_NH`) REFERENCES `NicHandle`(`Nic_Handle`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_irish_accent_ci;
CREATE TABLE `SecondaryMarketBuyRequestTelecom` (
  `BuyRequest_Id` INT UNSIGNED NOT NULL,
  `Number` VARCHAR(25) NOT NULL,
  `Type` ENUM('Phone', 'Fax') NOT NULL,
  `Order` INT NOT NULL,
  FOREIGN KEY (`BuyRequest_Id`) REFERENCES `SecondaryMarketBuyRequest`(`Id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_irish_accent_ci;

CREATE TABLE `SecondaryMarketBuyRequestHist` (
  `Chng_ID` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `Chng_TS` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Chng_NH` VARCHAR(12) NOT NULL,
  `Id` INT UNSIGNED NOT NULL,
  `Domain_Chng_ID` INT(10) UNSIGNED NOT NULL,
  `Creator_NH_Chng_ID` INT(10) UNSIGNED NOT NULL,
  `Account_Chng_ID` INT(10) UNSIGNED NOT NULL,
  `D_Holder` VARCHAR(255) NOT NULL,
  `Class_Id` INT(11) NOT NULL,
  `Category_Id` INT(11) NOT NULL,
  `Remark` TEXT,
  `H_Remark` TEXT,
  `Admin_Name` VARCHAR(255) NOT NULL,
  `Admin_Email` VARCHAR(255) NOT NULL,
  `Admin_Co_Name` VARCHAR(255) NOT NULL,
  `Admin_Address` VARCHAR(255) NOT NULL,
  `Admin_Country_Id` INT(3) UNSIGNED NOT NULL,
  `Admin_County_Id` INT(5) UNSIGNED NOT NULL,
  `Created_TS` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Authcode` CHAR(12),
  `Authcode_TS` TIMESTAMP NULL,
  `Status` ENUM('New', 'Passed', 'Hold Update', 'Hold Paperwork', 'Stalled', 'Renew', 'Finance Holdup', 'Cancelled',
                'Hold Registrar Approval', 'Documents Submitted') NOT NULL DEFAULT 'New',
  `CheckedOutTo_Chng_ID` INT(10) UNSIGNED,
  FOREIGN KEY (`Domain_Chng_ID`) REFERENCES `DomainHist`(`Chng_ID`) ON DELETE RESTRICT,
  FOREIGN KEY (`Creator_NH_Chng_ID`) REFERENCES `NicHandleHist`(`Chng_ID`) ON DELETE RESTRICT,
  FOREIGN KEY (`Account_Chng_ID`) REFERENCES `AccountHist`(`Chng_ID`) ON DELETE RESTRICT,
  FOREIGN KEY (`Class_Id`) REFERENCES `Class`(`id`) ON DELETE RESTRICT,
  FOREIGN KEY (`Category_Id`) REFERENCES `Category`(`id`) ON DELETE RESTRICT,
  FOREIGN KEY (`Admin_Country_Id`) REFERENCES `Countries`(`Id`) ON DELETE RESTRICT,
  FOREIGN KEY (`Admin_County_Id`) REFERENCES `Counties`(`Id`) ON DELETE RESTRICT,
  FOREIGN KEY (`CheckedOutTo_Chng_ID`) REFERENCES `NicHandleHist`(`Chng_ID`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_irish_accent_ci;
CREATE TABLE `SecondaryMarketBuyRequestTelecomHist` (
  `BuyRequest_Chng_ID` INT UNSIGNED NOT NULL,
  `Number` VARCHAR(25) NOT NULL,
  `Type` ENUM('Phone', 'Fax') NOT NULL,
  `Order` INT NOT NULL,
  FOREIGN KEY (`BuyRequest_Chng_ID`) REFERENCES `SecondaryMarketBuyRequestHist`(`Chng_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_irish_accent_ci;

ALTER TABLE `SecondaryMarketBuyRequest`
  ADD FOREIGN KEY (`Chng_ID`) REFERENCES `SecondaryMarketBuyRequestHist`(`Chng_ID`) ON DELETE RESTRICT;

-- CRS-1191

CREATE TABLE `SecondaryMarketSellRequest` (
  `Id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `Creator_NH` VARCHAR(12) NOT NULL,
  `Created_TS` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `BuyRequest_Id` INT UNSIGNED NOT NULL UNIQUE,
  `Chng_ID` INT(10) UNSIGNED,
  FOREIGN KEY (`Creator_NH`) REFERENCES `NicHandle`(`Nic_Handle`) ON DELETE RESTRICT,
  FOREIGN KEY (`BuyRequest_Id`) REFERENCES `SecondaryMarketBuyRequest`(`Id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_irish_accent_ci;

CREATE TABLE `SecondaryMarketSellRequestHist` (
  `Chng_ID` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `Chng_TS` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Chng_NH` VARCHAR(12) NOT NULL,
  `Id` INT UNSIGNED NOT NULL,
  `Creator_NH_Chng_ID` INT(10) UNSIGNED NOT NULL,
  `Created_TS` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `BuyRequest_Chng_ID` INT(10) UNSIGNED NOT NULL,
  FOREIGN KEY (`Creator_NH_Chng_ID`) REFERENCES `NicHandleHist`(`Chng_ID`) ON DELETE RESTRICT,
  FOREIGN KEY (`BuyRequest_Chng_ID`) REFERENCES `SecondaryMarketBuyRequestHist`(`Chng_ID`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_irish_accent_ci;

-- CRS-1187

ALTER TABLE `DSM_State` ADD COLUMN `Secondary_Market_Status` ENUM('NP', 'BR', 'SR', 'N/A') NOT NULL AFTER `NRP_Status`;
UPDATE `DSM_State` SET `Secondary_Market_Status` = 'NP';
UPDATE `DSM_State` SET `Secondary_Market_Status` = 'N/A' WHERE `State` IN (0, 385, 386, 387, 628, 629);

-- CRS-1188

INSERT INTO `DSM_State`
    SELECT `State` + 1024, `Published`, `D_Holder_Type`, `Renewal_Mode`, `Locked`, `Cust_Type`,
        `NRP_Status`, 'BR', `Comment`
    FROM `DSM_State` WHERE `NRP_Status` IN ('A', 'IM', 'IS', 'IMPP', 'ISPP', 'VM', 'VS');
INSERT INTO `DSM_State`
    SELECT `State` + 1024, `Published`, `D_Holder_Type`, `Renewal_Mode`, `Locked`, `Cust_Type`,
        `NRP_Status`, 'SR', `Comment`
    FROM `DSM_State` WHERE `NRP_Status` IN ('A', 'IM', 'IS', 'IMPP', 'ISPP')
        AND `Secondary_Market_Status` = 'BR' AND Locked = 'NO';

INSERT INTO `DSM_Event` VALUES
    (36, 'RegisterBuyRequest', NULL),
    (37, 'CancelLastBuyRequest', NULL),
    (38, 'RegisterSellRequest', NULL),
    (39, 'CancelSellRequest', NULL),
    (40, 'CompleteSellRequest', NULL);

INSERT INTO `DSM_Transition` (`Event_Id`, `Begin_State`, `End_State`)
    SELECT (SELECT `Id` FROM `DSM_Event` WHERE `Name` = 'RegisterBuyRequest'), Begin_DSM.State,
        End_DSM.State
    FROM `DSM_State` Begin_DSM INNER JOIN `DSM_State` End_DSM
        ON Begin_DSM.Published = End_DSM.Published
        AND Begin_DSM.D_Holder_Type = End_DSM.D_Holder_Type
        AND Begin_DSM.Renewal_Mode = End_DSM.Renewal_Mode AND Begin_DSM.Locked = End_DSM.Locked
        AND Begin_DSM.Cust_Type = End_DSM.Cust_Type AND Begin_DSM.NRP_Status = End_DSM.NRP_Status
    WHERE Begin_DSM.NRP_Status = 'A' AND Begin_DSM.Secondary_Market_Status IN ('NP', 'BR')
        AND End_DSM.Secondary_Market_Status = 'BR';

INSERT INTO `DSM_Transition` (`Event_Id`, `Begin_State`, `End_State`)
    SELECT (SELECT `Id` FROM `DSM_Event` WHERE `Name` = 'CancelLastBuyRequest'), Begin_DSM.State,
        End_DSM.State
    FROM `DSM_State` Begin_DSM INNER JOIN `DSM_State` End_DSM
        ON Begin_DSM.Published = End_DSM.Published
        AND Begin_DSM.D_Holder_Type = End_DSM.D_Holder_Type
        AND Begin_DSM.Renewal_Mode = End_DSM.Renewal_Mode AND Begin_DSM.Locked = End_DSM.Locked
        AND Begin_DSM.Cust_Type = End_DSM.Cust_Type AND Begin_DSM.NRP_Status = End_DSM.NRP_Status
    WHERE Begin_DSM.Secondary_Market_Status = 'BR' AND End_DSM.Secondary_Market_Status = 'NP';

INSERT INTO `DSM_Transition` (`Event_Id`, `Begin_State`, `End_State`)
    SELECT (SELECT `Id` FROM `DSM_Event` WHERE `Name` = 'RegisterSellRequest'), Begin_DSM.State,
        End_DSM.State
    FROM `DSM_State` Begin_DSM INNER JOIN `DSM_State` End_DSM
        ON Begin_DSM.Published = End_DSM.Published
        AND Begin_DSM.D_Holder_Type = End_DSM.D_Holder_Type
        AND Begin_DSM.Renewal_Mode = End_DSM.Renewal_Mode AND Begin_DSM.Locked = End_DSM.Locked
        AND Begin_DSM.Cust_Type = End_DSM.Cust_Type AND Begin_DSM.NRP_Status = End_DSM.NRP_Status
    WHERE Begin_DSM.NRP_Status = 'A' AND Begin_DSM.Locked = 'NO'
        AND Begin_DSM.Secondary_Market_Status IN ('NP', 'BR')
        AND End_DSM.Secondary_Market_Status = 'SR';

INSERT INTO `DSM_Transition` (`Event_Id`, `Begin_State`, `End_State`)
    SELECT (SELECT `Id` FROM `DSM_Event` WHERE `Name` = 'CancelSellRequest'), Begin_DSM.State,
        End_DSM.State
    FROM `DSM_State` Begin_DSM INNER JOIN `DSM_State` End_DSM
        ON Begin_DSM.Published = End_DSM.Published
        AND Begin_DSM.D_Holder_Type = End_DSM.D_Holder_Type
        AND Begin_DSM.Renewal_Mode = End_DSM.Renewal_Mode AND Begin_DSM.Locked = End_DSM.Locked
        AND Begin_DSM.Cust_Type = End_DSM.Cust_Type AND Begin_DSM.NRP_Status = End_DSM.NRP_Status
    WHERE Begin_DSM.Secondary_Market_Status = 'SR' AND End_DSM.Secondary_Market_Status = 'NP';

INSERT INTO `DSM_Transition` (`Event_Id`, `Begin_State`, `End_State`)
    SELECT (SELECT `Id` FROM `DSM_Event` WHERE `Name` = 'CompleteSellRequest'), Begin_DSM.State,
        End_DSM.State
    FROM `DSM_State` Begin_DSM INNER JOIN `DSM_State` End_DSM
        ON Begin_DSM.Published = End_DSM.Published AND Begin_DSM.Locked = End_DSM.Locked
        AND Begin_DSM.Cust_Type = End_DSM.Cust_Type AND Begin_DSM.NRP_Status = End_DSM.NRP_Status
    WHERE Begin_DSM.Secondary_Market_Status = 'SR' AND End_DSM.Secondary_Market_Status = 'NP'
        AND End_DSM.D_Holder_Type = 'B' AND End_DSM.Renewal_Mode = 'N';

-- R_ is for DSM states with Buy/Sell Requests registered, NP_ is for DSM states not in process.
INSERT INTO `DSM_Transition` (`Event_Id`, `Begin_State`, `End_State`)
    SELECT NP_Transition.Event_Id, R_Begin_DSM.State,  R_End_DSM.State
    FROM `DSM_State` R_Begin_DSM
    INNER JOIN `DSM_State` NP_Begin_DSM
        ON R_Begin_DSM.Published = NP_Begin_DSM.Published
        AND R_Begin_DSM.D_Holder_Type = NP_Begin_DSM.D_Holder_Type
        AND R_Begin_DSM.Renewal_Mode = NP_Begin_DSM.Renewal_Mode
        AND R_Begin_DSM.Locked = NP_Begin_DSM.Locked
        AND R_Begin_DSM.Cust_Type = NP_Begin_DSM.Cust_Type
        AND R_Begin_DSM.NRP_Status = NP_Begin_DSM.NRP_Status
    INNER JOIN `DSM_Transition` NP_Transition ON NP_Transition.Begin_State = NP_Begin_DSM.State
    INNER JOIN `DSM_Event` ON DSM_Event.Id = NP_Transition.Event_Id
    LEFT JOIN `DSM_State` NP_End_DSM ON NP_End_DSM.State = NP_Transition.End_State
    LEFT JOIN `DSM_State` R_End_DSM
        ON R_End_DSM.Published = NP_End_DSM.Published
        AND R_End_DSM.D_Holder_Type = NP_End_DSM.D_Holder_Type
        AND R_End_DSM.Renewal_Mode = NP_End_DSM.Renewal_Mode
        AND (R_End_DSM.Locked = NP_End_DSM.Locked OR (R_End_DSM.Locked IS NULL AND NP_End_DSM.Locked IS NULL))
        AND R_End_DSM.Cust_Type = NP_End_DSM.Cust_Type
        AND R_End_DSM.NRP_Status = NP_End_DSM.NRP_Status
    WHERE (
        -- Transitions for DSM states with Buy Requests Registered
        (R_Begin_DSM.Secondary_Market_Status = 'BR' AND NP_Begin_DSM.Secondary_Market_Status = 'NP')
        AND ((R_End_DSM.Secondary_Market_Status = 'BR' AND NP_End_DSM.Secondary_Market_Status = 'NP')
            OR (R_End_DSM.Secondary_Market_Status = 'N/A' AND NP_End_DSM.Secondary_Market_Status = 'N/A'))
        AND DSM_Event.Name IN ('RenewalDatePasses', 'SuspensionDatePasses', 'DeletionDatePasses', 'EnterVoluntaryNRP',
            'RemoveFromVoluntaryNRP', 'PaymentInitiated', 'PaymentSettled', 'SettlementFailure', 'SetAutoRenew',
            'SetNoAutoRenew', 'SetOnceAutoRenew', 'SetCharity', 'SetBillable', 'SetNonBillable', 'Lock', 'Unlock')
    ) OR (
        -- Transitions for DSM states with Sell Requests Registered
        (R_Begin_DSM.Secondary_Market_Status = 'SR' AND NP_Begin_DSM.Secondary_Market_Status = 'NP')
        AND ((R_End_DSM.Secondary_Market_Status = 'SR' AND NP_End_DSM.Secondary_Market_Status = 'NP')
            OR (R_End_DSM.Secondary_Market_Status = 'N/A' AND NP_End_DSM.Secondary_Market_Status = 'N/A'))
        AND DSM_Event.Name IN ('RenewalDatePasses', 'SuspensionDatePasses', 'DeletionDatePasses',
            'PaymentInitiated', 'PaymentSettled', 'SettlementFailure')
    );

INSERT INTO `DSM_Transition_Action`
        (`Transition_Id`, `Order`, `Action_Id`, `Action_Param`, `Execute_After_DSM_Change`)
    SELECT T.Id, 0, (SELECT `Id` FROM `DSM_Action` WHERE `Action_Name` = 'Email'), 195, 'YES'
    FROM `DSM_Transition` T LEFT JOIN `DSM_Event` E ON E.Id = T.Event_Id
    WHERE E.Name = 'CancelLastBuyRequest';

INSERT INTO `DSM_Transition_Action`
        (`Transition_Id`, `Order`, `Action_Id`, `Action_Param`, `Execute_After_DSM_Change`)
    SELECT T.Id, 0, (SELECT `Id` FROM `DSM_Action` WHERE `Action_Name` = 'Email'), 197, 'YES'
    FROM `DSM_Transition` T LEFT JOIN `DSM_Event` E ON E.Id = T.Event_Id
    WHERE E.Name = 'CancelSellRequest';

INSERT INTO `DSM_Transition_Action`
        (`Transition_Id`, `Order`, `Action_Id`, `Action_Param`, `Execute_After_DSM_Change`)
    SELECT R_T.Id, NP_TA.`Order`, NP_TA.Action_Id, NP_TA.Action_Param, NP_TA.Execute_After_DSM_Change
    FROM `DSM_Transition` R_T
    LEFT JOIN `DSM_Event` ON DSM_Event.Id = R_T.Event_Id
    LEFT JOIN `DSM_State` R_Begin_DSM ON R_Begin_DSM.State = R_T.Begin_State
    LEFT JOIN `DSM_State` NP_Begin_DSM
        ON R_Begin_DSM.Published = NP_Begin_DSM.Published
        AND R_Begin_DSM.D_Holder_Type = NP_Begin_DSM.D_Holder_Type
        AND R_Begin_DSM.Renewal_Mode = NP_Begin_DSM.Renewal_Mode
        AND R_Begin_DSM.Locked = NP_Begin_DSM.Locked
        AND R_Begin_DSM.Cust_Type = NP_Begin_DSM.Cust_Type
        AND R_Begin_DSM.NRP_Status = NP_Begin_DSM.NRP_Status
    INNER JOIN `DSM_Transition` NP_T ON NP_T.Begin_State = NP_Begin_DSM.State
    INNER JOIN `DSM_Transition_Action` NP_TA ON NP_TA.Transition_Id = NP_T.Id
    WHERE R_Begin_DSM.Secondary_Market_Status IN ('BR', 'SR')
        AND NP_Begin_DSM.Secondary_Market_Status = 'NP'
        AND NP_T.Event_Id = R_T.Event_Id
        AND DSM_Event.Name IN ('RenewalDatePasses', 'SuspensionDatePasses', 'DeletionDatePasses', 'EnterVoluntaryNRP',
            'RemoveFromVoluntaryNRP', 'PaymentInitiated', 'PaymentSettled', 'SettlementFailure', 'SetAutoRenew',
            'SetNoAutoRenew', 'SetOnceAutoRenew', 'SetCharity', 'SetBillable', 'SetNonBillable', 'Lock', 'Unlock');

INSERT INTO `Email` (`E_ID`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `E_Suppressible`) VALUES
    (194, '\n\n**IMPORTANT NOTICE – PLEASE READ**\n\nWe are contacting you to confirm that a registrant transfer request for the domain $DOMAIN$ has been received from an interested buyer.\n\nThis request is pending, and no changes will be made to your domain registration unless you request this with your Billing Contact (Registrar).\n\nPlease note that it will not be possible to transfer the management of the domain to another Billing Contact until this process is either completed or cancelled.\n\nIf you have any queries relating to this matter, please contact your Billing Contact in the first instance, or contact the IE Domain Registry’s Registrations Department on 01-2365400.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Registrant Transfer – Buyer Request Created', '$ADMIN-C_EMAIL$', '$BILL-C_EMAIL$', NULL, 'registrations@iedr.ie', NULL, NULL, 'registrations@iedr.ie', 'NO'),
    (195, '\n\n***IMPORTANT NOTICE – PLEASE READ***\n\nDomain Name: $DOMAIN$\n\nPlease note that a pending registrant transfer request for this domain has been cancelled and you can now modify your domain, as required.\n\nIf you have any queries relating to this, please contact your Registrar in the first instance, or the IEDR Registration Services Department on 01-2365400.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Registrant Transfer – Buyer Request Cancellation', '$ADMIN-C_EMAIL$', '$BILL-C_EMAIL$', NULL, 'registrations@iedr.ie', NULL, NULL, 'registrations@iedr.ie', 'NO'),
    (196, '\n\n***IMPORTANT NOTICE – PLEASE READ***\n\nDomain Name: $DOMAIN$\n\nYour registrant transfer request has been received, and this domain registration will be transferred to $HOLDER$.\n\nThis domain will now enter the $COUNTDOWN_PERIOD$-day countdown period, during which time no edits can be made to the registration.\n\nThe new domain holder will be able to edit this domain at 3pm, $COUNTDOWN_PERIOD$ days from today.\n\nIf you have any queries relating to this, please contact your Registrar in the first instance, or the IEDR Registration Services Department on 01-2365400.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Registrant Transfer – Request Received', '$BUYER_EMAIL$, $ADMIN-C_EMAIL$', '$BILL-C_EMAIL$', NULL, 'registrations@iedr.ie', NULL, NULL, 'registrations@iedr.ie', 'NO'),
    (197, '\n\n***IMPORTANT NOTICE – PLEASE READ***\n\nDomain Name: $DOMAIN$\n\nPlease note that this registrant transfer request for this domain has been cancelled during the $COUNTDOWN_PERIOD$-day countdown period.\n\nIf you have any queries relating to this, please contact your Registrar in the first instance, or the IEDR Registration Services Department on 01-2365400.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Registrant Transfer – Request Cancelled', '$BUYER_EMAIL$, $ADMIN-C_EMAIL$', '$BILL-C_EMAIL$', NULL, 'registrations@iedr.ie', NULL, NULL, 'registrations@iedr.ie', 'NO');

-- CRS-1192

INSERT INTO `Email` (`E_ID`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `E_Suppressible`) VALUES
    (198, '\n\n***IMPORTANT NOTICE – PLEASE READ***\n\nRequest ID: $REQUEST_ID$ - Domain Name: $DOMAIN$\n\nThank you for submitting this registrant transfer request.\n\nPlease note that the credit card payment for your non-refundable administration fee of €$TRANSACTION_VALUE$ has been received.\n\nOur Registrations Services Department will review your request and respond to you or your Registrar within 1-2 working days.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Registrant Transfer – Buyer Credit Card Payment Received', '$NIC_EMAIL$', NULL, NULL, 'registrations@iedr.ie', NULL, 'accounts@iedr.ie', 'registrations@iedr.ie', 'NO'),
    (199, '\n\n***IMPORTANT NOTICE – PLEASE READ***\n\nRequest ID: $REQUEST_ID$ - Domain Name: $DOMAIN$\n\nThank you for submitting this registrant transfer request.\n\nPlease note that payment of the non-refundable administration fee of €$TRANSACTION_VALUE$ has been deducted from your deposit account.\n\nOur Registrations Services Department will review your request and respond to you or your Registrar within 1-2 working days.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Registrant Transfer – Buyer Deposit Account Payment Deducted', '$NIC_EMAIL$', '$BILL-C_EMAIL$', NULL, 'registrations@iedr.ie', NULL, 'accounts@iedr.ie', 'registrations@iedr.ie', 'NO'),
    (200, '\n\nRequest ID: $REQUEST_ID$ - Domain Name: $DOMAIN$\n\nThank you for your request.\n\nOur Registration Services Team will review this within 1-2 working days, and will contact you then with further information.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Registrant Transfer – Buyer Request Received', '$BUYER_EMAIL$', '$BILL-C_EMAIL$, $NIC_EMAIL$', NULL, 'registrations@iedr.ie', NULL, NULL, 'registrations@iedr.ie', 'NO');

-- CRS-1193

INSERT INTO `Email` (`E_ID`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `E_Suppressible`) VALUES
    (201, '\n\n***IMPORTANT NOTICE – PLEASE READ***\n\nRequest ID: $REQUEST_ID$ - Domain Name: $DOMAIN$\n\nThank you for submitting this registrant transfer request.\n\nPlease note that the credit card payment for your non-refundable administration fee of €$TRANSACTION_VALUE$ has been received.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Registrant Transfer – Seller Credit Card Payment Received', '$NIC_EMAIL$', '$BILL-C_EMAIL$', NULL, 'registrations@iedr.ie', NULL, 'accounts@iedr.ie', 'registrations@iedr.ie', 'NO'),
    (202, '\n\n***IMPORTANT NOTICE – PLEASE READ***\n\nRequest ID: $REQUEST_ID$ - Domain Name: $DOMAIN$\n\nThank you for submitting this registrant transfer request.\n\nPlease note that payment of the non-refundable administration fee of €$TRANSACTION_VALUE$ has been deducted from your deposit account.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Registrant Transfer – Seller Deposit Account Payment Deducted', '$NIC_EMAIL$', '$BILL-C_EMAIL$', NULL, 'registrations@iedr.ie', NULL, 'accounts@iedr.ie', 'registrations@iedr.ie', 'NO');

-- CRS-1226

ALTER TABLE `INCOMING_DOC` ADD COLUMN `BuyRequestId` INT UNSIGNED;

-- CRS-1212

INSERT INTO `App_Config` VALUES (NULL, 'secondary_market_countdown_period', 'INT', '3');

-- CRS-1216

ALTER TABLE `SecondaryMarketSellRequest` ADD COLUMN `Status` ENUM('Processing', 'Completed', 'Cancelled') NOT NULL DEFAULT 'Processing' AFTER `Created_TS`;
ALTER TABLE `SecondaryMarketSellRequestHist` ADD COLUMN `Status` ENUM('Processing', 'Completed', 'Cancelled') NOT NULL DEFAULT 'Processing' AFTER `Created_TS`;

-- CRS-1203

INSERT INTO `Email` (`E_ID`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `E_Suppressible`) VALUES
    (203, '\n\n**IMPORTANT NOTICE – PLEASE READ**\n\nRequest ID: $REQUEST_ID$ - Domain Name: $DOMAIN$\n\nYour request has been reviewed by our Registration Services Team, who have verified that you are eligible to hold this domain, should the current holder decide to transfer the registration to you.\n\nPlease find your transfer authorisation code below, which acts as proof of your eligibility: - \n\n$BUY_REQUEST_AUTHCODE$\n\nYou should provide this authcode to the current domain holder or their Registrar, as they’ll need it to complete the registrant transfer process.\n\nPlease note that this code will expire in $DAYS_REMAINING_WITH_DAYS_SUFFIX$.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Registrant Transfer – Transfer Authcode', '$BUYER_EMAIL$', '$BILL-C_EMAIL$', NULL, 'registrations@iedr.ie', NULL, NULL, 'registrations@iedr.ie', 'NO');

-- CRS-1209

INSERT INTO `Email` (`E_ID`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `E_Suppressible`) VALUES
    (204, '\n\n***IMPORTANT NOTICE – PLEASE READ***\n\nRequest ID: $REQUEST_ID$ - Domain Name: $DOMAIN$\n\nPlease be advised that your request has been cancelled.\n\nTo discuss this matter further, please contact your Registrar in the first instance, or the IE Domain Registry’s Registration Services Department on 01-2365400.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Registrant Transfer – Buyer Request Cancelled', '$BUYER_EMAIL$', '$BILL-C_EMAIL$, $NIC_EMAIL$', NULL, 'registrations@iedr.ie', NULL, NULL, 'registrations@iedr.ie', 'NO'),
    (205, '\n\n***IMPORTANT NOTICE – PLEASE READ***\n\nRequest ID: $REQUEST_ID$ - Domain Name: $DOMAIN$\n\nPlease note that your registrant transfer authcode has been cancelled, and is no longer valid.\n\nIf you have any queries relating to this, please contact your Registrar in the first instance, or the IEDR Registration Services Department on 01-2365400.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Registrant Transfer – Authcode Cancelled', '$BUYER_EMAIL$', '$BILL-C_EMAIL$, $NIC_EMAIL$', NULL, 'registrations@iedr.ie', NULL, NULL, 'registrations@iedr.ie', 'NO');

-- CRS-1207

ALTER TABLE SecondaryMarketBuyRequest
  ADD COLUMN D_Name_FR ENUM('Incorrect') AFTER D_Name,
  ADD COLUMN D_Holder_FR ENUM('Incorrect') AFTER D_Holder,
  ADD COLUMN Class_FR ENUM('Incorrect') AFTER Class_Id,
  ADD COLUMN Category_FR ENUM('Incorrect') AFTER Category_Id,
  ADD COLUMN Admin_Name_FR ENUM('Incorrect') AFTER Admin_Name,
  ADD COLUMN Admin_Email_FR ENUM('Incorrect') AFTER Admin_Email,
  ADD COLUMN Admin_Co_Name_FR ENUM('Incorrect') AFTER Admin_Co_Name,
  ADD COLUMN Admin_Address_FR ENUM('Incorrect') AFTER Admin_Address,
  ADD COLUMN Admin_Country_FR ENUM('Incorrect') AFTER Admin_Country_Id,
  ADD COLUMN Admin_County_FR ENUM('Incorrect') AFTER Admin_County_Id,
  ADD COLUMN Admin_Phones_FR ENUM('Incorrect') AFTER Admin_County_FR,
  ADD COLUMN Admin_Faxes_FR ENUM('Incorrect') AFTER Admin_Phones_FR;

ALTER TABLE SecondaryMarketBuyRequestHist
  ADD COLUMN D_Name_FR ENUM('Incorrect') AFTER Domain_Chng_ID,
  ADD COLUMN D_Holder_FR ENUM('Incorrect') AFTER D_Holder,
  ADD COLUMN Class_FR ENUM('Incorrect') AFTER Class_Id,
  ADD COLUMN Category_FR ENUM('Incorrect') AFTER Category_Id,
  ADD COLUMN Admin_Name_FR ENUM('Incorrect') AFTER Admin_Name,
  ADD COLUMN Admin_Email_FR ENUM('Incorrect') AFTER Admin_Email,
  ADD COLUMN Admin_Co_Name_FR ENUM('Incorrect') AFTER Admin_Co_Name,
  ADD COLUMN Admin_Address_FR ENUM('Incorrect') AFTER Admin_Address,
  ADD COLUMN Admin_Country_FR ENUM('Incorrect') AFTER Admin_Country_Id,
  ADD COLUMN Admin_County_FR ENUM('Incorrect') AFTER Admin_County_Id,
  ADD COLUMN Admin_Phones_FR ENUM('Incorrect') AFTER Admin_County_FR,
  ADD COLUMN Admin_Faxes_FR ENUM('Incorrect') AFTER Admin_Phones_FR;

-- CRS-1196

INSERT INTO `App_Config` VALUES (NULL, 'secondary_market_authcode_expiration_period', 'INT', '90');

-- CRS-1220

INSERT INTO `DSM_Action` VALUES
(14, 'ApplySecondaryMarketChanges', NULL),
(15, 'ApplySecondaryMarketChangesForDirect', NULL);

INSERT INTO `DSM_Transition_Action`
(SELECT NULL, T.`Id`, 0, 14, NULL, NULL, 'NO'
FROM `DSM_Transition` T JOIN `DSM_State` S ON S.`State` = T.`Begin_State`
WHERE T.`Event_Id` = 40 AND S.`Cust_Type` = 'R');

INSERT INTO `DSM_Transition_Action`
(SELECT NULL, T.`Id`, 0, 15, NULL, NULL, 'NO'
FROM `DSM_Transition` T JOIN `DSM_State` S ON S.`State` = T.`Begin_State`
WHERE T.`Event_Id` = 40 AND S.`Cust_Type` = 'D');

INSERT INTO `NicHandleHist` VALUES
(NULL, 'SELLRQCOMPLT', 'SellRequestCompletionJob', 2, 'IEDR', 'NHAddress', 14, 121, 'asd@iedr.ie', 'Active', CURDATE(), CURDATE(), NOW(), 'NO', NULL, 'SELLRQCOMPLT', NULL, NULL, 'YES', 'IH4-IEDR', NOW());
INSERT INTO `NicHandle` VALUES
('SELLRQCOMPLT', 'SellRequestCompletionJob', 2, 'IEDR', 'NHAddress', 14, 121, 'asd@iedr.ie', 'Active', CURDATE(), CURDATE(), NOW(), 'NO', NULL, 'SELLRQCOMPLT', NULL, NULL, 'YES', LAST_INSERT_ID());
INSERT INTO `AccessHist`(`Nic_Handle_Chng_ID`, `NH_Level`) VALUES ((SELECT `Chng_ID` FROM `NicHandleHist` WHERE `Nic_Handle` = 'SELLRQCOMPLT'), 64);
INSERT INTO `Access`(`Nic_Handle`, `NH_Level`, `Chng_ID`) VALUES ('SELLRQCOMPLT', 64, LAST_INSERT_ID());
INSERT INTO `TelecomHist` VALUES ((SELECT `Chng_ID` FROM `NicHandleHist` WHERE `Nic_Handle` = 'SELLRQCOMPLT'), 'SELLRQCOMPLT', '+000000000', 'Phone', 0);
INSERT INTO `Telecom` VALUES ('SELLRQCOMPLT', '+000000000', 'Phone', 0);

INSERT INTO `SchedulerConfig` (`name`, `schedule`, `Active`, `RunningNicHandle`) VALUES
('SellRequestCompletion', '00 15 * * *', 'YES', 'SELLRQCOMPLT');

-- CRS-1225

CREATE TABLE `SecondaryMarketBuyRequestNotification` (
  `BuyRequest_Id` INT UNSIGNED NOT NULL,
  `Notification_Period` INT(11) NOT NULL,
  PRIMARY KEY (`BuyRequest_Id`, `Notification_Period`),
  FOREIGN KEY (`BuyRequest_Id`) REFERENCES `SecondaryMarketBuyRequest`(`Id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_irish_accent_ci;

INSERT INTO `Email` (`E_ID`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `E_Suppressible`) VALUES
    (206, '\n\n***IMPORTANT NOTICE – PLEASE READ***\n\nRequest ID: $REQUEST_ID$ - Domain Name: $DOMAIN$\n\nPlease note that your request will expire in $DAYS_REMAINING_WITH_DAYS_SUFFIX$.\n\nIf the required information is not provided to us and processed before your request expires, you will be unable to proceed with this process.\n\nNOTE: Any subsequent requests will be charged an additional administration fee.\n\nIf you have any queries relating to this, please contact your Registrar in the first instance, or the IEDR Registration Services Department on 01-2365400.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Request pending: Re: $DOMAIN$ <$DAYS_REMAINING_WITH_DAYS_SUFFIX$ remaining>', '$BUYER_EMAIL$', '$BILL-C_EMAIL$, $NIC_EMAIL$', NULL, 'registrations@iedr.ie', NULL, NULL, 'registrations@iedr.ie', 'NO');

UPDATE `Email` SET
  `E_Text` = REPLACE(`E_Text`, '$TICKET_DAYS_', '$DAYS_'),
  `E_Subject` = REPLACE(`E_Subject`, '$TICKET_DAYS_', '$DAYS_')
  WHERE `E_ID` IN (35, 41, 42);

-- CRS-1222

INSERT INTO `App_Config` VALUES (NULL, 'secondary_market_after_sale_period', 'INT', '5');

INSERT INTO `Email` (`E_ID`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `E_Suppressible`) VALUES
    (208, '***IMPORTANT NOTICE – PLEASE READ***\n\nRequest ID: $REQUEST_ID$ - Domain Name: $DOMAIN$\n\nPlease note that your registrant transfer request has not been accepted, as the holder of the domain has decided not to transfer the registration to you.\n\nAs such, your registrant transfer authorisation code has been invalidated.\n\nIf you have any queries relating to this, please contact your Registrar in the first instance, or the IEDR Registration Services Department on 01-2365400.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Registrant Transfer – Authcode Invalidated', '$BUYER_EMAIL$', '$BILL-C_EMAIL$', NULL, 'registrations@iedr.ie', NULL, NULL, 'registrations@iedr.ie', 'NO');

-- CRS-1224

INSERT INTO `Email` (`E_ID`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `E_Suppressible`) VALUES
    (209, '\n\n***IMPORTANT NOTICE – PLEASE READ***\n\nRequest ID: $REQUEST_ID$ - Domain Name: $DOMAIN$\n\nPlease note that your request has expired.\n\nIf you wish to re-submit your request, please contact the Registrar managing this domain, who will assist you further.\n\nPlease note that all re-submitted requests will be charged a non-refundable administration fee.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Registrant Transfer – Buyer Request Expired', '$BUYER_EMAIL$', '$BILL-C_EMAIL$, $NIC_EMAIL$', NULL, 'registrations@iedr.ie', NULL, NULL, 'registrations@iedr.ie', 'NO');

-- CRS-1264

INSERT INTO `DSM_Transition` (`Event_Id`, `Begin_State`, `End_State`)
    SELECT (SELECT `Id` FROM `DSM_Event` WHERE `Name` = 'CancelLastBuyRequest'), `State`, `State`
    FROM `DSM_State`
    WHERE `Secondary_Market_Status` = 'NP';

-- CRS-1229

-- CRS-1300 fix first
DELETE FROM EmailDisabler WHERE ED_Email_E_ID IN (187, 188);
DELETE FROM Email WHERE E_ID IN (187,188);

-- CRS-1228

ALTER TABLE `INCOMING_DOC` MODIFY COLUMN `DOC_PURPOSE`
    enum('New Reg', 'Bill-C Transfer', 'Deletion', 'General Mod', 'Misc', 'Name Server Mod',
        'Unnecessary', 'Buy Request')
    COLLATE utf8_irish_accent_ci NOT NULL;

-- CRS-1259

ALTER TABLE `Email` DROP COLUMN `E_Name`;
ALTER TABLE `EmailHist` DROP COLUMN `E_Name`;

INSERT INTO `Email` (`E_ID`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `E_Suppressible`) VALUES
    (210, '\n\n***IMPORTANT NOTICE – PLEASE READ***\n\nDomain Name: $DOMAIN$\n\nPlease note that this registrant transfer has been completed, and the domain can now be edited / modified, as required.\n\nIf you have any queries relating to this, please contact your Registrar in the first instance, or the IEDR Registration Services Department on 012365400.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Registrant Transfer – Request Completed', '$BUYER_EMAIL$, $ADMIN-C_EMAIL$', '$BILL-C_EMAIL$', NULL, 'registrations@iedr.ie', NULL, NULL, 'registrations@iedr.ie', 'NO');

-- CRS-1247

ALTER TABLE Zone_Published ADD KEY (Committed);

-- CRS-1269

INSERT INTO `Email` (`E_ID`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `active`, `Html`,
                     `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `E_Suppressible`, `E_Send_Reason`, `E_Non_Suppressible_Reason`,
                     `E_Suppressed_By_Gaining`)
VALUES
  (212, '\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nRemarks : $REMARK$\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365 \nWeb: www.iedr.ie\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n\n', '(Buy request Query) Re: $DOMAIN$ ', '$BUYER_EMAIL$', '$NIC_EMAIL$', NULL, 'registrations@iedr.ie', 'YES', 'NO', NULL, 'registrations@iedr.ie', 'hostmaster-archive@domainregistry.ie, registrations@iedr.ie', 'NO', '', '', 'YES');

-- CRS-1261

ALTER TABLE `Access` ADD COLUMN `Force_Password_Change` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO' AFTER `password_change_TS`;
ALTER TABLE `AccessHist` ADD COLUMN `Force_Password_Change` ENUM('YES', 'NO') NOT NULL DEFAULT 'NO' AFTER `password_change_TS`;

INSERT INTO `Email` (`E_ID`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `E_Suppressible`) VALUES
    (211, '***IMPORTANT NOTICE – PLEASE READ***\n\nDomain Name: $DOMAIN$\nNic-Handle: $NIC$\nTemporary Password: $PASSWORD$\n\nPlease note that the registrant transfer for this domain has now been completed.\n\nTo manage your domain, we’ve setup a new account for you – please see the Nic-Handle ID and temporary password above.\n\nWhen you first login, you will be asked to reset this temporary password. You can login at the following URL:\n\nhttps://console.iedr.ie/\n\nOtherwise, if you wish to manage your domain through an accredited .ie Registrar, you can find information on these companies at the URL below:\n\nhttps://www.iedr.ie/register-a-domain/accredited-registrar-list/\n\nIf you have any queries relating to this, please contact the IEDR Registration Services Department on 01-2365400, who will be happy to assist you.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Action Required: Registrant Transfer Completed – Account Setup', '$NIC_EMAIL$', '$BILL-C_EMAIL$', NULL, 'registrations@iedr.ie', NULL, NULL, 'registrations@iedr.ie', 'NO');

-- CRS-1288

ALTER TABLE `SecondaryMarketBuyRequestNotification`
ADD COLUMN `Notification_Type` ENUM('request', 'authcode') NOT NULL DEFAULT 'request';

INSERT INTO `Email` (`E_ID`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `E_Suppressible`) VALUES
    (207, '\n\n***IMPORTANT NOTICE – PLEASE READ***\n\nRequest ID: $REQUEST_ID$ - Domain Name: $DOMAIN$\n\nPlease note that your registrant transfer authcode will expire in $DAYS_REMAINING_WITH_DAYS_SUFFIX$.\n\nIf you and the domain holder wish to complete the registrant transfer process, please ensure this is done before the authcode expires.\n\nNOTE: All subsequent requests will be charged an additional administration fee.\n\nIf you have any queries relating to this, please contact your Registrar in the first instance, or the IEDR Registration Services Department on 01-2365400.\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n', 'Request pending: Registrant Transfer Authcode: $DAYS_REMAINING_WITH_DAYS_SUFFIX$ remaining', '$BUYER_EMAIL$', '$BILL-C_EMAIL$, $NIC_EMAIL$', NULL, 'registrations@iedr.ie', NULL, NULL, 'registrations@iedr.ie', 'NO');

INSERT INTO `App_Config` VALUES (NULL, 'secondary_market_authcode_expiration_notification_periods', 'STRING', '30');

