 /* CREATE A NEW REGISTRAR ACCOUNT */
INSERT INTO NicHandleHist VALUES (NULL, 'XNV498-IEDR', 'Domain Co - Test Acc', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Domain Co Limited', '1 The Road, Some Street', 14, 121, 'ciara@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XNV498-IEDR', 'B', 'vatregid', 'YES', 'XNV498-IEDR', NOW());
INSERT INTO NicHandle VALUES ('XNV498-IEDR', 'Domain Co - Test Acc', 1, 'Domain Co Limited', '1 The Road, Some Street', 14, 121, 'ciara@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XNV498-IEDR', 'B', 'vatregid', 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'));
INSERT INTO TelecomHist VALUES
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'XNV498-IEDR', '+35312345345', 'Phone', 0),
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'XNV498-IEDR', '+35312345300', 'Fax', 0);
INSERT INTO Telecom VALUES ('XNV498-IEDR', '+35312345345', 'Phone', 0), ('XNV498-IEDR', '+35312345300', 'Fax', 0);
INSERT INTO AccountHist VALUES (NULL, 603, 'Test Registrar', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, '2011-12-08 15:00:49', 'IH4-IEDR');
INSERT INTO Account VALUES (603, 'Test Registrar', 'XNV498-IEDR', 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603));
UPDATE NicHandleHist SET Account_Chng_Id = (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603) WHERE Nic_Handle = 'XNV498-IEDR';
UPDATE NicHandle SET A_Number = 603 WHERE Nic_Handle = 'XNV498-IEDR';

 /* SET REGISTRAR PERMISSIONS AND VALUES */
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', 'XNV498-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', NOW(), 'NO', 'NO', NULL);
INSERT INTO Access VALUES ('XNV498-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', '.Z805gDk7mXJJfbcIwZJde', NOW(), 'NO', 'NO', NULL, (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'XNV498-IEDR'));
INSERT INTO Reseller_Defaults VALUES ('XNV498-IEDR', 'XNV498-IEDR', NULL, 'xml');

 /* SET DEPOSIT BALANCE AND ADD HISTORICAL ENTRIES IN HIST */
INSERT INTO DepositHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), NOW(), 2000, 2000, '20130109164738-C-1205021', '2000', 'TOPUP', NULL, NULL, NOW());
INSERT INTO Deposit VALUES ('XNV498-IEDR', NOW(), 2000, 2000, '20130109164738-C-1205021', '2000', 'TOPUP', NULL, NULL, (SELECT MAX(D.Chng_Id) FROM DepositHist D JOIN NicHandleHist NH  ON D.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'XNV498-IEDR'));

 /* CREATE NIC HANDLE TO BE USED AS ADMIN CONTACTS ON THIS ACCOUNTS DOMAINS */
INSERT INTO NicHandleHist VALUES (NULL, 'XOE551-IEDR', 'Testy McTesterson', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), 'TestCo Limited', 'Harbour Square\r\nFourth Floor,\r\nDun Laoghaire\r\nBhaile ?tha Cliath', 22, 121, 'testersonMcTestsy@gmail.com', 'Active', '2012-03-20 12:12:53', '2012-03-20 12:12:53', '2012-03-20 12:12:53', 'NO', 'Creating NicHandle via CRS-WS-API by XNV498-IEDR ON Tue Mar 20 12:12:53 GMT 2012', 'XNV498-IEDR', 'A', 'vatregid', 'YES', 'XNV498-IEDR', '2012-03-20 12:12:53');
INSERT INTO NicHandle VALUES ('XOE551-IEDR', 'Testy McTesterson', 603, 'TestCo Limited', 'Harbour Square\r\nFourth Floor,\r\nDun Laoghaire\r\nBhaile ?tha Cliath', 22, 121, 'testersonMcTestsy@gmail.com', 'Active', '2012-03-20 12:12:53', '2012-03-20 12:12:53', '2012-03-20 12:12:53', 'NO', 'Creating NicHandle via CRS-WS-API by XNV498-IEDR ON Tue Mar 20 12:12:53 GMT 2012', 'XNV498-IEDR', 'A', 'vatregid', 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE551-IEDR'));
INSERT INTO TelecomHist VALUES
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE551-IEDR'), 'XOE551-IEDR', '086 3428372', 'Phone', 0),
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE551-IEDR'), 'XOE551-IEDR', '01 235664353', 'Phone', 1),
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE551-IEDR'), 'XOE551-IEDR', '0980989809', 'Fax', 0);
INSERT INTO Telecom VALUES ('XOE551-IEDR', '086 3428372', 'Phone', 0), ('XOE551-IEDR', '01 235664353', 'Phone', 1), ('XOE551-IEDR', '0980989809', 'Fax', 0);
INSERT INTO NicHandleHist VALUES (NULL, 'XOE552-IEDR', 'Testy McTesterson', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), 'TestCo Limited', 'Mangalia 4', 0, 199, 'testersonMcTestsy@gmail.com', 'Active', '2012-03-20 12:12:53', '2012-03-20 12:12:53', '2012-03-20 12:12:53', 'NO', 'Creating NicHandle via CRS-WS-API by XNV498-IEDR ON Tue Mar 20 12:12:53 GMT 2012', 'XBC189-IEDR', 'B', 'vatregid', 'YES', 'XNV498-IEDR', '2012-03-20 12:12:53');
INSERT INTO NicHandle VALUES ('XOE552-IEDR', 'Testy McTesterson', 603, 'TestCo Limited', 'Mangalia 4', 0, 199, 'testersonMcTestsy@gmail.com', 'Active', '2012-03-20 12:12:53', '2012-03-20 12:12:53', '2012-03-20 12:12:53', 'NO', 'Creating NicHandle via CRS-WS-API by XNV498-IEDR ON Tue Mar 20 12:12:53 GMT 2012', 'XBC189-IEDR', 'B', 'vatregid', 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE552-IEDR'));
INSERT INTO TelecomHist VALUES
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE552-IEDR'), 'XOE552-IEDR', '086 3428372', 'Phone', 0),
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE552-IEDR'), 'XOE552-IEDR', '01 235664353', 'Phone', 1);
INSERT INTO Telecom VALUES ('XOE552-IEDR', '086 3428372', 'Phone', 0), ('XOE552-IEDR', '01 235664353', 'Phone', 1);

