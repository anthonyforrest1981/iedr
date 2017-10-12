INSERT INTO NicHandleHist VALUES (NULL, 'UC051AA-IEDR', 'Use Case 051 - Direct', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc051_aa@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC051AA-IEDR', 'A', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC051AA-IEDR', 'Use Case 051 - Direct', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc051_aa@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC051AA-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC051AA-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC051AA-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC051AA-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC051AA-IEDR'));
INSERT INTO TelecomHist VALUES
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC051AA-IEDR'), 'UC051AA-IEDR', '123123123', 'Phone', 0),
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC051AA-IEDR'), 'UC051AA-IEDR', '123123123', 'Fax', 0);
INSERT INTO Telecom VALUES ('UC051AA-IEDR', '123123123', 'Phone', 0), ('UC051AA-IEDR', '123123123', 'Fax', 0);

INSERT INTO NicHandleHist VALUES (NULL, 'UC051AB-IEDR', 'Use Case 051 - Newly created Direct', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc051_ab@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC051AA-IEDR', 'A', NULL, 'NO', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC051AB-IEDR', 'Use Case 051 - Newly created Direct', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc051_ab@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC051AB-IEDR', 'A', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC051AB-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC051AB-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC051AB-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC051AB-IEDR'));
INSERT INTO TelecomHist VALUES
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC051AB-IEDR'), 'UC051AB-IEDR', '123123123', 'Phone', 0),
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC051AB-IEDR'), 'UC051AB-IEDR', '123123123', 'Fax', 0);
INSERT INTO Telecom VALUES ('UC051AB-IEDR', '123123123', 'Phone', 0), ('UC051AB-IEDR', '123123123', 'Fax', 0);

