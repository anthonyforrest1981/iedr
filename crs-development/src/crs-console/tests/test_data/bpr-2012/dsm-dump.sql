# --------------------------------------------------------
# Host:                         127.0.0.1
# Server version:               5.1.54-1ubuntu4
# Server OS:                    debian-linux-gnu
# HeidiSQL version:             6.0.0.3603
# Date/time:                    2012-04-17 13:40:24
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

# Dumping structure for table crsdb.DSM_Action
DROP TABLE IF EXISTS `DSM_Action`;
CREATE TABLE IF NOT EXISTS `DSM_Action` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id of the action',
  `Action_Name` varchar(255) NOT NULL COMMENT 'The name of the action to be fired',
  `Comment` text COMMENT 'Action description',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Action_Name` (`Action_Name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='Holds valid names of DSM actions';

# Dumping data for table crsdb.DSM_Action: ~9 rows (approximately)
/*!40000 ALTER TABLE `DSM_Action` DISABLE KEYS */;
REPLACE INTO `DSM_Action` (`Id`, `Action_Name`, `Comment`) VALUES
	(1, 'SetRenewalDate', NULL),
	(2, 'RollRenewalDate', NULL),
	(3, 'ClearSuspensionDate', NULL),
	(4, 'ClearDeletionDate', NULL),
	(5, 'SetSuspensionDateRenewal', NULL),
	(6, 'SetSuspensionDateCurrent', NULL),
	(7, 'SetDeletionDate', NULL),
	(8, 'SetGIBORetryTimeout', NULL),
	(9, 'RollRenewalDateOne', NULL);
/*!40000 ALTER TABLE `DSM_Action` ENABLE KEYS */;


# Dumping structure for table crsdb.DSM_Event
DROP TABLE IF EXISTS `DSM_Event`;
CREATE TABLE IF NOT EXISTS `DSM_Event` (
  `Id` int(11) NOT NULL COMMENT 'ID of the event',
  `Name` varchar(255) NOT NULL COMMENT 'Name of the event',
  `Comment` text COMMENT 'Event description',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Dsm_Event_Name` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Holds valid names of DSM Events';

# Dumping data for table crsdb.DSM_Event: ~29 rows (approximately)
/*!40000 ALTER TABLE `DSM_Event` DISABLE KEYS */;
REPLACE INTO `DSM_Event` (`Id`, `Name`, `Comment`) VALUES
	(1, 'CreateBillableDomainRegistrar', NULL),
	(2, 'CreateBillableDomainDirect', NULL),
	(3, 'CreateCharityDomainRegistrar', NULL),
	(4, 'CreateCharityDomainDirect', NULL),
	(5, 'CreateGIBODomain', NULL),
	(6, 'DeletedDomainRemoval', NULL),
	(7, 'RenewalDatePasses', NULL),
	(8, 'SuspensionDatePasses', NULL),
	(9, 'DeletionDatePasses', NULL),
	(10, 'GIBOAuthorisation', NULL),
	(11, 'GIBOAdminFailure', NULL),
	(12, 'GIBOPaymentFailure', NULL),
	(13, 'GIBOPaymentRetryTimeout', NULL),
	(14, 'EnterVoluntaryNRP', NULL),
	(15, 'RemoveFromVoluntaryNRP', NULL),
	(16, 'Payment', NULL),
	(17, 'TransferRequest', NULL),
	(18, 'TransferCancellation', NULL),
	(19, 'TransferToDirect', NULL),
	(20, 'TransferToRegistrar', NULL),
	(21, 'SetAutoRenew', NULL),
	(22, 'SetNoAutoRenew', NULL),
	(23, 'SetOnceAutoRenew', NULL),
	(24, 'SetCharity', NULL),
	(25, 'SetBillable', NULL),
	(26, 'SetNonBillable', NULL),
	(27, 'SetIEDR', NULL),
	(28, 'EnterWIPOArbitration', NULL),
	(29, 'ExitWIPOArbitration', NULL);
/*!40000 ALTER TABLE `DSM_Event` ENABLE KEYS */;


# Dumping structure for table crsdb.DSM_State
DROP TABLE IF EXISTS `DSM_State`;
CREATE TABLE IF NOT EXISTS `DSM_State` (
  `State` int(11) NOT NULL COMMENT 'DSM State',
  `Published` char(1) NOT NULL DEFAULT 'N' COMMENT 'Published flag',
  `D_Holder_Type` char(3) DEFAULT NULL COMMENT 'Domain Holder Type (B=Billable, C=Charity, I=IEDR, N=Non-Billable)',
  `Renewal_Mode` char(3) DEFAULT NULL COMMENT 'Renewal mode (N=No auto renew, R=Renew Once, A=Autorenew)',
  `WIPO` char(1) DEFAULT NULL COMMENT 'WIPO dispute flag',
  `Cust_Type` char(3) DEFAULT NULL COMMENT 'Customer type (R=Registrar, D=Direct)',
  `NRP_Status` char(3) DEFAULT NULL COMMENT 'A = Active, IM = Involuntary Mailed, IS = Involuntary Suspended, VM = Voluntary Mailed, VS = Voluntary Suspended, D = Deleted, P = Post-Transaction Audit, T = Transaction Failed,  XPA = Transfer Pending - Active, XPI = Transfer Pending - Inv. NRP, XPV = T',
  `Comment` text COMMENT 'State description',
  PRIMARY KEY (`State`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Defines valid DSM states';

# Dumping data for table crsdb.DSM_State: ~102 rows (approximately)
/*!40000 ALTER TABLE `DSM_State` DISABLE KEYS */;
REPLACE INTO `DSM_State` (`State`, `Published`, `D_Holder_Type`, `Renewal_Mode`, `WIPO`, `Cust_Type`, `NRP_Status`, `Comment`) VALUES
	(0, 'N', NULL, NULL, NULL, NULL, NULL, NULL),
	(1, 'N', 'B', 'N', 'Y', 'R', 'A', NULL),
	(2, 'N', 'B', 'N', 'Y', 'R', 'IM', NULL),
	(3, 'N', 'B', 'N', 'Y', 'R', 'IS', NULL),
	(4, 'N', 'B', 'N', 'Y', 'R', 'VM', NULL),
	(5, 'N', 'B', 'N', 'Y', 'R', 'VS', NULL),
	(7, 'N', 'B', 'N', 'Y', 'R', 'P', NULL),
	(8, 'N', 'B', 'N', 'Y', 'R', 'T', NULL),
	(9, 'N', 'B', 'N', 'Y', 'D', 'A', NULL),
	(10, 'N', 'B', 'N', 'Y', 'D', 'IM', NULL),
	(11, 'N', 'B', 'N', 'Y', 'D', 'IS', NULL),
	(12, 'N', 'B', 'N', 'Y', 'D', 'VM', NULL),
	(13, 'N', 'B', 'N', 'Y', 'D', 'VS', NULL),
	(17, 'N', 'B', 'N', 'N', 'R', 'A', NULL),
	(18, 'N', 'B', 'N', 'N', 'R', 'IM', NULL),
	(19, 'N', 'B', 'N', 'N', 'R', 'IS', NULL),
	(20, 'N', 'B', 'N', 'N', 'R', 'VM', NULL),
	(21, 'N', 'B', 'N', 'N', 'R', 'VS', NULL),
	(23, 'N', 'B', 'N', 'N', 'R', 'P', NULL),
	(24, 'N', 'B', 'N', 'N', 'R', 'T', NULL),
	(25, 'N', 'B', 'N', 'N', 'D', 'A', NULL),
	(26, 'N', 'B', 'N', 'N', 'D', 'IM', NULL),
	(27, 'N', 'B', 'N', 'N', 'D', 'IS', NULL),
	(28, 'N', 'B', 'N', 'N', 'D', 'VM', NULL),
	(29, 'N', 'B', 'N', 'N', 'D', 'VS', NULL),
	(33, 'N', 'B', 'R', 'Y', 'R', 'A', NULL),
	(39, 'N', 'B', 'R', 'Y', 'R', 'P', NULL),
	(40, 'N', 'B', 'R', 'Y', 'R', 'T', NULL),
	(49, 'N', 'B', 'R', 'N', 'R', 'A', NULL),
	(55, 'N', 'B', 'R', 'N', 'R', 'P', NULL),
	(56, 'N', 'B', 'R', 'N', 'R', 'T', NULL),
	(65, 'N', 'B', 'A', 'Y', 'R', 'A', NULL),
	(66, 'N', 'B', 'A', 'Y', 'R', 'IM', NULL),
	(67, 'N', 'B', 'A', 'Y', 'R', 'IS', NULL),
	(71, 'N', 'B', 'A', 'Y', 'R', 'P', NULL),
	(72, 'N', 'B', 'A', 'Y', 'R', 'T', NULL),
	(81, 'N', 'B', 'A', 'N', 'R', 'A', NULL),
	(82, 'N', 'B', 'A', 'N', 'R', 'IM', NULL),
	(83, 'N', 'B', 'A', 'N', 'R', 'IS', NULL),
	(87, 'N', 'B', 'A', 'N', 'R', 'P', NULL),
	(88, 'N', 'B', 'A', 'N', 'R', 'T', NULL),
	(97, 'N', 'C', 'N', 'Y', 'R', 'A', NULL),
	(100, 'N', 'C', 'N', 'Y', 'R', 'VM', NULL),
	(101, 'N', 'C', 'N', 'Y', 'R', 'VS', NULL),
	(105, 'N', 'C', 'N', 'Y', 'D', 'A', NULL),
	(108, 'N', 'C', 'N', 'Y', 'D', 'VM', NULL),
	(109, 'N', 'C', 'N', 'Y', 'D', 'VS', NULL),
	(113, 'N', 'C', 'N', 'N', 'R', 'A', NULL),
	(116, 'N', 'C', 'N', 'N', 'R', 'VM', NULL),
	(117, 'N', 'C', 'N', 'N', 'R', 'VS', NULL),
	(121, 'N', 'C', 'N', 'N', 'D', 'A', NULL),
	(124, 'N', 'C', 'N', 'N', 'D', 'VM', NULL),
	(125, 'N', 'C', 'N', 'N', 'D', 'VS', NULL),
	(289, 'N', 'N', 'N', 'Y', 'R', 'A', NULL),
	(292, 'N', 'N', 'N', 'Y', 'R', 'VM', NULL),
	(293, 'N', 'N', 'N', 'Y', 'R', 'VS', NULL),
	(297, 'N', 'N', 'N', 'Y', 'D', 'A', NULL),
	(300, 'N', 'N', 'N', 'Y', 'D', 'VM', NULL),
	(301, 'N', 'N', 'N', 'Y', 'D', 'VS', NULL),
	(305, 'N', 'N', 'N', 'N', 'R', 'A', NULL),
	(308, 'N', 'N', 'N', 'N', 'R', 'VM', NULL),
	(309, 'N', 'N', 'N', 'N', 'R', 'VS', NULL),
	(313, 'N', 'N', 'N', 'N', 'D', 'A', NULL),
	(316, 'N', 'N', 'N', 'N', 'D', 'VM', NULL),
	(317, 'N', 'N', 'N', 'N', 'D', 'VS', NULL),
	(385, 'N', NULL, NULL, NULL, NULL, 'D', NULL),
	(386, 'N', 'I', NULL, NULL, NULL, NULL, NULL),
	(387, 'N', NULL, NULL, NULL, NULL, NULL, NULL),
	(388, 'N', 'B', 'N', 'Y', 'R', 'XPA', NULL),
	(389, 'N', 'B', 'N', 'Y', 'D', 'XPA', NULL),
	(390, 'N', 'B', 'N', 'N', 'R', 'XPA', NULL),
	(391, 'N', 'B', 'N', 'N', 'D', 'XPA', NULL),
	(392, 'N', 'B', 'R', 'Y', 'R', 'XPA', NULL),
	(394, 'N', 'B', 'R', 'N', 'R', 'XPA', NULL),
	(396, 'N', 'B', 'A', 'Y', 'R', 'XPA', NULL),
	(398, 'N', 'B', 'A', 'N', 'R', 'XPA', NULL),
	(400, 'N', 'C', 'N', 'Y', 'R', 'XPA', NULL),
	(401, 'N', 'C', 'N', 'Y', 'D', 'XPA', NULL),
	(402, 'N', 'C', 'N', 'N', 'R', 'XPA', NULL),
	(403, 'N', 'C', 'N', 'N', 'D', 'XPA', NULL),
	(424, 'N', 'N', 'N', 'Y', 'R', 'XPA', NULL),
	(425, 'N', 'N', 'N', 'Y', 'D', 'XPA', NULL),
	(426, 'N', 'N', 'N', 'N', 'R', 'XPA', NULL),
	(427, 'N', 'N', 'N', 'N', 'D', 'XPA', NULL),
	(436, 'N', 'B', 'N', 'Y', 'R', 'XPI', NULL),
	(437, 'N', 'B', 'N', 'Y', 'D', 'XPI', NULL),
	(438, 'N', 'B', 'N', 'N', 'R', 'XPI', NULL),
	(439, 'N', 'B', 'N', 'N', 'D', 'XPI', NULL),
	(444, 'N', 'B', 'A', 'Y', 'R', 'XPI', NULL),
	(446, 'N', 'B', 'A', 'N', 'R', 'XPI', NULL),
	(484, 'N', 'B', 'N', 'Y', 'R', 'XPV', NULL),
	(485, 'N', 'B', 'N', 'Y', 'D', 'XPV', NULL),
	(486, 'N', 'B', 'N', 'N', 'R', 'XPV', NULL),
	(487, 'N', 'B', 'N', 'N', 'D', 'XPV', NULL),
	(496, 'N', 'C', 'N', 'Y', 'R', 'XPV', NULL),
	(497, 'N', 'C', 'N', 'Y', 'D', 'XPV', NULL),
	(498, 'N', 'C', 'N', 'N', 'R', 'XPV', NULL),
	(499, 'N', 'C', 'N', 'N', 'D', 'XPV', NULL),
	(520, 'N', 'N', 'N', 'Y', 'R', 'XPV', NULL),
	(521, 'N', 'N', 'N', 'Y', 'D', 'XPV', NULL),
	(522, 'N', 'N', 'N', 'N', 'R', 'XPV', NULL),
	(523, 'N', 'N', 'N', 'N', 'D', 'XPV', NULL);
/*!40000 ALTER TABLE `DSM_State` ENABLE KEYS */;


# Dumping structure for table crsdb.DSM_Transition
DROP TABLE IF EXISTS `DSM_Transition`;
CREATE TABLE IF NOT EXISTS `DSM_Transition` (
  `Id` int(11) NOT NULL COMMENT 'ID of the transition',
  `Event_Id` int(11) NOT NULL COMMENT 'ID of the event which causes this transition to be used when changing the state',
  `Begin_State` int(11) NOT NULL COMMENT 'Starting state for the transition',
  `End_State` int(11) NOT NULL COMMENT 'End state for th transition',
  `Comment` text COMMENT 'Description for the transition',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Dsm_Transition_From_State` (`Event_Id`,`Begin_State`),
  KEY `FK_dsm_transition_start_state` (`Begin_State`),
  KEY `FK_dsm_transition_end_state` (`End_State`),
  KEY `FK_dsm_transition_event` (`Event_Id`),
  CONSTRAINT `FK_dsm_transition_start_state` FOREIGN KEY (`Begin_State`) REFERENCES `DSM_State` (`State`),
  CONSTRAINT `FK_dsm_transition_end_state` FOREIGN KEY (`End_State`) REFERENCES `DSM_State` (`State`),
  CONSTRAINT `FK_dsm_transition_event` FOREIGN KEY (`Event_Id`) REFERENCES `DSM_Event` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Defines valid DSM transitions between domain states';

# Dumping data for table crsdb.DSM_Transition: ~304 rows (approximately)
/*!40000 ALTER TABLE `DSM_Transition` DISABLE KEYS */;
REPLACE INTO `DSM_Transition` (`Id`, `Event_Id`, `Begin_State`, `End_State`, `Comment`) VALUES
	(1, 1, 0, 17, NULL),
	(2, 2, 0, 25, NULL),
	(3, 3, 0, 113, NULL),
	(4, 4, 0, 121, NULL),
	(5, 5, 0, 23, NULL),
	(6, 16, 1, 1, NULL),
	(7, 16, 2, 1, NULL),
	(8, 16, 3, 1, NULL),
	(9, 15, 4, 1, NULL),
	(10, 16, 4, 1, NULL),
	(11, 15, 5, 1, NULL),
	(12, 16, 5, 1, NULL),
	(13, 16, 9, 9, NULL),
	(14, 16, 10, 9, NULL),
	(15, 16, 11, 9, NULL),
	(16, 15, 12, 9, NULL),
	(17, 16, 12, 9, NULL),
	(18, 15, 13, 9, NULL),
	(19, 16, 13, 9, NULL),
	(20, 7, 17, 18, NULL),
	(21, 14, 17, 20, NULL),
	(22, 16, 17, 17, NULL),
	(23, 17, 17, 390, NULL),
	(24, 19, 17, 25, NULL),
	(25, 20, 17, 17, NULL),
	(26, 8, 18, 19, NULL),
	(27, 16, 18, 17, NULL),
	(28, 17, 18, 438, NULL),
	(29, 19, 18, 25, NULL),
	(30, 20, 18, 17, NULL),
	(31, 9, 19, 385, NULL),
	(32, 16, 19, 17, NULL),
	(33, 17, 19, 438, NULL),
	(34, 19, 19, 25, NULL),
	(35, 20, 19, 17, NULL),
	(36, 7, 20, 18, NULL),
	(37, 8, 20, 21, NULL),
	(38, 15, 20, 17, NULL),
	(39, 16, 20, 17, NULL),
	(40, 17, 20, 486, NULL),
	(41, 19, 20, 25, NULL),
	(42, 20, 20, 17, NULL),
	(43, 7, 21, 19, NULL),
	(44, 9, 21, 385, NULL),
	(45, 15, 21, 17, NULL),
	(46, 16, 21, 17, NULL),
	(47, 17, 21, 486, NULL),
	(48, 19, 21, 25, NULL),
	(49, 20, 21, 17, NULL),
	(50, 10, 23, 17, NULL),
	(51, 11, 23, 385, NULL),
	(52, 12, 23, 24, NULL),
	(53, 10, 24, 17, NULL),
	(54, 13, 24, 385, NULL),
	(55, 7, 25, 26, NULL),
	(56, 14, 25, 28, NULL),
	(57, 16, 25, 25, NULL),
	(58, 17, 25, 391, NULL),
	(59, 19, 25, 25, NULL),
	(60, 20, 25, 17, NULL),
	(61, 8, 26, 27, NULL),
	(62, 16, 26, 25, NULL),
	(63, 17, 26, 439, NULL),
	(64, 19, 26, 25, NULL),
	(65, 20, 26, 17, NULL),
	(66, 9, 27, 385, NULL),
	(67, 16, 27, 25, NULL),
	(68, 17, 27, 439, NULL),
	(69, 19, 27, 25, NULL),
	(70, 20, 27, 17, NULL),
	(71, 7, 28, 26, NULL),
	(72, 8, 28, 29, NULL),
	(73, 15, 28, 25, NULL),
	(74, 16, 28, 25, NULL),
	(75, 17, 28, 487, NULL),
	(76, 19, 28, 25, NULL),
	(77, 20, 28, 17, NULL),
	(78, 7, 29, 27, NULL),
	(79, 9, 29, 385, NULL),
	(80, 15, 29, 25, NULL),
	(81, 16, 29, 25, NULL),
	(82, 17, 29, 487, NULL),
	(83, 19, 29, 25, NULL),
	(84, 20, 29, 17, NULL),
	(85, 16, 33, 1, NULL),
	(86, 7, 49, 18, NULL),
	(87, 14, 49, 20, NULL),
	(88, 16, 49, 17, NULL),
	(89, 17, 49, 390, NULL),
	(90, 19, 49, 25, NULL),
	(91, 20, 49, 17, NULL),
	(92, 10, 55, 49, NULL),
	(93, 11, 55, 385, NULL),
	(94, 12, 55, 56, NULL),
	(95, 10, 56, 49, NULL),
	(96, 13, 56, 385, NULL),
	(97, 16, 65, 65, NULL),
	(98, 16, 66, 65, NULL),
	(99, 16, 67, 65, NULL),
	(100, 7, 81, 82, NULL),
	(101, 14, 81, 20, NULL),
	(102, 16, 81, 81, NULL),
	(103, 17, 81, 390, NULL),
	(104, 19, 81, 25, NULL),
	(105, 20, 81, 17, NULL),
	(106, 8, 82, 83, NULL),
	(107, 16, 82, 81, NULL),
	(108, 17, 82, 438, NULL),
	(109, 19, 82, 25, NULL),
	(110, 20, 82, 17, NULL),
	(111, 9, 83, 385, NULL),
	(112, 16, 83, 81, NULL),
	(113, 17, 83, 438, NULL),
	(114, 19, 83, 25, NULL),
	(115, 20, 83, 17, NULL),
	(116, 10, 87, 81, NULL),
	(117, 11, 87, 385, NULL),
	(118, 12, 87, 88, NULL),
	(119, 10, 88, 81, NULL),
	(120, 13, 88, 385, NULL),
	(121, 15, 100, 97, NULL),
	(122, 15, 101, 97, NULL),
	(123, 15, 108, 105, NULL),
	(124, 15, 109, 105, NULL),
	(125, 7, 113, 113, NULL),
	(126, 14, 113, 116, NULL),
	(127, 17, 113, 402, NULL),
	(128, 19, 113, 121, NULL),
	(129, 20, 113, 113, NULL),
	(130, 8, 116, 117, NULL),
	(131, 15, 116, 113, NULL),
	(132, 17, 116, 498, NULL),
	(133, 19, 116, 121, NULL),
	(134, 20, 116, 113, NULL),
	(135, 9, 117, 385, NULL),
	(136, 15, 117, 113, NULL),
	(137, 17, 117, 498, NULL),
	(138, 19, 117, 121, NULL),
	(139, 20, 117, 113, NULL),
	(140, 7, 121, 121, NULL),
	(141, 14, 121, 124, NULL),
	(142, 17, 121, 403, NULL),
	(143, 19, 121, 121, NULL),
	(144, 20, 121, 113, NULL),
	(145, 8, 124, 125, NULL),
	(146, 15, 124, 121, NULL),
	(147, 17, 124, 499, NULL),
	(148, 19, 124, 121, NULL),
	(149, 20, 124, 113, NULL),
	(150, 9, 125, 385, NULL),
	(151, 15, 125, 121, NULL),
	(152, 17, 125, 499, NULL),
	(153, 19, 125, 121, NULL),
	(154, 20, 125, 113, NULL),
	(155, 15, 292, 289, NULL),
	(156, 15, 293, 289, NULL),
	(157, 15, 300, 297, NULL),
	(158, 15, 301, 297, NULL),
	(159, 7, 305, 305, NULL),
	(160, 14, 305, 308, NULL),
	(161, 17, 305, 426, NULL),
	(162, 19, 305, 313, NULL),
	(163, 20, 305, 305, NULL),
	(164, 8, 308, 309, NULL),
	(165, 15, 308, 305, NULL),
	(166, 17, 308, 522, NULL),
	(167, 19, 308, 313, NULL),
	(168, 20, 308, 305, NULL),
	(169, 9, 309, 385, NULL),
	(170, 15, 309, 305, NULL),
	(171, 17, 309, 522, NULL),
	(172, 19, 309, 313, NULL),
	(173, 20, 309, 305, NULL),
	(174, 7, 313, 313, NULL),
	(175, 14, 313, 316, NULL),
	(176, 17, 313, 427, NULL),
	(177, 19, 313, 313, NULL),
	(178, 20, 313, 305, NULL),
	(179, 8, 316, 317, NULL),
	(180, 15, 316, 313, NULL),
	(181, 17, 316, 523, NULL),
	(182, 19, 316, 313, NULL),
	(183, 20, 316, 305, NULL),
	(184, 9, 317, 385, NULL),
	(185, 15, 317, 313, NULL),
	(186, 17, 317, 523, NULL),
	(187, 19, 317, 313, NULL),
	(188, 20, 317, 305, NULL),
	(189, 6, 385, 387, NULL),
	(190, 16, 388, 388, NULL),
	(191, 18, 388, 1, NULL),
	(192, 16, 389, 389, NULL),
	(193, 18, 389, 9, NULL),
	(194, 7, 390, 438, NULL),
	(195, 14, 390, 486, NULL),
	(196, 16, 390, 390, NULL),
	(197, 18, 390, 17, NULL),
	(198, 19, 390, 25, NULL),
	(199, 20, 390, 17, NULL),
	(200, 7, 391, 439, NULL),
	(201, 14, 391, 487, NULL),
	(202, 16, 391, 391, NULL),
	(203, 18, 391, 25, NULL),
	(204, 19, 391, 25, NULL),
	(205, 20, 391, 17, NULL),
	(206, 16, 392, 388, NULL),
	(207, 18, 392, 33, NULL),
	(208, 7, 394, 438, NULL),
	(209, 14, 394, 486, NULL),
	(210, 16, 394, 390, NULL),
	(211, 18, 394, 49, NULL),
	(212, 19, 394, 25, NULL),
	(213, 20, 394, 17, NULL),
	(214, 16, 396, 396, NULL),
	(215, 18, 396, 65, NULL),
	(216, 7, 398, 446, NULL),
	(217, 14, 398, 486, NULL),
	(218, 16, 398, 398, NULL),
	(219, 18, 398, 81, NULL),
	(220, 19, 398, 25, NULL),
	(221, 20, 398, 17, NULL),
	(222, 18, 400, 97, NULL),
	(223, 18, 401, 105, NULL),
	(224, 7, 402, 402, NULL),
	(225, 14, 402, 498, NULL),
	(226, 18, 402, 113, NULL),
	(227, 19, 402, 121, NULL),
	(228, 20, 402, 113, NULL),
	(229, 7, 403, 403, NULL),
	(230, 14, 403, 499, NULL),
	(231, 18, 403, 121, NULL),
	(232, 19, 403, 121, NULL),
	(233, 20, 403, 113, NULL),
	(234, 18, 424, 289, NULL),
	(235, 18, 425, 297, NULL),
	(236, 7, 426, 426, NULL),
	(237, 14, 426, 522, NULL),
	(238, 18, 426, 305, NULL),
	(239, 19, 426, 313, NULL),
	(240, 20, 426, 305, NULL),
	(242, 14, 427, 523, NULL),
	(243, 18, 427, 313, NULL),
	(244, 19, 427, 313, NULL),
	(245, 20, 427, 305, NULL),
	(246, 16, 436, 388, NULL),
	(247, 18, 436, 2, NULL),
	(248, 16, 437, 389, NULL),
	(249, 18, 437, 10, NULL),
	(250, 16, 438, 390, NULL),
	(251, 18, 438, 18, NULL),
	(252, 19, 438, 25, NULL),
	(253, 20, 438, 17, NULL),
	(254, 16, 439, 391, NULL),
	(255, 18, 439, 26, NULL),
	(256, 19, 439, 25, NULL),
	(257, 20, 439, 17, NULL),
	(258, 16, 444, 396, NULL),
	(259, 18, 444, 66, NULL),
	(260, 16, 446, 398, NULL),
	(261, 18, 446, 82, NULL),
	(262, 19, 446, 25, NULL),
	(263, 20, 446, 17, NULL),
	(264, 15, 484, 388, NULL),
	(265, 16, 484, 388, NULL),
	(266, 18, 484, 4, NULL),
	(267, 15, 485, 389, NULL),
	(268, 16, 485, 389, NULL),
	(269, 18, 485, 12, NULL),
	(270, 7, 486, 438, NULL),
	(271, 15, 486, 390, NULL),
	(272, 16, 486, 390, NULL),
	(273, 18, 486, 20, NULL),
	(274, 19, 486, 25, NULL),
	(275, 20, 486, 17, NULL),
	(276, 7, 487, 439, NULL),
	(277, 15, 487, 391, NULL),
	(278, 16, 487, 391, NULL),
	(279, 18, 487, 28, NULL),
	(280, 19, 487, 25, NULL),
	(281, 20, 487, 17, NULL),
	(282, 15, 496, 400, NULL),
	(283, 18, 496, 100, NULL),
	(284, 15, 497, 401, NULL),
	(285, 18, 497, 108, NULL),
	(286, 15, 498, 403, NULL),
	(287, 18, 498, 116, NULL),
	(288, 19, 498, 121, NULL),
	(289, 20, 498, 113, NULL),
	(290, 15, 499, 403, NULL),
	(291, 18, 499, 124, NULL),
	(292, 19, 499, 121, NULL),
	(293, 20, 499, 113, NULL),
	(294, 15, 520, 424, NULL),
	(295, 18, 520, 292, NULL),
	(296, 15, 521, 425, NULL),
	(297, 18, 521, 300, NULL),
	(298, 15, 522, 426, NULL),
	(299, 18, 522, 308, NULL),
	(300, 19, 522, 313, NULL),
	(301, 20, 522, 305, NULL),
	(302, 15, 523, 427, NULL),
	(303, 18, 523, 316, NULL),
	(304, 19, 523, 313, NULL),
	(305, 20, 523, 305, NULL);
/*!40000 ALTER TABLE `DSM_Transition` ENABLE KEYS */;


# Dumping structure for table crsdb.DSM_Transition_Action
DROP TABLE IF EXISTS `DSM_Transition_Action`;
CREATE TABLE IF NOT EXISTS `DSM_Transition_Action` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Artificial primary key',
  `Transition_Id` int(11) NOT NULL COMMENT 'ID of the transition',
  `Order` int(11) NOT NULL COMMENT 'Defines the order in which the actions will be fired for the given transition',
  `Action_Id` int(11) NOT NULL COMMENT 'The ID of the action to be fired',
  `Comment` text COMMENT 'Action description',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Dsm_Transition_Action_NK` (`Transition_Id`,`Order`),
  KEY `Dsm_Transition_Action_action` (`Action_Id`),
  CONSTRAINT `FK_dsm_transition_action_transitionId` FOREIGN KEY (`Transition_Id`) REFERENCES `DSM_Transition` (`Id`),
  CONSTRAINT `FK_dsm_transition_action_actionId` FOREIGN KEY (`Action_Id`) REFERENCES `DSM_Action` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8 COMMENT='Specifies which actions are to be run when using a transitio';

# Dumping data for table crsdb.DSM_Transition_Action: ~212 rows (approximately)
/*!40000 ALTER TABLE `DSM_Transition_Action` DISABLE KEYS */;
REPLACE INTO `DSM_Transition_Action` (`Id`, `Transition_Id`, `Order`, `Action_Id`, `Comment`) VALUES
	(1, 1, 0, 1, NULL),
	(2, 2, 0, 1, NULL),
	(3, 3, 0, 1, NULL),
	(4, 4, 0, 1, NULL),
	(5, 5, 0, 1, NULL),
	(6, 6, 0, 2, NULL),
	(7, 7, 0, 2, NULL),
	(8, 7, 1, 3, NULL),
	(9, 8, 0, 2, NULL),
	(10, 8, 1, 4, NULL),
	(11, 9, 0, 3, NULL),
	(12, 10, 0, 2, NULL),
	(13, 10, 1, 3, NULL),
	(14, 11, 0, 4, NULL),
	(15, 12, 0, 2, NULL),
	(16, 12, 1, 4, NULL),
	(17, 13, 0, 2, NULL),
	(18, 14, 0, 2, NULL),
	(19, 14, 1, 3, NULL),
	(20, 15, 0, 2, NULL),
	(21, 15, 1, 4, NULL),
	(22, 16, 0, 3, NULL),
	(23, 17, 0, 2, NULL),
	(24, 17, 1, 3, NULL),
	(25, 18, 0, 4, NULL),
	(26, 19, 0, 2, NULL),
	(27, 19, 1, 4, NULL),
	(28, 20, 0, 5, NULL),
	(29, 21, 0, 6, NULL),
	(30, 22, 0, 2, NULL),
	(31, 26, 0, 7, NULL),
	(32, 26, 1, 3, NULL),
	(33, 27, 0, 2, NULL),
	(34, 27, 1, 3, NULL),
	(35, 28, 0, 3, NULL),
	(36, 29, 0, 3, NULL),
	(37, 30, 0, 3, NULL),
	(38, 32, 0, 2, NULL),
	(39, 32, 1, 4, NULL),
	(40, 33, 0, 4, NULL),
	(41, 34, 0, 4, NULL),
	(42, 35, 0, 4, NULL),
	(43, 37, 0, 7, NULL),
	(44, 37, 1, 3, NULL),
	(45, 38, 0, 3, NULL),
	(46, 39, 0, 2, NULL),
	(47, 39, 1, 3, NULL),
	(48, 40, 0, 3, NULL),
	(49, 41, 0, 3, NULL),
	(50, 42, 0, 3, NULL),
	(51, 45, 0, 4, NULL),
	(52, 46, 0, 2, NULL),
	(53, 46, 1, 4, NULL),
	(54, 47, 0, 4, NULL),
	(55, 48, 0, 4, NULL),
	(56, 49, 0, 4, NULL),
	(57, 52, 0, 8, NULL),
	(58, 55, 0, 5, NULL),
	(59, 56, 0, 6, NULL),
	(60, 57, 0, 2, NULL),
	(61, 61, 0, 7, NULL),
	(62, 61, 1, 3, NULL),
	(63, 62, 0, 2, NULL),
	(64, 62, 1, 3, NULL),
	(65, 63, 0, 3, NULL),
	(66, 64, 0, 3, NULL),
	(67, 65, 0, 3, NULL),
	(68, 67, 0, 2, NULL),
	(69, 67, 1, 4, NULL),
	(70, 68, 0, 4, NULL),
	(71, 69, 0, 4, NULL),
	(72, 70, 0, 4, NULL),
	(73, 72, 0, 7, NULL),
	(74, 72, 1, 3, NULL),
	(75, 73, 0, 3, NULL),
	(76, 74, 0, 2, NULL),
	(77, 74, 1, 3, NULL),
	(78, 75, 0, 3, NULL),
	(79, 76, 0, 3, NULL),
	(80, 77, 0, 3, NULL),
	(81, 80, 0, 4, NULL),
	(82, 81, 0, 2, NULL),
	(83, 81, 1, 4, NULL),
	(84, 82, 0, 4, NULL),
	(85, 83, 0, 4, NULL),
	(86, 84, 0, 4, NULL),
	(87, 85, 0, 2, NULL),
	(88, 86, 0, 5, NULL),
	(89, 87, 0, 6, NULL),
	(90, 88, 0, 2, NULL),
	(91, 94, 0, 8, NULL),
	(92, 97, 0, 2, NULL),
	(93, 98, 0, 2, NULL),
	(94, 98, 1, 3, NULL),
	(95, 99, 0, 2, NULL),
	(96, 99, 1, 4, NULL),
	(97, 100, 0, 5, NULL),
	(98, 101, 0, 6, NULL),
	(99, 102, 0, 2, NULL),
	(100, 106, 0, 7, NULL),
	(101, 106, 1, 3, NULL),
	(102, 107, 0, 2, NULL),
	(103, 107, 1, 3, NULL),
	(104, 108, 0, 3, NULL),
	(105, 109, 0, 3, NULL),
	(106, 110, 0, 3, NULL),
	(107, 112, 0, 2, NULL),
	(108, 112, 1, 4, NULL),
	(109, 113, 0, 4, NULL),
	(110, 114, 0, 4, NULL),
	(111, 115, 0, 4, NULL),
	(112, 118, 0, 8, NULL),
	(113, 121, 0, 3, NULL),
	(114, 122, 0, 4, NULL),
	(115, 123, 0, 3, NULL),
	(116, 124, 0, 4, NULL),
	(117, 125, 0, 9, NULL),
	(118, 126, 0, 6, NULL),
	(119, 130, 0, 7, NULL),
	(120, 130, 1, 3, NULL),
	(121, 131, 0, 3, NULL),
	(122, 132, 0, 3, NULL),
	(123, 133, 0, 3, NULL),
	(124, 134, 0, 3, NULL),
	(125, 136, 0, 4, NULL),
	(126, 137, 0, 4, NULL),
	(127, 138, 0, 4, NULL),
	(128, 139, 0, 4, NULL),
	(129, 140, 0, 9, NULL),
	(130, 141, 0, 6, NULL),
	(131, 145, 0, 7, NULL),
	(132, 145, 1, 3, NULL),
	(133, 146, 0, 3, NULL),
	(134, 147, 0, 3, NULL),
	(135, 148, 0, 3, NULL),
	(136, 149, 0, 3, NULL),
	(137, 151, 0, 4, NULL),
	(138, 152, 0, 4, NULL),
	(139, 153, 0, 4, NULL),
	(140, 154, 0, 4, NULL),
	(141, 155, 0, 3, NULL),
	(142, 156, 0, 4, NULL),
	(143, 157, 0, 3, NULL),
	(144, 158, 0, 4, NULL),
	(145, 159, 0, 9, NULL),
	(146, 160, 0, 6, NULL),
	(147, 164, 0, 7, NULL),
	(148, 164, 1, 3, NULL),
	(149, 165, 0, 3, NULL),
	(150, 166, 0, 3, NULL),
	(151, 167, 0, 3, NULL),
	(152, 168, 0, 4, NULL),
	(153, 170, 0, 4, NULL),
	(154, 171, 0, 4, NULL),
	(155, 172, 0, 4, NULL),
	(156, 173, 0, 3, NULL),
	(157, 174, 0, 9, NULL),
	(158, 175, 0, 6, NULL),
	(159, 179, 0, 7, NULL),
	(160, 179, 1, 3, NULL),
	(161, 180, 0, 3, NULL),
	(162, 181, 0, 3, NULL),
	(163, 182, 0, 3, NULL),
	(164, 183, 0, 4, NULL),
	(165, 185, 0, 4, NULL),
	(166, 186, 0, 4, NULL),
	(167, 187, 0, 4, NULL),
	(168, 188, 0, 3, NULL),
	(169, 190, 0, 2, NULL),
	(170, 192, 0, 2, NULL),
	(171, 194, 0, 5, NULL),
	(172, 196, 0, 2, NULL),
	(173, 201, 0, 6, NULL),
	(174, 202, 0, 2, NULL),
	(175, 206, 0, 2, NULL),
	(176, 208, 0, 5, NULL),
	(177, 210, 0, 2, NULL),
	(178, 214, 0, 2, NULL),
	(179, 215, 0, 6, NULL),
	(180, 216, 0, 5, NULL),
	(181, 218, 0, 2, NULL),
	(182, 224, 0, 9, NULL),
	(183, 229, 0, 9, NULL),
	(184, 236, 0, 9, NULL),
	(186, 246, 0, 2, NULL),
	(187, 247, 0, 6, NULL),
	(188, 248, 0, 2, NULL),
	(189, 249, 0, 6, NULL),
	(190, 250, 0, 2, NULL),
	(191, 251, 0, 6, NULL),
	(192, 254, 0, 2, NULL),
	(193, 255, 0, 6, NULL),
	(194, 258, 0, 2, NULL),
	(195, 259, 0, 6, NULL),
	(196, 260, 0, 2, NULL),
	(197, 261, 0, 6, NULL),
	(198, 265, 0, 2, NULL),
	(199, 266, 0, 6, NULL),
	(200, 268, 0, 2, NULL),
	(201, 269, 0, 6, NULL),
	(202, 272, 0, 2, NULL),
	(203, 273, 0, 6, NULL),
	(204, 278, 0, 2, NULL),
	(205, 279, 0, 6, NULL),
	(206, 283, 0, 6, NULL),
	(207, 285, 0, 6, NULL),
	(208, 287, 0, 6, NULL),
	(209, 291, 0, 6, NULL),
	(210, 295, 0, 6, NULL),
	(211, 297, 0, 6, NULL),
	(212, 299, 0, 6, NULL),
	(213, 303, 0, 6, NULL);
/*!40000 ALTER TABLE `DSM_Transition_Action` ENABLE KEYS */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
