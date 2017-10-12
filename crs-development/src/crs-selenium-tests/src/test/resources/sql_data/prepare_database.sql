# Create required nic handles and accounts.
INSERT INTO NicHandleHist VALUES (NULL, 'XBC189-IEDR', 'Registrar (Vat Account)', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'xbc189@iedr.ie','Active', NOW(), NOW(), NOW(),'NO','test data generation','XBC189-IEDR', 'A', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('XBC189-IEDR', 'Registrar (Vat Account)', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'xbc189@iedr.ie','Active', NOW(), NOW(), NOW(),'NO','test data generation','XBC189-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('XBC189-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'XBC189-IEDR'));
INSERT INTO AccountHist VALUES (NULL, 600, 'Test Registrar', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, NOW(), 'XBC189-IEDR');
INSERT INTO Account VALUES (600, 'Test Registrar', 'XBC189-IEDR', 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600));
UPDATE NicHandleHist SET Account_Chng_Id = (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600) WHERE Nic_Handle = 'XBC189-IEDR';
UPDATE NicHandle SET A_Number = 600 WHERE Nic_Handle = 'XBC189-IEDR';
INSERT INTO DepositHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NOW(), 2000, 2000, '20130109164738-C-1205021', '2000', 'INIT', NULL, NULL, NOW());
INSERT INTO Deposit VALUES ('XBC189-IEDR', NOW(), 2000, 2000, '20130109164738-C-1205021', '2000', 'INIT', NULL, NULL, (SELECT MAX(D.Chng_Id) FROM DepositHist D JOIN NicHandleHist NH  ON D.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'XBC189-IEDR'));
INSERT INTO TelecomHist VALUES ((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'XBC189-IEDR', '123123123', 'Phone', 0);
INSERT INTO Telecom VALUES ('XBC189-IEDR', '123123123', 'Phone', 0);
INSERT INTO Reseller_Defaults VALUES ('XBC189-IEDR', 'XBC189-IEDR', NULL, 'both');
INSERT INTO ResellerDefaultNameservers VALUES ('XBC189-IEDR', 'ns1.blacknightsolutions.com', 0, NOW());
INSERT INTO ResellerDefaultNameservers VALUES ('XBC189-IEDR', 'ns2.blacknightsolutions.com', 1, NOW());

INSERT INTO NicHandleHist VALUES (NULL, 'XOE550-IEDR', 'Registrar (Vat Account)', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'xoe550@iedr.ie','Active', NOW(), NOW(), NOW(),'NO','test data generation','XBC189-IEDR', 'A', NULL, 'YES', 'XBC189-IEDR', NOW());
INSERT INTO NicHandle VALUES ('XOE550-IEDR', 'Contact for XBC189-IEDR', '600', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'xoe550@iedr.ie','Active', NOW(), NOW(), NOW(),'NO','test data generation','XBC189-IEDR', 'A', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XBC189-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('XOE550-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'XOE550-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'XNV498-IEDR', 'Registrar (Vat Account)', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'xnv498@iedr.ie','Active', NOW(), NOW(), NOW(),'NO','test data generation','XNV498-IEDR', 'A', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('XNV498-IEDR', 'Registrar (Non Vat Account)', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'xnv498@iedr.ie','Active', NOW(), NOW(), NOW(),'NO','test data generation','XNV498-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('XNV498-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'XNV498-IEDR'));
INSERT INTO AccountHist VALUES (NULL, 603, 'Test Registrar', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, NOW(), 'XNV498-IEDR');
INSERT INTO Account VALUES (603, 'Test Registrar', 'XNV498-IEDR', 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603));
UPDATE NicHandleHist SET Account_Chng_Id = (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603) WHERE Nic_Handle = 'XNV498-IEDR';
UPDATE NicHandle SET A_Number = 603 WHERE Nic_Handle = 'XNV498-IEDR';
INSERT INTO DepositHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), NOW(), 2000, 2000, '20130109164738-C-1205021', '2000', 'INIT', NULL, NULL, NOW());
INSERT INTO Deposit VALUES ('XNV498-IEDR', NOW(), 2000, 2000, '20130109164738-C-1205021', '2000', 'INIT', NULL, NULL, (SELECT MAX(D.Chng_Id) FROM DepositHist D JOIN NicHandleHist NH  ON D.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'XNV498-IEDR'));
INSERT INTO TelecomHist VALUES ((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'XNV498-IEDR', '123123123', 'Phone', 0);
INSERT INTO Telecom VALUES ('XNV498-IEDR', '123123123', 'Phone', 0);

INSERT INTO NicHandleHist VALUES (NULL, 'XDD274-IEDR', 'Direct', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'xdd274@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XNV498-IEDR', 'A', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('XDD274-IEDR', 'Direct', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'xdd274@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XDD274-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('XDD274-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'XDD274-IEDR'));
INSERT INTO TelecomHist VALUES ((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'XDD274-IEDR', '123123123', 'Phone', 0);
INSERT INTO Telecom VALUES ('XDD274-IEDR', '123123123', 'Phone', 0);

UPDATE Access SET password_change_TS=CURDATE() WHERE Nic_Handle='IDL2-IEDR';

# Make sure there are two countries with VAT category A and one with B.
UPDATE Countries SET vat_category='A' WHERE Id = 121;
UPDATE Countries SET vat_category='A' WHERE Id = 119;
UPDATE Countries SET vat_category='B' WHERE Id = 1;

# Make sure that during test run scheduler won't run any jobs on its own.
UPDATE SchedulerConfig SET schedule="0 5 31 2 *";
