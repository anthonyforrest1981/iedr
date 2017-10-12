INSERT INTO `SecondaryMarketBuyRequestHist` VALUES
  (101, NOW(), 'XBC189-IEDR', 101, (SELECT Chng_ID FROM Domain WHERE D_Name = 'example0501.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'XBC189-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 600), 'Hist holder', NULL, 1, NULL, 1, NULL, 'Hist remark', 'Hist hostmaster remark', 'Hist admin name', NULL, 'ha@a.ie', NULL, 'Hist Test Inc.', NULL, 'Hist Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW(), NULL, NULL, 'New',
   (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'XBC189-IEDR')),
  (102, NOW(), 'XBC189-IEDR', 102, (SELECT Chng_ID FROM Domain WHERE D_Name = 'example0502.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'XBC189-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 600), 'Hist holder', NULL, 1, NULL, 1, NULL, 'Hist remark', 'Hist hostmaster remark', 'Hist admin name', NULL, 'hb@b.ie', NULL, 'Hist Test Inc.', NULL, 'Hist Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW(), NULL, NULL, 'New',
   (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'XBC189-IEDR')),
  (103, NOW(), 'XBC189-IEDR', 102, (SELECT Chng_ID FROM Domain WHERE D_Name = 'example0503.ie'), NULL, (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'XBC189-IEDR'), (SELECT Chng_ID FROM Account WHERE A_Number = 600), 'Hist holder', NULL, 1, NULL, 1, NULL, 'Hist remark', 'Hist hostmaster remark', 'Hist admin name', NULL, 'hb@b.ie', NULL, 'Hist Test Inc.', NULL, 'Hist Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW(), NULL, NULL, 'New',
   (SELECT Chng_ID FROM NicHandle WHERE Nic_Handle = 'XBC189-IEDR'));
INSERT INTO `SecondaryMarketBuyRequestTelecomHist` VALUES
  (101, '123456789', 'Phone', 0),
  (102, '987654321', 'Phone', 0),
  (103, '987654321', 'Phone', 0);
INSERT INTO `SecondaryMarketBuyRequest` VALUES
  (101, 'example0501.ie', NULL, 'XBC189-IEDR', 600, 'First holder', NULL, 1, NULL, 1, NULL, 'First remark', 'First hostmaster remark', 'Admin1 name', NULL, 'a@a.ie', NULL, 'Test1 Inc.', NULL, 'Test Street 1', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 20 DAY, 'APIAUTHCODE', NOW(), 'Passed', 'XBC189-IEDR', 101),
  (102, 'example0502.ie', NULL, 'XBC189-IEDR', 600, 'Second holder', NULL, 1, NULL, 1, NULL, 'Second remark', 'Second hostmaster remark', 'Admin2 name', NULL, 'b@b.ie', NULL, 'Test2 Inc.', NULL, 'Test Street 2', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 10 DAY, NULL, NULL, 'New', NULL, 102),
  (103, 'example0503.ie', NULL, 'XBC189-IEDR', 600, 'Holder', NULL, 1, NULL, 1, NULL, 'Remark', 'Hostmaster remark', 'Admin name', NULL, 'b@b.ie', NULL, 'Test Inc.', NULL, 'Test Street', NULL, 119, NULL, 2, NULL, NULL, NULL, NOW() - INTERVAL 10 DAY, NULL, NULL, 'New', NULL, 103);
INSERT INTO `SecondaryMarketBuyRequestTelecom` VALUES
  (101, '123456789', 'Phone', 0),
  (102, '987654321', 'Phone', 0),
  (103, '987654321', 'Phone', 0);

UPDATE Domain SET DSM_State = 1041 WHERE D_Name IN ('example0501.ie', 'example0502.ie', 'example0503.ie');
