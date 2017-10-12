INSERT INTO NicHandleHist VALUES (NULL, 'UC045R1-IEDR', 'Use Case 045 - Registrar - Losing Account', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045r1@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R1-IEDR', 'A', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC045R1-IEDR', 'Use Case 045 - Registrar - Losing Account', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045r1@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R1-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R1-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R1-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC045R1-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC045R1-IEDR'));
INSERT INTO AccountHist VALUES (NULL, 1111, 'UC045 Losing Account', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R1-IEDR'), 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, NOW(), 'UC045R1-IEDR');
INSERT INTO Account VALUES (1111, 'UC045 Losing Account', 'UC045R1-IEDR', 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1111));
UPDATE NicHandleHist SET Account_Chng_Id = (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1111) WHERE Nic_Handle = 'UC045R1-IEDR';
UPDATE NicHandle SET A_Number = 1111 WHERE Nic_Handle = 'UC045R1-IEDR';

INSERT INTO NicHandleHist VALUES (NULL, 'UC045R2-IEDR', 'Use Case 045 - Registrar - Losing Account', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045r2@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R2-IEDR', 'A', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC045R2-IEDR', 'Use Case 045 - Registrar - Losing Account', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045r2@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R2-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC045R2-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC045R2-IEDR'));
INSERT INTO AccountHist VALUES (NULL, 1112, 'UC045 Losing Account', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, NOW(), 'UC045R2-IEDR');
INSERT INTO Account VALUES (1112, 'UC045 Losing Account', 'UC045R2-IEDR', 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1112));
UPDATE NicHandleHist SET Account_Chng_Id = (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1112) WHERE Nic_Handle = 'UC045R2-IEDR';
UPDATE NicHandle SET A_Number = 1112 WHERE Nic_Handle = 'UC045R2-IEDR';

INSERT INTO NicHandleHist VALUES (NULL, 'UC045R3-IEDR', 'Use Case 045 - Registrar - Gaining Account', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045r3@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R3-IEDR', 'A', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC045R3-IEDR', 'Use Case 045 - Registrar - Gaining Account', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045r3@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R3-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R3-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R3-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC045R3-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC045R3-IEDR'));
INSERT INTO AccountHist VALUES (NULL, 1113, 'UC045 Gaining Account', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R3-IEDR'), 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, NOW(), 'UC045R3-IEDR');
INSERT INTO Account VALUES (1113, 'UC045 Gaining Account', 'UC045R3-IEDR', 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1113));
UPDATE NicHandleHist SET Account_Chng_Id = (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1113) WHERE Nic_Handle = 'UC045R3-IEDR';
UPDATE NicHandle SET A_Number = 1113 WHERE Nic_Handle = 'UC045R3-IEDR';

INSERT INTO NicHandleHist VALUES (NULL, 'UC045C1-IEDR', 'Use Case 045 - Contact', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1111), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045c1@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R1-IEDR', 'A', NULL, 'NO', 'UC045R1-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC045C1-IEDR', 'Use Case 045 - Contact', '1111', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045c1@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R1-IEDR', 'A', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C1-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C1-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'UC045R1-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC045C1-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC045C1-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UC045C2-IEDR', 'Use Case 045 - Contact', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1112), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc04c2@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R2-IEDR', 'A', NULL, 'NO', 'UC045R2-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC045C2-IEDR', 'Use Case 045 - Contact', '1112', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc04c2@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R2-IEDR', 'A', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C2-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C2-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'UC045R2-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC045C2-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC045C2-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UC045C3-IEDR', 'Use Case 045 - Contact', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1112), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045c3@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R2-IEDR', 'A', NULL, 'NO', 'UC045R2-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC045C3-IEDR', 'Use Case 045 - Contact', '1112', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045c3@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R2-IEDR', 'A', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C3-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C3-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'UC045R2-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC045C3-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC045C3-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UC045C4-IEDR', 'Use Case 045 - Contact', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1112), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045c4@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R2-IEDR', 'A', NULL, 'NO', 'UC045R2-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC045C4-IEDR', 'Use Case 045 - Contact', '1112', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045c4@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R2-IEDR', 'A', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C4-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C4-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'UC045R2-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC045C4-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC045C4-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UC045C5-IEDR', 'Use Case 045 - Contact', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1111), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045c5@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R1-IEDR', 'A', NULL, 'NO', 'UC045R1-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC045C5-IEDR', 'Use Case 045 - Contact', '1111', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045c5@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R1-IEDR', 'A', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C5-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C5-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'UC045R1-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC045C5-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC045C5-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UC045C6-IEDR', 'Use Case 045 - Contact', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1113), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045c6@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R3-IEDR', 'A', NULL, 'NO', 'UC045R3-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC045C6-IEDR', 'Use Case 045 - Contact', '1113', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045c6@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC045R3-IEDR', 'A', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C6-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C6-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'UC045R3-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC045C6-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC045C6-IEDR'));

INSERT INTO Reseller_Defaults VALUES ('UC045R1-IEDR', 'UC045R1-IEDR', NULL, 'pdf');
INSERT INTO ResellerDefaultNameservers VALUES ('UC045R1-IEDR', 'dns.ns1.ie', 0, NOW());
INSERT INTO ResellerDefaultNameservers VALUES ('UC045R1-IEDR', 'dns.ns2.ie', 1, NOW());

INSERT INTO Reseller_Defaults VALUES ('UC045R3-IEDR', 'UC045R3-IEDR', NULL, 'pdf');
INSERT INTO ResellerDefaultNameservers VALUES ('UC045R3-IEDR', 'dns.ns1.ie', 0, NOW());
INSERT INTO ResellerDefaultNameservers VALUES ('UC045R3-IEDR', 'dns.ns2.ie', 1, NOW());

INSERT INTO DomainHist VALUES (NULL, 'uc045-sc01a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1111),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC045R1-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc045-sc01a.ie', 'Test Holder 0001', 3, 2, NULL, 1111,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01a.ie'), 'uc045-sc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C1-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01a.ie'), 'uc045-sc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R1-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01a.ie'), 'uc045-sc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R1-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01a.ie'), 'uc045-sc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R1-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc045-sc01a.ie', 'UC045C1-IEDR', 'Admin'), ('uc045-sc01a.ie', 'UC045R1-IEDR', 'Billing'), ('uc045-sc01a.ie', 'UC045R1-IEDR', 'Creator'), ('uc045-sc01a.ie', 'UC045C1-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01a.ie'), 'uc045-sc01a.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01a.ie'), 'uc045-sc01a.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01a.ie'), 'uc045-sc01a.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc045-sc01a.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc045-sc01a.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc045-sc01a.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc045-sc01b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1111),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC045R1-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc045-sc01b.ie', 'Test Holder 0001', 3, 2, NULL, 1111,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01b.ie'), 'uc045-sc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C1-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01b.ie'), 'uc045-sc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R1-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01b.ie'), 'uc045-sc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R1-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01b.ie'), 'uc045-sc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R1-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc045-sc01b.ie', 'UC045C1-IEDR', 'Admin'), ('uc045-sc01b.ie', 'UC045R1-IEDR', 'Billing'), ('uc045-sc01b.ie', 'UC045R1-IEDR', 'Creator'), ('uc045-sc01b.ie', 'UC045C1-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01b.ie'), 'uc045-sc01b.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01b.ie'), 'uc045-sc01b.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc01b.ie'), 'uc045-sc01b.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc045-sc01b.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc045-sc01b.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc045-sc01b.ie', 'ns3.dns.ie', NULL, NULL, '3');

-- "The system confirms that each selected domain currently has the losing customer as the bill-c [just a double-check]."
INSERT INTO DomainHist VALUES (NULL, 'uc045-sc02a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1112),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC045R2-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc045-sc02a.ie', 'Test Holder 0001', 3, 2, NULL, 1112,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02a.ie'), 'uc045-sc02a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C2-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02a.ie'), 'uc045-sc02a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02a.ie'), 'uc045-sc02a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02a.ie'), 'uc045-sc02a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc045-sc02a.ie', 'UC045C2-IEDR', 'Admin'), ('uc045-sc02a.ie', 'UC045C2-IEDR', 'Billing'), ('uc045-sc02a.ie', 'UC045R2-IEDR', 'Creator'), ('uc045-sc02a.ie', 'UC045C2-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02a.ie'), 'uc045-sc02a.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02a.ie'), 'uc045-sc02a.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02a.ie'), 'uc045-sc02a.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc045-sc02a.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc045-sc02a.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc045-sc02a.ie', 'ns3.dns.ie', NULL, NULL, '3');

-- "The system confirms that each admin-c associated with each domain selected for transfer is not associated with any domain not selected for transfer."
INSERT INTO DomainHist VALUES (NULL, 'uc045-sc02b1.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1112),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC045R2-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc045-sc02b1.ie', 'Test Holder 0001', 3, 2, NULL, 1112,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b1.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b1.ie'), 'uc045-sc02b1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C2-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b1.ie'), 'uc045-sc02b1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b1.ie'), 'uc045-sc02b1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b1.ie'), 'uc045-sc02b1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc045-sc02b1.ie', 'UC045C2-IEDR', 'Admin'), ('uc045-sc02b1.ie', 'UC045R2-IEDR', 'Billing'), ('uc045-sc02b1.ie', 'UC045R2-IEDR', 'Creator'), ('uc045-sc02b1.ie', 'UC045C2-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b1.ie'), 'uc045-sc02b1.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b1.ie'), 'uc045-sc02b1.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b1.ie'), 'uc045-sc02b1.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc045-sc02b1.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc045-sc02b1.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc045-sc02b1.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc045-sc02b2.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1112),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC045R2-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc045-sc02b2.ie', 'Test Holder 0001', 3, 2, NULL, 1112,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b2.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b2.ie'), 'uc045-sc02b2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C2-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b2.ie'), 'uc045-sc02b2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b2.ie'), 'uc045-sc02b2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b2.ie'), 'uc045-sc02b2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc045-sc02b2.ie', 'UC045C2-IEDR', 'Admin'), ('uc045-sc02b2.ie', 'UC045C2-IEDR', 'Billing'), ('uc045-sc02b2.ie', 'UC045C2-IEDR', 'Creator'), ('uc045-sc02b2.ie', 'UC045C2-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b2.ie'), 'uc045-sc02b2.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b2.ie'), 'uc045-sc02b2.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02b2.ie'), 'uc045-sc02b2.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc045-sc02b2.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc045-sc02b2.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc045-sc02b2.ie', 'ns3.dns.ie', NULL, NULL, '3');

-- "The system confirms that each selected domain's current admin-c is associated with the same Account number - that of the losing customer. Some selected domains are at issue in this regard."
INSERT INTO DomainHist VALUES (NULL, 'uc045-sc02c.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1112),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC045R2-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc045-sc02c.ie', 'Test Holder 0001', 3, 2, NULL, 1112,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02c.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02c.ie'), 'uc045-sc02c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C2-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02c.ie'), 'uc045-sc02c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02c.ie'), 'uc045-sc02c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02c.ie'), 'uc045-sc02c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc045-sc02c.ie', 'UC045C5-IEDR', 'Admin'), ('uc045-sc02c.ie', 'UC045R2-IEDR', 'Billing'), ('uc045-sc02c.ie', 'UC045R2-IEDR', 'Creator'), ('uc045-sc02c.ie', 'UC045C2-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02c.ie'), 'uc045-sc02c.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02c.ie'), 'uc045-sc02c.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02c.ie'), 'uc045-sc02c.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc045-sc02c.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc045-sc02c.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc045-sc02c.ie', 'ns3.dns.ie', NULL, NULL, '3');

-- "The system confirms that each domain's current admin-c is not associated with any domain which is not also associated with the losing customer's account. Some selected domains are at issue in this regard."
INSERT INTO DomainHist VALUES (NULL, 'uc045-sc02d1.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1112),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC045R2-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc045-sc02d1.ie', 'Test Holder 0001', 3, 2, NULL, 1112,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d1.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d1.ie'), 'uc045-sc02d1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C2-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d1.ie'), 'uc045-sc02d1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d1.ie'), 'uc045-sc02d1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d1.ie'), 'uc045-sc02d1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc045-sc02d1.ie', 'UC045C3-IEDR', 'Admin'), ('uc045-sc02d1.ie', 'UC045R2-IEDR', 'Billing'), ('uc045-sc02d1.ie', 'UC045R2-IEDR', 'Creator'), ('uc045-sc02d1.ie', 'UC045C3-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d1.ie'), 'uc045-sc02d1.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d1.ie'), 'uc045-sc02d1.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d1.ie'), 'uc045-sc02d1.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc045-sc02d1.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc045-sc02d1.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc045-sc02d1.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc045-sc02d2.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1113),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC045R3-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc045-sc02d2.ie', 'Test Holder 0001', 3, 2, NULL, 1113,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d2.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d2.ie'), 'uc045-sc02d2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C3-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d2.ie'), 'uc045-sc02d2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R3-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d2.ie'), 'uc045-sc02d2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R3-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d2.ie'), 'uc045-sc02d2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R3-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc045-sc02d2.ie', 'UC045C3-IEDR', 'Admin'), ('uc045-sc02d2.ie', 'UC045R3-IEDR', 'Billing'), ('uc045-sc02d2.ie', 'UC045R3-IEDR', 'Creator'), ('uc045-sc02d2.ie', 'UC045C3-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d2.ie'), 'uc045-sc02d2.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d2.ie'), 'uc045-sc02d2.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02d2.ie'), 'uc045-sc02d2.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc045-sc02d2.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc045-sc02d2.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc045-sc02d2.ie', 'ns3.dns.ie', NULL, NULL, '3');

-- "The system confirms that, for each domain, there is no unsettled transaction in the system, for any transaction type. Some selected domains are at issue in this regard."
INSERT INTO DomainHist VALUES (NULL, 'uc045-sc02e.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1112),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC045R2-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc045-sc02e.ie', 'Test Holder 0001', 3, 2, NULL, 1112,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02e.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02e.ie'), 'uc045-sc02e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C2-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02e.ie'), 'uc045-sc02e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02e.ie'), 'uc045-sc02e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02e.ie'), 'uc045-sc02e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc045-sc02e.ie', 'UC045C4-IEDR', 'Admin'), ('uc045-sc02e.ie', 'UC045R2-IEDR', 'Billing'), ('uc045-sc02e.ie', 'UC045R2-IEDR', 'Creator'), ('uc045-sc02e.ie', 'UC045C4-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02e.ie'), 'uc045-sc02e.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02e.ie'), 'uc045-sc02e.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02e.ie'), 'uc045-sc02e.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc045-sc02e.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc045-sc02e.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc045-sc02e.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO TransactionIndex VALUES (NULL);
INSERT INTO Transaction VALUES ((SELECT MAX(id) FROM TransactionIndex), 'NO', 'NO', '14400', 'NO', NULL, NOW(), NULL, '12000', '2400', '20130615121643-C-3505172', 'NO', NULL, NULL, '111723', '13686130436804');
INSERT INTO ReservationIndex VALUES (NULL);
INSERT INTO Reservation VALUES ((SELECT MAX(id) FROM ReservationIndex), 'UC045R2-IEDR', 'uc045-sc02e.ie', 12, NOW(), 120.00, 2, 24.00, 'YES', 'NO', NULL, NULL, (SELECT ID FROM Transaction WHERE Order_ID = '20130615121643-C-3505172'), (SELECT Id FROM Product WHERE P_Active = 'YES' AND CURDATE() BETWEEN P_Valid_From_Dt AND P_Valid_To_Dt AND P_Reg = 'YES' and P_Guest = 'YES' AND P_Duration = 1), 'renewal', NULL, NULL, 'Credit Card');

-- "The system confirms that no domain has an NRP status of XPA, XPI, XPV. Some selected domains are at issue in this regard."
INSERT INTO DomainHist VALUES (NULL, 'uc045-sc02f3.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1112),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC045R2-IEDR', NOW(), 'NO', '390', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc045-sc02f3.ie', 'Test Holder 0001', 3, 2, NULL, 1112,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '390', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f3.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f3.ie'), 'uc045-sc02f3.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C2-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f3.ie'), 'uc045-sc02f3.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f3.ie'), 'uc045-sc02f3.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f3.ie'), 'uc045-sc02f3.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc045-sc02f3.ie', 'UC045C4-IEDR', 'Admin'), ('uc045-sc02f3.ie', 'UC045R2-IEDR', 'Billing'), ('uc045-sc02f3.ie', 'UC045R2-IEDR', 'Creator'), ('uc045-sc02f3.ie', 'UC045C4-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f3.ie'), 'uc045-sc02f3.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f3.ie'), 'uc045-sc02f3.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f3.ie'), 'uc045-sc02f3.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc045-sc02f3.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc045-sc02f3.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc045-sc02f3.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc045-sc02f4.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1112),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC045R2-IEDR', NOW(), 'NO', '438', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc045-sc02f4.ie', 'Test Holder 0001', 3, 2, NULL, 1112,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '438', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f4.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f4.ie'), 'uc045-sc02f4.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C2-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f4.ie'), 'uc045-sc02f4.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f4.ie'), 'uc045-sc02f4.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f4.ie'), 'uc045-sc02f4.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc045-sc02f4.ie', 'UC045C4-IEDR', 'Admin'), ('uc045-sc02f4.ie', 'UC045R2-IEDR', 'Billing'), ('uc045-sc02f4.ie', 'UC045R2-IEDR', 'Creator'), ('uc045-sc02f4.ie', 'UC045C4-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f4.ie'), 'uc045-sc02f4.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f4.ie'), 'uc045-sc02f4.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f4.ie'), 'uc045-sc02f4.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc045-sc02f4.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc045-sc02f4.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc045-sc02f4.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc045-sc02f5.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1112),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC045R2-IEDR', NOW(), 'NO', '486', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc045-sc02f5.ie', 'Test Holder 0001', 3, 2, NULL, 1112,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '486', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f5.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f5.ie'), 'uc045-sc02f5.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C2-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f5.ie'), 'uc045-sc02f5.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f5.ie'), 'uc045-sc02f5.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f5.ie'), 'uc045-sc02f5.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc045-sc02f5.ie', 'UC045C4-IEDR', 'Admin'), ('uc045-sc02f5.ie', 'UC045R2-IEDR', 'Billing'), ('uc045-sc02f5.ie', 'UC045R2-IEDR', 'Creator'), ('uc045-sc02f5.ie', 'UC045C4-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f5.ie'), 'uc045-sc02f5.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f5.ie'), 'uc045-sc02f5.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02f5.ie'), 'uc045-sc02f5.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc045-sc02f5.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc045-sc02f5.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc045-sc02f5.ie', 'ns3.dns.ie', NULL, NULL, '3');

-- "The system confirms that no domain is locked. Some selected domains are at issue in this regard."
INSERT INTO DomainHist VALUES (NULL, 'uc045-sc02g.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1112),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC045R2-IEDR', NOW(), 'NO', '1', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month);
INSERT INTO Domain VALUES ('uc045-sc02g.ie', 'Test Holder 0001', 3, 2, NULL, 1112,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '1', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02g.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02g.ie'), 'uc045-sc02g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C2-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02g.ie'), 'uc045-sc02g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02g.ie'), 'uc045-sc02g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02g.ie'), 'uc045-sc02g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R2-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc045-sc02g.ie', 'UC045C4-IEDR', 'Admin'), ('uc045-sc02g.ie', 'UC045R2-IEDR', 'Billing'), ('uc045-sc02g.ie', 'UC045R2-IEDR', 'Creator'), ('uc045-sc02g.ie', 'UC045C4-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02g.ie'), 'uc045-sc02g.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02g.ie'), 'uc045-sc02g.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-sc02g.ie'), 'uc045-sc02g.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc045-sc02g.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc045-sc02g.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc045-sc02g.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc045-nosc01a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1113),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC045R3-IEDR', NOW(), 'NO', '18', CURDATE() + Interval 6 Month, CURDATE() + Interval 6 Month, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc045-nosc01a.ie', 'Test Holder 0001', 3, 2, NULL, 1113,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '18', CURDATE() + Interval 6 Month, CURDATE() + Interval 6 Month, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01a.ie'), 'uc045-nosc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C3-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01a.ie'), 'uc045-nosc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R3-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01a.ie'), 'uc045-nosc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R3-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01a.ie'), 'uc045-nosc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R3-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc045-nosc01a.ie', 'UC045C6-IEDR', 'Admin'), ('uc045-nosc01a.ie', 'UC045R3-IEDR', 'Billing'), ('uc045-nosc01a.ie', 'UC045R3-IEDR', 'Creator'), ('uc045-nosc01a.ie', 'UC045C6-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01a.ie'), 'uc045-nosc01a.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01a.ie'), 'uc045-nosc01a.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01a.ie'), 'uc045-nosc01a.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc045-nosc01a.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc045-nosc01a.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc045-nosc01a.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc045-nosc01b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1113),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC045R3-IEDR', NOW(), 'NO', '18', CURDATE() + Interval 6 Month, CURDATE() + Interval 6 Month, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc045-nosc01b.ie', 'Test Holder 0001', 3, 2, NULL, 1113,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '18', CURDATE() + Interval 6 Month, CURDATE() + Interval 6 Month, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01b.ie'), 'uc045-nosc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045C3-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01b.ie'), 'uc045-nosc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R3-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01b.ie'), 'uc045-nosc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R3-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01b.ie'), 'uc045-nosc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC045R3-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc045-nosc01b.ie', 'UC045C6-IEDR', 'Admin'), ('uc045-nosc01b.ie', 'UC045R3-IEDR', 'Billing'), ('uc045-nosc01b.ie', 'UC045R3-IEDR', 'Creator'), ('uc045-nosc01b.ie', 'UC045C6-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01b.ie'), 'uc045-nosc01b.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01b.ie'), 'uc045-nosc01b.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc045-nosc01b.ie'), 'uc045-nosc01b.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc045-nosc01b.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc045-nosc01b.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc045-nosc01b.ie', 'ns3.dns.ie', NULL, NULL, '3');

