INSERT INTO NicHandleHist VALUES (NULL, 'UC001AA-IEDR', 'Use Case 001 - Direct - VAT Extempt', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc001_aa@iedr.ie', 'Active', NOW(), NOW(), NOW(),'NO','test data generation','UC001AA-IEDR', 'B', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC001AA-IEDR', 'Use Case 001 - Direct - VAT Extempt', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc001_aa@iedr.ie', 'Active', NOW(), NOW(), NOW(),'NO','test data generation','UC001AA-IEDR', 'B', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC001AA-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC001AA-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC001AA-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC001AA-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UC001AB-IEDR', 'Use Case 001 - Admin Contact 1', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc001_ab@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC001AB-IEDR', 'B', NULL, 'NO', 'XBC189-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC001AB-IEDR', 'Use Case 001 - Admin Contact 1', 600, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc001_ab@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC001AB-IEDR', 'B', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC001AB-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC001AB-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XBC189-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC001AB-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC001AB-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UC001AC-IEDR', 'Use Case 001 - Admin Contact 2', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc001_ac@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC001AC-IEDR', 'B', NULL, 'NO', 'XBC189-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC001AC-IEDR', 'Use Case 001 - Admin Contact 2', 600, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc001_ac@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC001AC-IEDR', 'B', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC001AC-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC001AC-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XBC189-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC001AC-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC001AC-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UC001SU-IEDR', 'Use Case 001 - Direct - Suspended', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc001_su@iedr.ie', 'Suspended', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC001SU-IEDR', 'B', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC001SU-IEDR', 'Use Case 001 - Direct - Suspended', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc001_su@iedr.ie', 'Suspended', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC001SU-IEDR', 'B', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC001SU-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC001SU-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC001SU-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC001SU-IEDR'));

INSERT INTO DomainHist VALUES (NULL, 'uc001-qa11210a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc001-qa11210a.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc001-qa11210a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc001-qa11210a.ie'), 'uc001-qa11210a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc001-qa11210a.ie'), 'uc001-qa11210a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc001-qa11210a.ie'), 'uc001-qa11210a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc001-qa11210a.ie'), 'uc001-qa11210a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc001-qa11210a.ie','XOE550-IEDR', 'Admin'), ('uc001-qa11210a.ie','XDD274-IEDR', 'Billing'), ('uc001-qa11210a.ie','XDD274-IEDR', 'Creator'), ('uc001-qa11210a.ie','XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc001-qa11210a.ie'), 'uc001-qa11210a.ie', 'ns1.uc001-qa11210a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc001-qa11210a.ie'), 'uc001-qa11210a.ie', 'ns2.uc001-qa11210a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc001-qa11210a.ie'), 'uc001-qa11210a.ie', 'ns3.uc001-qa11210a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc001-qa11210a.ie', 'ns1.uc001-qa11210a.ie', '10.10.121.23', NULL, '1'), ('uc001-qa11210a.ie', 'ns2.uc001-qa11210a.ie', '1.3.4.2', NULL, '2'), ('uc001-qa11210a.ie', 'ns3.uc001-qa11210a.ie', '10.12.13.14', NULL, '3');
