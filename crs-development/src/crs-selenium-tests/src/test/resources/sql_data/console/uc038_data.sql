INSERT INTO NicHandleHist VALUES (NULL, 'UC038AA-IEDR', 'Use Case 038 - Registrar', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc038_aa@iedr.ie','Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC038AA-IEDR', 'A', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC038AA-IEDR', 'Use Case 038 - Registrar', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc038_aa@iedr.ie','Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC038AA-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC038AA-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC038AA-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC038AA-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC038AA-IEDR'));
INSERT INTO AccountHist VALUES (NULL, 700, 'Test Registrar', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC038AA-IEDR'), 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, NOW(), 'UC024AA-IEDR');
INSERT INTO Account VALUES (700, 'Test Registrar', 'UC038AA-IEDR', 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 700));
UPDATE NicHandleHist SET Account_Chng_Id = (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 700) WHERE Nic_Handle = 'UC038AA-IEDR';
UPDATE NicHandle SET A_Number = 700 WHERE Nic_Handle = 'UC038AA-IEDR';

# UC#038: Query Deposit Account Balance - XSS
INSERT INTO DepositHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC038AA-IEDR'), NOW() - Interval 15 Day, 0.00, 2000.00, 'UC038-NOSC01', 2000.00, 'TOPUP', NULL, NULL, NOW() - Interval 15 Day);
INSERT INTO DepositHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC038AA-IEDR'), NOW() - Interval 5 Day, 0.00, 2000.00, 'UC038-NOSC01', 2000.00, 'TOPUP', NULL, NULL, NOW() - Interval 5 Day);

