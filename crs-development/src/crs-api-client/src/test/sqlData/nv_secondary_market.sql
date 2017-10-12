INSERT INTO `SecondaryMarketBuyRequest` VALUES
  (201, 'nv-domain0105.ie', NULL, 'XNV498-IEDR', 603, 'New Test Holder', NULL, 1, NULL, 1, NULL, 'Test Remark', 'Hostmaster Remark', 'New Admin', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'RTQ6E2PDZBRN', NOW(), 'Passed', NULL, 1),
  (202, 'nv-domain0106.ie', NULL, 'XNV498-IEDR', 603, 'New Test Holder', NULL, 1, NULL, 1, NULL, 'Test Remark', 'Hostmaster Remark', 'New Admin', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'RTR31H5B2P2I', NOW(), 'Passed', NULL, 1);
INSERT INTO `SecondaryMarketBuyRequestTelecom` VALUES
  (201, '+35312345345', 'Phone', 0),
  (202, '+35312345346', 'Phone', 0);
INSERT INTO `SecondaryMarketBuyRequestHist` VALUES
  (NULL, NOW(), 'XNV498-IEDR', 201, (SELECT Chng_ID FROM Domain WHERE D_Name = 'nv-domain0105.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'XNV498-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 603), 'New Test Holder', NULL, 1, NULL, 1, NULL, 'Test Remark', 'Hostmaster Remark', 'New Admin', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'RTQ6E2PDZBRN', NOW(), 'Passed', NULL),
  (NULL, NOW(), 'XNV498-IEDR', 202, (SELECT Chng_ID FROM Domain WHERE D_Name = 'nv-domain0106.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'XNV498-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 603), 'New Test Holder', NULL, 1, NULL, 1, NULL, 'Test Remark', 'Hostmaster Remark', 'New Admin', NULL, 'email@iedr.ie', NULL, 'Test Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 14 DAY, 'RTR31H5B2P2I', NOW(), 'Passed', NULL);
INSERT INTO `SecondaryMarketBuyRequestTelecomHist` VALUES
  ((SELECT MAX(`Chng_ID`) FROM `SecondaryMarketBuyRequestHist` WHERE `Id` = 201), '+35312345345', 'Phone', 0),
  ((SELECT MAX(`Chng_ID`) FROM `SecondaryMarketBuyRequestHist` WHERE `Id` = 202), '+35312345346', 'Phone', 0);
UPDATE `SecondaryMarketBuyRequest` BR SET `Chng_ID` = (SELECT MAX(`Chng_ID`) FROM `SecondaryMarketBuyRequestHist` WHERE `Id` = BR.`Id`) WHERE `D_Name` IN ('nv-domain0105.ie', 'nv-domain0106.ie');

INSERT INTO `SecondaryMarketSellRequest` VALUES
  (201, 'XNV498-IEDR', CURDATE() - INTERVAL 1 DAY, 'Processing', 201, 1),
  (202, 'XNV498-IEDR', CURDATE() - INTERVAL 1 DAY, 'Processing', 202, 1);
INSERT INTO `SecondaryMarketSellRequestHist` VALUES
  (NULL, NOW() - INTERVAL 1 DAY, 'XNV498-IEDR', 201, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'XNV498-IEDR'), NOW() - INTERVAL 1 DAY, 'Processing', (SELECT MAX(`Chng_ID`) FROM `SecondaryMarketBuyRequestHist` WHERE `Id` = 201)),
  (NULL, NOW() - INTERVAL 1 DAY, 'XNV498-IEDR', 202, (SELECT MAX(`Chng_ID`) FROM `NicHandle` WHERE `Nic_Handle` = 'XNV498-IEDR'), NOW() - INTERVAL 1 DAY, 'Processing', (SELECT MAX(`Chng_ID`) FROM `SecondaryMarketBuyRequestHist` WHERE `Id` = 202));
UPDATE `SecondaryMarketSellRequest` SR SET `Chng_ID` = (SELECT MAX(`Chng_ID`) FROM `SecondaryMarketSellRequestHist` WHERE `Id` = SR.`Id`) WHERE `Id` IN (201, 202);

