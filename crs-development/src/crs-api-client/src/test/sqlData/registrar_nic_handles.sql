/* CREATE A NEW REGISTRAR ACCOUNT WITH NO DEFAULT NAMESERVERS */
INSERT INTO NicHandleHist VALUES (NULL, 'XBC189-IEDR', 'Test Registrar Nic', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'ciara@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XBC189-IEDR', 'A', 'vatregid', 'YES', 'XBC189-IEDR', NOW());
INSERT INTO NicHandle VALUES ('XBC189-IEDR', 'Test Registrar Nic', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'ciara@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XBC189-IEDR', 'A', 'vatregid', 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'));
INSERT INTO TelecomHist VALUES
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'XBC189-IEDR', '+35312345345', 'Phone', 0),
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'XBC189-IEDR', '+35312345300', 'Fax', 0);
INSERT INTO Telecom VALUES ('XBC189-IEDR', '+35312345345', 'Phone', 0), ('XBC189-IEDR', '+35312345300', 'Fax', 0);
INSERT INTO DepositHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NOW(), 20000, 20000, '20130109164738-C-1205021', '20000', 'TOPUP', NULL, NULL, NOW());
INSERT INTO Deposit VALUES ('XBC189-IEDR', NOW(), 20000, 20000, '20130109164738-C-1205021', '20000', 'TOPUP', NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'));
INSERT INTO AccountHist VALUES (NULL, 600, 'Test Registrar', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, '2011-12-08 15:00:49', 'IH4-IEDR');
INSERT INTO Account VALUES (600, 'Test Registrar', 'XBC189-IEDR', 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600));
UPDATE NicHandleHist SET Account_Chng_Id = (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600) WHERE Nic_Handle = 'XBC189-IEDR';
UPDATE NicHandle SET A_Number = 600 WHERE Nic_Handle = 'XBC189-IEDR';

/* SET REGISTRAR PERMISSIONS AND VALUES */
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 1024, 'NO', 'XBC189-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', NOW(), 'NO', 'NO', NULL);
INSERT INTO Access VALUES ('XBC189-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 1024, 'NO', '.Z805gDk7mXJJfbcIwZJde', NOW(), 'NO', 'NO', NULL, (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'XBC189-IEDR'));
INSERT INTO Reseller_Defaults VALUES ('XBC189-IEDR', 'XBC189-IEDR', NULL, 'pdf');

/* CREATE NIC HANDLE TO BE USED AS ADMIN CONTACTS ON THIS ACCOUNTS DOMAINS */
INSERT INTO NicHandleHist VALUES (NULL, 'XOE550-IEDR', 'Testy McTesterson', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), 'TestCo Limited', 'Harbour Square\r\nFourth Floor,\r\nDun Laoghaire\r\nBhaile ?tha Cliath', 22, 121, 'testersonMcTestsy@gmail.com', 'Active', '2012-03-20 12:12:53', '2012-03-20 12:12:53', '2012-03-20 12:12:53', 'NO', 'Creating NicHandle via CRS-WS-API by XBC189-IEDR ON Tue Mar 20 12:12:53 GMT 2012', 'XBC189-IEDR', 'A', 'vatregid', 'YES', 'XBC189-IEDR', '2012-03-20 12:12:55');
INSERT INTO NicHandle VALUES ('XOE550-IEDR', 'Testy McTesterson', 600, 'TestCo Limited', 'Harbour Square\r\nFourth Floor,\r\nDun Laoghaire\r\nBhaile ?tha Cliath', 22, 121, 'testersonMcTestsy@gmail.com', 'Active', '2012-03-20 12:12:53', '2012-03-20 12:12:53', '2012-03-20 12:12:53', 'NO', 'Creating NicHandle via CRS-WS-API by XBC189-IEDR ON Tue Mar 20 12:12:53 GMT 2012', 'XBC189-IEDR', 'A', 'vatregid', 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'));
INSERT INTO TelecomHist VALUES
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'XOE550-IEDR', '086 3428372', 'Phone', 0),
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'XOE550-IEDR', '0980989809', 'Fax', 0);
INSERT INTO Telecom VALUES ('XOE550-IEDR', '086 3428372', 'Phone', 0), ('XOE550-IEDR', '0980989809', 'Fax', 0);
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XBC189-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('XOE550-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'XOE550-IEDR'));


/* CREATE A NEW REGISTRAR ACCOUNT WITH DEFAULT NAMESERVERS */
INSERT INTO NicHandleHist VALUES (NULL, 'XBC199-IEDR', 'Test Registrar Nic', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'ciara@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XBC199-IEDR', 'A', 'vatregid', 'YES', 'XBC199-IEDR', NOW());
INSERT INTO NicHandle VALUES ('XBC199-IEDR', 'Test Registrar Nic', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'ciara@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XBC199-IEDR', 'A', 'vatregid', 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC199-IEDR'));
INSERT INTO TelecomHist VALUES
  ((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC199-IEDR'), 'XBC199-IEDR', '+35312345345', 'Phone', 0),
  ((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC199-IEDR'), 'XBC199-IEDR', '+35312345300', 'Fax', 0);
INSERT INTO Telecom VALUES ('XBC199-IEDR', '+35312345345', 'Phone', 0), ('XBC199-IEDR', '+35312345300', 'Fax', 0);
INSERT INTO DepositHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC199-IEDR'), NOW(), 20000, 20000, '20130109164738-C-1205021', '20000', 'TOPUP', NULL, NULL, NOW());
INSERT INTO Deposit VALUES ('XBC199-IEDR', NOW(), 20000, 20000, '20130109164738-C-1205021', '20000', 'TOPUP', NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC199-IEDR'));
INSERT INTO AccountHist VALUES (NULL, 602, 'Test Registrar', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC199-IEDR'), 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, '2011-12-08 15:00:49', 'IH4-IEDR');
INSERT INTO Account VALUES (602, 'Test Registrar', 'XBC199-IEDR', 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 602));
UPDATE NicHandleHist SET Account_Chng_Id = (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 602) WHERE Nic_Handle = 'XBC199-IEDR';
UPDATE NicHandle SET A_Number = 602 WHERE Nic_Handle = 'XBC199-IEDR';

/* SET REGISTRAR PERMISSIONS AND VALUES */
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC199-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 1024, 'NO', 'XBC199-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', NOW(), 'NO', 'NO', NULL);
INSERT INTO Access VALUES ('XBC199-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 1024, 'NO', '.Z805gDk7mXJJfbcIwZJde', NOW(), 'NO', 'NO', NULL, (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'XBC199-IEDR'));
INSERT INTO Reseller_Defaults VALUES ('XBC199-IEDR', 'XBC199-IEDR', NULL, 'pdf');
INSERT INTO ResellerDefaultNameservers VALUES
  ('XBC199-IEDR', 'ns1.defaultns.ie', 1, NOW() - INTERVAL 3 MONTH),
  ('XBC199-IEDR', 'ns2.defaultns.ie', 2, NOW() - INTERVAL 3 MONTH),
  ('XBC199-IEDR', 'ns3.defaultns.ie', 3, NOW() - INTERVAL 3 MONTH);

/* CREATE NIC HANDLE TO BE USED AS ADMIN CONTACTS ON THIS ACCOUNTS DOMAINS */
INSERT INTO NicHandleHist VALUES (NULL, 'XOE555-IEDR', 'Testy McTesterson', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 602), 'TestCo Limited', 'Harbour Square\r\nFourth Floor,\r\nDun Laoghaire\r\nBhaile ?tha Cliath', 22, 121, 'testersonMcTestsy@gmail.com', 'Active', '2012-03-20 12:12:53', '2012-03-20 12:12:53', '2012-03-20 12:12:53', 'NO', 'Creating NicHandle via CRS-WS-API by XBC199-IEDR ON Tue Mar 20 12:12:53 GMT 2012', 'XBC199-IEDR', 'A', 'vatregid', 'YES', 'XBC199-IEDR', '2012-03-20 12:12:55');
INSERT INTO NicHandle VALUES ('XOE555-IEDR', 'Testy McTesterson', 602, 'TestCo Limited', 'Harbour Square\r\nFourth Floor,\r\nDun Laoghaire\r\nBhaile ?tha Cliath', 22, 121, 'testersonMcTestsy@gmail.com', 'Active', '2012-03-20 12:12:53', '2012-03-20 12:12:53', '2012-03-20 12:12:53', 'NO', 'Creating NicHandle via CRS-WS-API by XBC199-IEDR ON Tue Mar 20 12:12:53 GMT 2012', 'XBC199-IEDR', 'A', 'vatregid', 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE555-IEDR'));
INSERT INTO TelecomHist VALUES
  ((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE555-IEDR'), 'XOE555-IEDR', '086 3428372', 'Phone', 0),
  ((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE555-IEDR'), 'XOE555-IEDR', '0980989809', 'Fax', 0);
INSERT INTO Telecom VALUES ('XOE555-IEDR', '086 3428372', 'Phone', 0), ('XOE555-IEDR', '0980989809', 'Fax', 0);
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE555-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XBC199-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('XOE555-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'XOE555-IEDR'));
