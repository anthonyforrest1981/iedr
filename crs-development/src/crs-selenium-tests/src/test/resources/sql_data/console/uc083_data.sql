INSERT INTO NicHandleHist VALUES (NULL, 'UC083AA-IEDR', 'Use Case 083 - Registrar', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc083_aa@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC083AA-IEDR', 'B', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC083AA-IEDR', 'Use Case 083 - Registrar', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc083_aa@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC083AA-IEDR', 'B', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC083AA-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC083AA-IEDR'));
INSERT INTO TelecomHist VALUES
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'UC083AA-IEDR', '123123123', 'Phone', 0),
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'UC083AA-IEDR', '123123123', 'Fax', 0);
INSERT INTO Telecom VALUES ('UC083AA-IEDR', '123123123', 'Phone', 0), ('UC083AA-IEDR', '123123123', 'Fax', 0);

INSERT INTO NicHandleHist VALUES (NULL, 'UC083AB-IEDR', 'Use Case 083 - Registrar', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc083_ab@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC083AA-IEDR', 'B', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC083AB-IEDR', 'Use Case 083 - Registrar', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc083_ab@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC083AB-IEDR', 'B', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AB-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AB-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC083AB-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC083AB-IEDR'));
INSERT INTO TelecomHist VALUES
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AB-IEDR'), 'UC083AB-IEDR', '123123123', 'Phone', 0),
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AB-IEDR'), 'UC083AB-IEDR', '123123123', 'Fax', 0);
INSERT INTO Telecom VALUES ('UC083AB-IEDR', '123123123', 'Phone', 0), ('UC083AB-IEDR', '123123123', 'Fax', 0);

INSERT INTO Account VALUES (NULL, 'UC083AA', 'UC083AA-IEDR', 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, 1);
INSERT INTO AccountHist VALUES (NULL, (SELECT A_Number FROM Account WHERE A_Name = 'UC083AA'), 'UC083AA', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, NOW(), 'UC083AA-IEDR');
UPDATE Account A SET Chng_Id = (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = A.A_Number) WHERE A.A_Name = 'UC083AA';
UPDATE NicHandle SET A_Number = (SELECT A_Number FROM Account WHERE A_Name = 'UC083AA') WHERE Nic_Handle IN ('UC083AA-IEDR', 'UC083AB-IEDR');
UPDATE NicHandleHist SET Account_Chng_ID = (SELECT Chng_ID FROM Account WHERE A_Name = 'UC083AA') WHERE Nic_Handle IN ('UC083AA-IEDR', 'UC083AB-IEDR');

INSERT INTO DepositHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), NOW(), 2000, 2000, '20130109164738-C-1205021', '2000', 'INIT', NULL, NULL, NOW());
INSERT INTO Deposit VALUES ('UC083AA-IEDR', NOW(), 2000, 2000, '20130109164738-C-1205021', '2000', 'INIT', NULL, NULL, (SELECT MAX(D.Chng_Id) FROM DepositHist D JOIN NicHandleHist NH  ON D.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC083AA-IEDR'));
INSERT INTO DepositHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AB-IEDR'), NOW(), 2000, 2000, '20130109164738-C-1205021', '2000', 'INIT', NULL, NULL, NOW());
INSERT INTO Deposit VALUES ('UC083AB-IEDR', NOW(), 2000, 2000, '20130109164738-C-1205021', '2000', 'INIT', NULL, NULL, (SELECT MAX(D.Chng_Id) FROM DepositHist D JOIN NicHandleHist NH  ON D.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC083AB-IEDR'));
INSERT INTO Reseller_Defaults VALUES ('UC083AA-IEDR', 'UC083AA-IEDR', NULL, 'pdf');
INSERT INTO Reseller_Defaults VALUES ('UC083AB-IEDR', 'UC083AA-IEDR', NULL, 'pdf');
INSERT INTO ResellerDefaultNameservers VALUES ('UC083AA-IEDR', 'dns.ns1.ie', 0, NOW()), ('UC083AA-IEDR', 'dns.ns2.ie', 1, NOW());
INSERT INTO ResellerDefaultNameservers VALUES ('UC083AB-IEDR', 'dns.ns1.ie', 0, NOW()), ('UC083AB-IEDR', 'dns.ns2.ie', 1, NOW());

INSERT INTO DomainHist VALUES (NULL, 'uc083-transfer.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc083-transfer.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-transfer.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-transfer.ie'), 'uc083-transfer.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-transfer.ie'), 'uc083-transfer.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-transfer.ie'), 'uc083-transfer.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-transfer.ie'), 'uc083-transfer.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc083-transfer.ie', 'XOE550-IEDR', 'Admin'), ('uc083-transfer.ie', 'XBC189-IEDR', 'Billing'), ('uc083-transfer.ie', 'XBC189-IEDR', 'Creator'), ('uc083-transfer.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-transfer.ie'), 'uc083-transfer.ie', 'ns1.uc083-transfer.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-transfer.ie'), 'uc083-transfer.ie', 'ns2.uc083-transfer.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-transfer.ie'), 'uc083-transfer.ie', 'ns3.uc083-transfer.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc083-transfer.ie', 'ns1.uc083-transfer.ie', '10.10.121.23', NULL, '1'), ('uc083-transfer.ie', 'ns2.uc083-transfer.ie', '1.3.4.2', NULL, '2'), ('uc083-transfer.ie', 'ns3.uc083-transfer.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc083-modify.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_ID) FROM AccountHist WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC083AA-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc083-modify.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT A_Number FROM Account WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify.ie'), 'uc083-modify.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify.ie'), 'uc083-modify.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify.ie'), 'uc083-modify.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify.ie'), 'uc083-modify.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc083-modify.ie', 'UC083AA-IEDR', 'Admin'), ('uc083-modify.ie', 'UC083AA-IEDR', 'Billing'), ('uc083-modify.ie', 'UC083AA-IEDR', 'Creator'), ('uc083-modify.ie', 'UC083AA-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify.ie'), 'uc083-modify.ie', 'ns1.uc083-modify.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify.ie'), 'uc083-modify.ie', 'ns2.uc083-modify.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify.ie'), 'uc083-modify.ie', 'ns3.uc083-modify.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc083-modify.ie', 'ns1.uc083-modify.ie', '10.10.121.23', NULL, '1'), ('uc083-modify.ie', 'ns2.uc083-modify.ie', '1.3.4.2', NULL, '2'), ('uc083-modify.ie', 'ns3.uc083-modify.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc083-modify-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_ID) FROM AccountHist WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC083AA-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc083-modify-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT A_Number FROM Account WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-a.ie'), 'uc083-modify-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-a.ie'), 'uc083-modify-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-a.ie'), 'uc083-modify-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-a.ie'), 'uc083-modify-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc083-modify-a.ie', 'UC083AA-IEDR', 'Admin'), ('uc083-modify-a.ie', 'UC083AB-IEDR', 'Billing'), ('uc083-modify-a.ie', 'UC083AB-IEDR', 'Creator'), ('uc083-modify-a.ie', 'UC083AB-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-a.ie'), 'uc083-modify-a.ie', 'ns1.uc083-modify-a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-a.ie'), 'uc083-modify-a.ie', 'ns2.uc083-modify-a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-a.ie'), 'uc083-modify-a.ie', 'ns3.uc083-modify-a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc083-modify-a.ie', 'ns1.uc083-modify-a.ie', '10.10.121.23', NULL, '1'), ('uc083-modify-a.ie', 'ns2.uc083-modify-a.ie', '1.3.4.2', NULL, '2'), ('uc083-modify-a.ie', 'ns3.uc083-modify-a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc083-modify-b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_ID) FROM AccountHist WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC083AA-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc083-modify-b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT A_Number FROM Account WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-b.ie'), 'uc083-modify-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-b.ie'), 'uc083-modify-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-b.ie'), 'uc083-modify-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-b.ie'), 'uc083-modify-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc083-modify-b.ie', 'UC083AB-IEDR', 'Admin'), ('uc083-modify-b.ie', 'UC083AA-IEDR', 'Billing'), ('uc083-modify-b.ie', 'UC083AA-IEDR', 'Creator'), ('uc083-modify-b.ie', 'UC083AB-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-b.ie'), 'uc083-modify-b.ie', 'ns1.uc083-modify-b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-b.ie'), 'uc083-modify-b.ie', 'ns2.uc083-modify-b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-modify-b.ie'), 'uc083-modify-b.ie', 'ns3.uc083-modify-b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc083-modify-b.ie', 'ns1.uc083-modify-b.ie', '10.10.121.23', NULL, '1'), ('uc083-modify-b.ie', 'ns2.uc083-modify-b.ie', '1.3.4.2', NULL, '2'), ('uc083-modify-b.ie', 'ns3.uc083-modify-b.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc083-renewals.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_ID) FROM AccountHist WHERE A_Name = 'UC083AA'), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'UC083AA-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc083-renewals.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT A_Number FROM Account WHERE A_Name = 'UC083AA'),CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals.ie'), 'uc083-renewals.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals.ie'), 'uc083-renewals.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals.ie'), 'uc083-renewals.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals.ie'), 'uc083-renewals.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc083-renewals.ie', 'UC083AA-IEDR', 'Admin'), ('uc083-renewals.ie', 'UC083AA-IEDR', 'Billing'), ('uc083-renewals.ie', 'UC083AA-IEDR', 'Creator'), ('uc083-renewals.ie', 'UC083AA-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals.ie'), 'uc083-renewals.ie', 'ns1.uc083-renewals.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals.ie'), 'uc083-renewals.ie', 'ns2.uc083-renewals.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals.ie'), 'uc083-renewals.ie', 'ns3.uc083-renewals.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc083-renewals.ie', 'ns1.uc083-renewals.ie', '10.10.121.23', NULL, '1'), ('uc083-renewals.ie', 'ns2.uc083-renewals.ie', '1.3.4.2', NULL, '2'), ('uc083-renewals.ie', 'ns3.uc083-renewals.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc083-renewals-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_ID) FROM AccountHist WHERE A_Name = 'UC083AA'), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'UC083AA-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc083-renewals-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT A_Number FROM Account WHERE A_Name = 'UC083AA'),CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals-a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals-a.ie'), 'uc083-renewals-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals-a.ie'), 'uc083-renewals-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals-a.ie'), 'uc083-renewals-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals-a.ie'), 'uc083-renewals-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc083-renewals-a.ie', 'UC083AB-IEDR', 'Admin'), ('uc083-renewals-a.ie', 'UC083AA-IEDR', 'Billing'), ('uc083-renewals-a.ie', 'UC083AA-IEDR', 'Creator'), ('uc083-renewals-a.ie', 'UC083AB-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals-a.ie'), 'uc083-renewals-a.ie', 'ns1.uc083-renewals-a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals-a.ie'), 'uc083-renewals-a.ie', 'ns2.uc083-renewals-a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-renewals-a.ie'), 'uc083-renewals-a.ie', 'ns3.uc083-renewals-a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc083-renewals-a.ie', 'ns1.uc083-renewals-a.ie', '10.10.121.23', NULL, '1'), ('uc083-renewals-a.ie', 'ns2.uc083-renewals-a.ie', '1.3.4.2', NULL, '2'), ('uc083-renewals-a.ie', 'ns3.uc083-renewals-a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc083-eid15.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_ID) FROM AccountHist WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC083AA-IEDR', NOW(), 'NO', '20', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc083-eid15.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT A_Number FROM Account WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '20', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid15.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid15.ie'), 'uc083-eid15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid15.ie'), 'uc083-eid15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid15.ie'), 'uc083-eid15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid15.ie'), 'uc083-eid15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc083-eid15.ie', 'UC083AB-IEDR', 'Admin'), ('uc083-eid15.ie', 'UC083AA-IEDR', 'Billing'), ('uc083-eid15.ie', 'UC083AA-IEDR', 'Creator'), ('uc083-eid15.ie', 'UC083AB-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid15.ie'), 'uc083-eid15.ie', 'ns1.uc083-eid15.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid15.ie'), 'uc083-eid15.ie', 'ns2.uc083-eid15.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid15.ie'), 'uc083-eid15.ie', 'ns3.uc083-eid15.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc083-eid15.ie', 'ns1.uc083-eid15.ie', '10.10.121.23', NULL, '1'), ('uc083-eid15.ie', 'ns2.uc083-eid15.ie', '1.3.4.2', NULL, '2'), ('uc083-eid15.ie', 'ns3.uc083-eid15.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc083-eid13.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_ID) FROM AccountHist WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC083AA-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc083-eid13.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT A_Number FROM Account WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid13.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid13.ie'), 'uc083-eid13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid13.ie'), 'uc083-eid13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid13.ie'), 'uc083-eid13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid13.ie'), 'uc083-eid13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc083-eid13.ie', 'UC083AA-IEDR', 'Admin'), ('uc083-eid13.ie', 'UC083AA-IEDR', 'Billing'), ('uc083-eid13.ie', 'UC083AA-IEDR', 'Creator'), ('uc083-eid13.ie', 'UC083AA-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid13.ie'), 'uc083-eid13.ie', 'ns1.uc083-eid13.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid13.ie'), 'uc083-eid13.ie', 'ns2.uc083-eid13.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid13.ie'), 'uc083-eid13.ie', 'ns3.uc083-eid13.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc083-eid13.ie', 'ns1.uc083-eid13.ie', '10.10.121.23', NULL, '1'), ('uc083-eid13.ie', 'ns2.uc083-eid13.ie', '1.3.4.2', NULL, '2'), ('uc083-eid13.ie', 'ns3.uc083-eid13.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc083-eid64.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_ID) FROM AccountHist WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC083AA-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc083-eid64.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT A_Number FROM Account WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid64.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid64.ie'), 'uc083-eid64.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid64.ie'), 'uc083-eid64.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid64.ie'), 'uc083-eid64.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid64.ie'), 'uc083-eid64.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc083-eid64.ie', 'XOE550-IEDR', 'Admin'), ('uc083-eid64.ie', 'UC083AA-IEDR', 'Billing'), ('uc083-eid64.ie', 'UC083AA-IEDR', 'Creator'), ('uc083-eid64.ie', 'UC083AB-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid64.ie'), 'uc083-eid64.ie', 'ns1.uc083-eid64.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid64.ie'), 'uc083-eid64.ie', 'ns2.uc083-eid64.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid64.ie'), 'uc083-eid64.ie', 'ns3.uc083-eid64.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc083-eid64.ie', 'ns1.uc083-eid64.ie', '10.10.121.23', NULL, '1'), ('uc083-eid64.ie', 'ns2.uc083-eid64.ie', '1.3.4.2', NULL, '2'), ('uc083-eid64.ie', 'ns3.uc083-eid64.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc083-eid11.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_ID) FROM AccountHist WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'UC083AA-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc083-eid11.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT A_Number FROM Account WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid11.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid11.ie'), 'uc083-eid11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid11.ie'), 'uc083-eid11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid11.ie'), 'uc083-eid11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid11.ie'), 'uc083-eid11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc083-eid11.ie', 'UC083AA-IEDR', 'Admin'), ('uc083-eid11.ie', 'UC083AA-IEDR', 'Billing'), ('uc083-eid11.ie', 'UC083AA-IEDR', 'Creator'), ('uc083-eid11.ie', 'UC083AA-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid11.ie'), 'uc083-eid11.ie', 'ns1.uc083-eid11.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid11.ie'), 'uc083-eid11.ie', 'ns2.uc083-eid11.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid11.ie'), 'uc083-eid11.ie', 'ns3.uc083-eid11.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc083-eid11.ie', 'ns1.uc083-eid11.ie', '10.10.121.23', NULL, '1'), ('uc083-eid11.ie', 'ns2.uc083-eid11.ie', '1.3.4.2', NULL, '2'), ('uc083-eid11.ie', 'ns3.uc083-eid11.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc083-eid16.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_ID) FROM AccountHist WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'UC083AA-IEDR', NOW(), 'NO', '20', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc083-eid16.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT A_Number FROM Account WHERE A_Name = 'UC083AA'), CURDATE() - Interval 6 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'NO', '20', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid16.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid16.ie'), 'uc083-eid16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid16.ie'), 'uc083-eid16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid16.ie'), 'uc083-eid16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid16.ie'), 'uc083-eid16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc083-eid16.ie', 'UC083AA-IEDR', 'Admin'), ('uc083-eid16.ie', 'UC083AA-IEDR', 'Billing'), ('uc083-eid16.ie', 'UC083AA-IEDR', 'Creator'), ('uc083-eid16.ie', 'UC083AA-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid16.ie'), 'uc083-eid16.ie', 'ns1.uc083-eid16.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid16.ie'), 'uc083-eid16.ie', 'ns2.uc083-eid16.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid16.ie'), 'uc083-eid16.ie', 'ns3.uc083-eid16.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc083-eid16.ie', 'ns1.uc083-eid16.ie', '10.10.121.23', NULL, '1'), ('uc083-eid16.ie', 'ns2.uc083-eid16.ie', '1.3.4.2', NULL, '2'), ('uc083-eid16.ie', 'ns3.uc083-eid16.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc083-eid47.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_ID) FROM AccountHist WHERE A_Name = 'UC083AA'), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'UC083AA-IEDR', NOW(), 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc083-eid47.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT A_Number FROM Account WHERE A_Name = 'UC083AA'),CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid47.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid47.ie'), 'uc083-eid47.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid47.ie'), 'uc083-eid47.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid47.ie'), 'uc083-eid47.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid47.ie'), 'uc083-eid47.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc083-eid47.ie', 'UC083AA-IEDR', 'Admin'), ('uc083-eid47.ie', 'UC083AA-IEDR', 'Billing'), ('uc083-eid47.ie', 'UC083AA-IEDR', 'Creator'), ('uc083-eid47.ie', 'UC083AA-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid47.ie'), 'uc083-eid47.ie', 'ns1.uc083-eid47.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid47.ie'), 'uc083-eid47.ie', 'ns2.uc083-eid47.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid47.ie'), 'uc083-eid47.ie', 'ns3.uc083-eid47.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc083-eid47.ie', 'ns1.uc083-eid47.ie', '10.10.121.23', NULL, '1'), ('uc083-eid47.ie', 'ns2.uc083-eid47.ie', '1.3.4.2', NULL, '2'), ('uc083-eid47.ie', 'ns3.uc083-eid47.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc083-eid91.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_ID) FROM AccountHist WHERE A_Name = 'UC083AA'), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'UC083AA-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc083-eid91.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT A_Number FROM Account WHERE A_Name = 'UC083AA'),CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid91.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid91.ie'), 'uc083-eid91.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid91.ie'), 'uc083-eid91.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid91.ie'), 'uc083-eid91.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid91.ie'), 'uc083-eid91.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc083-eid91.ie', 'UC083AA-IEDR', 'Admin'), ('uc083-eid91.ie', 'UC083AA-IEDR', 'Billing'), ('uc083-eid91.ie', 'UC083AA-IEDR', 'Creator'), ('uc083-eid91.ie', 'UC083AA-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid91.ie'), 'uc083-eid91.ie', 'ns1.uc083-eid91.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid91.ie'), 'uc083-eid91.ie', 'ns2.uc083-eid91.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid91.ie'), 'uc083-eid91.ie', 'ns3.uc083-eid91.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc083-eid91.ie', 'ns1.uc083-eid91.ie', '10.10.121.23', NULL, '1'), ('uc083-eid91.ie', 'ns2.uc083-eid91.ie', '1.3.4.2', NULL, '2'), ('uc083-eid91.ie', 'ns3.uc083-eid91.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc083-eid92.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_ID) FROM AccountHist WHERE A_Name = 'UC083AA'), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'UC083AA-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc083-eid92.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT A_Number FROM Account WHERE A_Name = 'UC083AA'),CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid92.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid92.ie'), 'uc083-eid92.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid92.ie'), 'uc083-eid92.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid92.ie'), 'uc083-eid92.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid92.ie'), 'uc083-eid92.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC083AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc083-eid92.ie', 'UC083AA-IEDR', 'Admin'), ('uc083-eid92.ie', 'UC083AA-IEDR', 'Billing'), ('uc083-eid92.ie', 'UC083AA-IEDR', 'Creator'), ('uc083-eid92.ie', 'UC083AA-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid92.ie'), 'uc083-eid92.ie', 'ns1.uc083-eid92.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid92.ie'), 'uc083-eid92.ie', 'ns2.uc083-eid92.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc083-eid92.ie'), 'uc083-eid92.ie', 'ns3.uc083-eid92.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc083-eid92.ie', 'ns1.uc083-eid92.ie', '10.10.121.23', NULL, '1'), ('uc083-eid92.ie', 'ns2.uc083-eid92.ie', '1.3.4.2', NULL, '2'), ('uc083-eid92.ie', 'ns3.uc083-eid92.ie', '10.12.13.14', NULL, '3');

